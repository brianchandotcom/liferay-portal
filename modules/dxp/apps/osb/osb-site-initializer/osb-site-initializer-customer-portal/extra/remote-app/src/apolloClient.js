import {ApolloClient, InMemoryCache} from '@apollo/client';
import {API_BASE_URL} from './common/utils';

const authToken = Liferay?.authToken;

const client = new ApolloClient({
	cache: new InMemoryCache(),
	headers: {
		'x-csrf-token': authToken,
	},
	uri: `${API_BASE_URL}/o/graphql`,
});

export default client;
