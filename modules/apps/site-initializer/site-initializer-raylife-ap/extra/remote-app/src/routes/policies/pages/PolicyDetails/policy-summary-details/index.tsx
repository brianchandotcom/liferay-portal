/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {useMemo} from 'react';

import Summary from '../../../../../common/components/summary';

type SummaryType = {
	summaryData: {
		applicationDataJSON: string;
		boundDate: string;
		commission: number;
		email: string;
		endDate: string;
		phone: string;
		policyDataJSON: JSON;
		termPremium: number;
	};
};

function dateFormatter(date: string) {
	const formattedDate = new Date(date).toLocaleDateString('en-us', {
		day: '2-digit',
		month: '2-digit',
		timeZone: 'UTC',
		year: 'numeric',
	});

	return formattedDate;
}

function valueFormatter(value: number) {
	const dollarValue = Intl.NumberFormat('en-US', {
		currency: 'USD',
		style: 'currency',
	});

	return dollarValue.format(value);
}

const PolicySummary = ({summaryData}: SummaryType) => {
	const applicationDataJSON = summaryData.applicationDataJSON
		? JSON.parse(summaryData.applicationDataJSON)
		: {};

	const {driverInfo} = applicationDataJSON;

	const policyEndDate = Date.parse(summaryData?.endDate);

	const differenceOfDays = Math.abs(+policyEndDate - +new Date());

	const renewalDue = Math.floor(differenceOfDays / (1000 * 60 * 60 * 24)) + 1;

	const totalClaimAmount = 1963.58;

	const coverageLimit = '$2,500.00/$100,000.00';

	const summaryPolicyData = useMemo(
		() => [
			{
				data: `${dateFormatter(
					summaryData?.boundDate
				)} - ${dateFormatter(summaryData?.endDate)}`,
				key: 'currentPeriod',
				text: 'Current Period',
			},
			{
				data: renewalDue,
				key: 'renewalDue',
				text: 'Renewal Due',
			},
			{
				data: `${valueFormatter(
					Number(summaryData?.termPremium?.toFixed(2))
				)}`,
				key: 'totalPremium',
				text: 'Total Premium',
			},
			{
				data: `${valueFormatter(
					Number(summaryData?.commission?.toFixed(2))
				)}`,
				key: 'commission',
				text: 'Commission',
			},
			{
				data: `${valueFormatter(totalClaimAmount)}`,
				key: 'totalClaimAmount',
				text: 'Total Claim Amount',
			},
			{
				data: coverageLimit,
				key: 'coverageLimit',
				text: 'Coverage Limit (Used/Available)',
			},
			{
				data: `${driverInfo?.form[0]?.firstName} ${driverInfo?.form[0]?.lastName}`,
				key: 'primaryHolder',
				text: 'Primary Holder',
			},
			{data: summaryData.phone, key: 'phone', text: 'Phone'},
			{
				data: summaryData.email,
				key: 'email',
				redirectTo: summaryData.email,
				text: 'Email',
				type: 'link',
			},
		],
		[summaryData, renewalDue, totalClaimAmount, coverageLimit, driverInfo]
	);

	return <Summary dataSummary={summaryPolicyData} />;
};

export default PolicySummary;
