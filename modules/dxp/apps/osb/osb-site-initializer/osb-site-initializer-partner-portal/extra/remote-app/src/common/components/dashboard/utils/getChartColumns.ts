/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import formatCurrency from './formatCurrency';

export default function getChartColumns(
	mdfCurrency: any,
	mdfRequests: any,
	setColumnsMDFChart: any,
	setTitleChart: any,
	setValueChart: any
) {
	const chartColumns: any[] = [];

	const totalMDFActivitiesAmount = totalMDFActivities(
		mdfRequests,
		chartColumns
	);

	totalMDFApprovedRequests(mdfRequests, chartColumns);

	totalMDFRequestToClaims(mdfRequests, chartColumns);

	totalApprovedMDFToClaims(mdfRequests, chartColumns);

	expiringSoonTotalActivities(mdfRequests, chartColumns);

	expiredTotalActivites(mdfRequests, chartColumns);
	setValueChart(formatCurrency(totalMDFActivitiesAmount, mdfCurrency));
	setTitleChart('Total MDF');
	setColumnsMDFChart(chartColumns);
}

const expiredDate = 30;

function expiredTotalActivites(mdfRequests: any, chartColumns: any) {
	const expiredActivities = mdfRequests?.items
		?.map((activity: any) =>
			activity?.mdfReqToActs?.filter(
				(request: any) =>
					new Date(request.endDate).setTime(expiredDate) >
					new Date().getTime()
			)
		)
		.flat();
	const totalExpiredActivities = expiredActivities?.reduce(
		(acc: any, value: any) => acc + parseFloat(value.mdfRequestAmount),
		0
	);

	const numberOfExpiredActivities = expiredActivities.length;

	chartColumns.push([
		'Expired',
		totalExpiredActivities,
		numberOfExpiredActivities,
	]);
}

function expiringSoonTotalActivities(mdfRequests: any, chartColumns: any) {
	const expiringSoonActivitiesDate = mdfRequests?.items
		?.map((activity: any) =>
			activity.mdfReqToActs.filter(
				(request: any) =>
					new Date(request.endDate).setTime(expiredDate) <
					new Date().getTime()
			)
		)
		.flat();

	const totalExpiringSoonActivites = expiringSoonActivitiesDate?.reduce(
		(acc: any, value: any) => acc + parseFloat(value.mdfRequestAmount),
		0
	);

	const numberOfExpiringSoonActivites = expiringSoonActivitiesDate.length;

	chartColumns.push([
		'Expiring Soon',
		totalExpiringSoonActivites,
		numberOfExpiringSoonActivites,
	]);
}

function totalApprovedMDFToClaims(mdfRequests: any, chartColumns: any) {
	const claimedRequests = mdfRequests?.items
		?.map((claim: any) =>
			claim.mdfReqToMDFClms.filter(
				(request: any) => request.mdfClaimStatus.key === 'approved'
			)
		)
		.flat();

	const totalClaimedApprovedRequestsAmount = claimedRequests?.reduce(
		(acc: any, value: any) => acc + value?.totalClaimAmount || 0,
		0
	);

	const numberOfClaimedApprovedRequests = claimedRequests.length;

	chartColumns.push([
		'Claim Approved',
		totalClaimedApprovedRequestsAmount,
		numberOfClaimedApprovedRequests,
	]);
}

function totalMDFRequestToClaims(mdfRequests: any, chartColumns: any) {
	const totalClaimedRequestsAmount = mdfRequests?.items?.reduce(
		(acc: any, value: any) =>
			acc + parseFloat(value.totalClaimedRequest || 0),
		0
	);
	chartColumns.push(['Claimed', totalClaimedRequestsAmount]);
}

function totalMDFActivities(mdfRequests: any, chartColumns: any) {
	const totalMDFActivitiesAmount = mdfRequests?.items?.reduce(
		(prevValue: any, currValue: any) =>
			prevValue + (parseFloat(currValue.totalMDFRequestAmount) || 0),
		0
	);

	const numberOfMDFActivities = mdfRequests?.items?.length;

	chartColumns.push([
		'Requested',
		totalMDFActivitiesAmount,
		numberOfMDFActivities,
	]);

	return totalMDFActivitiesAmount;
}

function totalMDFApprovedRequests(mdfRequests: any, chartColumns: any) {
	const mdfApprovedRequests = mdfRequests?.items?.filter(
		(request: any) => request.mdfRequestStatus.key === 'approved'
	);
	const totalMDFApprovedRequestsAmount = mdfApprovedRequests?.reduce(
		(acc: any, value: any) => acc + parseFloat(value.totalMDFRequestAmount),
		0
	);

	const numberOfMDFApprovedRequests = mdfApprovedRequests.length;

	chartColumns.push([
		'Approved',
		totalMDFApprovedRequestsAmount,
		numberOfMDFApprovedRequests,
	]);
}
