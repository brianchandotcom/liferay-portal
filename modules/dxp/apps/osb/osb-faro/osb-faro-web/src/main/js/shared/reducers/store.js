import {actionTypes} from '../actions/store';
import {createReducer} from 'redux-toolbox';
import {loadState} from 'shared/store/local-storage';
import {Map} from 'immutable';

const actionHandlers = {
	[actionTypes.CLEAR_STORE]: () => loadState() || new Map()
};

export default createReducer(loadState() || new Map(), actionHandlers);
