import React from 'react';
import {useFormContext} from 'react-hook-form';

import {TIP_EVENT} from '../../../events';
import useFormActions from '../../../hooks/useFormActions';
import {useStepWizard} from '../../../hooks/useStepWizard';
import {useTriggerContext} from '../../../hooks/useTriggerContext';
import {AVAILABLE_STEPS} from '../../../utils/constants';
import {NumberControlledInput} from '../../connectors/Controlled/Input/Number';
import {CurrencyControlledInput} from '../../connectors/Controlled/Input/WithMask/Currency';
import {FEINControlledInput} from '../../connectors/Controlled/Input/WithMask/FEIN';
import {YearControlledInput} from '../../connectors/Controlled/Input/WithMask/Year';
import {ControlledSwitch} from '../../connectors/Controlled/Switch';
import {CardFormActionsWithSave} from '../../fragments/Card/FormActionsWithSave';

const setFormPath = (value) => `employees.${value}`;

const hasFein = (value) => value === 'true';

export const FormEmployees = ({form}) => {
	const {selectedStep} = useStepWizard();
	const {
		control,
		formState: {isValid},
	} = useFormContext();

	const {onNext, onPrevious, onSave} = useFormActions(
		form,
		AVAILABLE_STEPS.BUSINESS,
		AVAILABLE_STEPS.PROPERTY
	);

	const {isSelected, updateState} = useTriggerContext();

	return (
		<div className="card">
			<div className="card-content">
				<ControlledSwitch
					control={control}
					label="Does your business have a Federal Employer Identification Number (FEIN)?"
					name={setFormPath('hasFein')}
					rules={{required: true}}
				/>
				{hasFein(form?.employees?.hasFein) && (
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
						rules={{
							required: 'FEIN is required.',
						}}
					/>
				)}
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
			<CardFormActionsWithSave
				isValid={isValid}
				onNext={onNext}
				onPrevious={onPrevious}
				onSave={onSave}
			/>
		</div>
	);
};
