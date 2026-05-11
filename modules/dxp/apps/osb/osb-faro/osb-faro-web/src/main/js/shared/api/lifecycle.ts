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

export async function fetchLifecycleStages({
	groupId,
	lifecycleId
}: {
	groupId: string;
	lifecycleId: string;
}) {
	return sendRequest({
		method: 'GET',
		path: `contacts/${groupId}/account-lifecycle/${lifecycleId}/stages`
	});
}
