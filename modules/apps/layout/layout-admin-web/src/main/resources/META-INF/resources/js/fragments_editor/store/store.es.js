import State, {Config} from 'metal-state';

/**
 * Connects a given component to a given store, syncing it's properties with it.
 * @param {Component} component
 * @param {Store} store
 * @review
 */

const connect = function(component, store) {
	store.on(
		'change',
		(nextState) => Object.entries(nextState).forEach(
			([key, value]) => {
				component[key] = value;
			}
		)
	);

	component.on(
		'stateChanged',
		(data) => {
			const nextState = Object.assign({}, store.getState());

			Object.values(data.changes).forEach(
				(change) => {
					if (change.key in nextState) {
						nextState[change.key] = change.newVal;
					}
				}
			);

			store._setInitialState(nextState);
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
	 * @param {string|number|array|object|undefined} [payload=undefined]
	 * @return {Store}
	 * @review
	 */

	dispatchAction(actionType, payload = undefined) {
		this._dispatchPromise = this._dispatchPromise.then(
			() => {
				return this._reducers.reduce(
					(promiseNextState, reducer) => {
						return promiseNextState.then(
							nextState => {
								return Promise.resolve(
									reducer(nextState, actionType, payload)
								);
							}
						);
					},
					Promise.resolve(this._state)
				).then(
					nextState => {
						this._state = this._getFrozenState(nextState);

						this.emit('change', this._state);

						return this;
					}
				);
			}
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
		this._reducers = [...this._reducers, reducer];
	}

	/**
	 * Adds a list of reducers to the store.
	 * @param {!function[]} reducers
	 * @review
	 * @see {Store.registerReducer}
	 */

	registerReducers(reducers) {
		this._reducers = [...this._reducers, ...reducers];
	}

	/**
	 * For a given state, returns a frozen copy of it
	 * @param {object} state
	 * @private
	 * @return {object} Frozen state
	 * @review
	 */

	_getFrozenState(state) {
		const frozenState = Object.assign(
			{},
			state
		);

		Object.freeze(frozenState);

		return frozenState;
	}

	/**
	 * Sets the store state to the given state. This function should not be
	 * called after setting the initialState.
	 * @param {!object} initialState
	 * @private
	 * @review
	 */

	_setInitialState(initialState) {
		this._state = this._getFrozenState(initialState);
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

export {connect, Store};
export default Store;