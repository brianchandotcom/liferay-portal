import sendRequest from 'shared/util/request';

export const DEFAULT_LIFECYCLE_ID = '1';

export interface IAccountLifecycle {
	accountId?: string;
	id: string;
}

export interface IAccountLifecycleStageStatus {
	description?: string;
	displayOrder: number;
	endDate?: string;
	id: string;
	maxDuration?: number;
	stageType: string;
	startDate?: string;
}

export interface IAccountLifecycleStatus {
	id: string;
	name: string;
	stages?: IAccountLifecycleStageStatus[];
}

interface IFetchOverviewMetrics {
	country?: string;
	groupId?: string;
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
	groupId?: string;
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
}): Promise<IAccountLifecycle[]> {
	return sendRequest({
		method: 'GET',
		path: `contacts/${groupId}/account-lifecycle`
	});
}
