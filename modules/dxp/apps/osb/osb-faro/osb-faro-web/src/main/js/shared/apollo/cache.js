import {InMemoryCache} from '@apollo/client';

/*
 * `merge: true` instructs InMemoryCache to shallow-merge incoming writes into
 * the existing cached object instead of replacing it, so the two queries
 * coexist in a single cache entry. See:
 * https://www.apollographql.com/docs/react/caching/cache-field-behavior#merging-non-normalized-objects
 *
 * This is a client-side workaround. The canonical fix is server-side: each
 * root metric type should expose a stable, deterministic `id` derived from the
 * resolver arguments, so InMemoryCache can normalize them automatically. Once
 * the backend ships that change, this typePolicies block can be removed and
 * the metric-card queries should select the new `id` field. Tracking ticket:
 * <add backend ticket id here once filed>.
 */

const metricRootMerge = {
	blog: {merge: true},
	document: {merge: true},
	form: {merge: true},
	journal: {merge: true},
	objectEntry: {merge: true},
	page: {merge: true},
	site: {merge: true}
};

export default new InMemoryCache({
	typePolicies: {
		Query: {
			fields: metricRootMerge
		}
	}
});
