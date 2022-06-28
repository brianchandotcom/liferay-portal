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
