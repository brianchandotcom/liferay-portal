import sendRequest from 'shared/util/request';

interface ILifecycle {
	description?: string;
	id: string;
	name?: string;
	segmentId?: string;
}

interface IFetchLifecycles {
	groupId: string;
}

export async function fetchLifecycles({
	groupId
}: IFetchLifecycles): Promise<ILifecycle[]> {
	return sendRequest({
		method: 'GET',
		path: `contacts/${groupId}/account-lifecycle`
	});
}

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

export async function fetchAccountLifecycles({
	groupId
}: {
	groupId: string;
}): Promise<any> {
	return sendRequest({
		method: 'GET',
		path: `contacts/${groupId}/account-lifecycle`
	});
}

export async function fetchAccountLifecycleStatus({
	accountId,
	accountLifecycleId,
	groupId
}: {
	accountId: string;
	accountLifecycleId: string;
	groupId: string;
}): Promise<any> {
	return sendRequest({
		method: 'GET',
		path: `contacts/${groupId}/account/${accountId}/account-lifecycles/${accountLifecycleId}`
	});
}
