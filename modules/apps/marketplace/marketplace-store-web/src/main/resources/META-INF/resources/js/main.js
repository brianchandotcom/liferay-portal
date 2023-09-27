/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-marketplace-messenger',
	(A) => {
		const NATIVE_MSG = !!window.postMessage;

		const MarketplaceMessenger = {
			_messages: [],
			_targetFrame: null,
			_targetURI: null,

			init(options, initMessage) {
				const instance = this;

				if (A.Lang.isString(options)) {
					instance._targetURI = options;
				}
				else if (A.Lang.isObject(options)) {
					const targetFrame = options.targetFrame;

					instance._targetFrame = A.one(targetFrame);

					instance._targetURI = options.targetURI;
				}

				if (initMessage) {
					instance.postMessage(initMessage);
				}
			},

			postMessage(message) {
				const instance = this;

				if (NATIVE_MSG) {
					A.postMessage(
						message,
						instance._targetURI,
						instance._targetFrame
					);
				}
				else {
					instance._messages.push(message);

					if (instance._messages.length === 1) {
						A.postMessage(
							message,
							instance._targetURI,
							instance._targetFrame
						);
					}
				}
			},

			receiveMessage(callback, validator) {
				const instance = this;

				validator = validator || instance._targetURI;

				if (NATIVE_MSG) {
					A.receiveMessage(callback, validator);
				}
				else {
					const wrappedCallback = function (event) {
						const response = event.responseData;

						callback(event);

						instance._messages.shift();

						let message = null;

						if (instance._messages.length) {
							message = instance._messages[0];
						}
						else if (!response.empty) {
							message = {
								empty: true,
							};
						}

						if (message) {
							A.postMessage(
								message,
								instance._targetURI,
								instance._targetFrame
							);
						}
					};

					A.receiveMessage(wrappedCallback, validator);
				}
			},

			setTargetFrame(targetFrame) {
				this._targetFrame = targetFrame;
			},

			setTargetURI(targetURI) {
				this._targetURI = targetURI;
			},
		};

		Liferay.MarketplaceMessenger = MarketplaceMessenger;
	},
	'',
	{
		requires: ['aui-messaging'],
	}
);

AUI.add(
	'liferay-marketplace-util',
	(A) => {
		const MarketplaceUtil = {
			namespaceObject(namespace, object) {
				const returnObject = {};

				const keys = Object.keys(object);

				A.Array.each(keys, (key) => {
					returnObject[namespace + key] = object[key];
				});

				return returnObject;
			},
		};

		Liferay.MarketplaceUtil = MarketplaceUtil;
	},
	'',
	{
		requires: ['aui-base'],
	}
);
