import {actionTypes} from '../actions/sidebar';
import {createReducer} from 'redux-toolbox';
import {Map} from 'immutable';

const actionHandlers = {
	[actionTypes.COLLAPSE_SIDEBAR]: (state, {payload}) => {
		const {collapsed, currentUserId} = payload;

		return state.set(String(currentUserId), collapsed);
	}
};

export default createReducer(new Map(), actionHandlers);
