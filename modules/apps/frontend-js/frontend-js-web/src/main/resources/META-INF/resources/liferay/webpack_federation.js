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

(function() {
	const CONTAINER_REQUESTS_SYMBOL = Symbol.for('__LIFERAY_WEBPACK_CONTAINER_REQUESTS__');
	const CONTAINERS_SYMBOL = Symbol.for('__LIFERAY_WEBPACK_CONTAINERS__');
	const GET_MODULE_SYMBOL = Symbol.for('__LIFERAY_WEBPACK_GET_MODULE__');
	const SHARED_SCOPE_SYMBOL = Symbol.for('__LIFERAY_WEBPACK_SHARED_SCOPE__');

	window[CONTAINER_REQUESTS_SYMBOL] = window[CONTAINER_REQUESTS_SYMBOL] || {};
	window[CONTAINERS_SYMBOL] = window[CONTAINERS_SYMBOL] || {};
	window[SHARED_SCOPE_SYMBOL] = window[SHARED_SCOPE_SYMBOL] || {};

	const containerRequests = window[CONTAINER_REQUESTS_SYMBOL];
	const sharedScope = window[SHARED_SCOPE_SYMBOL];

	/**
	 * Create a new container request
	 */
	function createContainerRequest(url, onLoadHandler) {
		const script = document.createElement('script');

		const containerRequest = {
			// set if request failed
			error: undefined,
			// set to true if the .js file has been fetched from the server
			fetched: false,
			// set to the exported value (if error is set module is undefined)
			module: undefined,
			// set to the <script> DOM node while it is being retrieved (undefined after)
			script,
			// set to all people that needs to be notified once the <script> loads
			subscribers: [],
		};

		script.src = url;
		script.onload = () => onLoadHandler(containerRequest);

		document.body.appendChild(script);

		return containerRequest;
	}

	/**
	 * Log messages if `explain resolutions` option is turned on.
	 */
	function explain(...things) {
		// Until we get to the end of webpack 5 migration we will log all
		// messages no matter what is configured. Once finished, we will only
		// write when the old existing `explain resolutions` option is on.
		console.log(...things);
	}

	/**
	 * Fetch a container's module by path
	 */
	function fetch(containerId, path, resolve, reject) {
		const url = '/o/' + containerId + '/__generated__/container.js';

		explain('Fetching container', containerId, 'from', url);

		containerRequests[containerId] = createContainerRequest(
			url,
			(containerRequest) => {
				explain('Fetched container', containerId);

				const container = getContainer(containerId);

				if (container) {
					explain(
						'Initializing container', containerId, '\n',
						Object.entries(sharedScope).map(([name, data]) =>
							`${name}: ` +
							Object.entries(data).map(
								([version, data]) => `(${version}: ${data.from})`
                            )
						).join('\n ')
					);

					Promise.resolve(
						container.init(sharedScope)
					)
					.then(
						() => container.get(path)
					)
					.then(
						(moduleFactory) => {
							finalizeContainerRequest(
								containerRequest, moduleFactory());
						}
					);
				}
				else {
					const message =
						`Container ${containerId} was fetched but its script ` +
						`failed to register with Liferay`;

					console.warn(message);

					finalizeContainerRequest(
						containerRequest, new Error(message));
				}
			}
		);


		subscribeContainerRequest(
			containerRequests[containerId], resolve, reject);
	}

	/**
	 * Finalize an ongoing container request: set its result (error or module)
	 * and notify all pending subscribers.
	 */
	function finalizeContainerRequest(containerRequest, result) {
		const {script, subscribers} = containerRequest;

		containerRequest.fetched = true;
		containerRequest.script = undefined;
		containerRequest.subscribers = undefined;

		if(result instanceof Error) {
			explain('Rejecting container', script.src, 'for', subscribers.length, 'subscribers');
			containerRequest.error = result;
			subscribers.forEach(({reject}) => reject(result));
		} else {
			explain('Resolving container', script.src, 'for', subscribers.length, 'subscribers');
			containerRequest.module = result;
			subscribers.forEach(({resolve}) => resolve(result));
		}
	};

	/**
	 * Get a defined container object
	 */
	function getContainer(containerId) {
		return window[CONTAINERS_SYMBOL][containerId];
	}

	/**
	 * Get a module (like require for webpack federation)
	 */
	function getModule(moduleName) {
		return new Promise((resolve, reject) => {
			const {containerId, path} = splitModuleName(moduleName);

			const containerRequest = containerRequests[containerId];

			if (!containerRequest) {
				fetch(containerId, path, resolve, reject);
			}
			else {
				const {error, module} = containerRequest;

				if (error) {
					explain('Rejecting fetched container', containerId, error);
					reject(error);
				} else if(module) {
					explain('Resolving fetched container', containerId);
					resolve(module);
				} else {
					explain('Subscribing to container request', containerId);
					subscribeContainerRequest(containerRequest, resolve, reject);
				}
			}
		});
	}

	/**
	 * Split a module name in its `containerId` and `path` parts
	 */
	function splitModuleName(moduleName) {
		const i = moduleName.indexOf('/');

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
})();