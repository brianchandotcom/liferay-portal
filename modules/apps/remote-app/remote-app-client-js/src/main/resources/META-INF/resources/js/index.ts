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

import {default as logger} from './log';

/**
 * Subset of the full-fledged `Response` type that can actually be serialized
 * across iframes.
 */
type BasicResponse = Pick<
	Response,
	| 'blob'
	| 'json'
	| 'ok'
	| 'redirected'
	| 'status'
	| 'statusText'
	| 'text'
	| 'url'
> & {
	headers: Pick<Headers, 'get' | 'has'>;
};

type ClientOptions = {
	debug: boolean;
};

/**
 * Properties accessible via the `get()` method. The remote-app-support-web
 * module will reject requests for properties outside of this list.
 */
type GettableProperties =
	| 'companyId'
	| 'css'
	| 'defaultLanguageId'
	| 'isControlPanel'
	| 'isSignedIn'
	| 'languageId'
	| 'siteGroupId'
	| 'userId'
	| 'userName';

/**
 * @see https://developer.mozilla.org/en-US/docs/Web/API/Web_Workers_API/Structured_clone_algorithm
 */
type StructuredClonable =
	| Array<StructuredClonable>
	| ArrayBuffer
	| ArrayBufferView
	| Blob
	| Boolean
	| Date
	| File
	| FileList
	| ImageBitmap
	| ImageData
	| Map<StructuredClonable, StructuredClonable>
	| RegExp
	| Set<StructuredClonable>
	| String
	| boolean
	| null
	| number
	| string
	| undefined
	| {[key: string]: StructuredClonable};

/**
 * Payloads may only contain properties which we can send across iframes
 * using the `postMessage` API. Complex properties like functions cannot
 * be sent because the "Structured clone algorithm" does not support
 * them.
 *
 * @see https://developer.mozilla.org/en-US/docs/Web/API/Client/postMessage
 */
type Payload = {[key: string]: StructuredClonable};

type PromiseMap<T = unknown> = {
	[key: string]: {
		reject: (reason: any) => void;
		resolve: (value: T | PromiseLike<T>) => void;
	};
};

type State =
	| 'disposed'
	| 'invalid'
	| 'registered'
	| 'registering'
	| 'unregistered';

type Subscription = {
	release: () => void;
};

// TODO consider moving these into a shared location

const ERROR_CODES = {
	/* eslint-disable sort-keys */
	UNKNOWN: -100,
	ALREADY_REGISTERED: -101,
	/* eslint-enable sort-keys */
};

const REMOTE_APP_PROTOCOL = 'com.liferay.remote.app.protocol';

const VERSION = 0;

function getArray(
	object: object,
	property: string,
	fallback?: undefined
): Array<unknown> | undefined;

function getArray(
	object: object,
	property: string,
	fallback?: Array<unknown>
): Array<unknown>;

function getArray(
	object: object,
	property: string,
	fallback?: Array<unknown>
): Array<unknown> | undefined {
	if (has(object, property)) {
		const value = object[property];

		if (isArray(value)) {
			return value;
		}
	}

	return fallback;
}

function getBoolean(
	object: object,
	property: string,
	fallback?: undefined
): boolean | undefined;

function getBoolean(
	object: object,
	property: string,
	fallback?: boolean
): boolean;

function getBoolean(
	object: object,
	property: string,
	fallback?: boolean
): boolean | undefined {
	if (has(object, property)) {
		const value = object[property];

		if (typeof value === 'boolean') {
			return value;
		}
	}

	return fallback;
}

function getNumber(
	object: object,
	property: string,
	fallback?: undefined
): number | undefined;

function getNumber(object: object, property: string, fallback?: number): number;

function getNumber(
	object: object,
	property: string,
	fallback?: number
): number | undefined {
	if (has(object, property)) {
		const value = object[property];

		if (typeof value === 'number') {
			return value;
		}
	}

	return fallback;
}

function getString(
	object: object,
	property: string,
	fallback?: undefined
): string | undefined;

function getString(object: object, property: string, fallback?: string): string;

function getString(
	object: object,
	property: string,
	fallback?: string
): string | undefined {
	if (has(object, property)) {
		const value = object[property];

		if (typeof value === 'string') {
			return value;
		}
	}

	return fallback;
}

function has<T extends {}, K extends PropertyKey>(
	object: unknown,
	property: K
): object is T & Record<K, unknown> {
	return Object.prototype.hasOwnProperty.call(object, property);
}

/**
 * Returns a v4 UUID.
 *
 * @see https://www.ietf.org/rfc/rfc4122.txt
 */
function getUUID(): string {
	const timestamp = crypto.getRandomValues(new Uint8Array(16));

	// Version 4.

	timestamp[6] = (timestamp[6] & 0x0f) | 0x40;

	// Variant 1.

	timestamp[8] = (timestamp[8] & 0xc0) | 0x80;

	let i = 0;

	return 'xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx'.replace(/xx/g, () => {
		return `0${timestamp[i++].toString(16)}`.slice(-2);
	});
}

/**
 * Ponyfill for `Array.isArray` so that that SDK clients don't need to
 * bring their own polyfill on IE.
 */
function isArray(object: unknown): object is Array<unknown> {
	return Object.prototype.toString.call(object) === '[object Array]';
}

function isString(value: unknown): value is string {
	return typeof value === 'string';
}

/**
 * Ponyfill for `String.prototype.startsWith`.
 */
function startsWith(haystack: string, needle: string): boolean {
	return haystack.indexOf(needle) === 0;
}

function retry({
	callback,
	until,
}: {
	callback: () => void;
	until: () => boolean;
}) {
	let interval = 100;

	function fn() {
		if (!until()) {
			callback();

			setTimeout(fn, interval);

			// Exponential back-off.

			interval *= 1.5;
		}
	}

	fn();
}

/**
 * @see https://developer.mozilla.org/en-US/docs/Web/API/ArrayBufferView
 */
function isArrayBufferView(value: unknown): value is ArrayBufferView {
	return (
		value instanceof DataView ||
		value instanceof Float32Array ||
		value instanceof Float64Array ||
		value instanceof Int16Array ||
		value instanceof Int32Array ||
		value instanceof Int8Array ||
		value instanceof Uint16Array ||
		value instanceof Uint32Array ||
		value instanceof Uint8Array ||
		value instanceof Uint8ClampedArray
	);
}

function serializeBody(body: BodyInit | null): StructuredClonable {
	if (
		body === null ||
		typeof body === 'string' ||
		body instanceof Blob ||
		body instanceof ArrayBuffer ||
		isArrayBufferView(body)
	) {
		return body;
	}
	else if (body instanceof FormData) {
		const serializedFormData: {[key: string]: File | string} = {};

		body.forEach((value: File | string, key: string) => {
			serializedFormData[key] = value;
		});

		return {
			__FORM_DATA__: serializedFormData,
		};
	}
	else {

		// warn about unserializable body type (eg. search params,
		// readable stream),

	}
}

function serializeHeaders(headers: HeadersInit): StructuredClonable {
	if (isArray(headers)) {
		return headers;
	}

	if (typeof headers.forEach === 'function') {
		const record: {[key: string]: string} = {};

		headers.forEach((value: string, key: string) => {
			record[key] = value;
		});

		return record;
	}

	return headers as Record<string, string>;
}

function serializeRequestInfo(resource: RequestInfo): StructuredClonable {
	if (typeof resource === 'string') {
		return resource;
	}

	const serialized: StructuredClonable = {};

	serialized.cache = resource.cache;
	serialized.credentials = resource.credentials;
	serialized.destination = resource.destination;
	serialized.integrity = resource.integrity;
	serialized.isHistoryNavigation = (resource as any).isHistoryNavigation;
	serialized.isReloadNavigation = (resource as any).isReloadNavigation;
	serialized.keepalive = resource.keepalive;
	serialized.method = resource.method;
	serialized.mode = resource.mode;
	serialized.redirect = resource.redirect;
	serialized.referrer = resource.referrer;
	serialized.referrerPolicy = resource.referrerPolicy;
	serialized.url = resource.url;

	return serialized;
}

function serializeRequestInit(init: RequestInit): StructuredClonable {
	const serialized: StructuredClonable = {};

	if (init.body !== undefined) {
		serialized.body = serializeBody(init.body);
	}

	if (init.cache != null) {
		serialized.cache = init.cache;
	}

	if (init.credentials != null) {
		serialized.credentials = init.credentials;
	}

	if (init.headers != null) {
		serialized.headers = serializeHeaders(init.headers);
	}

	if (init.integrity != null) {
		serialized.integrity = init.integrity;
	}

	if (init.keepalive != null) {
		serialized.keepalive = init.keepalive;
	}

	if (init.method != null) {
		serialized.method = init.method;
	}

	if (init.redirect != null) {
		serialized.redirect = init.redirect;
	}

	if (init.referrer != null) {
		serialized.referrer = init.referrer;
	}

	if (init.referrerPolicy != null) {
		serialized.referrerPolicy = init.referrerPolicy;
	}

	if (init.window === null) {
		serialized.window = null;
	}

	return serialized;
}

type SDKEvent = {
	type: string;
	[key: string]: any;
};

type Listeners = {
	error: {
		[key: string]: (event: SDKEvent) => void;
	};
	[key: string]: {
		[key: string]: (event: SDKEvent) => void;
	};
};

function Client({debug}: ClientOptions = {debug: false}) {

	// TODO: warn if no promise polyfill present
	// (need that just like we expect DXP environment ot have it)
	// or provide very minimal fallback ponyfill

	// TODO: consider renaming this to clientID for consistency with name of
	// constructor

	const appID = getUUID();

	let listenerID = 0;

	const listeners: Listeners = {
		error: {},
	};
	const messageQueue: Array<Payload> = [];

	const promises = {
		'fetch': {} as PromiseMap<BasicResponse>,
		'fetch:response:blob': {} as PromiseMap<Blob>,
		'fetch:response:json': {} as PromiseMap<JSONValue>,
		'fetch:response:text': {} as PromiseMap<string>,
		'get': {} as PromiseMap<string>,
	} as const;

	let state: State = 'unregistered';

	register();

	function handleError(message: string, code: number) {
		log(`Error: ${message} (${code})`);

		if (code === ERROR_CODES.ALREADY_REGISTERED) {
			state = 'invalid';
		}

		Object.keys(listeners.error).forEach((key) => {
			try {
				listeners.error[key]({
					code,
					message,
					type: 'error',
				});
			}
			catch (error) {
				log(`Error caught while notifying listener: ${error}`);
			}
		});
	}

	function log(...messages: Array<unknown>) {
		if (debug) {
			logger(`[CLIENT: ${appID.slice(0, 8)}...]`, ...messages);
		}
	}

	function postMessage(
		data: Payload,
		{force}: {force: boolean} = {force: false}
	) {
		if (state === 'disposed') {
			log('ignoring message (disposed client)...', data);
		}
		else if (state === 'registered' || force) {
			log('posting message...', data);

			if (window.parent) {
				try {
					window.parent.postMessage(
						{
							...data,
							appID,
							protocol: REMOTE_APP_PROTOCOL,
							role: 'client',
							version: VERSION,
						},
						'*'
					);
				}
				catch (error) {
					log(`error sending message: ${error}`);
				}
			}
			else {
				log('no parent...');
			}
		}
		else if (state === 'invalid') {
			log('ignoring message (invalid client)...', data);
		}
		else if (state === 'registering') {
			log('enqueuing message...', data);

			messageQueue.push(data);
		}
	}

	function receiveMessage(event: MessageEvent) {
		const {data, source} = event;

		if (source !== parent.window) {
			return;
		}

		log('received', data);

		// TODO: may want to parse event into structured type in one place

		if (
			data &&
			getString(data, 'protocol') === REMOTE_APP_PROTOCOL &&
			getString(data, 'role') === 'host' &&
			getNumber(data, 'version') === VERSION
		) {
			const kind = getString(data, 'kind');

			if (getString(data, 'appID') !== appID) {
				log('appID mismatch');
			}
			else if (kind === 'error') {
				const message = getString(data, 'message', 'unknown');

				const code = getNumber(data, 'code', ERROR_CODES.UNKNOWN);

				handleError(message, code);
			}
			else if (kind === 'fetch:reject') {
				const requestID = getString(data, 'requestID');

				if (requestID) {
					promises.fetch[requestID]?.reject?.(data.error);
				}
			}
			else if (kind === 'fetch:resolve') {
				const requestID = getString(data, 'requestID');

				if (requestID) {
					promises.fetch[requestID]?.resolve?.({
						blob() {
							postMessage({
								command: 'fetch:response:blob',
								requestID,
							});

							return new Promise((resolve, reject) => {
								promises['fetch:response:blob'][requestID] = {
									reject,
									resolve,
								};
							});
						},

						// TODO: consider also propagating trailer
						/**
						 * Partial proxy for Headers object.
						 *
						 * @see https://developer.mozilla.org/en-US/docs/Web/API/Headers
						 */
						headers: {
							get(name: string): string | null {
								const headers = getArray(data, 'headers', []);

								for (let i = 0; i < headers.length; i++) {
									const tuple = headers[i];
									if (isArray(tuple) && isString(tuple[0])) {
										if (
											tuple[0].toLowerCase() ===
											name.toLowerCase()
										) {
											const value = tuple[1];

											if (typeof value === 'string') {
												return value;
											}

											break;
										}
									}
								}

								return null;
							},

							has(name: string): boolean {
								const headers = getArray(data, 'headers', []);

								return headers.some((tuple) => {
									return (
										isArray(tuple) &&
										isString(tuple[0]) &&
										tuple[0].toLowerCase() ===
											name.toLowerCase() &&
										typeof tuple[1] === 'string'
									);
								});
							},
						},

						json() {
							postMessage({
								command: 'fetch:response:json',
								requestID,
							});

							return new Promise((resolve, reject) => {
								promises['fetch:response:json'][requestID] = {
									reject,
									resolve,
								};
							});
						},

						ok: getBoolean(data, 'ok', false),
						redirected: getBoolean(data, 'redirected', false),
						status: getNumber(data, 'status', 0),
						statusText: getString(data, 'statusText', ''),

						text() {
							postMessage({
								command: 'fetch:response:text',
								requestID,
							});

							return new Promise((resolve, reject) => {
								promises['fetch:response:text'][requestID] = {
									reject,
									resolve,
								};
							});
						},

						url: getString(data, 'url', ''),
					});
				}
			}
			else if (kind === 'fetch:response:blob:reject') {
				const requestID = getString(data, 'requestID');

				if (requestID) {
					promises['fetch:response:blob'][requestID]?.reject?.(
						data.error
					);
				}
			}
			else if (kind === 'fetch:response:blob:resolve') {
				const requestID = getString(data, 'requestID');

				if (requestID) {
					promises['fetch:response:blob'][requestID]?.resolve?.(
						data.blob
					);
				}
			}
			else if (kind === 'fetch:response:json:reject') {
				const requestID = getString(data, 'requestID');

				if (requestID) {
					promises['fetch:response:json'][requestID]?.reject?.(
						data.error
					);
				}
			}
			else if (kind === 'fetch:response:json:resolve') {
				const requestID = getString(data, 'requestID');

				if (requestID) {
					promises['fetch:response:json'][requestID]?.resolve?.(
						data.json
					);
				}
			}
			else if (kind === 'fetch:response:text:reject') {
				const requestID = getString(data, 'requestID');

				if (requestID) {
					promises['fetch:response:text'][requestID]?.reject?.(
						data.error
					);
				}
			}
			else if (kind === 'fetch:response:text:resolve') {
				const requestID = getString(data, 'requestID');

				if (requestID) {
					promises['fetch:response:text'][requestID]?.resolve?.(
						data.text
					);
				}
			}
			else if (kind === 'get:reject') {
				const requestID = getString(data, 'requestID');

				if (requestID) {
					promises.get[requestID]?.reject?.(data.error);
				}
			}
			else if (kind === 'get:resolve') {
				const requestID = getString(data, 'requestID');

				if (requestID) {
					promises.get[requestID]?.resolve?.(data.value);
				}
			}
			else if (kind === 'registered') {

				// TODO replace with actual reducer

				state = 'registered';

				log('registered');

				// Replay messages.

				while (true) {
					const data = messageQueue.shift();

					if (data) {
						postMessage(data);

						continue;
					}

					break;
				}
			}
		}
	}

	function register() {
		state = 'registering';

		window.addEventListener('message', receiveMessage);

		retry({
			callback() {
				postMessage({command: 'register'}, {force: true});
			},

			until() {
				return state !== 'registering';
			},
		});
	}

	const Public = {
		get debug() {
			return debug;
		},

		set debug(value) {
			debug = value;
		},

		dispose() {
			if (state !== 'disposed' && state !== 'invalid') {
				if (state === 'registered' || state === 'registering') {
					postMessage({command: 'unregister'});
				}

				state = 'disposed';
			}
		},

		fetch(
			resource: RequestInfo,
			init: RequestInit = {}
		): Promise<BasicResponse> {

			// TODO: if Array.from Headers etc exist (ie. IE with polyfill
			// or evergreen), try to extract headers from resource and merge
			// them into init instead.

			const requestID = getUUID();

			postMessage({
				command: 'fetch',
				init: serializeRequestInit(init),
				requestID,
				resource: serializeRequestInfo(resource),
			});

			return new Promise<BasicResponse>((resolve, reject) => {
				promises.fetch[requestID] = {
					reject,
					resolve,
				};
			});
		},

		get(property: GettableProperties): Promise<string> {
			const requestID = getUUID();

			postMessage({
				command: 'get',
				property,
				requestID,
			});

			return new Promise<string>((resolve, reject) => {
				promises.get[requestID] = {
					reject,
					resolve,
				};
			});
		},

		/**
		 * @see https://github.com/prisma-labs/graphql-request
		 */
		graphql(
			query: string,
			variables?: {[key: string]: JSONValue}
		): Promise<JSONValue | string> {
			return Public.fetch('/o/graphql', {
				body: JSON.stringify({
					query,
					variables,
				}),
				headers: {
					'Content-Type': 'application/json',
				},
				method: 'POST',
			}).then((response) => {
				const contentType = response.headers.get('Content-Type');

				if (
					contentType &&
					startsWith(contentType, 'application/json')
				) {
					return response.json();
				}
				else {
					return response.text();
				}
			});
		},

		log(...messages: Array<unknown>) {
			log(...messages);
		},

		navigate(url: string) {
			postMessage({
				command: 'navigate',
				url,
			});
		},

		on(event: string, subscriber: (event: SDKEvent) => void): Subscription {
			if (!listeners[event]) {
				listeners[event] = {};
			}

			const id = listenerID++;

			listeners[event][id] = subscriber;

			return {
				release() {
					delete listeners[event][id];
				},
			};
		},

		openToast({message = '', type = 'info'} = {}) {
			postMessage({
				command: 'openToast',
				message,
				type,
			});
		},

		get state() {
			return state;
		},
	};

	return Public;
}

const SDK = Object.freeze({
	Client,
	VERSION,
});

/**
 * Due to webpack configuration, the SDK will be assigned to
 * `window.__LIFERAY_REMOTE_APP_SDK__`.
 */
export const __LIFERAY_REMOTE_APP_SDK__ = SDK;
