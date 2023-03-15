import middleware from './configure-middleware';
import reducers from '../reducers';
import {compose, createStore} from 'redux';

export default function configureStore(initialState) {
	return createStore(
		reducers,
		initialState,
		compose(
			middleware,
			window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ // eslint-disable-line no-underscore-dangle
				? window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__() // eslint-disable-line no-underscore-dangle
				: f => f
		)
	);
}
