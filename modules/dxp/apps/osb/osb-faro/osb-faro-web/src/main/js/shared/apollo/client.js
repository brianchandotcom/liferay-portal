import cache from './cache';
import {ApolloClient} from 'apollo-client';
import {DEVELOPER_MODE} from 'shared/util/constants';
import {get} from 'lodash';
import {HttpLink} from 'apollo-link-http';
import {onError} from 'apollo-link-error';
import {reloadPage} from 'shared/util/router';
import {resolvers} from './resolvers/resolvers';

const groupIdRegex = /^\/workspace\/([a-z0-9._-]+)/;

/**
 * Fecth With Project ID
 * @param {string} uri
 * @param {object} options
 */
const fetchWithGroupId = (uri, options) => {
	const currentUri = new URL(window.location.href);
	const {operationName} = JSON.parse(options.body);
	const matches = currentUri.pathname.match(groupIdRegex);

	if (matches !== null && matches.length > 1) {
		const groupId = matches[1];

		return fetch(
			`${uri}?opname=${operationName}&projectGroupId=${groupId}`,
			options
		);
	}

	return fetch(`${uri}/&opname=${operationName}`, options);
};

const client = new ApolloClient({
	addTypename: true,
	cache,
	connectToDevTools: FARO_DEV_MODE,
	defaultOptions: {
		watchQuery: {
			notifyOnNetworkStatusChange: true
		}
	},
	link: HttpLink.from([
		onError(({operation}) => {
			const status = get(operation.getContext(), ['response', 'status']);

			if (status === 401) {
				reloadPage();
			}
		}),
		new HttpLink({
			credentials: 'same-origin',
			fetch: fetchWithGroupId,
			uri: '/o/cerebro/graphql'
		})
	]),

	resolvers: {
		/**
		 * Queries must render only in the dev mode to avoid
		 * add unnecessary code in the final bundle
		 */
		Query: DEVELOPER_MODE ? resolvers : {}
	}
});

export default client;
