import {actionTypes} from '../actions/distributions';
import {createReducer} from 'redux-toolbox';
import {handleError, handleLoading} from '../util/redux';
import {Map} from 'immutable';

const actionHandlers = {
	[actionTypes.FETCH_DISTRIBUTION_REQUEST]: handleLoading,
	[actionTypes.FETCH_DISTRIBUTION_FAILURE]: handleError
};

export default createReducer(new Map(), actionHandlers);
