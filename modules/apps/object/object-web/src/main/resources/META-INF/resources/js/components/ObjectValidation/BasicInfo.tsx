/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	AutoComplete,
	Card,
	Input,
	RadioField,
	Toggle,
	filterArrayByQuery,
	getLocalizableLabel,
} from '@liferay/object-js-components-web';
import {InputLocalized} from 'frontend-js-components-web';
import React, {useMemo, useState} from 'react';

import {NAME_OUTPUT_OBJECT_FIELD_EXTERNAL_REFERENCE_CODE} from '../../utils/constants';
import {TriggerEventContainer} from './TriggerEventContainer';
import {TabProps} from './useObjectValidationForm';

interface BasicInfoProps extends TabProps {
	componentLabel: string;
	creationLanguageId: Liferay.Language.Locale;
	objectFields: ObjectField[];
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

export function BasicInfo({
	componentLabel,
	creationLanguageId,
	disabled,
	errors,
	objectFields,
	setValues,
	values,
}: BasicInfoProps) {
	const [query, setQuery] = useState<string>('');

	const filteredObjectFields = useMemo(() => {
		if (objectFields) {
			return filterArrayByQuery({
				array: objectFields,
				query,
				str: 'label',
			});
		}
	}, [objectFields, query]);
	const getSelectedPartialValidationField = () => {
		if (values.objectValidationRuleSettings?.length) {
			const [
				partialValidationField,
			] = values.objectValidationRuleSettings;

			const objectField = objectFields.find(
				(field) =>
					field.externalReferenceCode === partialValidationField.value
			);

			return getLocalizableLabel(
				creationLanguageId,
				objectField?.label,
				objectField?.name
			);
		}

		return '';
	};

	return (
		<>
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

				<Toggle
					disabled={disabled}
					label={Liferay.Language.get('active-validation')}
					onToggle={(active) => setValues({active})}
					toggled={values.active}
				/>
			</Card>

			<TriggerEventContainer
				disabled={disabled}
				eventTypes={[{label: Liferay.Language.get('on-submission')}]}
			/>

			{values.engine?.startsWith('function#') && (
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

					{Liferay.FeatureFlags['LPS-187846'] && (
						<>
							<RadioField
								defaultValue={values.outputType}
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
								<AutoComplete<ObjectField>
									emptyStateMessage={Liferay.Language.get(
										'no-fields-were-found'
									)}
									error={errors.outputType}
									items={filteredObjectFields ?? []}
									label={Liferay.Language.get('fields')}
									onChangeQuery={setQuery}
									onSelectItem={(item) => {
										setValues({
											objectValidationRuleSettings: [
												{
													name: NAME_OUTPUT_OBJECT_FIELD_EXTERNAL_REFERENCE_CODE,
													value: item.externalReferenceCode as string,
												},
											],
										});
									}}
									query={query}
									required
									value={getSelectedPartialValidationField()}
								>
									{({label, name}) => (
										<div className="d-flex justify-content-between">
											<div>
												{getLocalizableLabel(
													creationLanguageId,
													label,
													name
												)}
											</div>
										</div>
									)}
								</AutoComplete>
							)}
						</>
					)}
				</Card>
			)}
		</>
	);
}
