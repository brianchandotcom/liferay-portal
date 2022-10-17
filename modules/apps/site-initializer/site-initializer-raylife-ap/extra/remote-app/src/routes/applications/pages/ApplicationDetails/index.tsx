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

import classNames from 'classnames';
import {useCallback, useEffect, useState} from 'react';

import MultiSteps from '../../../../common/components/multi-steps';
import {getApplicationByExternalReferenceCode} from '../../../../common/services';
import {setFirstLetterUpperCase} from '../../../../common/utils';
import {CONSTANTS} from '../../../../common/utils/constants';
import {redirectTo} from '../../../../common/utils/liferay';
import ActionDetail from './components/action-detail';
import QuotedSummary from './components/quoted-summary';
import BoundContent from './components/status-content/bound-content';
import IncompleteContent from './components/status-content/incomplete-content';
import Summary from './components/summary';

enum STEP {
	OPEN = 0,
	INCOMPLETE = 1,
	QUOTED = 2,
	UNDERWRITING = 3,
	REVIEWED = 4,
	BOUND = 5,
}

const classes = {
	bound: 'bound-step-details',
};

const ApplicationDetails = () => {
	const [currentStep, setCurrentStep] = useState<Number>(1);
	const [app, setApp] = useState<any>(null);

	const steps = [
		{
			active: currentStep === STEP.OPEN,
			complete: currentStep > STEP.OPEN,
			show: currentStep === STEP.BOUND || currentStep === STEP.INCOMPLETE,
			title: setFirstLetterUpperCase(CONSTANTS.APPLICATION_STATUS.OPEN),
		},
		{
			active: currentStep === STEP.INCOMPLETE,
			complete: currentStep > STEP.INCOMPLETE,
			show: false || currentStep === STEP.INCOMPLETE,
			title: setFirstLetterUpperCase(
				CONSTANTS.APPLICATION_STATUS.INCOMPLETE
			),
		},
		{
			active: currentStep === STEP.QUOTED,
			complete: currentStep > STEP.QUOTED,
			show: currentStep === STEP.BOUND || currentStep === STEP.INCOMPLETE,
			title: setFirstLetterUpperCase(CONSTANTS.APPLICATION_STATUS.QUOTED),
		},
		{
			active: currentStep === STEP.UNDERWRITING,
			complete: currentStep > STEP.UNDERWRITING,
			show: false || currentStep === STEP.INCOMPLETE,
			title: setFirstLetterUpperCase(
				CONSTANTS.APPLICATION_STATUS.UNDERWRITING
			),
		},
		{
			active: currentStep === STEP.REVIEWED,
			complete: currentStep > STEP.REVIEWED,
			show: false || currentStep === STEP.INCOMPLETE,
			title: setFirstLetterUpperCase(
				CONSTANTS.APPLICATION_STATUS.REVIEWED
			),
		},
		{
			active: currentStep === STEP.BOUND,
			complete: currentStep > STEP.BOUND,
			show: currentStep === STEP.BOUND || currentStep === STEP.INCOMPLETE,
			title: setFirstLetterUpperCase(CONSTANTS.APPLICATION_STATUS.BOUND),
		},
	];

	const selectCurrentStep = (applicationStatus: string) => {
		if (applicationStatus === CONSTANTS.APPLICATION_STATUS.OPEN) {
			return setCurrentStep(STEP.OPEN);
		}

		if (applicationStatus === CONSTANTS.APPLICATION_STATUS.INCOMPLETE) {
			return setCurrentStep(STEP.INCOMPLETE);
		}

		if (applicationStatus === CONSTANTS.APPLICATION_STATUS.QUOTED) {
			return setCurrentStep(STEP.QUOTED);
		}

		if (applicationStatus === CONSTANTS.APPLICATION_STATUS.UNDERWRITING) {
			return setCurrentStep(STEP.UNDERWRITING);
		}

		if (applicationStatus === CONSTANTS.APPLICATION_STATUS.REVIEWED) {
			return setCurrentStep(STEP.REVIEWED);
		}

		if (applicationStatus === CONSTANTS.APPLICATION_STATUS.BOUND) {
			return setCurrentStep(STEP.BOUND);
		}
	};

	const getApplication = async (externalReferenceCode: string) => {
		const application = await getApplicationByExternalReferenceCode(
			externalReferenceCode
		);

		const applicationStatus = application?.data?.applicationStatus?.key;

		selectCurrentStep(applicationStatus);
		setApp(application);
	};

	useEffect(() => {
		const queryParams = new URLSearchParams(window.location.search);
		const externalReferenceCode = queryParams.get('externalReferenceCode');

		if (externalReferenceCode) {
			getApplication(externalReferenceCode);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const handleClick = useCallback(() => {
		redirectTo(
			`app-edit?externalReferenceCode=${app.data?.externalReferenceCode}`
		);
	}, [app]);

	return (
		app && (
			<div className="application-details-container">
				<div className="align-items-center bg-neutral-0 d-flex justify-content-center multi-steps-content">
					<MultiSteps
						classes={
							currentStep === STEP.BOUND ? classes.bound : ''
						}
						steps={steps.filter((step) => step.show)}
					/>
				</div>

				<div className="application-detail-content py-4 row">
					<div className="application-summary-content col-12 col-lg-12 col-md-12 col-sm-12 col-xl-3 mb-4">
						<Summary application={app} />
					</div>

					<div className="application-action-detail-content col-12 col-lg-12 col-md-12 col-sm-12 col-xl-9 mb-4">
						<ActionDetail>
							{currentStep === STEP.INCOMPLETE && (
								<IncompleteContent onClick={handleClick} />
							)}

							{currentStep === STEP.BOUND && <BoundContent />}
						</ActionDetail>
					</div>
				</div>

				<div
					className={classNames('quoted-summary-detail-content row', {
						'd-block': currentStep === STEP.BOUND,
						'd-none': currentStep !== STEP.BOUND,
					})}
				>
					<div className="col-12">
						<QuotedSummary
							externalReferenceCode={
								app?.data?.externalReferenceCode
							}
						/>
					</div>
				</div>
			</div>
		)
	);
};

export default ApplicationDetails;
