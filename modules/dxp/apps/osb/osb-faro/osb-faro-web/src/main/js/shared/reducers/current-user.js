import {actionTypes} from '../actions/users';
import {createReducer} from 'redux-toolbox';
import {RemoteData} from '../util/records';

const actionHandlers = {
	[actionTypes.FETCH_CURRENT_USER_SUCCESS]: (state, action) =>
		state.merge({
			data: String(action.payload.result),
			error: false,
			loading: false
		}),
	[actionTypes.FETCH_CURRENT_USER_REQUEST]: state =>
		state.merge({error: false, loading: true}),
	[actionTypes.FETCH_CURRENT_USER_FAILURE]: state =>
		state.merge({
			error: true,
			loading: false
		})
};

export default createReducer(new RemoteData(), actionHandlers);
