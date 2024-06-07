/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Card, RadioField} from '@liferay/object-js-components-web';
import {InputLocalized} from 'frontend-js-components-web';
import React from 'react';

import {ObjectValidationErrors} from './useObjectValidationForm';

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

interface ErrorMessageProps {
	children: React.ReactNode;
	disabled: boolean;
	errors: ObjectValidationErrors;
	setValues: (values: Partial<ObjectValidation>) => void;
	values: Partial<ObjectValidation>;
}

export function ErrorMessage({
	children,
	disabled,
	errors,
	setValues,
	values,
}: ErrorMessageProps) {
	return (
		<Card title={Liferay.Language.get('error-message')}>
			<InputLocalized
				disabled={disabled}
				error={errors.errorLabel}
				label={Liferay.Language.get('message')}
				onChange={(errorLabel) => setValues({errorLabel})}
				placeholder={Liferay.Language.get('add-an-error-message')}
				required
				translations={values.errorLabel!}
			/>

			<RadioField
				defaultValue={values.outputType}
				inline={false}
				label={Liferay.Language.get('output-validation-type')}
				onChange={(value) => {
					if (value === 'fullValidation') {
						setValues({
							objectValidationRuleSettings:
								values.engine === 'compositeKey'
									? values.objectValidationRuleSettings?.filter(
											(objectValidationRuleSetting) =>
												objectValidationRuleSetting.name ===
												'compositeKeyObjectFieldExternalReferenceCode'
										)
									: [],
							outputType: value as string,
						});

						return;
					}
					else if (
						value === 'partialValidation' &&
						values.engine === 'compositeKey'
					) {
						const outputObjectFieldExternalReferenceCode =
							values.objectValidationRuleSettings?.map(
								(objectValidationRuleSetting) => {
									return {
										name: 'outputObjectFieldExternalReferenceCode',
										value: objectValidationRuleSetting.value,
									};
								}
							) as ObjectValidationRuleSetting[];

						setValues({
							objectValidationRuleSettings:
								values.objectValidationRuleSettings?.concat(
									outputObjectFieldExternalReferenceCode
								),
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
					header: Liferay.Language.get('message-location'),
				}}
			/>

			{values.outputType === 'partialValidation' && children}
		</Card>
	);
}
