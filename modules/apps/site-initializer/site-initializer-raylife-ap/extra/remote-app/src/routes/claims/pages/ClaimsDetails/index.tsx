/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

import ActionDetail from '../../../../common/components/action-detail/action-content';
import MultiSteps from '../../../../common/components/multi-steps';
import {getClaimsData} from '../../../../common/services';
import {setFirstLetterUpperCase} from '../../../../common/utils';
import {CONSTANTS} from '../../../../common/utils/constants';
import {ClaimType} from './Types';
import ClaimActionComponent from './claim-action-details';
import ClaimDetailsActivities from './claim-activities-details';
import ClaimDetailsSummary from './claim-summary-details';
import ClaimNavigator from './claims-navigator-details';

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

	const claimStatus = claimData?.claimStatus.key;

	return (
		<div className="claim-details-container">
			{claimData && (
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
								<ClaimDetailsSummary
									claimData={claimData}
									isClaimSettled={isClaimSettled}
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

						<ClaimNavigator claimData={claimData} />

						<ClaimDetailsActivities claimData={claimData} />
					</div>
				</>
			)}
		</div>
	);
};

export default ClaimDetails;
