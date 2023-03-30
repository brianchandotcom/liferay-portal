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

import React from 'react';

import Summary from '../../../../../common/components/summary';
import {currencyFormatter} from '../../../../../common/utils/currencyFormatter';
import {dateFormatter} from '../../../../../common/utils/dateFormatter';
import {ClaimComponentsType, ClaimDetailDataType} from '../Types';

const ClaimDetailsSummary = ({
	claimData,
	isClaimSettled,
}: ClaimComponentsType) => {
	const applicationData =
		claimData?.r_policyToClaims_c_raylifePolicy
			?.r_quoteToPolicies_c_raylifeQuote
			?.r_applicationToQuotes_c_raylifeApplication;

	const fullName = applicationData?.firstName
		? `${applicationData?.firstName} ${applicationData?.lastName}`
		: applicationData?.firstName;

	const summaryClaimData: ClaimDetailDataType[] = [
		{
			data: dateFormatter(claimData?.claimCreateDate),
			key: 'submittedOn',
			text: 'Submitted on',
		},
		{
			data: claimData?.r_policyToClaims_c_raylifePolicyId,
			icon: true,
			key: 'entryID',
			redirectTo: `${'policy-details'}?externalReferenceCode=${
				claimData?.r_policyToClaims_c_raylifePolicyERC
			}`,
			text: 'Policy Number',
			type: 'link',
		},
		{
			data: fullName,
			key: 'name',
			text: 'Name',
		},
		{
			data: applicationData?.email,
			key: 'email',
			redirectTo: applicationData?.email,
			text: 'Email',
			type: 'link',
		},
		{data: applicationData?.phone, key: 'phone', text: 'Phone'},
	];

	const summaryClaimDataSettled: ClaimDetailDataType[] = [
		{
			data: claimData?.claimStatus?.name,
			greenColor: true,
			key: 'status',
			text: `Status`,
		},
		{
			data: dateFormatter(claimData?.settledDate),
			key: 'settledOn',
			text: `Settled on`,
		},
		{
			data: currencyFormatter(claimData?.claimAmount),
			key: 'settlementAmount',
			text: `Settlement Amount`,
		},
		...summaryClaimData,
	];

	return (
		<Summary
			dataSummary={
				isClaimSettled ? summaryClaimDataSettled : summaryClaimData
			}
		/>
	);
};

export default ClaimDetailsSummary;
