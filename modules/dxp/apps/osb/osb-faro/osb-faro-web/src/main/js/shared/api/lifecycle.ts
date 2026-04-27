import sendRequest from 'shared/util/request';

interface IFetchOverviewMetrics {
	country?: string;
	groupId: string;
	industry?: string;
	lifecycleId: number;
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
