/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import classNames from 'classnames';
import {ReactNode, useCallback, useContext, useState} from 'react';

import MultiSteps from '../../../../../common/components/multi-steps';
import ClayIconProvider from '../../../../../common/context/ClayIconProvider';
import {
	createOrUpdateRaylifeApplication,
	createRaylifeAutoPolicy,
	exitRaylifeApplication,
	getApplicationsById,
} from '../../../../../common/services';
import {createRaylifeAutoQuote} from '../../../../../common/services/Quote';
import {CONSTANTS} from '../../../../../common/utils/constants';
import {redirectTo} from '../../../../../common/utils/liferay';
import {
	ACTIONS,
	NewApplicationAutoContext,
} from '../../../context/NewApplicationAutoContextProvider';

type DriverInfoProps = {
	children: ReactNode;
};

const tooltipTitle =
	'You must enter first name, last name, phone number and email address to save this quote.';

const NewApplicationAuto = ({children}: DriverInfoProps) => {
	const [state, dispatch] = useContext(NewApplicationAutoContext);

	const [saveChanges, setSaveChanges] = useState<boolean>(false);

	const {form} = state?.steps?.driverInfo;

	const steps = [
		{
			active: state.currentStep === 0,
			complete: state.currentStep > 0,
			show: true,
			title: state.steps.contactInfo.name,
		},
		{
			active: state.currentStep === 1,
			complete: state.currentStep > 1,
			show: true,
			title: state.steps.vehicleInfo.name,
		},
		{
			active: state.currentStep === 2,
			complete: state.currentStep > 2,
			show: true,
			title: state.steps.driverInfo.name,
		},
		{
			active: state.currentStep === 3,
			complete: state.currentStep > 3,
			show: true,
			title: state.steps.coverage.name,
		},
		{
			active: state.currentStep === 4,
			complete: state.currentStep > 4,
			show: true,
			title: state.steps.review.name,
		},
	];

	const handleSave = useCallback(
		async (applicationStatus: string) => {
			const response = await createOrUpdateRaylifeApplication(
				state,
				applicationStatus
			);

			const {
				data: {externalReferenceCode, id},
			} = response;

			dispatch({
				payload: {externalReferenceCode, id},
				type: ACTIONS.SET_APPLICATION,
			});
		},
		[dispatch, state]
	);

	const handleNextClick = async (event: any) => {
		setSaveChanges(true);

		event?.preventDefault();
		dispatch({payload: false, type: ACTIONS.SET_HAS_FORM_CHANGE});

		const hasUnderwritingStatus = form?.some(
			(currentIndex) => currentIndex.hasAccidentOrCitations === 'yes'
		);

		const hasOpenOrBoundStatus =
			state.currentStep < 4
				? CONSTANTS.APPLICATION_STATUS['open'].NAME
				: CONSTANTS.APPLICATION_STATUS['bound'].NAME;

		const applicationStatus = hasUnderwritingStatus
			? CONSTANTS.APPLICATION_STATUS['underwriting'].NAME
			: hasOpenOrBoundStatus;

		if (state.currentStep < steps.length - 1) {
			dispatch({
				payload: state.currentStep + 1,
				type: ACTIONS.SET_CURRENT_STEP,
			});
		}

		if (
			state.currentStep === 2 &&
			form[0]?.accidentCitation[0]?.value ===
				'Citation - Driving under the influence'
		) {
			handleSave(CONSTANTS.APPLICATION_STATUS['rejected'].NAME);

			return dispatch({
				payload: 5,
				type: ACTIONS.SET_CURRENT_STEP,
			});
		}

		await handleSave(applicationStatus);

		if (state.currentStep === 4) {
			const quote = await createRaylifeAutoQuote(state);

			const quoteId = quote?.data?.id;

			const application = await getApplicationsById(
				Number(state.applicationId)
			);

			const applicationData = application?.data?.items[0];

			createRaylifeAutoPolicy(applicationData, quoteId);

			redirectTo('Applications');
			createRaylifeAutoQuote(state);
		}
	};

	const handlePreviousClick = () => {
		setSaveChanges(false);
		dispatch({payload: false, type: ACTIONS.SET_HAS_FORM_CHANGE});
		if (state.currentStep < 5) {
			return dispatch({
				payload: state.currentStep - 1,
				type: ACTIONS.SET_CURRENT_STEP,
			});
		}

		dispatch({
			payload: 2,
			type: ACTIONS.SET_CURRENT_STEP,
		});
	};

	const handleSaveChanges = async () => {
		setSaveChanges(true);
		dispatch({payload: false, type: ACTIONS.SET_HAS_FORM_CHANGE});

		await handleSave(CONSTANTS.APPLICATION_STATUS['open'].NAME);
	};

	const handleExitClick = () => {
		exitRaylifeApplication(
			state,
			CONSTANTS.APPLICATION_STATUS['incomplete'].NAME
		).then((response) => {
			const {
				data: {externalReferenceCode, id},
			} = response;
			dispatch({
				payload: {externalReferenceCode, id},
				type: ACTIONS.SET_APPLICATION,
			});
		});

		redirectTo('dashboard');
	};

	const handleCancelClick = () => {
		redirectTo('Applications');
	};

	const ChangeStatusMessage = ({text}: any) => (
		<div className="text-neutral-7 text-paragraph-sm">
			<ClayIcon
				className={classNames('', {
					'text-accent-4': state.hasFormChanges,
					'text-success': saveChanges && !state.hasFormChanges,
				})}
				symbol="simple-circle"
			></ClayIcon>

			<span>{text}</span>
		</div>
	);

	const getChangeStatusMessage = () => {
		let statusMessage = '';

		if (state.hasFormChanges) {
			statusMessage = 'Unsaved Changes';
		}
		else if (!state.hasFormChanges && !saveChanges) {
			statusMessage = 'No Changes Made';
		}
		else if (saveChanges && !state.hasFormChanges) {
			statusMessage = 'All Changes Saved';
		}

		return <ChangeStatusMessage text={statusMessage} />;
	};

	return (
		<ClayIconProvider>
			<div className="container">
				<div className="border mt-4 sheet sheet-dataset-content">
					<div className="align-items-center d-flex flex-column flex-md-row justify-content-md-between">
						<h5 className="my-2 my-md-0">New Application</h5>

						<div className="my-2 my-md-0">
							{getChangeStatusMessage()}
						</div>

						<div className="my-2 my-md-0">
							<ClayButton
								className="text-uppercase"
								displayType={null}
								onClick={() => handleExitClick()}
								small={true}
							>
								Exit
							</ClayButton>

							{!state.isAbleToBeSave ||
							state.currentStep === 5 ? (
								<ClayTooltipProvider>
									<ClayButton
										aria-disabled="true"
										className="disabled text-uppercase"
										data-tooltip-align="top"
										displayType="secondary"
										onClick={() => handleSaveChanges()}
										small={true}
										title={tooltipTitle}
									>
										Save
									</ClayButton>
								</ClayTooltipProvider>
							) : (
								<ClayButton
									className="text-uppercase"
									disabled={false}
									displayType="secondary"
									onClick={() => handleSaveChanges()}
									small={true}
								>
									Save
								</ClayButton>
							)}
						</div>
					</div>

					<hr></hr>

					<div className="d-flex justify-content-center">
						<MultiSteps steps={steps} />

						<hr className="mb-5"></hr>
					</div>

					{children}

					<hr></hr>

					<div
						className={classNames('d-flex  mt-4', {
							'justify-content-between': state.currentStep > 0,
							'justify-content-end': state.currentStep === 0,
						})}
					>
						{state.currentStep > 0 && (
							<ClayButton
								disabled={state.currentStep === 5}
								displayType={null}
								onClick={() => handlePreviousClick()}
								small={true}
							>
								<ClayIcon symbol="order-arrow-left" />
								&nbsp;PREVIOUS
							</ClayButton>
						)}

						{state.currentStep <= 2 && (
							<ClayButton
								className="text-uppercase"
								disabled={!state.isAbleToNextStep}
								displayType="primary"
								onClick={(event) => handleNextClick(event)}
								small={true}
							>
								Next
							</ClayButton>
						)}

						{state.currentStep === 3 && (
							<ClayButton
								className="text-uppercase"
								disabled={!state.isAbleToNextStep}
								displayType="primary"
								onClick={(event) => handleNextClick(event)}
								small={true}
							>
								Review quote
							</ClayButton>
						)}

						{state.currentStep === 4 && (
							<ClayButton
								className="text-uppercase"
								disabled={!state.isAbleToNextStep}
								displayType="primary"
								onClick={(event) => handleNextClick(event)}
								small={true}
							>
								Generate Quote
							</ClayButton>
						)}

						{state.currentStep === 5 && (
							<ClayButton
								className="justify-content-end text-uppercase"
								displayType="secondary"
								onClick={() => handleCancelClick()}
								small={true}
							>
								Cancel
							</ClayButton>
						)}
					</div>
				</div>
			</div>
		</ClayIconProvider>
	);
};

export default NewApplicationAuto;
