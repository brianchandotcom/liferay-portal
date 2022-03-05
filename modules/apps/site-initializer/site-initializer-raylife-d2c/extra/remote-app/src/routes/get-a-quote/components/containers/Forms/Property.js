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

import React, {useContext, useEffect} from 'react';
import {useFormContext} from 'react-hook-form';
import {NumberControlledInput} from '../../../../../common/components/connectors/Controlled/Input/Number';
import {SquareFeatControlledInput} from '../../../../../common/components/connectors/Controlled/Input/WithMask/SquareFeet';
import {YearControlledInput} from '../../../../../common/components/connectors/Controlled/Input/WithMask/Year';
import {ControlledSwitch} from '../../../../../common/components/connectors/Controlled/Switch';
import {TIP_EVENT} from '../../../../../common/utils/events';
import {ActionTypes, AppContext} from '../../../context/AppContextProvider';
import useMobileContainer from '../../../hooks/useMobileContainer';
import {useTriggerContext} from '../../../hooks/useTriggerContext';
import {AVAILABLE_STEPS, SUBSECTION_KEYS} from '../../../utils/constants';
import {isHabitational, isThereSwimming} from '../../../utils/propertyFields';
import MobileContainer from '../../mobile/MobileContainer';

const setFormPath = (value) => `property.${value}`;

export function FormProperty({form}) {
	const {isSelected, updateState} = useTriggerContext();
	const {control, getValues, setValue} = useFormContext();

	const {dispatch} = useContext(AppContext);

	const properties = form?.basics?.properties;
	const {
		getMobileSubSection,
		mobileContainerProps,
		nextStep,
	} = useMobileContainer();

	const forceValidation = () => {
		setValue(
			setFormPath('doOwnBuildingAtAddress'),
			getValues(setFormPath('doOwnBuildingAtAddress')),
			{shouldValidate: true}
		);
	};
	useEffect(() => {
		forceValidation();

		if (!isHabitational(form?.basics?.properties?.segment.toLowerCase())) {
			AVAILABLE_STEPS.PROPERTY.mobileSubSections.map(
				(mobileSubSection) => {
					if (
						mobileSubSection.title ===
						SUBSECTION_KEYS.PRIMARY_LOCATION
					) {
						mobileSubSection.hideContinueButton = false;
					}
				}
			);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		const getPropertyNameIfNotValid = (isValid, propertyName) =>
			isValid ? '' : propertyName;

		dispatch({
			payload: [
				getPropertyNameIfNotValid(
					isHabitational(properties?.segment.toLowerCase()),
					SUBSECTION_KEYS.PREMISES
				),
			].filter(Boolean),
			type: ActionTypes.SET_MOBILE_SUBSECTION_DISABLE,
		});
	}, [dispatch, properties]);

	return (
		<div className="card-content">
			<MobileContainer
				{...mobileContainerProps}
				hasAddress={`${form.basics.businessInformation.business.location.address}?`}
				mobileSubSection={getMobileSubSection(
					SUBSECTION_KEYS.DO_YOU_OWN_THE_BUILDING_AT
				)}
			>
				<ControlledSwitch
					control={control}
					label={`Do you own the building at ${form.basics.businessInformation.business.location.address}?`}
					name={setFormPath('doOwnBuildingAtAddress')}
					onSelect={nextStep}
					rules={{required: true}}
				/>
			</MobileContainer>

			<MobileContainer
				{...mobileContainerProps}
				mobileSubSection={getMobileSubSection(
					SUBSECTION_KEYS.HOW_MANY_STORIES_IS_THIS_BUILDING
				)}
			>
				<NumberControlledInput
					control={control}
					label={SUBSECTION_KEYS.HOW_MANY_STORIES_IS_THIS_BUILDING}
					name={setFormPath('stories')}
					rules={{
						min: {
							message: 'Must be equal or greater than 0.',
							value: 0,
						},
						required: 'This field is required',
					}}
				/>
			</MobileContainer>

			<MobileContainer
				{...mobileContainerProps}
				mobileSubSection={getMobileSubSection(
					SUBSECTION_KEYS.HOW_MANY_SQUARE_FEET_OF_THE_BUILDING
				)}
			>
				<SquareFeatControlledInput
					control={control}
					label={SUBSECTION_KEYS.HOW_MANY_SQUARE_FEET_OF_THE_BUILDING}
					moreInfoProps={{
						callback: () =>
							updateState(
								setFormPath('buildingSquareFeetOccupied')
							),
						event: TIP_EVENT,
						selected: isSelected(
							setFormPath('buildingSquareFeetOccupied')
						),
						value: {
							inputName: setFormPath(
								'buildingSquareFeetOccupied'
							),
							templateName: 'building-square-footage',
							value: form?.property?.buildingSquareFeetOccupied,
						},
					}}
					name={setFormPath('buildingSquareFeetOccupied')}
					rules={{
						required: 'This field is required',
					}}
				/>
			</MobileContainer>

			<MobileContainer
				{...mobileContainerProps}
				mobileSubSection={getMobileSubSection(
					SUBSECTION_KEYS.HOW_MANY_TOTAL_SQUARE_FEET_IS_THE_BUILDING
				)}
			>
				<SquareFeatControlledInput
					control={control}
					label={
						SUBSECTION_KEYS.HOW_MANY_TOTAL_SQUARE_FEET_IS_THE_BUILDING
					}
					name={setFormPath('totalBuildingSquareFeet')}
					rules={{
						required: 'This field is required',
					}}
				/>
			</MobileContainer>

			<MobileContainer
				{...mobileContainerProps}
				mobileSubSection={getMobileSubSection(
					SUBSECTION_KEYS.WHAT_YEAR_WAS_THE_BUILDING_CONSTRUCTED
				)}
			>
				<YearControlledInput
					control={control}
					label={
						SUBSECTION_KEYS.WHAT_YEAR_WAS_THE_BUILDING_CONSTRUCTED
					}
					moreInfoProps={{
						callback: () =>
							updateState(setFormPath('yearBuilding')),
						event: TIP_EVENT,
						selected: isSelected(setFormPath('yearBuilding')),
						value: {
							inputName: setFormPath('yearBuilding'),
							templateName: 'year-constructed',
							value: form?.property?.yearBuilding,
						},
					}}
					name={setFormPath('yearBuilding')}
					rules={{
						required: 'This field is required',
					}}
				/>
			</MobileContainer>

			<MobileContainer
				{...mobileContainerProps}
				mobileSubSection={getMobileSubSection(
					SUBSECTION_KEYS.PRIMARY_LOCATION
				)}
			>
				<ControlledSwitch
					control={control}
					label={SUBSECTION_KEYS.PRIMARY_LOCATION}
					moreInfoProps={{
						callback: () =>
							updateState(
								setFormPath('isPrimaryBusinessLocation')
							),
						event: TIP_EVENT,
						selected: isSelected(
							setFormPath('isPrimaryBusinessLocation')
						),
						value: {
							inputName: setFormPath('isPrimaryBusinessLocation'),
							templateName: 'primary-location',
							value: form?.property?.isPrimaryBusinessLocation,
						},
					}}
					name={setFormPath('isPrimaryBusinessLocation')}
					onSelect={
						isHabitational(
							form?.basics?.properties?.segment.toLowerCase()
						)
							? nextStep
							: null
					}
					rules={{required: true}}
				/>
			</MobileContainer>

			{isHabitational(
				form?.basics?.properties?.segment.toLowerCase()
			) && (
				<MobileContainer
					{...mobileContainerProps}
					mobileSubSection={getMobileSubSection(
						SUBSECTION_KEYS.PREMISES
					)}
				>
					<div className="mb-4">
						<ControlledSwitch
							control={control}
							inputProps={{
								onChange: (value) => {
									AVAILABLE_STEPS.PROPERTY.mobileSubSections.map(
										(mobileSubSection) => {
											if (
												mobileSubSection.title ===
												SUBSECTION_KEYS.PREMISES
											) {
												mobileSubSection.hideContinueButton = false;
											}
										}
									);

									setValue(
										setFormPath('isThereSwimming'),
										value,
										{
											shouldValidate: true,
										}
									);

									if (value === 'false') {
										setValue(
											setFormPath('isThereDivingBoards'),
											''
										);
									}
								},
							}}
							label={SUBSECTION_KEYS.SWIMMING_POOL}
							name={setFormPath('isThereSwimming')}
							rules={{required: true}}
						/>
					</div>

					{isThereSwimming(form?.property?.isThereSwimming) && (
						<ControlledSwitch
							control={control}
							label={SUBSECTION_KEYS.DIVING_BOARDS}
							name={setFormPath('isThereDivingBoards')}
							rules={{required: true}}
						/>
					)}
				</MobileContainer>
			)}
		</div>
	);
}
