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

import {useEffect, useState} from 'react';

import MultiSteps from '../../../../common/components/multi-steps';
import Summary from '../../../../common/components/summary';
import {getClaimsData} from '../../../../common/services';
import {setFirstLetterUpperCase} from '../../../../common/utils';
import {CONSTANTS} from '../../../../common/utils/constants';
import {currencyFormatter} from '../../../../common/utils/currencyFormatter';
import {dateFormatter} from '../../../../common/utils/dateFormatter';

import './index.scss';
import {ClaimDetailDataType, ClaimType} from './Types';

enum STEP {
	APPROVED = 3,
	CLAIMSUBMITTED = 0,
	DECLINED = 7,
	INESTIMATION = 2,
	ININVESTIGATION = 1,
	PEDDINGSETTLEMENT = 5,
	REPAIR = 4,
	SETTLED = 6,
}

const ClaimDetails = () => {
	const [currentStep, setCurrentStep] = useState<number>(1);

	const [claimData, setClaimData] = useState<ClaimType>();

	const [isClaimSettled, setIsClaimSettled] = useState<boolean>(false);

	const handleSetStepTitle = (title: string) => {
		const claimStatus = CONSTANTS.CLAIM_STATUS[title].NAME;

		const splittedUpperCaseCharacter = setFirstLetterUpperCase(claimStatus)
			.split(/(?=[A-Z])/)
			.join(' ');

		if (claimStatus === 'approved' || claimStatus === 'repair') {
			setFirstLetterUpperCase(claimStatus);
		}

		return splittedUpperCaseCharacter;
	};

	const steps = [
		{
			active: currentStep === STEP.CLAIMSUBMITTED,
			complete: currentStep > STEP.CLAIMSUBMITTED,
			show: true,
			title: handleSetStepTitle(
				CONSTANTS.CLAIM_STATUS['claimSubmitted'].NAME
			),
		},
		{
			active: currentStep === STEP.ININVESTIGATION,
			complete: currentStep > STEP.ININVESTIGATION,
			show: true,
			title: handleSetStepTitle(
				CONSTANTS.CLAIM_STATUS['inInvestigation'].NAME
			),
		},
		{
			active: currentStep === STEP.INESTIMATION,
			complete: currentStep > STEP.INESTIMATION,
			show: true,
			title: handleSetStepTitle(
				CONSTANTS.CLAIM_STATUS['inEstimation'].NAME
			),
		},
		{
			active: currentStep === STEP.APPROVED,
			complete: currentStep > STEP.APPROVED,
			show: true,
			title: handleSetStepTitle(CONSTANTS.CLAIM_STATUS['approved'].NAME),
		},
		{
			active: currentStep === STEP.REPAIR,
			complete: currentStep > STEP.REPAIR,
			show: true,
			title: handleSetStepTitle(CONSTANTS.CLAIM_STATUS['repair'].NAME),
		},
		{
			active: currentStep === STEP.PEDDINGSETTLEMENT,
			complete: currentStep > STEP.PEDDINGSETTLEMENT,
			show: true,
			title: handleSetStepTitle(
				CONSTANTS.CLAIM_STATUS['pendingSettlement'].NAME
			),
		},
	];

	const selectCurrentStep = (claimStatus: string) => {
		const status = CONSTANTS.CLAIM_STATUS[claimStatus].INDEX;

		setCurrentStep(status);
	};

	useEffect(() => {
		const queryParams = new URLSearchParams(window.location.search);
		const claimId = Number(Array.from(queryParams.values())[0]);

		if (claimId) {
			getClaimsData(claimId).then((response) => {
				const claimData = response?.data;

				const claimStatus = claimData?.claimStatus?.key;

				if (claimStatus === 'settled') {
					setIsClaimSettled(true);
				}

				selectCurrentStep(claimStatus);

				setClaimData(claimData);
			});
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

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
		<div className="claim-details-container">
			{!isClaimSettled && (
				<div className="align-items-center bg-neutral-0 d-flex justify-content-center multi-steps-content">
					<MultiSteps steps={steps.filter((step) => step.show)} />
				</div>
			)}

			<div className="claim-detail-content d-flex py-4 row">
				<div className="col-xl-3 d-flex mb-4">
					<Summary
						dataSummary={
							isClaimSettled
								? summaryClaimDataSettled
								: summaryClaimData
						}
					/>
				</div>
			</div>
		</div>
	);
};

export default ClaimDetails;
