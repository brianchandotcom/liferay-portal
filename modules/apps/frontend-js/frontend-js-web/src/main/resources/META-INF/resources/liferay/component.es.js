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

import {isFunction} from 'metal';

const componentConfigs = {};
let componentPromiseWrappers = {};
const components = {};
let componentsCache = {};
const componentsFn = {};

const DEFAULT_CACHE_VALIDATION_PARAMS = ['p_p_id', 'p_p_lifecycle'];
const DEFAULT_CACHE_VALIDATION_PORTLET_PARAMS = [
	'ddmStructureKey',
	'fileEntryTypeId',
	'folderId',
	'navigation',
	'status'
];

const LIFERAY_COMPONENT = 'liferay.component';

const _createPromiseWrapper = function(value) {
	let promiseWrapper;

	if (value) {
		promiseWrapper = {
			promise: Promise.resolve(value),
			resolve() {}
		};
	} else {
		let promiseResolve;

		const promise = new Promise(resolve => {
			promiseResolve = resolve;
		});

		promiseWrapper = {
			promise,
			resolve: promiseResolve
		};
	}

	return promiseWrapper;
};

/**
 * Restores a previously cached component markup.
 *
 * @param {object} state The stored state associated with the registered task.
 * @param {object} params The additional params passed in the task registration.
 * @param {Fragment} node The temporary fragment holding the new markup.
 * @private
 */
const _restoreTask = function(state, params, node) {
	const cache = state.data;
	const componentIds = Object.keys(cache);

	componentIds.forEach(componentId => {
		const container = node.querySelector(`#${componentId}`);

		if (container) {
			container.innerHTML = cache[componentId].html;
		}
	});
};

/**
 * Runs when an SPA navigation start is detected to
 *
 * <ul>
 * <li>
 * Cache the state and current markup of registered components that have
 * requested it through the <code>cacheState</code> configuration option. This
 * state can be used to initialize the component in the same state if it
 * persists throughout navigations.
 * </li>
 * <li>
 * Register a DOM task to restore the markup of components that are present in
 * the next screen to avoid a flickering effect due to state changes. This can
 * be done by querying the components screen cache using the
 * <code>Liferay.getComponentsCache</code> method.
 * </li>
 * </ul>
 *
 * @private
 */

const _onStartNavigate = function(event) {
	const currentUri = new URL(window.location.href);
	const uri = new URL(event.path, window.location.href);

	const cacheableUri = DEFAULT_CACHE_VALIDATION_PARAMS.every(param => {
		return (
			uri.searchParams.get(param) === currentUri.searchParams.get(param)
		);
	});

	if (cacheableUri) {
		var componentIds = Object.keys(components);

		componentIds = componentIds.filter(componentId => {
			const component = components[componentId];
			const componentConfig = componentConfigs[componentId];

			const cacheablePortletUri = DEFAULT_CACHE_VALIDATION_PORTLET_PARAMS.every(
				param => {
					let cacheable = false;

					if (componentConfig) {
						const namespacedParam = `_${componentConfig.portletId}_${param}`;

						cacheable =
							uri.searchParams.get(namespacedParam) ===
							currentUri.searchParams.get(namespacedParam);
					}

					return cacheable;
				}
			);

			const cacheableComponent = isFunction(component.isCacheable)
				? component.isCacheable(uri)
				: false;

			return (
				cacheableComponent &&
				cacheablePortletUri &&
				componentConfig &&
				componentConfig.cacheState &&
				component.element &&
				component.getState
			);
		});

		componentsCache = componentIds.reduce((cache, componentId) => {
			const component = components[componentId];
			const componentConfig = componentConfigs[componentId];
			const componentState = component.getState();

			const componentCache = componentConfig.cacheState.reduce(
				(cache, stateKey) => {
					cache[stateKey] = componentState[stateKey];

					return cache;
				},
				{}
			);

			cache[componentId] = {
				html: component.element.innerHTML,
				state: componentCache
			};

			return cache;
		}, []);

		Liferay.DOMTaskRunner.addTask({
			action: _restoreTask,
			condition: state => state.owner === LIFERAY_COMPONENT
		});

		Liferay.DOMTaskRunner.addTaskState({
			data: componentsCache,
			owner: LIFERAY_COMPONENT
		});
	} else {
		componentsCache = {};
	}
};

/**
 * Registers a component and retrieves its instance from the global registry.
 *
 * @param  {string} id The ID of the component to retrieve or register.
 * @param  {object} value The component instance or a component constructor. If
 *         a constructor is provided, it will be invoked the first time the
 *         component is requested and its result will be stored and returned as
 *         the component.
 * @param  {object} componentConfig The Custom component configuration. This can
 *         be used to provide additional hints for the system handling of the
 *         component lifecycle.
 * @return {object} The passed value, or the stored component for the provided
 *         ID.
 */
const component = function(id, value, componentConfig) {
	let retVal;

	if (arguments.length === 1) {
		let component = components[id];

		if (component && isFunction(component)) {
			componentsFn[id] = component;

			component = component();

			components[id] = component;
		}

		retVal = component;
	} else {
		if (components[id] && value !== null) {
			delete componentConfigs[id];
			delete componentPromiseWrappers[id];

			// eslint-disable-next-line no-console
			console.warn(
				'Component with id "' +
					id +
					'" is being registered twice. This can lead to unexpected behaviour in the "Liferay.component" and "Liferay.componentReady" APIs, as well as in the "*:registered" events.'
			);
		}

		retVal = components[id] = value;

		if (value === null) {
			delete componentConfigs[id];
			delete componentPromiseWrappers[id];
		} else {
			componentConfigs[id] = componentConfig;

			Liferay.fire(id + ':registered');

			const componentPromiseWrapper = componentPromiseWrappers[id];

			if (componentPromiseWrapper) {
				componentPromiseWrapper.resolve(value);
			} else {
				componentPromiseWrappers[id] = _createPromiseWrapper(value);
			}
		}
	}

	return retVal;
};

/**
 * Retrieves a list of component instances after they've been registered.
 *
 * @param {...string} componentId The IDs of the components to receive.
 * @return {Promise} A promise to be resolved with all the requested component
 *         instances after they've been successfully registered.
 */
const componentReady = function() {
	let component;
	let componentPromise;

	if (arguments.length === 1) {
		component = arguments[0];
	} else {
		component = [];

		for (var i = 0; i < arguments.length; i++) {
			component[i] = arguments[i];
		}
	}

	if (Array.isArray(component)) {
		componentPromise = Promise.all(component.map(id => componentReady(id)));
	} else {
		let componentPromiseWrapper = componentPromiseWrappers[component];

		if (!componentPromiseWrapper) {
			componentPromiseWrappers[
				component
			] = componentPromiseWrapper = _createPromiseWrapper();
		}

		componentPromise = componentPromiseWrapper.promise;
	}

	return componentPromise;
};

/**
 * Destroys the component registered by the provided component ID. This invokes
 * the component's own destroy lifecycle methods (destroy or dispose) and
 * deletes the internal references to the component in the component registry.
 *
 * @param {string} componentId The ID of the component to destroy.
 */
const destroyComponent = function(componentId) {
	const component = components[componentId];

	if (component) {
		const destroyFn = component.destroy || component.dispose;

		if (destroyFn) {
			destroyFn.call(component);
		}

		delete componentConfigs[componentId];
		delete componentPromiseWrappers[componentId];
		delete componentsFn[componentId];
		delete components[componentId];
	}
};

/**
 * Destroys registered components matching the provided filter function. If no
 * filter function is provided, it destroys all registered components.
 *
 * @param {Function} filterFn A method that receives a component's destroy
 *        options and the component itself, and returns <code>true</code> if the
 *        component should be destroyed.
 */
const destroyComponents = function(filterFn) {
	var componentIds = Object.keys(components);

	if (filterFn) {
		componentIds = componentIds.filter(componentId => {
			return filterFn(
				components[componentId],
				componentConfigs[componentId] || {}
			);
		});
	}

	componentIds.forEach(destroyComponent);
};

/**
 * Clears the component promises map to make sure pending promises don't get
 * accidentally resolved at a later stage if a component with the same ID
 * appears, causing stale code to run.
 */
const destroyUnfulfilledPromises = function() {
	componentPromiseWrappers = {};
};

/**
 * Retrieves a registered component's cached state.
 *
 * @param {string} componentId The ID used to register the component.
 * @return {object} The state the component had prior to the previous navigation.
 */
const getComponentCache = function(componentId) {
	const componentCache = componentsCache[componentId];

	return componentCache ? componentCache.state : {};
};

/**
 * Initializes the component cache mechanism.
 */
const initComponentCache = function() {
	Liferay.on('startNavigate', _onStartNavigate);
};

export {
	component,
	componentReady,
	destroyComponent,
	destroyComponents,
	destroyUnfulfilledPromises,
	getComponentCache,
	initComponentCache
};
export default component;
