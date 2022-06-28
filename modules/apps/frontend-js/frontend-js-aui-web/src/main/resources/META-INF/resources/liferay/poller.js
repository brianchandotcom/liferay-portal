/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

/**
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
AUI.add(
	'liferay-poller',
	(A) => {
		// eslint-disable-next-line @liferay/aui/no-object
		const AObject = A.Object;

		const _browserKey = Math.ceil(Math.random() * Number.MAX_SAFE_INTEGER);
		let _enabled = false;
		let _encryptedUserId = null;
		let _supportsComet = false;

		let _delayAccessCount = 0;
		let _delayIndex = 0;
		const _delays = [1, 2, 3, 4, 5, 7, 10];

		const _getEncryptedUserId = function () {
			return _encryptedUserId;
		};

		let _frozen = false;
		let _locked = false;

		const _maxDelay = _delays.length - 1;

		const _portletIdsMap = {};

		const _metaData = {
			browserKey: _browserKey,
			companyId: themeDisplay.getCompanyId(),
			portletIdsMap: _portletIdsMap,
			startPolling: true,
		};

		let _customDelay = null;
		const _portlets = {};
		let _requestDelay = _delays[0];
		const _sendQueue = [];
		let _suspended = false;
		let _timerId = null;

		let _url = themeDisplay.getPathContext() + '/poller';

		const _receiveChannel = _url + '/receive';
		const _sendChannel = _url + '/send';

		const _closeCurlyBrace = '}';
		const _openCurlyBrace = '{';

		const _escapedCloseCurlyBrace = '[$CLOSE_CURLY_BRACE$]';
		const _escapedOpenCurlyBrace = '[$OPEN_CURLY_BRACE$]';

		const Poller = {
			addListener(key, listener, scope) {
				_portlets[key] = {
					initialRequest: true,
					listener,
					scope,
				};

				if (!_enabled) {
					_enabled = true;

					_receive();
				}
			},

			cancelCustomDelay() {
				_customDelay = null;
			},

			getDelay() {
				if (_customDelay !== null) {
					_requestDelay = _customDelay;
				}
				else if (_delayIndex <= _maxDelay) {
					_requestDelay = _delays[_delayIndex];
					_delayAccessCount++;

					if (_delayAccessCount === 3) {
						_delayIndex++;
						_delayAccessCount = 0;
					}
				}

				return _requestDelay * 1000;
			},

			getReceiveUrl: _getReceiveUrl,
			getSendUrl: _getSendUrl,

			init(options) {
				const instance = this;

				instance.setEncryptedUserId(options.encryptedUserId);
				instance.setSupportsComet(options.supportsComet);
			},

			isSupportsComet() {
				return _supportsComet;
			},

			processResponse: _processResponse,

			removeListener(key) {
				if (key in _portlets) {
					delete _portlets[key];
				}

				if (!AObject.keys(_portlets).length) {
					_enabled = false;

					_cancelRequestTimer();
				}
			},

			resume() {
				_suspended = false;

				_createRequestTimer();
			},

			setCustomDelay(delay) {
				if (delay === null) {
					_customDelay = delay;
				}
				else {
					_customDelay = delay / 1000;
				}
			},

			setDelay(delay) {
				_requestDelay = delay / 1000;
			},

			setEncryptedUserId(encryptedUserId) {
				_encryptedUserId = encryptedUserId;
			},

			setSupportsComet(supportsComet) {
				_supportsComet = supportsComet;
			},

			setUrl(url) {
				_url = url;
			},

			submitRequest(key, data, chunkId) {
				if (!_frozen && key in _portlets) {
					for (const i in data) {
						if (Object.prototype.hasOwnProperty.call(data, i)) {
							let content = data[i];

							if (content.replace) {
								content = content.replace(
									_openCurlyBrace,
									_escapedOpenCurlyBrace
								);
								content = content.replace(
									_closeCurlyBrace,
									_escapedCloseCurlyBrace
								);

								data[i] = content;
							}
						}
					}

					const requestData = {
						data,
						portletId: key,
					};

					if (chunkId) {
						requestData.chunkId = chunkId;
					}

					_sendQueue.push(requestData);

					_send();
				}
			},

			suspend() {
				_cancelRequestTimer();

				_suspended = true;
			},

			url: _url,
		};

		function _cancelRequestTimer() {
			clearTimeout(_timerId);

			_timerId = null;
		}

		function _createRequestTimer() {
			_cancelRequestTimer();

			if (_enabled) {
				if (Poller.isSupportsComet()) {
					_receive();
				}
				else {
					_timerId = setTimeout(_receive, Poller.getDelay());
				}
			}
		}

		function _freezeConnection() {
			_frozen = true;

			_cancelRequestTimer();
		}

		function _getReceiveUrl() {
			return _receiveChannel;
		}

		function _getSendUrl() {
			return _sendChannel;
		}

		function _processResponse(id, object) {
			const response = JSON.parse(object.responseText);
			let send = false;

			if (Array.isArray(response)) {
				const meta = response.shift();

				for (let i = 0; i < response.length; i++) {
					const chunk = response[i].payload;

					const chunkData = chunk.data;

					const portletId = chunk.portletId;

					const portlet = _portlets[portletId];

					if (portlet) {
						const currentPortletId = _portletIdsMap[portletId];

						if (chunkData && currentPortletId) {
							chunkData.initialRequest = portlet.initialRequest;
						}

						portlet.listener.call(
							portlet.scope || Poller,
							chunkData,
							chunk.chunkId
						);

						if (chunkData && chunkData.pollerHintHighConnectivity) {
							_requestDelay = _delays[0];
							_delayIndex = 0;
						}

						if (portlet.initialRequest && currentPortletId) {
							send = true;

							portlet.initialRequest = false;
						}
					}
				}

				if ('startPolling' in _metaData) {
					delete _metaData.startPolling;
				}

				if (send) {
					_send();
				}

				if (!meta.suspendPolling) {
					_thawConnection();
				}
				else {
					_freezeConnection();
				}
			}
		}

		function _receive() {
			if (!_suspended && !_frozen) {
				_metaData.userId = _getEncryptedUserId();
				_metaData.timestamp = new Date().getTime();

				AObject.each(_portlets, _updatePortletIdsMap);

				const requestStr = JSON.stringify([_metaData]);

				const body = new URLSearchParams();
				body.append('pollerRequest', requestStr);

				Liferay.Util.fetch(_getReceiveUrl(), {
					body,
					method: 'POST',
				})
					.then((response) => {
						return response.text();
					})
					.then((responseText) => {
						_processResponse(null, {responseText});
					});
			}
		}

		function _releaseLock() {
			_locked = false;
		}

		function _send() {
			if (
				_enabled &&
				!_locked &&
				_sendQueue.length &&
				!_suspended &&
				!_frozen
			) {
				_locked = true;

				const data = _sendQueue.shift();

				_metaData.userId = _getEncryptedUserId();
				_metaData.timestamp = new Date().getTime();

				AObject.each(_portlets, _updatePortletIdsMap);

				const requestStr = JSON.stringify([_metaData].concat(data));

				const body = new URLSearchParams();
				body.append('pollerRequest', requestStr);

				Liferay.Util.fetch(_getSendUrl(), {
					body,
					method: 'POST',
				})
					.then((response) => {
						return response.text();
					})
					.then(_sendComplete);
			}
		}

		function _sendComplete() {
			_releaseLock();
			_send();
		}

		function _thawConnection() {
			_frozen = false;

			_createRequestTimer();
		}

		function _updatePortletIdsMap(item, index) {
			_portletIdsMap[index] = item.initialRequest;
		}

		A.getWin().on('focus', () => {
			_metaData.startPolling = true;

			_thawConnection();
		});

		Liferay.Poller = Poller;
	},
	'',
	{
		requires: ['aui-base', 'json'],
	}
);
