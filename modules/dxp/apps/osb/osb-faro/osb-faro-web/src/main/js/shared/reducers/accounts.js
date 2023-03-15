import {createReducer} from 'redux-toolbox';
import {Map} from 'immutable';

const actionHandlers = {};

export default createReducer(new Map(), actionHandlers);
