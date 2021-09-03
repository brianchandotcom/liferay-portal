import React from 'react';
import {useFormContext} from 'react-hook-form';

import {TIP_EVENT} from '../../../events';
import useFormActions from '../../../hooks/useFormActions';
import {useStepWizard} from '../../../hooks/useStepWizard';
import {useTriggerContext} from '../../../hooks/useTriggerContext';
import {
	validateOverallSales,
	validateOwnBrandLabel,
	validatePercentSales,
} from '../../../utils/businessFields';
import {AVAILABLE_STEPS} from '../../../utils/constants';
import {PERCENTAGE_REGEX_MAX_100} from '../../../utils/patterns';
import {NumberControlledInput} from '../../connectors/Controlled/Input/Number';
import {PercentageControlledInput} from '../../connectors/Controlled/Input/WithMask/Percentage';
import {LegalEntityControlledSelect} from '../../connectors/Controlled/Select/LegalEntity';
import {ControlledSwitch} from '../../connectors/Controlled/Switch';
import {CardFormActionsWithSave} from '../../fragments/Card/FormActionsWithSave';

const setFormPath = (value) => `business.${value}`;

export const FormBusiness = ({form}) => {
	const {
		control,
		formState: {isValid},
	} = useFormContext();
	const {selectedStep} = useStepWizard();
	const {onNext, onPrevious, onSave} = useFormActions(
		form,
		AVAILABLE_STEPS.BASICS_PRODUCT_QUOTE,
		AVAILABLE_STEPS.EMPLOYEES
	);
	const {isSelected, updateState} = useTriggerContext();

	return (
		<div className="card">
			<div className="card-content">
				<NumberControlledInput
					control={control}
					label="Years of industry experience?"
					moreInfoProps={{
						callback: () =>
							updateState(setFormPath('yearsOfExperience')),
						event: TIP_EVENT,
						selected: isSelected(setFormPath('yearsOfExperience')),
						value: {
							inputName: setFormPath('yearsOfExperience'),
							step: selectedStep,
							templateName: 'years-of-industry-experience',
							value: form?.business?.yearsOfExperience,
						},
					}}
					name={setFormPath('yearsOfExperience')}
					rules={{
						min: {
							message: 'Must be equal or grater than 0.',
							value: 0,
						},
						required: 'This field is required',
					}}
				/>
				<ControlledSwitch
					control={control}
					defaultValue="true"
					label="Do you store personally identifiable information about your customers?"
					name={setFormPath('hasStoredCustomerInformation')}
					rules={{required: true}}
				/>
				<ControlledSwitch
					control={control}
					label="Do you have a Raylife Auto policy?"
					name={setFormPath('hasAutoPolicy')}
					rules={{required: true}}
				/>
				<LegalEntityControlledSelect
					control={control}
					label="Legal Entity"
					name={setFormPath('legalEntity')}
					rules={{
						required: 'This field is required.',
					}}
				/>
				{validatePercentSales(form?.basics?.properties?.naics) && (
					<PercentageControlledInput
						control={control}
						label="Percent of sales from used merchandise?"
						moreInfoProps={{
							callback: () =>
								updateState(setFormPath('salesMerchandise')),
							event: TIP_EVENT,
							selected: isSelected(
								setFormPath('salesMerchandise')
							),
							value: {
								inputName: setFormPath('salesMerchandise'),
								step: selectedStep,
								templateName:
									'percent-of-sales-from-used-merchandise',
								value: form?.business?.salesMerchandise,
							},
						}}
						name={setFormPath('salesMerchandise')}
						rules={{
							pattern: {
								message: 'Value must not be greater than 100%.',
								value: PERCENTAGE_REGEX_MAX_100,
							},
							required: 'Percent of sales is required.',
						}}
					/>
				)}
				{validateOwnBrandLabel(form?.basics?.properties?.naics) && (
					<ControlledSwitch
						control={control}
						label="Do you sell products under your own brand or label?"
						name={setFormPath('hasSellProductsUnderOwnBrand')}
						rules={{required: true}}
					/>
				)}
				{validateOverallSales(form?.basics?.properties?.segment) && (
					<PercentageControlledInput
						control={control}
						label="What percentage of overall sales involve delivery?"
						name={setFormPath('overallSales')}
						rules={{
							pattern: {
								message: 'Value must not be greater than 100%.',
								value: PERCENTAGE_REGEX_MAX_100,
							},
							required: 'Percent of overall sales is required.',
						}}
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
