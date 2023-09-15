/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

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
	setValueChart(totalMDFActivitiesAmount);
	setTitleChart('Total MDF ');
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

	const claimedRequests = mdfRequests?.items
		?.map((claim: any) =>
			claim.mdfReqToMDFClms.filter(
				(request: any) => request.mdfClaimStatus.key === 'claimPaid'
			)
		)
		.flat();

	const totalClaimedActivites = claimedRequests.reduce(
		(acc: any, value: any) =>
			acc + parseFloat(value.mdfClaimActivitiesCount),
		0
	);

	chartColumns.push([
		'Claimed',
		totalClaimedRequestsAmount,
		totalClaimedActivites,
	]);
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
