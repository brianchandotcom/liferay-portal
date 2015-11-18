AUI.add(
	'liferay-poller',
	function(A) {
		var AObject = A.Object;
		var JSON = A.JSON;
		var Util = Liferay.Util;

		var owns = AObject.owns;

		var _browserKey = Util.randomInt();
		var _enabled = false;
		var _supportsComet = false;
		var _encryptedUserId = null;

		var _delays = [1, 2, 3, 4, 5, 7, 10];
		var _delayIndex = 0;
		var _delayAccessCount = 0;

		var _getEncryptedUserId = function() {
			return _encryptedUserId;
		};

		var _frozen = false;
		var _locked = false;

		var _maxDelay = _delays.length - 1;

		var _portletIdsMap = {};

		var _metaData = {
			browserKey: _browserKey,
			companyId: themeDisplay.getCompanyId(),
			portletIdsMap: _portletIdsMap,
			startPolling: true
		};

		var _customDelay = null;
		var _portlets = {};
		var _requestDelay = _delays[0];
		var _sendQueue = [];
		var _suspended = false;
		var _timerId = null;

		var _url = themeDisplay.getPathContext() + '/poller';
		var _receiveChannel = _url + '/receive';
		var _sendChannel = _url + '/send';

		var _closeCurlyBrace = '}';
		var _escapedCloseCurlyBrace = '[$CLOSE_CURLY_BRACE$]';

		var _openCurlyBrace = '{';
		var _escapedOpenCurlyBrace = '[$OPEN_CURLY_BRACE$]';

		var _cancelRequestTimer = function() {
			clearTimeout(_timerId);

			_timerId = null;
		};

		var _createRequestTimer = function() {
			_cancelRequestTimer();

			if (_enabled) {
				if (Poller.isSupportsComet()) {
					_receive();
				}
				else {
					_timerId = setTimeout(_receive, Poller.getDelay());
				}
			}
		};

		var _freezeConnection = function() {
			_frozen = true;

			_cancelRequestTimer();
		};

		var _getReceiveUrl = function() {
			return _receiveChannel;
		};

		var _getSendUrl = function() {
			return _sendChannel;
		};

		var _processResponse = function(id, obj) {
			var response = JSON.parse(obj.responseText);
			var send = false;

			if (Util.isArray(response)) {
				var meta = response.shift();

				for (var i = 0, length = response.length; i < length; i++) {
					var chunk = response[i].payload;

					var chunkData = chunk.data;

					var portletId = chunk.portletId;

					var portlet = _portlets[portletId];

					if (portlet) {
						var currentPortletId = _portletIdsMap[portletId];

						if (chunkData && currentPortletId) {
							chunkData.initialRequest = portlet.initialRequest;
						}

						portlet.listener.call(portlet.scope || Poller, chunkData, chunk.chunkId);

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
		};

		var _receive = function() {
			if (!_suspended && !_frozen) {
				_metaData.userId = _getEncryptedUserId();
				_metaData.timestamp = (new Date()).getTime();

				AObject.each(_portlets, _updatePortletIdsMap);

				var requestStr = JSON.stringify([_metaData]);

				A.io(
					_getReceiveUrl(),
					{
						data: {
							pollerRequest: requestStr
						},
						method: A.config.io.method,
						on: {
							success: _processResponse
						}
					}
				);
			}
		};

		var _releaseLock = function() {
			_locked = false;
		};

		var _sendComplete = function() {
			_releaseLock();
			_send();
		};

		var _send = function() {
			if (_enabled && !_locked && _sendQueue.length && !_suspended && !_frozen) {
				_locked = true;

				var data = _sendQueue.shift();

				_metaData.userId = _getEncryptedUserId();
				_metaData.timestamp = (new Date()).getTime();

				AObject.each(_portlets, _updatePortletIdsMap);

				var requestStr = JSON.stringify([_metaData].concat(data));

				A.io(
					_getSendUrl(),
					{
						data: {
							pollerRequest: requestStr
						},
						method: A.config.io.method,
						on: {
							complete: _sendComplete
						}
					}
				);
			}
		};

		var _thawConnection = function() {
			_frozen = false;

			_createRequestTimer();
		};

		var _updatePortletIdsMap = function(item, index, collection) {
			_portletIdsMap[index] = item.initialRequest;
		};

		var Poller = {
			init: function(options) {
				var instance = this;

				instance.setEncryptedUserId(options.encryptedUserId);
				instance.setSupportsComet(options.supportsComet);
			},

			url: _url,

			addListener: function(key, listener, scope) {
				_portlets[key] = {
					initialRequest: true,
					listener: listener,
					scope: scope
				};

				if (!_enabled) {
					_enabled = true;

					_receive();
				}
			},

			cancelCustomDelay: function() {
				_customDelay = null;
			},

			getDelay: function() {
				if (_customDelay !== null) {
					_requestDelay = _customDelay;
				}
				else if (_delayIndex <= _maxDelay) {
					_requestDelay = _delays[_delayIndex];
					_delayAccessCount++;

					if (_delayAccessCount == 3) {
						_delayIndex++;
						_delayAccessCount = 0;
					}
				}

				return _requestDelay * 1000;
			},

			getReceiveUrl: _getReceiveUrl,
			getSendUrl: _getSendUrl,

			isSupportsComet: function() {
				return _supportsComet;
			},

			processResponse: _processResponse,

			removeListener: function(key) {
				var instance = this;

				if (key in _portlets) {
					delete _portlets[key];
				}

				if (AObject.keys(_portlets).length === 0) {
					_enabled = false;

					_cancelRequestTimer();
				}
			},

			resume: function() {
				_suspended = false;

				_createRequestTimer();
			},

			setCustomDelay: function(delay) {
				if (delay === null) {
					_customDelay = delay;
				}
				else {
					_customDelay = delay / 1000;
				}
			},

			setDelay: function(delay) {
				_requestDelay = delay / 1000;
			},

			setEncryptedUserId: function(encryptedUserId) {
				_encryptedUserId = encryptedUserId;
			},

			setSupportsComet: function(supportsComet) {
				_supportsComet = supportsComet;
			},

			setUrl: function(url) {
				_url = url;
			},

			submitRequest: function(key, data, chunkId) {
				if (!_frozen && (key in _portlets)) {
					for (var i in data) {
						if (owns(data, i)) {
							var content = data[i];

							if (content.replace) {
								content = content.replace(_openCurlyBrace, _escapedOpenCurlyBrace);
								content = content.replace(_closeCurlyBrace, _escapedCloseCurlyBrace);

								data[i] = content;
							}
						}
					}

					var requestData = {
						portletId: key,
						data: data
					};

					if (chunkId) {
						requestData.chunkId = chunkId;
					}

					_sendQueue.push(requestData);

					_send();
				}
			},

			suspend: function() {
				_cancelRequestTimer();

				_suspended = true;
			}
		};

		A.getWin().on(
			'focus',
			function(event) {
				_metaData.startPolling = true;

				_thawConnection();
			}
		);

		Liferay.Poller = Poller;
	},
	'',
	{
		requires: ['aui-base', 'io', 'json']
	}
);