import api from 'shared/middleware/api';
import normalizer from 'shared/middleware/normalizer';
import thunk from 'redux-thunk';
import {applyMiddleware} from 'redux';

const middleware = applyMiddleware(api, normalizer, thunk);

export default middleware;
