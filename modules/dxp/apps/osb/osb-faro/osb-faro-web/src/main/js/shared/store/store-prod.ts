import middleware from './configure-middleware';
import reducers from '../reducers';
import {createStore} from 'redux';

export default function configureStore(initialState) {
	return createStore(reducers, initialState, middleware);
}
