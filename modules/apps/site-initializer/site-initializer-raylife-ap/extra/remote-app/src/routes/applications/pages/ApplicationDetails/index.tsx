/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {useCallback, useEffect, useState} from 'react';

import ActionDetail from '../../../../common/components/action-detail/action-content';
import NoActionRequired from '../../../../common/components/action-detail/action-content/no-action-required';
import MultiSteps from '../../../../common/components/multi-steps';
import {getApplicationByExternalReferenceCode} from '../../../../common/services';
import {setFirstLetterUpperCase} from '../../../../common/utils';
import {CONSTANTS} from '../../../../common/utils/constants';
import {redirectTo} from '../../../../common/utils/liferay';
import QuotedSummary from './components/quoted-summary';
import IncompleteContent from './components/status-content/incomplete-content';
import UnderwritingContent from './components/status-content/reviewed-rejected-content';
import Summary from './components/summary';

enum STEP {
	OPEN = 0,
	INCOMPLETE = 1,
	QUOTED = 2,
	UNDERWRITING = 3,
	REVIEWED = 4,
	REJECTED = 5,
	BOUND = 6,
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
			show:
				currentStep === STEP.BOUND ||
				currentStep === STEP.INCOMPLETE ||
				currentStep === STEP.REVIEWED ||
				currentStep === STEP.REJECTED,
			title: setFirstLetterUpperCase(
				CONSTANTS.APPLICATION_STATUS['open'].NAME
			),
		},
		{
			active: currentStep === STEP.INCOMPLETE,
			complete: currentStep > STEP.INCOMPLETE,
			show: false || currentStep === STEP.INCOMPLETE,
			title: setFirstLetterUpperCase(
				CONSTANTS.APPLICATION_STATUS['incomplete'].NAME
			),
		},
		{
			active: currentStep === STEP.QUOTED,
			complete: currentStep > STEP.QUOTED,
			show:
				currentStep === STEP.BOUND ||
				currentStep === STEP.INCOMPLETE ||
				currentStep === STEP.REVIEWED ||
				currentStep === STEP.REJECTED,
			title: setFirstLetterUpperCase(
				CONSTANTS.APPLICATION_STATUS['quoted'].NAME
			),
		},
		{
			active: currentStep === STEP.UNDERWRITING,
			complete: currentStep > STEP.UNDERWRITING,
			show:
				false ||
				currentStep === STEP.INCOMPLETE ||
				currentStep === STEP.REVIEWED ||
				currentStep === STEP.REJECTED,
			title: setFirstLetterUpperCase(
				CONSTANTS.APPLICATION_STATUS['underwriting'].NAME
			),
		},
		{
			active:
				currentStep === STEP.REVIEWED || currentStep === STEP.REJECTED,
			complete: currentStep > STEP.REVIEWED,
			show:
				false ||
				currentStep === STEP.INCOMPLETE ||
				currentStep === STEP.REVIEWED ||
				currentStep === STEP.REJECTED,
			title: setFirstLetterUpperCase(
				CONSTANTS.APPLICATION_STATUS['reviewed'].NAME
			),
		},
		{
			active: currentStep === STEP.BOUND,
			complete: currentStep > STEP.BOUND,
			show:
				currentStep === STEP.BOUND ||
				currentStep === STEP.INCOMPLETE ||
				currentStep === STEP.REVIEWED ||
				currentStep === STEP.REJECTED,
			title: setFirstLetterUpperCase(
				CONSTANTS.APPLICATION_STATUS['bound'].NAME
			),
		},
	];

	const selectCurrentStep = (applicationStatus: string) => {
		const status = CONSTANTS.APPLICATION_STATUS[applicationStatus].INDEX;

		setCurrentStep(status);
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

				<div className="application-detail-content d-flex py-4 row">
					<div className="application-summary-content col-12 col-lg-12 col-md-12 col-sm-12 col-xl-3 d-flex mb-4">
						<Summary application={app} />
					</div>

					<div className="application-action-detail-content col-12 col-lg-12 col-md-12 col-sm-12 col-xl-9 d-flex mb-4">
						<ActionDetail>
							{currentStep === STEP.INCOMPLETE && (
								<IncompleteContent onClick={handleClick} />
							)}

							{currentStep === STEP.BOUND && <NoActionRequired />}

							{currentStep === STEP.REVIEWED && (
								<UnderwritingContent
									externalReferenceCode={
										app?.data?.externalReferenceCode
									}
								/>
							)}

							{currentStep === STEP.REJECTED && (
								<UnderwritingContent id={app?.data?.id} />
							)}
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
