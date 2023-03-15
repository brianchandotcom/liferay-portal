import {actionTypes} from '../actions/data-sources';
import {createReducer} from 'redux-toolbox';
import {handleError, handleLoading} from '../util/redux';
import {Map} from 'immutable';

const actionHandlers = {
	[actionTypes.DELETE_DATA_SOURCE_SUCCESS]: (state, {meta}) =>
		state.delete(String(meta.id)),
	[actionTypes.FETCH_DATA_SOURCE_REQUEST]: handleLoading,
	[actionTypes.FETCH_DATA_SOURCE_FAILURE]: handleError
};

export default createReducer(new Map(), actionHandlers);
