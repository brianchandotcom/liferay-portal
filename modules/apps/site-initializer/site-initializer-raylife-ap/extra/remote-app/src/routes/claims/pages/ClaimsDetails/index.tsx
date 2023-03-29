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

import ActionDetail from '../../../../common/components/action-detail/action-content';
import ActivitiesComponent from '../../../../common/components/activities-table-list';
import MultiSteps from '../../../../common/components/multi-steps';
import Summary from '../../../../common/components/summary';
import {getClaimsData} from '../../../../common/services';
import {setFirstLetterUpperCase} from '../../../../common/utils';
import {CONSTANTS} from '../../../../common/utils/constants';
import {currencyFormatter} from '../../../../common/utils/currencyFormatter';
import {
	dateFormatter,
	dateFormatterLocalString,
} from '../../../../common/utils/dateFormatter';
import {ClaimActivitiesDataType, ClaimDetailDataType, ClaimType} from './Types';
import ClaimActionComponent from './claims-action-details';

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

	const [isClaimSettled, setIsClaimSettled] = useState<boolean>();

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

				claimStatus === 'settled'
					? setIsClaimSettled(true)
					: setIsClaimSettled(false);

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

	const claimStatus = claimData?.claimStatus.key;

	const claimDataJSON = claimData?.dataJSON
		? JSON.parse(claimData?.dataJSON)
		: {};

	const claimActivityData: ClaimActivitiesDataType[] = claimData?.dataJSON
		? [
				{
					activity: claimDataJSON.Detail.Activity.Desc4,
					body: true,
					by: claimDataJSON.Detail.Activity.By4,
					date: `${dateFormatterLocalString(
						claimDataJSON.Detail.Activity.Date4
					)}`,
					message: 'Insured contacted mechanic and completed repair.',
				},
				{
					activity: claimDataJSON.Detail.Activity.Desc3,
					by: claimDataJSON.Detail.Activity.By3,
					date: `${dateFormatterLocalString(
						claimDataJSON.Detail.Activity.Date3
					)}`,
					message:
						'After reviewing all accident details, estimation has been completed and submitted to insured. Insured is responsible for continuing with repairs.',
				},
				{
					activity: claimDataJSON.Detail.Activity.Desc2,
					by: claimDataJSON.Detail.Activity.By2,
					date: `${dateFormatterLocalString(
						claimDataJSON.Detail.Activity.Date2
					)}`,
					message: `I went to insured's residence this morning to assess damage. I also requested police report of accident. Estimation to follow.`,
				},
				{
					activity: claimDataJSON.Detail.Activity.Desc1,
					by: claimDataJSON.Detail.Activity.By1,
					date: `${dateFormatterLocalString(
						claimDataJSON.Detail.Activity.Date1
					)}`,
					message:
						'The insured called me this morning at 10am after getting into an accident. I submitted a claim on behalf of the insured, and the claim is currently waiting for investigation process to begin.',
				},
		  ]
		: [];

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

	const BodyElement = () => (
		<div className="ml-3">
			<div className="font-weight-bold"> Detail below:</div>

			<div className="claim-activities-body-element">
				<div className="d-flex justify-content-between">
					<div>
						<div className="mt-3 text-neutral-6">Mechanic</div>

						<div>
							{claimDataJSON.Detail.VehicleRepair.MechanicName}
						</div>

						<div>
							{claimDataJSON.Detail.VehicleRepair.MechanicPhone}
						</div>
					</div>

					<div>
						<div className="mt-3 text-neutral-6">Order #</div>

						<div className="text-uppercase">
							{
								claimDataJSON.Detail.VehicleRepair
									.MechanicOrderNum
							}
						</div>
					</div>

					<div className="mr-10">
						<div className="mt-3 text-neutral-6"> Completed on</div>

						<div>
							{dateFormatter(
								claimDataJSON.Detail.VehicleRepair
									.MechanicCompleteDate
							)}
						</div>
					</div>
				</div>

				<div>
					<div className="mt-5 text-neutral-6">Cost</div>

					<div>
						{currencyFormatter(
							claimDataJSON.Detail.VehicleRepair.MechanicCost
						)}
					</div>
				</div>
			</div>
		</div>
	);

	return (
		<div className="claim-details-container">
			{claimData && claimActivityData && (
				<>
					{!isClaimSettled && (
						<div className="align-items-center bg-neutral-0 d-flex justify-content-center multi-steps-content">
							<MultiSteps
								steps={steps.filter((step) => step.show)}
							/>
						</div>
					)}

					<div className="claim-detail-content">
						<div className="d-flex py-4 row">
							<div className="col-xl-3 d-flex mb-4">
								<Summary
									dataSummary={
										isClaimSettled
											? summaryClaimDataSettled
											: summaryClaimData
									}
								/>
							</div>

							{claimStatus && (
								<div className="col-xl-9 d-flex mb-4">
									<ActionDetail>
										<ClaimActionComponent
											claimStatus={claimStatus}
										/>
									</ActionDetail>
								</div>
							)}
						</div>

						<ActivitiesComponent
							BodyElement={BodyElement}
							activitiesData={claimActivityData}
						/>
					</div>
				</>
			)}
		</div>
	);
};

export default ClaimDetails;
