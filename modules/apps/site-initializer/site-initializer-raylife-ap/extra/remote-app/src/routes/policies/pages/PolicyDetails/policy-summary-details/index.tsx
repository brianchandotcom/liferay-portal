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

import Summary from '../../../../../common/components/summary';

const PolicySummary = ({application, policy}: any) => {
	const {data} = policy;

	const applicationDataJSON = application?.dataJSON
		? JSON.parse(application?.dataJSON)
		: {};

	const {driverInfo} = applicationDataJSON;

	const policyEndDate = Date.parse(data?.endDate);

	const differenceOfDays = Math.abs(+policyEndDate - +new Date());

	const renewalDue = Math.floor(differenceOfDays / (1000 * 60 * 60 * 24)) + 1;

	const totalClaimAmount = 1963.58;

	const coverageLimit = '$2,500.00/$100,000.00';

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

	const summaryPolicyData = [
		{
			data: `${dateFormatter(data?.boundDate)} - ${dateFormatter(
				data?.endDate
			)}`,
			key: 'currentPeriod',
			text: 'Current Period',
		},
		{
			data: renewalDue,
			key: 'renewalDue',
			text: 'Renewal Due',
		},
		{
			data: `${valueFormatter(data?.termPremium?.toFixed(2))}`,
			key: 'totalPremium',
			text: 'Total Premium',
		},
		{
			data: `${valueFormatter(data?.commission?.toFixed(2))}`,
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
		{data: application?.phone, key: 'phone', text: 'Phone'},
		{
			data: application?.email,
			key: 'email',
			redirectTo: application?.email,
			text: 'Email',
			type: 'link',
		},
	];

	return <Summary dataSummary={summaryPolicyData} />;
};

export default PolicySummary;
