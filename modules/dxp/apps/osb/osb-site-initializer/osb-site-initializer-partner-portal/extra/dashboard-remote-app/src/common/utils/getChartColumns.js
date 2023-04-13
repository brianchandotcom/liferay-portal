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

import {currencyFormat} from '.';

export default function getChartColumns(
	mdfCurrency,
	mdfRequests,
	setColumnsMDFChart,
	setTitleChart,
	setValueChart
) {
	const chartColumns = [];

	const totalMDFActivitiesAmount = totalMDFActivities(
		mdfRequests,
		chartColumns
	);

	totalMDFApprovedRequests(mdfRequests, chartColumns);

	totalMDFRequestToClaims(mdfRequests, chartColumns);

	totalApprovedMDFToClaims(mdfRequests, chartColumns);

	expiringSoonTotalActivities(mdfRequests, chartColumns);

	expiredTotalActivites(mdfRequests, chartColumns);
	setValueChart(currencyFormat(totalMDFActivitiesAmount, mdfCurrency));
	setTitleChart('Total MDF');
	setColumnsMDFChart(chartColumns);
}

const expiredDate = 30;

function expiredTotalActivites(mdfRequests, chartColumns) {
	const expiredActivities = mdfRequests?.items
		?.map((activity) =>
			activity?.mdfReqToActs?.filter(
				(request) =>
					new Date(request.endDate).setTime(expiredDate) > new Date()
			)
		)
		.flat();
	const totalExpiredActivities = expiredActivities?.reduce(
		(acc, value) => acc + parseFloat(value.mdfRequestAmount),
		0
	);

	const numberOfExpiredActivities = expiredActivities.length;

	chartColumns.push([
		'Expired',
		totalExpiredActivities,
		numberOfExpiredActivities,
	]);
}

function expiringSoonTotalActivities(mdfRequests, chartColumns) {
	const expiringSoonActivitiesDate = mdfRequests?.items
		?.map((activity) =>
			activity.mdfReqToActs.filter(
				(request) =>
					new Date(request.endDate).setTime(expiredDate) < new Date()
			)
		)
		.flat();

	const totalExpiringSoonActivites = expiringSoonActivitiesDate?.reduce(
		(acc, value) => acc + parseFloat(value.mdfRequestAmount),
		0
	);

	const numberOfExpiringSoonActivites = expiringSoonActivitiesDate.length;

	chartColumns.push([
		'Expiring Soon',
		totalExpiringSoonActivites,
		numberOfExpiringSoonActivites,
	]);
}

function totalApprovedMDFToClaims(mdfRequests, chartColumns) {
	const claimedRequests = mdfRequests?.items
		?.map((claim) =>
			claim.mdfReqToMDFClms.filter(
				(request) => request.mdfClaimStatus.key === 'approved'
			)
		)
		.flat();

	const totalClaimedApprovedRequestsAmount = claimedRequests?.reduce(
		(acc, value) => acc + value?.amountClaimed || 0,
		0
	);

	const numberOfClaimedApprovedRequests = claimedRequests.length;

	chartColumns.push([
		'Claim Approved',
		totalClaimedApprovedRequestsAmount,
		numberOfClaimedApprovedRequests,
	]);
}

function totalMDFRequestToClaims(mdfRequests, chartColumns) {
	const totalClaimedRequestsAmount = mdfRequests?.items?.reduce(
		(acc, value) => acc + parseFloat(value.totalClaimedRequest || 0),
		0
	);
	chartColumns.push(['Claimed', totalClaimedRequestsAmount]);
}

function totalMDFActivities(mdfRequests, chartColumns) {
	const totalMDFActivitiesAmount = mdfRequests?.items?.reduce(
		(prevValue, currValue) =>
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

function totalMDFApprovedRequests(mdfRequests, chartColumns) {
	const mdfApprovedRequests = mdfRequests?.items?.filter(
		(request) => request.mdfRequestStatus.key === 'approved'
	);
	const totalMDFApprovedRequestsAmount = mdfApprovedRequests?.reduce(
		(acc, value) => acc + parseFloat(value.totalMDFRequestAmount),
		0
	);

	const numberOfMDFApprovedRequests = mdfApprovedRequests.length;

	chartColumns.push([
		'Approved',
		totalMDFApprovedRequestsAmount,
		numberOfMDFApprovedRequests,
	]);
}
