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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayMultiStepNav from '@clayui/multi-step-nav';
import {ClayTooltipProvider} from '@clayui/tooltip';
import classNames from 'classnames';
import {ReactNode, useContext, useState} from 'react';

import ClayIconProvider from '../../../../../common/context/ClayIconProvider';
import {createOrUpdateRaylifeApplication} from '../../../../../common/services';
import {CONSTANTS} from '../../../../../common/utils/constants';
import {
	ACTIONS,
	NewApplicationAutoContext,
} from '../../../context/NewApplicationAutoContextProvider';

type DriverInfoProps = {
	children: ReactNode;
};

const NewApplicationAuto = ({children}: DriverInfoProps) => {
	const [state, dispatch] = useContext(NewApplicationAutoContext);

	const [saveChanges, setSaveChanges] = useState<boolean>(false);

	const tooltipTitle =
		'You must enter first name, last name, phone number and email address to save this quote.';

	const steps = [
		{
			active: state.currentStep === 0,
			complete: state.currentStep > 0,
			title: state.steps.contactInfo.name,
		},
		{
			active: state.currentStep === 1,
			complete: state.currentStep > 1,
			title: state.steps.vehicleInfo.name,
		},
		{
			active: state.currentStep === 2,
			complete: state.currentStep > 2,
			title: state.steps.driverInfo.name,
		},
		{
			active: state.currentStep === 3,
			complete: state.currentStep > 3,
			title: state.steps.coverage.name,
		},
		{
			active: state.currentStep === 4,
			complete: state.currentStep > 4,
			title: state.steps.review.name,
		},
	];

	const handleNextClick = (event: any) => {
		setSaveChanges(true);

		event?.preventDefault();
		dispatch({payload: false, type: ACTIONS.SET_HAS_FORM_CHANGE});

		const applicationStatus =
			state.currentStep < 4
				? CONSTANTS.APPLICATION_STATUS.OPEN
				: CONSTANTS.APPLICATION_STATUS.QUOTED;

		createOrUpdateRaylifeApplication(state, applicationStatus).then(
			(response) => {
				const {
					data: {id},
				} = response;

				dispatch({payload: {id}, type: ACTIONS.SET_APPLICATION_ID});
			}
		);

		if (state.currentStep < steps.length) {
			dispatch({
				payload: state.currentStep + 1,
				type: ACTIONS.SET_CURRENT_STEP,
			});
		}
	};

	const handlePreviousClick = () => {
		setSaveChanges(false);
		dispatch({payload: false, type: ACTIONS.SET_HAS_FORM_CHANGE});
		if (state.currentStep > 0) {
			dispatch({
				payload: state.currentStep - 1,
				type: ACTIONS.SET_CURRENT_STEP,
			});
		}
	};

	const handleSaveChanges = () => {
		setSaveChanges(true);

		dispatch({payload: false, type: ACTIONS.SET_HAS_FORM_CHANGE});

		return saveChanges;
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

	return (
		<ClayIconProvider>
			<div className="container">
				<div className="border mt-4 sheet sheet-dataset-content">
					<div className="d-flex justify-content-between">
						<h5>New Application</h5>

						{state.hasFormChanges && (
							<ChangeStatusMessage text="Unsave Changes" />
						)}

						{!state.hasFormChanges && !saveChanges && (
							<ChangeStatusMessage text="No Changes Made" />
						)}

						{saveChanges && !state.hasFormChanges && (
							<ChangeStatusMessage text="All Changes Saved" />
						)}

						<div>
							<ClayButton
								className="text-uppercase"
								displayType={null}
								small={true}
							>
								Exit
							</ClayButton>

							{!state.isAbleToBeSave ? (
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
					<>
						<ClayMultiStepNav className="mx-10">
							{steps.map(({active, complete, title}, index) => (
								<ClayMultiStepNav.Item
									active={active}
									complete={complete}
									expand={index + 1 !== steps.length}
									key={index}
								>
									<ClayMultiStepNav.Title>
										{title}
									</ClayMultiStepNav.Title>

									{index + 1 !== steps.length ? (
										<ClayMultiStepNav.Divider />
									) : (
										''
									)}

									<ClayMultiStepNav.Indicator
										complete={complete}
										label={1 + index}
									/>
								</ClayMultiStepNav.Item>
							))}
						</ClayMultiStepNav>
						<hr className="mb-5"></hr>
					</>

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
								displayType={null}
								onClick={() => handlePreviousClick()}
								small={true}
							>
								<ClayIcon symbol="order-arrow-left" />
								&nbsp;PREVIOUS
							</ClayButton>
						)}

						<ClayButton
							className="text-uppercase"
							disabled={!state.isAbleToNextStep}
							displayType="primary"
							onClick={(event) => handleNextClick(event)}
							small={true}
						>
							Next
						</ClayButton>
					</div>
				</div>
			</div>
		</ClayIconProvider>
	);
};

export default NewApplicationAuto;
