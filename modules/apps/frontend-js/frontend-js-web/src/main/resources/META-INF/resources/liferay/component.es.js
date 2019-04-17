import {isFunction} from 'metal';
import Uri from 'metal-uri';

let componentConfigs = {};
let componentPromiseWrappers = {};
let components = {};
let componentsCache = {};
let componentsFn = {};

const DEFAULT_CACHE_VALIDATION_PARAMS = ['p_p_id', 'p_p_lifecycle'];
const DEFAULT_CACHE_VALIDATION_PORTLET_PARAMS = ['ddmStructureKey', 'fileEntryTypeId', 'folderId', 'navigation', 'status'];

const LIFERAY_COMPONENT = 'liferay.component';

const _createPromiseWrapper = function(value) {
	let promiseWrapper;

	if (value) {
		promiseWrapper = {
			promise: Promise.resolve(value),
			resolve: function() {}
		};
	}
	else {
		let promiseResolve;

		const promise = new Promise(
			function(resolve) {
				promiseResolve = resolve;
			}
		);

		promiseWrapper = {
			promise: promise,
			resolve: promiseResolve
		};
	}

	return promiseWrapper;
};

/**
 * Restores a previously cached component markup
 *
 * @param {object} state Stored state associated with the registered task
 * @param {object} params Additional params passed in the task registration
 * @param {Fragment} node Temporary fragment holding the new markup
 * @private
 * @review
 */
const _restoreTask = function(state, params, node) {
	const cache = state.data;
	const componentIds = Object.keys(cache);

	componentIds.forEach(
		componentId => {
			const container = node.getElementById(componentId);

			if (container) {
				container.innerHTML = cache[componentId].html;
			}
		}
	);
};

/**
 * Runs when an SPA navigation start is detected to:
 * - Cache the state and current markup of registered components matching that
 * have requested it through the `cacheState` configuration option. This state
 * can be used to initialize the component in the same state if it persists
 * throughout navigations.
 * - Register a DOM task to restore the markup of those components that are
 * present in the next screen to avoid a flickering effect due to state changes.
 * This can be done by querying the components screen cache using the
 * `Liferay.getComponentsCache` method.
 *
 * @private
 * @review
 */

const _onStartNavigate = function(event) {
	const currentUri = new Uri(window.location.href);
	const uri = new Uri(event.path);

	const cacheableUri = DEFAULT_CACHE_VALIDATION_PARAMS.every(
		param => {
			return uri.getParameterValue(param) === currentUri.getParameterValue(param);
		}
	);

	if (cacheableUri) {
		var componentIds = Object.keys(components);

		componentIds = componentIds.filter(
			componentId => {
				const component = components[componentId];
				const componentConfig = componentConfigs[componentId];

				const cacheablePortletUri = DEFAULT_CACHE_VALIDATION_PORTLET_PARAMS.every(
					param => {
						let cacheable = false;

						if (componentConfig) {
							const namespacedParam = `_${componentConfig.portletId}_${param}`;

							cacheable = uri.getParameterValue(namespacedParam) === currentUri.getParameterValue(namespacedParam);
						}

						return cacheable;
					}
				);

				const cacheableComponent = isFunction(component.isCacheable) ? component.isCacheable(uri) : false;

				return cacheableComponent &&
					cacheablePortletUri &&
					componentConfig &&
					componentConfig.cacheState &&
					component.element &&
					component.getState;
			}
		);

		componentsCache = componentIds.reduce(
			(cache, componentId) => {
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
			},
			[]
		);

		Liferay.DOMTaskRunner.addTask(
			{
				action: _restoreTask,
				condition: state => state.owner === LIFERAY_COMPONENT
			}
		);

		Liferay.DOMTaskRunner.addTaskState(
			{
				data: componentsCache,
				owner: LIFERAY_COMPONENT
			}
		);
	}
	else {
		componentsCache = {};
	}
};

/**
 * This method acts in a dual way. It allows both to register a component and to
 * retrieve its instance from the global register.
 *
 * @param {string} id The id of the component to retrieve or register
 * @param {object} value The component instance or a component constructor. If a
 * constructor is provided, it will be invoked the first time the component is
 * requested and its result will be stored and returned as the component
 * @param {object} componentConfig Custom component configuration. Can be used to
 * provide additional hints for the system handling of the component lifecycle
 * @return {object} The passed value, or the stored component for the provided id
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
	}
	else {
		if (components[id] && value !== null) {
			delete componentConfigs[id];
			delete componentPromiseWrappers[id];

			console.warn('Component with id "' + id + '" is being registered twice. This can lead to unexpected behaviour in the "Liferay.component" and "Liferay.componentReady" APIs, as well as in the "*:registered" events.');
		}

		retVal = (components[id] = value);

		if (value === null) {
			delete componentConfigs[id];
			delete componentPromiseWrappers[id];
		}
		else {
			componentConfigs[id] = componentConfig;

			Liferay.fire(id + ':registered');

			const componentPromiseWrapper = componentPromiseWrappers[id];

			if (componentPromiseWrapper) {
				componentPromiseWrapper.resolve(value);
			}
			else {
				componentPromiseWrappers[id] = _createPromiseWrapper(value);
			}
		}
	}

	return retVal;
};

/**
 * Retrieves a list of component instances after they've been registered.
 *
 * @param {...string} componentId The ids of the components to be received
 * @return {Promise} A promise to be resolved with all the requested component
 * instances after they've been successfully registered
 * @review
 */

const componentReady = function() {
	let component;
	let componentPromise;

	if (arguments.length === 1) {
		component = arguments[0];
	}
	else {
		component = [];

		for (var i = 0; i < arguments.length; i++) {
			component[i] = arguments[i];
		}
	}

	if (Array.isArray(component)) {
		componentPromise = Promise.all(
			component.map(
				id => componentReady(id)
			)
		);
	}
	else {
		let componentPromiseWrapper = componentPromiseWrappers[component];

		if (!componentPromiseWrapper) {
			componentPromiseWrappers[component] = componentPromiseWrapper = _createPromiseWrapper();
		}

		componentPromise = componentPromiseWrapper.promise;
	}

	return componentPromise;
};

/**
 * Destroys the component registered by the provided component id. Invokes the
 * component's own destroy lifecycle methods (destroy or dispose) and deletes
 * the internal references to the component in the component registry.
 *
 * @param {string} componentId The id of the component to destroy
 * @review
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
 * Destroys registered components matching the provided filter function. If
 * no filter function is provided, it will destroy all registered components.
 *
 * @param {Function} filterFn A method that receives a component destroy options
 * and the component itself and returns true if the component should be destroyed
 * @review
 */

const destroyComponents = function(filterFn) {
	var componentIds = Object.keys(components);

	if (filterFn) {
		componentIds = componentIds.filter(
			componentId => {
				return filterFn(
					components[componentId],
					componentConfigs[componentId] || {}
				);
			}
		);
	}

	componentIds.forEach(destroyComponent);
};

/**
 * Clears the component promises map to make sure pending promises won't get
 * accidentally resolved at a later stage if a component with the same id appears
 * causing stale code to run.
 */

const destroyUnfulfilledPromises = function() {
	componentPromiseWrappers = {};
};

/**
 * Retrieves a registered component cached state.
 *
 * @param {string} componentId The id used to register the component
 * @return {object} The state the component had prior to the previous navigation
 * @review
 */

const getComponentCache = function(componentId) {
	const componentCache = componentsCache[componentId];

	return componentCache ? componentCache.state : {};
};

/**
 * Initializes the component cache mechanism
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