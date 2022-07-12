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
import classNames from 'classnames';
import {ReactNode, useContext} from 'react';

import ClayIconProvider from '../../../../common/context/ClayIconProvider';
import {
	ACTIONS,
	NewApplicationAutoContext,
} from '../../context/NewApplicationAutoContextProvider';

type DriverInfoProps = {
	children: ReactNode;
};

const NewApplication = ({children}: DriverInfoProps) => {
	const [state, dispatch] = useContext(NewApplicationAutoContext);

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

	const handleNextClick = () => {
		dispatch({payload: false, type: ACTIONS.SET_HAS_FORM_CHANGE});
		if (state.currentStep < steps.length) {
			dispatch({
				payload: state.currentStep + 1,
				type: ACTIONS.SET_CURRENT_STEP,
			});
		}
	};

	const handlePreviousClick = () => {
		dispatch({payload: false, type: ACTIONS.SET_HAS_FORM_CHANGE});
		if (state.currentStep > 0) {
			dispatch({
				payload: state.currentStep - 1,
				type: ACTIONS.SET_CURRENT_STEP,
			});
		}
	};

	return (
		<ClayIconProvider>
			<div className="container">
				<div className="border mt-4 sheet sheet-dataset-content">
					<div className="d-flex justify-content-between">
						<h5>New Application</h5>

						<div className="text-neutral-7 text-paragraph-sm">
							<ClayIcon
								className={classNames('', {
									'text-accent-4': state.hasFormChanges,
								})}
								symbol="simple-circle"
							></ClayIcon>

							{state.hasFormChanges && (
								<span>Unsave Changes</span>
							)}

							{!state.hasFormChanges && (
								<span>No Changes Made</span>
							)}
						</div>

						<div>
							<ClayButton displayType={null} small={true}>
								EXIT
							</ClayButton>

							<ClayButton displayType="secondary" small={true}>
								SAVE
							</ClayButton>
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
							displayType="primary"
							onClick={() => handleNextClick()}
							small={true}
						>
							NEXT
						</ClayButton>
					</div>
				</div>
			</div>
		</ClayIconProvider>
	);
};

export default NewApplication;
