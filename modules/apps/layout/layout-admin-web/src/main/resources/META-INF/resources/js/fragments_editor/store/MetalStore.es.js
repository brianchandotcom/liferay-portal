import State, {Config} from 'metal-state';

/**
 * Redux-like store that can be used for maintaining
 * an State that can only be modified with pure reducers.
 *
 * MetalStore emits a "change" event with the nextState everytime
 * the state has been changed.
 */

class MetalStore extends State {

	/**
	 * @param {object} [initialState={}]
	 * @param {function[]} [reducers=[]]
	 */

	constructor(initialState = {}, reducers = []) {
		super();

		this._setInitialState(initialState);
		this.registerReducers(reducers);
	}

	/**
	 * Dispatch an action to the store. Each action is identified
	 * by a given actionType, and can contain an optional payload with
	 * any kind of information.
	 * @param {!string} actionType
	 * @param {string|number|array|object|undefined} [payload=undefined]
	 */

	dispatchAction(actionType, payload = undefined) {
		this._state = this._reducers.reduce(
			(nextState, reducer) => {
				return this._getFrozenState(
					reducer(nextState, actionType, payload)
				);
			},
			this._state
		);

		this.emit('change', this._state);
	}

	/**
	 * Returns current state.
	 * Warning: that state cannot be modified anyway.
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
	 */

	registerReducer(reducer) {
		this._reducers = [...this._reducers, reducer];
	}

	/**
	 * Adds a list of reducers to the store.
	 * @param {!function[]} reducers
	 * @see {MetalStore.registerReducer}
	 */

	registerReducers(reducers) {
		this._reducers = [...this._reducers, ...reducers];
	}

	/**
	 * For a given state, returns a frozen copy of it
	 * @param {object} state
	 * @private
	 * @return {object} Frozen state
	 */

	_getFrozenState(state) {
		const frozenState = Object.assign({}, state);
		Object.freeze(frozenState);
		return frozenState;
	}

	/**
	 * Sets the store state to the given state.
	 * This function should not be called after setting the initialState.
	 * @param {!object} initialState
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

MetalStore.STATE = {

	/**
	 * @default []
	 * @instance
	 * @memberOf MetalStore
	 * @private
	 * @type {function[]}
	 */

	_reducers: Config
		.arrayOf(Config.func())
		.internal()
		.value([]),

	/**
	 * @default {}
	 * @instance
	 * @memberOf MetalStore
	 * @private
	 * @type {object}
	 */

	_state: Config
		.object()
		.internal()
		.value({})
};

export {MetalStore};
export default MetalStore;