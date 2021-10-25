import React, {useEffect} from 'react';
import {useFormContext} from 'react-hook-form';
import {NumberControlledInput} from '~/common/components/connectors/Controlled/Input/Number';
import {SquareFeatControlledInput} from '~/common/components/connectors/Controlled/Input/WithMask/SquareFeet';
import {YearControlledInput} from '~/common/components/connectors/Controlled/Input/WithMask/Year';
import {ControlledSwitch} from '~/common/components/connectors/Controlled/Switch';
import {CardFormActionsWithSave} from '~/common/components/fragments/Card/FormActionsWithSave';
import {TIP_EVENT} from '~/common/utils/events';
import useFormActions from '~/routes/get-a-quote/hooks/useFormActions';
import {useStepWizard} from '~/routes/get-a-quote/hooks/useStepWizard';
import {useTriggerContext} from '~/routes/get-a-quote/hooks/useTriggerContext';
import {AVAILABLE_STEPS} from '~/routes/get-a-quote/utils/constants';
import {
	isHabitational,
	isThereSwimming,
} from '~/routes/get-a-quote/utils/propertyFields';

const setFormPath = (value) => `property.${value}`;

export const FormProperty = ({form}) => {
	const {selectedStep} = useStepWizard();
	const {
		control,
		formState: {isValid},
		getValues,
		setValue,
	} = useFormContext();

	const forceValidation = () => {
		setValue(
			setFormPath('doOwnBuildingAtAddress'),
			getValues(setFormPath('doOwnBuildingAtAddress')),
			{shouldValidate: true}
		);
	};
	useEffect(() => {
		forceValidation();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const {onNext, onPrevious, onSave} = useFormActions(
		form,
		AVAILABLE_STEPS.EMPLOYEES
	);

	const {isSelected, updateState} = useTriggerContext();

	return (
		<div className="card">
			<div className="card-content">
				<ControlledSwitch
					control={control}
					label={`Do you own the building at ${form.basics.businessInformation.business.location.address}?`}
					name={setFormPath('doOwnBuildingAtAddress')}
					rules={{required: true}}
				/>

				<NumberControlledInput
					control={control}
					label="How many stories is this building?"
					name={setFormPath('stories')}
					rules={{
						min: {
							message: 'Must be equal or greater than 0.',
							value: 0,
						},
						required: 'This field is required',
					}}
				/>

				<SquareFeatControlledInput
					control={control}
					label="How many square feet of the building does your business occupy?"
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
							step: selectedStep,
							templateName: 'building-square-footage',
							value: form?.property?.buildingSquareFeetOccupied,
						},
					}}
					name={setFormPath('buildingSquareFeetOccupied')}
					rules={{
						required: 'This field is required',
					}}
				/>

				<SquareFeatControlledInput
					control={control}
					label="How many total square feet is the building?"
					name={setFormPath('totalBuildingSquareFeet')}
					rules={{
						required: 'This field is required',
					}}
				/>

				<YearControlledInput
					control={control}
					label="What year was the building constructed?"
					moreInfoProps={{
						callback: () =>
							updateState(setFormPath('yearBuilding')),
						event: TIP_EVENT,
						selected: isSelected(setFormPath('yearBuilding')),
						value: {
							inputName: setFormPath('yearBuilding'),
							step: selectedStep,
							templateName: 'year-constructed',
							value: form?.property?.yearBuilding,
						},
					}}
					name={setFormPath('yearBuilding')}
					rules={{
						required: 'This field is required',
					}}
				/>

				<ControlledSwitch
					control={control}
					label="Is this the primary location where you conduct business?"
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
							step: selectedStep,
							templateName: 'primary-location',
							value: form?.property?.isPrimaryBusinessLocation,
						},
					}}
					name={setFormPath('isPrimaryBusinessLocation')}
					rules={{required: true}}
				/>
				{isHabitational(
					form?.basics?.properties?.segment.toLowerCase()
				) && (
					<ControlledSwitch
						control={control}
						inputProps={{
							onChange: (value) => {
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
						label="Are there swimming pool(s) on the premises?"
						name={setFormPath('isThereSwimming')}
						rules={{required: true}}
					/>
				)}
				{isThereSwimming(form?.property?.isThereSwimming) && (
					<ControlledSwitch
						control={control}
						label="Are there diving boards or slides?"
						name={setFormPath('isThereDivingBoards')}
						rules={{required: true}}
					/>
				)}
			</div>

			<CardFormActionsWithSave
				isValid={isValid}
				onNext={onNext}
				onPrevious={onPrevious}
				onSave={onSave}
			/>
		</div>
	);
};
