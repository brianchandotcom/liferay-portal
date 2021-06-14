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

(function () {
	const CONTAINER_REQUESTS_SYMBOL = Symbol.for(
		'__LIFERAY_WEBPACK_CONTAINER_REQUESTS__'
	);
	const CONTAINERS_SYMBOL = Symbol.for('__LIFERAY_WEBPACK_CONTAINERS__');
	const GET_MODULE_SYMBOL = Symbol.for('__LIFERAY_WEBPACK_GET_MODULE__');
	const SHARED_SCOPE_SYMBOL = Symbol.for('__LIFERAY_WEBPACK_SHARED_SCOPE__');
	const WEBPACK_HELPERS_SYMBOL = Symbol.for('__LIFERAY_WEBPACK_HELPERS__');

	window[CONTAINER_REQUESTS_SYMBOL] = window[CONTAINER_REQUESTS_SYMBOL] || {};
	window[CONTAINERS_SYMBOL] = window[CONTAINERS_SYMBOL] || {};
	window[SHARED_SCOPE_SYMBOL] = window[SHARED_SCOPE_SYMBOL] || {};

	const containerRequests = window[CONTAINER_REQUESTS_SYMBOL];
	const sharedScope = window[SHARED_SCOPE_SYMBOL];

	let inGetModule = false;
	let nextCallId = 1;
	const queuedGetModuleCalls = [];
	const serializeModuleRequests = false;

	/**
	 * Create a new container request
	 */
	function createContainerRequest(url, onLoadHandler) {
		const script = document.createElement('script');

		const containerRequest = {

			// Set to the container's exported object

			container: undefined,

			// Set if request failed

			error: undefined,

			// Set to true if the .js file has been fetched from the server

			fetched: false,

			// Set to the exported values of each module (undefined if error set)

			modules: undefined,

			// Set to the <script> DOM node while it is being retrieved (undefined after)

			script,

			// Set to an array of functions to be invoked once the <script> loads

			subscribers: [],
		};

		script.src = url;
		script.onload = () => onLoadHandler(containerRequest);

		document.head.appendChild(script);

		return containerRequest;
	}

	/**
	 * Log messages if `explain resolutions` option is turned on.
	 */
	function explain(...things) {
		if (Liferay.EXPLAIN_RESOLUTIONS) {
			// eslint-disable-next-line no-console
			console.log(...things);
		}
	}

	/**
	 * Transform a webpack chunk script file name.
	 *
	 * The webpack federation runtimes for projects built with npm-scripts
	 * delegate to this method the logic to compose the URL to retrieve the
	 * chunk files from server.
	 */
	function transformChunkScriptFilename(chunkScriptFileName) {
		return (
			chunkScriptFileName +
			'?languageId=' +
			Liferay.ThemeDisplay.getLanguageId()
		);
	}

	/**
	 * Compute container URL for a given containerId
	 */
	function getContainerURL(containerId) {

		// Strip the scope to derive the context path (this can be done because
		// our SF rules enforce a certain structure on context and package
		// names, but it is not valid for the general case).

		if (containerId[0] === '@') {
			const i = containerId.indexOf('/');

			containerId = containerId.substr(i + 1);
		}

		return (
			'/o/' +
			containerId +
			'/__generated__/container.js?languageId=' +
			Liferay.ThemeDisplay.getLanguageId()
		);
	}

	/**
	 * Fetch a container
	 */
	function fetchContainer(callId, containerId, resolve, reject) {
		if (containerRequests[containerId]) {
			throw new Error(
				`A request is already registered for container ${containerId}`
			);
		}

		const url = getContainerURL(containerId);

		explain(callId, 'Fetching container from URL', url);

		containerRequests[containerId] = createContainerRequest(
			url,
			(containerRequest) => {
				explain(callId, 'Fetched container', containerId);

				const container = getContainer(containerId);

				if (container) {
					explain(
						callId,
						'Initializing container with scope:\n',
						'   ',
						Object.entries(sharedScope)
							.map(
								([name, data]) =>
									`${name}: ` +
									Object.entries(data).map(
										([version, data]) =>
											`(${version}: ${data.from})`
									)
							)
							.join('\n     ')
					);

					Promise.resolve(container.init(sharedScope))
						.then(() =>
							finalizeContainerRequest(
								callId,
								containerRequest,
								container
							)
						)
						.catch(reject);
				}
				else {
					const message =
						`Container ${containerId} was fetched but its script ` +
						`failed to register with Liferay`;

					console.warn(message);

					finalizeContainerRequest(
						callId,
						containerRequest,
						new Error(message)
					);
				}
			}
		);

		explain(callId, 'Subscribing to container request');

		subscribeContainerRequest(
			containerRequests[containerId],
			resolve,
			reject
		);
	}

	/**
	 * Finalize an ongoing container request: set its result (error or
	 * container object) and notify all pending subscribers.
	 */
	function finalizeContainerRequest(callId, containerRequest, result) {
		const {script, subscribers} = containerRequest;

		containerRequest.fetched = true;
		containerRequest.script = undefined;
		containerRequest.subscribers = undefined;

		if (result instanceof Error) {
			explain(
				callId,
				'Rejecting container',
				script.src,
				'for',
				subscribers.length,
				'subscribers'
			);

			containerRequest.error = result;

			subscribers.forEach(({reject}) => reject(result));
		}
		else {
			explain(
				callId,
				'Resolving container',
				script.src,
				'for',
				subscribers.length,
				'subscribers'
			);

			containerRequest.container = result;
			containerRequest.modules = {};

			subscribers.forEach(({resolve}) => resolve(result));
		}
	}

	/**
	 * Get a defined container object
	 */
	function getContainer(containerId) {
		if (containerId[0] === '@') {
			containerId = containerId.substr(1).replace('/', '!');
		}

		return window[CONTAINERS_SYMBOL][containerId];
	}

	/**
	 * Get a module (like require for webpack federation)
	 */
	function getModule(moduleName, caller) {

		// Queue this call for later resolution if needed

		if (serializeModuleRequests) {
			if (inGetModule) {
				return new Promise((resolve, reject) => {
					queuedGetModuleCalls.push(() =>
						getModule(moduleName, caller)
							.then(resolve)
							.catch(reject)
					);
				});
			}
			else {
				inGetModule = true;
			}
		}

		const callId = `[${nextCallId++}]`;

		caller = caller || `[${new Error().stack.split('\n')[1]}]`;

		explain(callId, 'Getting module', moduleName, 'for', caller);

		return new Promise((resolve, reject) => {
			const {containerId, path} = splitModuleName(moduleName);

			const dispatchNextQueuedCall = () => {
				if (!serializeModuleRequests) {
					return;
				}

				inGetModule = false;

				if (queuedGetModuleCalls.length) {
					setTimeout(queuedGetModuleCalls.shift(), 0);
				}
			};

			const resolveGetModuleCall = () => {
				const containerRequest = containerRequests[containerId];
				const {modules} = containerRequest;
				const module = modules[path];

				if (module) {
					explain(callId, 'Resolving cached module');

					resolve(module);

					dispatchNextQueuedCall();
				}
				else {
					const {container} = containerRequest;

					explain(callId, 'Getting module from container');

					Promise.resolve(container.get(path))
						.then((moduleFactory) => {
							const module = moduleFactory();

							modules[path] = module;

							return module;
						})
						.then((module) => {
							explain(callId, 'Resolving module');

							resolve(module);

							dispatchNextQueuedCall();
						})
						.catch((error) => {
							explain(callId, 'Rejecting module', error);

							reject(error);

							dispatchNextQueuedCall();
						});
				}
			};

			const containerRequest = containerRequests[containerId];

			if (containerRequest) {
				const {fetched} = containerRequest;

				if (!fetched) {
					explain(callId, 'Subscribing to container request');

					subscribeContainerRequest(
						containerRequest,
						resolveGetModuleCall,
						reject
					);

					return;
				}

				const {error} = containerRequest;

				if (error) {
					explain(callId, 'Rejecting with error', error);

					reject(error);

					return;
				}

				resolveGetModuleCall();
			}
			else {
				fetchContainer(
					callId,
					containerId,
					resolveGetModuleCall,
					reject
				);
			}
		});
	}

	/**
	 * Split a module name in its `containerId` and `path` parts
	 */
	function splitModuleName(moduleName) {
		let i = moduleName.indexOf('/');

		// Skip first / for scoped packages

		if (moduleName[0] === '@') {
			i = moduleName.indexOf('/', i + 1);
		}

		let containerId, path;

		if (i === -1) {
			containerId = moduleName;
			path = '.';
		}
		else {
			containerId = moduleName.substring(0, i);
			path = moduleName.substring(i + 1);
		}

		return {containerId, path};
	}

	/**
	 * Subscribe to a container request (to be notified when the <script>
	 * finishes loading)
	 */
	function subscribeContainerRequest(containerRequest, resolve, reject) {
		containerRequest.subscribers.push({reject, resolve});
	}

	window[GET_MODULE_SYMBOL] = getModule;
	window[WEBPACK_HELPERS_SYMBOL] = {
		transformChunkScriptFilename,
	};
})();
