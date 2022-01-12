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

import React, {useEffect} from 'react';
import {useFormContext} from 'react-hook-form';
import {NumberControlledInput} from '../../../../../common/components/connectors/Controlled/Input/Number';
import {CurrencyControlledInput} from '../../../../../common/components/connectors/Controlled/Input/WithMask/Currency';
import {FEINControlledInput} from '../../../../../common/components/connectors/Controlled/Input/WithMask/FEIN';
import {YearControlledInput} from '../../../../../common/components/connectors/Controlled/Input/WithMask/Year';
import {ControlledSwitch} from '../../../../../common/components/connectors/Controlled/Switch';
import {CardFormActions} from '../../../../../common/components/fragments/Card/FormActions';
import FormCard from '../../../../../common/components/fragments/Card/FormCard';
import {TIP_EVENT} from '../../../../../common/utils/events';
import useFormActions from '../../../hooks/useFormActions';
import {useStepWizard} from '../../../hooks/useStepWizard';
import {useTriggerContext} from '../../../hooks/useTriggerContext';
import {AVAILABLE_STEPS} from '../../../utils/constants';

const setFormPath = (value) => `employees.${value}`;

const hasFein = (value) => value === 'true';

export function FormEmployees({form}) {
	const {selectedStep} = useStepWizard();
	const {
		control,
		formState: {isValid},
		getValues,
		setValue,
	} = useFormContext();

	const forceValidation = () => {
		setValue(
			setFormPath('businessOperatesYearRound'),
			getValues(setFormPath('businessOperatesYearRound')),
			{shouldValidate: true}
		);
	};

	useEffect(() => {
		forceValidation();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const {onNext, onPrevious, onSave} = useFormActions(
		form,
		AVAILABLE_STEPS.BUSINESS,
		AVAILABLE_STEPS.PROPERTY
	);

	const {isSelected, updateState} = useTriggerContext();

	return (
		<FormCard>
			<div className="card-content">
				<ControlledSwitch
					control={control}
					inputProps={{
						onChange: (value) => {
							setValue(setFormPath('hasFein'), value, {
								shouldValidate: true,
							});

							if (value === 'false') {
								setValue(setFormPath('fein'), '');
							}
						},
					}}
					label="Does your business have a Federal Employer Identification Number (FEIN)?"
					name={setFormPath('hasFein')}
					rules={{required: true}}
				/>

				<FEINControlledInput
					control={control}
					label="Federal Employer Identification Number (FEIN)"
					moreInfoProps={{
						callback: () => updateState(setFormPath('fein')),
						event: TIP_EVENT,
						selected: isSelected(setFormPath('fein')),
						value: {
							inputName: setFormPath('fein'),
							step: selectedStep,
							templateName:
								'federal-employer-identification-number',
							value: form?.employees?.fein,
						},
					}}
					name={setFormPath('fein')}
					renderInput={hasFein(form?.employees?.hasFein)}
					rules={{
						required: 'FEIN is required.',
					}}
				/>

				<YearControlledInput
					control={control}
					label="What year did you start your business?"
					name={setFormPath('startBusinessAtYear')}
					rules={{required: 'This field is required'}}
				/>

				<ControlledSwitch
					control={control}
					label="Does your business operate year round?"
					name={setFormPath('businessOperatesYearRound')}
					rules={{required: true}}
				/>

				<NumberControlledInput
					control={control}
					label="How many full or part time employees do you have?"
					moreInfoProps={{
						callback: () =>
							updateState(setFormPath('partTimeEmployees')),
						event: TIP_EVENT,
						selected: isSelected(setFormPath('partTimeEmployees')),
						value: {
							inputName: setFormPath('partTimeEmployees'),
							step: selectedStep,
							templateName: 'full-or-part-time-employees',
							value: form?.employees?.partTimeEmployees,
						},
					}}
					name={setFormPath('partTimeEmployees')}
					rules={{
						min: {
							message: 'You must have at least one employee.',
							value: 1,
						},
						required: 'This field is required',
					}}
				/>

				<CurrencyControlledInput
					control={control}
					label="What is your estimated annual gross revenue for the next 12 months?"
					name={setFormPath('estimatedAnnualGrossRevenue')}
					rules={{required: 'This field is required'}}
				/>

				<CurrencyControlledInput
					control={control}
					label="What do you anticipate your annual payroll will be for all owner(s) over the next 12 months?"
					name={setFormPath('annualPayrollForOwner')}
					rules={{required: 'This field is required'}}
				/>

				<CurrencyControlledInput
					control={control}
					label="What do you anticipate your annual payroll will be for all employees over the next 12 months?"
					name={setFormPath('annualPayrollForEmployees')}
					rules={{required: 'This field is required'}}
				/>
			</div>

			<CardFormActions
				isValid={isValid}
				onNext={onNext}
				onPrevious={onPrevious}
				onSave={onSave}
			/>
		</FormCard>
	);
}
