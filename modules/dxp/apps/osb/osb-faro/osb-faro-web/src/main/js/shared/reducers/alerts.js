import {actionTypes} from '../actions/alerts';
import {createReducer} from 'redux-toolbox';
import {Map} from 'immutable';

const actionHandlers = {
	[actionTypes.ADD_ALERT]: (state, {payload}) => {
		const {alertType, id, message} = payload;

		return state.mergeIn([id], {
			alertType,
			id,
			message
		});
	},

	[actionTypes.REMOVE_ALERT]: (state, {payload}) => {
		const {id} = payload;

		return state.delete(id);
	},

	[actionTypes.UPDATE_ALERT]: (state, {payload}) => {
		const {alertType, id, message} = payload;

		return state.mergeIn([id], {
			alertType,
			message
		});
	}
};

export default createReducer(new Map(), actionHandlers);
