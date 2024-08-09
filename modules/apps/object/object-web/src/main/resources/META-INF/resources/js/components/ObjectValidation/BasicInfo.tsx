/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	Card,
	Input,
	RadioField,
	SingleSelect,
	stringUtils,
} from '@liferay/object-js-components-web';
import {InputLocalized} from 'frontend-js-components-web';
import React, {useMemo} from 'react';

import {NAME_OUTPUT_OBJECT_FIELD_EXTERNAL_REFERENCE_CODE} from '../../utils/constants';
import {DisabledGroovyScriptAlert} from '../DisabledGroovyScriptAlert';
import {ObjectValidationActiveToggle} from './ObjectValidationActiveToggle';
import {TabProps} from './useObjectValidationForm';

export interface BasicInfoProps extends TabProps {
	componentLabel: string;
	creationLanguageId: Liferay.Language.Locale;
	customObjectFields: ObjectField[];
	disabledGroovyValidation: boolean;
}

const outputValidationTypeArray = [
	{
		label: Liferay.Language.get('full-validation-form-summary'),
		value: 'fullValidation',
	},
	{
		label: Liferay.Language.get('partial-validation-inline-field'),
		value: 'partialValidation',
	},
];

const TriggerEventOptions = [
	{
		label: Liferay.Language.get('on-submission'),
		value: 'onSubmission',
	},
];

export function BasicInfo({
	componentLabel,
	creationLanguageId,
	customObjectFields,
	disabled,
	disabledGroovyValidation,
	errors,
	scriptManagementConfigurationPortletURL,
	selectedPartialValidationField,
	setValues,
	values,
}: BasicInfoProps) {
	const objectFieldsItems = useMemo(() => {
		return customObjectFields.map(
			({externalReferenceCode, label, name}) => ({
				label: stringUtils.getLocalizableLabel(
					creationLanguageId,
					label,
					name
				),
				value: externalReferenceCode,
			})
		);
	}, [creationLanguageId, customObjectFields]);

	return (
		<>
			{disabledGroovyValidation && (
				<DisabledGroovyScriptAlert
					scriptManagementConfigurationPortletURL={
						scriptManagementConfigurationPortletURL
					}
				/>
			)}

			<Card title={componentLabel}>
				<InputLocalized
					disabled={disabled}
					error={errors.name}
					label={Liferay.Language.get('label')}
					onChange={(name) => setValues({name})}
					placeholder={Liferay.Language.get('add-a-label')}
					required
					translations={values.name!}
				/>

				<Input
					disabled
					label={Liferay.Language.get('type')}
					value={values.engineLabel}
				/>

				{values.engine !== 'compositeKey' && (
					<ObjectValidationActiveToggle
						disabled={disabled}
						disabledGroovyValidation={disabledGroovyValidation}
						setValues={setValues}
						values={values}
					/>
				)}
			</Card>

			<Card title={Liferay.Language.get('trigger-event')}>
				<SingleSelect
					defaultSelectedKey="onSubmission"
					disabled={disabled}
					items={TriggerEventOptions}
					label={Liferay.Language.get('event')}
				/>
			</Card>

			{(values.engine?.startsWith('function#') ||
				values.engine?.startsWith('javaDelegate#')) && (
				<Card title={Liferay.Language.get('error-message')}>
					<InputLocalized
						disabled={disabled}
						error={errors.errorLabel}
						label={Liferay.Language.get('message')}
						onChange={(errorLabel) => setValues({errorLabel})}
						placeholder={Liferay.Language.get(
							'add-an-error-message'
						)}
						required
						translations={values.errorLabel!}
					/>

					<>
						<RadioField
							defaultValue={values.outputType}
							disabled={disabled}
							inline={false}
							label={Liferay.Language.get(
								'output-validation-type'
							)}
							onChange={(value) => {
								if (value === 'fullValidation') {
									setValues({
										objectValidationRuleSettings: [],
										outputType: value as string,
									});

									return;
								}

								setValues({
									outputType: value as string,
								});
							}}
							options={outputValidationTypeArray}
							popover={{
								alignPosition: 'top',
								content: Liferay.Language.get(
									'map-the-error-message-to-be-displayed-next-to-the-validated-field'
								),
								header: Liferay.Language.get(
									'message-location'
								),
							}}
						/>

						{values.outputType === 'partialValidation' && (
							<SingleSelect
								error={errors.outputType}
								id="objectValidationBasicInfo"
								items={objectFieldsItems}
								label={Liferay.Language.get('fields')}
								onSelectionChange={(value) => {
									setValues({
										objectValidationRuleSettings: [
											{
												name: NAME_OUTPUT_OBJECT_FIELD_EXTERNAL_REFERENCE_CODE,
												value: value as string,
											},
										],
									});
								}}
								required
								selectedKey={selectedPartialValidationField}
							/>
						)}
					</>
				</Card>
			)}
		</>
	);
}
