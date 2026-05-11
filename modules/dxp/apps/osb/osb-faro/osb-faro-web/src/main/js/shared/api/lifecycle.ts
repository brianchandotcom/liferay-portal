import sendRequest from 'shared/util/request';

export const DEFAULT_LIFECYCLE_ID = '1';

interface IFetchOverviewMetrics {
	country?: string;
	groupId: string;
	industry?: string;
	lifecycleId: string;
}

export async function fetchOverviewMetrics({
	country,
	groupId,
	industry,
	lifecycleId
}: IFetchOverviewMetrics) {
	return sendRequest({
		data: {
			...(country && {country}),
			...(industry && {industry})
		},
		method: 'GET',
		path: `contacts/${groupId}/account-lifecycle/${lifecycleId}/overview`
	});
}

interface IFetchLifecycleStages {
	country?: string;
	groupId: string;
	industry?: string;
	lifecycleId: string;
}

export async function fetchLifecycleStages({
	country,
	groupId,
	industry,
	lifecycleId
}: IFetchLifecycleStages) {
	return sendRequest({
		data: {
			...(country && {country}),
			...(industry && {industry})
		},
		method: 'GET',
		path: `contacts/${groupId}/account-lifecycle/${lifecycleId}/stages`
	});
}
