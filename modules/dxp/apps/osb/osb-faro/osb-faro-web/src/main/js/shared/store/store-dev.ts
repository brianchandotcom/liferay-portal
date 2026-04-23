import middleware from './configure-middleware';
import reducers from '../reducers';
import {compose, createStore, StoreEnhancer} from 'redux';

export default function configureStore(initialState: any) {
	return createStore(
		reducers,
		initialState,
		compose(
			middleware,
			window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ // eslint-disable-line no-underscore-dangle
				? window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__() // eslint-disable-line no-underscore-dangle
				: (f: StoreEnhancer<any>) => f
		)
	);
}
