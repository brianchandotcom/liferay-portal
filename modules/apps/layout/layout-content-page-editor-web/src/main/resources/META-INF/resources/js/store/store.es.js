import State, {Config} from 'metal-state';
import {DEFAULT_INITIAL_STATE} from './state.es';

/**
 * Connects a given component to a given store, syncing it's properties with it.
 * @param {Component} component
 * @param {Store} store
 * @review
 */
const connect = function(component, store) {
	component._storeChangeListener = store.on(
		'change',
		() => syncStoreState(component, store)
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
 * @param {function[]} reducers
 * @param {string[]} componentIds
 * @return {Store}
 * @review
 */
const createStore = function(initialState, reducers, componentIds = []) {
	const store = new Store(initialState, reducers);

	componentIds.forEach(
		componentId => {
			Liferay
				.componentReady(
					componentId
				)
				.then(
					component => {
						component.store = store;

						connect(component, store);
					}
				);
		}
	);

	return store;
};

/**
 * @param {ConnectedComponent} component
 * @param {Store} store
 */
const syncStoreState = function(component, store) {
	const state = store.getState();

	component.getStateKeys()
		.filter(key => key in state)
		.filter(key => component[key] !== state[key])
		.forEach(
			key => {
				component[key] = state[key];
			}
		);
};

/**
 * Redux-like store that can be used for maintaining a State that can only be
 * modified with pure reducers.
 *
 * Store emits a "change" event with the nextState every time the state has
 * been changed.
 *
 * @review
 */
class Store extends State {

	/**
	 * @param {object} [initialState={}]
	 * @param {function[]} [reducers=[]]
	 * @review
	 */
	constructor(initialState = {}, reducers = []) {
		super();

		this._setInitialState(initialState);
		this.registerReducers(reducers);
	}

	/**
	 * Dispatch an action to the store. Each action is identified by a given
	 * actionType, and can contain an optional payload with any kind of
	 * information.
	 * @param {!string} actionType
	 * @param {string|number|array|object} payload
	 * @return {Store}
	 * @review
	 */
	dispatchAction(actionType, payload) {
		this._dispatchPromise = this._dispatchPromise.then(
			() => this._reducers.reduce(
				(promiseNextState, reducer) => promiseNextState.then(
					nextState => Promise.resolve(
						reducer(nextState, actionType, payload)
					)
				),
				Promise.resolve(this._state)
			)
				.then(
					nextState => {
						this._state = this._getFrozenState(nextState);

						this.emit('change', this._state);

						return this;
					}
				)
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
	 * Adds a new reducer to the store.
	 *
	 * A reducer is a function that receives a state, an actionType and
	 * an optional payload with information, and returns a new state without
	 * altering the original one.
	 *
	 * @param {!function} reducer
	 * @review
	 */
	registerReducer(reducer) {
		this._reducers = [
			...this._reducers,
			reducer
		];
	}

	/**
	 * Adds a list of reducers to the store.
	 * @param {!Array<function>} reducers
	 * @review
	 * @see {Store.registerReducer}
	 */
	registerReducers(reducers) {
		this._reducers = [
			...this._reducers,
			...reducers
		];
	}

	/**
	 * For a given state, returns a frozen copy of it
	 * @param {object} state
	 * @private
	 * @return {object} Frozen state
	 * @review
	 */
	_getFrozenState(state) {
		const differentState = Object.entries(state).some(
			entry => entry[1] !== this._state[entry[0]]
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
		this._state = Object.assign(
		this._state = this._getFrozenState(
			Object.assign(
				{},
			DEFAULT_INITIAL_STATE,
				initialState
			)
		);

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
	 * @default Promise.resolve()
	 * @instance
	 * @memberOf Store
	 * @private
	 * @review
	 * @type {Promise}
	 */
	_dispatchPromise: Config
		.instanceOf(Promise)
		.internal()
		.value(Promise.resolve()),

	/**
	 * @default []
	 * @instance
	 * @memberOf Store
	 * @private
	 * @review
	 * @type {function[]}
	 */
	_reducers: Config
		.arrayOf(Config.func())
		.internal()
		.value([]),

	/**
	 * @default {}
	 * @instance
	 * @memberOf Store
	 * @private
	 * @review
	 * @type {object}
	 */
	_state: Config
		.object()
		.internal()
		.value({})
};

export {connect, disconnect, createStore, Store};
export default Store;