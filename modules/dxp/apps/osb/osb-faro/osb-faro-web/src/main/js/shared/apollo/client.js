import cache from './cache';
import Uri from 'metal-uri';
import {ApolloClient} from 'apollo-client';
import {
	CommerceAverageOrderValueResolver,
	CommerceAverageRevenuePerAccountResolver,
	CommerceIncompleteOrdersResolver,
	CommerceTotalOrderValueResolver,
	CustomAssetsListResolver,
	EventAnalysisListResolver,
	ExperimentResolver as Experiment
} from './resolvers';
import {get} from 'lodash';
import {HttpLink} from 'apollo-link-http';
import {onError} from 'apollo-link-error';
import {reloadPage} from 'shared/util/router';

const groupIdRegex = /^\/workspace\/([a-z0-9._-]+)/;

/**
 * Fecth With Project ID
 * @param {string} uri
 * @param {object} options
 */
const fetchWithGroupId = (uri, options) => {
	const currentUri = new Uri(window.location.href);
	const {operationName} = JSON.parse(options.body);
	const pathname = currentUri.getPathname();
	const matches = pathname.match(groupIdRegex);

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
		Experiment,
		Query: {
			dashboards(_, params) {
				return CustomAssetsListResolver(params);
			},
			eventAnalysisList(_, params) {
				return EventAnalysisListResolver(params);
			},
			orderAccountAverageCurrencyValues(_, params) {
				return CommerceAverageRevenuePerAccountResolver(params);
			},
			orderAverageCurrencyValues(_, params) {
				return CommerceAverageOrderValueResolver(params);
			},
			orderIncompleteCurrencyValues(_, params) {
				return CommerceIncompleteOrdersResolver(params);
			},
			orderTotalCurrencyValues(_, params) {
				return CommerceTotalOrderValueResolver(params);
			}
		}
	}
});

export default client;
