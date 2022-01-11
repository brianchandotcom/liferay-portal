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
import {PercentageControlledInput} from '../../../../../common/components/connectors/Controlled/Input/WithMask/Percentage';
import {LegalEntityControlledSelect} from '../../../../../common/components/connectors/Controlled/Select/LegalEntity';
import {ControlledSwitch} from '../../../../../common/components/connectors/Controlled/Switch';
import {CardFormActions} from '../../../../../common/components/fragments/Card/FormActions';
import {CardFormActionsMobile} from '../../../../../common/components/fragments/Card/FormActionsMobile';
import FormCard from '../../../../../common/components/fragments/Card/FormCard';
import {TIP_EVENT} from '../../../../../common/utils/events';
import {PERCENTAGE_REGEX_MAX_100} from '../../../../../common/utils/patterns';
import useFormActions from '../../../hooks/useFormActions';
import {useStepWizard} from '../../../hooks/useStepWizard';
import {useTriggerContext} from '../../../hooks/useTriggerContext';
import {
	validateOverallSales,
	validateOwnBrandLabel,
	validatePercentSales,
} from '../../../utils/businessFields';
import {AVAILABLE_STEPS} from '../../../utils/constants';

const setFormPath = (value) => `business.${value}`;

export function FormBusiness({form}) {
	const {selectedStep} = useStepWizard();
	const {
		control,
		formState: {isValid},
		getValues,
		setValue,
	} = useFormContext();

	const {onNext, onPrevious, onSave} = useFormActions(
		form,
		AVAILABLE_STEPS.BASICS_BUSINESS_INFORMATION,
		AVAILABLE_STEPS.EMPLOYEES
	);

	const {isSelected, updateState} = useTriggerContext();

	const forceValidation = () => {
		setValue(
			setFormPath('hasAutoPolicy'),
			getValues(setFormPath('hasAutoPolicy')),
			{shouldValidate: true}
		);
	};

	useEffect(() => {
		forceValidation();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<>
			<FormCard>
				<div className="card-content">
					<NumberControlledInput
						control={control}
						label="Years of industry experience?"
						moreInfoProps={{
							callback: () =>
								updateState(setFormPath('yearsOfExperience')),
							event: TIP_EVENT,
							selected: isSelected(
								setFormPath('yearsOfExperience')
							),
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
								message: 'Must be equal or greater than 0.',
								value: 0,
							},
							required: 'This field is required',
						}}
					/>

					<ControlledSwitch
						control={control}
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
						inputProps={{className: 'mb-5'}}
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
									updateState(
										setFormPath('salesMerchandise')
									),
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
									message:
										'Value must not be greater than 100%.',
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

					{validateOverallSales(
						form?.basics?.properties?.segment
					) && (
						<PercentageControlledInput
							control={control}
							label="What percentage of overall sales involve delivery?"
							name={setFormPath('overallSales')}
							rules={{
								pattern: {
									message:
										'Value must not be greater than 100%.',
									value: PERCENTAGE_REGEX_MAX_100,
								},
								required:
									'Percent of overall sales is required.',
							}}
						/>
					)}
				</div>

				<CardFormActions
					isValid={isValid}
					onNext={onNext}
					onPrevious={onPrevious}
					onSave={onSave}
				/>
			</FormCard>
			<CardFormActionsMobile onPrevious={onPrevious} onSave={onSave} />
		</>
	);
}
