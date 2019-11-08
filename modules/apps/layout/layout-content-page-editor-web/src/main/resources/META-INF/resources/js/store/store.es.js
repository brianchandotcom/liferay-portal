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

import State, {Config} from 'metal-state';

import {DEFAULT_INITIAL_STATE} from './state.es';

/**
 * ID of the development devTool that may be connected to store.
 * We are relying on redux-devtools, so we can continue using
 * them when we move to a proper state-management library.
 *
 * They provide a global hook that is available when the browser
 * has redux-devtools-extension installed:
 *
 * http://extension.remotedev.io/#usage
 *
 * @review
 */
const STORE_DEVTOOLS_ID = '__REDUX_DEVTOOLS_EXTENSION__';

/**
 * Internal store instance
 * @type {Store}
 */
let _store;

/**
 * Connects a given component to a given store, syncing it's properties with it.
 * @param {Component} component
 * @param {Store} store
 * @review
 */
const connect = function(component, store) {
	component._storeChangeListener = store.on('change', () =>
		syncStoreState(component, store)
	);

	syncStoreState(component, store);
};

/**
 * Disconnects a given component from it's store
 * @param {ConnectedComponent} component
 */
const disconnect = function(component) {
	if (component._storeChangeListener) {
		component._storeChangeListener.removeListener();

		component._storeChangeListener = null;
	}
};

/**
 * Creates a store and links the given components to it.
 * Each component will receive the store as `store` attribute.
 * @param {object} initialState
 * @param {function} reducer
 * @param {string[]} componentIds
 * @return {Store}
 * @review
 */
const createStore = function(initialState, reducer, componentIds = []) {
	_store = new Store(initialState, reducer);

	componentIds.forEach(componentId => {
		Liferay.componentReady(componentId).then(component => {
			component.store = _store;

			connect(
				component,
				_store
			);
		});
	});

	return _store;
};

/**
 * Returns the existing store state.
 * Warning: this is a workaround to get constants from DisplayContext
 * until we split them into another module.
 * @return {object}
 */
const getState = function() {
	return _store ? _store.getState() : {};
};

/**
 * @param {ConnectedComponent} component
 * @param {Store} store
 */
const syncStoreState = function(component, store) {
	const state = store.getState();

	component
		.getStateKeys()
		.filter(key => key in state)
		.filter(key => component[key] !== state[key])
		.forEach(key => {
			component[key] = state[key];
		});
};

/**
 * Redux-like store.
 * Store emits a "change" event with the nextState every time the state has
 * been changed.
 *
 * @review
 */
class Store extends State {
	/**
	 * @param {object} [initialState={}]
	 * @param {function} [reducer]
	 * @review
	 */
	constructor(initialState = {}, reducer = state => state) {
		super();

		this.dispatch = this.dispatch.bind(this);
		this.getState = this.getState.bind(this);

		this._setInitialState(initialState);
		this.registerReducer(reducer);

		if (
			process.env.NODE_ENV === 'development' &&
			STORE_DEVTOOLS_ID in window
		) {
			this._devTools = window[STORE_DEVTOOLS_ID].connect();

			this._devTools.init(this._state);
		}
	}

	/**
	 * @inheritDoc
	 */
	disposed() {
		if (process.env.NODE_ENV === 'development' && this._devTools) {
			this._devTools.disconnect();
		}
	}

	/**
	 * Dispatch an action to the store. Each action is identified by a given
	 * actionType, and can contain an optional payload with any kind of
	 * information.
	 * @param {{type: string}|function(function, function): Promise|void} action
	 * @return {Store}
	 * @review
	 */
	dispatch(action) {
		if (typeof action === 'function') {
			this._dispatchPromise = this._dispatchPromise.then(() =>
				Promise.resolve(action(this.dispatch, this.getState))
			);
		} else {
			this._dispatchPromise = this._dispatchPromise
				.then(() => this._reducer(this._state, action))
				.then(nextState => {
					if (this._state !== nextState) {
						this._state = this._getFrozenState(nextState);

						this.emit('change', this._state);

						if (
							process.env.NODE_ENV === 'development' &&
							this._devTools
						) {
							this._devTools.send(action, this._state);
						}
					}

					return new Promise(resolve => {
						requestAnimationFrame(() => {
							resolve(this);
						});
					});
				});
		}

		return this;
	}

	done(callback) {
		this._dispatchPromise = this._dispatchPromise.then(() =>
			callback(this)
		);

		return this;
	}

	failed(callback) {
		this._dispatchPromise = this._dispatchPromise.catch(error =>
			callback(error)
		);

		return this;
	}

	/**
	 * Returns current state.
	 * Warning: that state cannot be modified anyway.
	 * @return {object} Current state
	 * @review
	 */
	getState() {
		return this._state;
	}

	/**
	 * Set's store reducer.
	 *
	 * A reducer is a function that receives a state, an actionType and
	 * an optional payload with information, and returns a new state without
	 * altering the original one.
	 *
	 * @param {function} reducer
	 * @review
	 */
	registerReducer(reducer) {
		this._reducer = reducer;
	}

	/**
	 * For a given state, returns a frozen copy of it
	 * @param {object} state
	 * @private
	 * @return {object} Frozen state
	 * @review
	 */
	_getFrozenState(state) {
		const differentState =
			!this._state ||
			Object.entries(state).some(
				([key, value]) => this._state[key] !== value
			);

		if (differentState) {
			this._state = state;

			Object.freeze(this._state);
		}

		return this._state;
	}

	/**
	 * Sets the store state to the given state. This function should not be
	 * called after setting the initialState.
	 * The given initial state is combined with DEFAULT_INITIAL_STATE to provide
	 * default values for unknown data.
	 * @param {!Object} initialState
	 * @return {Object}
	 * @private
	 * @review
	 */
	_setInitialState(initialState) {
		if (this._state) {
			throw new Error('State already initialized');
		}

		this._state = this._getFrozenState({
			...DEFAULT_INITIAL_STATE,
			...initialState,
			selectedSidebarPanelId: initialState.lockedSegmentsExperience
				? null
				: initialState.selectedSidebarPanelId
		});

		return this._state;
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
Store.STATE = {
	/**
	 * Redux devtools
	 * @instance
	 * @memberOf Store
	 * @private
	 * @review
	 * @type {any|null}
	 */
	_devTools: Config.any()
		.internal()
		.value(null),

	/**
	 * @default Promise.resolve()
	 * @instance
	 * @memberOf Store
	 * @private
	 * @review
	 * @type {Promise}
	 */
	_dispatchPromise: Config.instanceOf(Promise)
		.internal()
		.value(Promise.resolve()),

	/**
	 * @default []
	 * @instance
	 * @memberOf Store
	 * @private
	 * @review
	 * @type {function}
	 */
	_reducer: Config.func()
		.internal()
		.value([]),

	/**
	 * @default null
	 * @instance
	 * @memberOf Store
	 * @private
	 * @review
	 * @type {object}
	 */
	_state: Config.object()
		.internal()
		.value(null)
};

export {connect, disconnect, createStore, getState, Store};
export default Store;
