import {isFunction} from 'metal';

let componentConfigs = {};
let componentPromiseWrappers = {};
let components = {};
let componentsFn = {};

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

export {
    component,
    componentReady,
    destroyComponent,
    destroyComponents,
    destroyUnfulfilledPromises
};
export default component;