/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';

import 'codemirror/mode/groovy/groovy';
import {
	AutoComplete,
	Card,
	CodeEditor,
	RadioField,
	SidebarCategory,
	filterArrayByQuery,
	getLocalizableLabel,
} from '@liferay/object-js-components-web';
import {
	InputLocalized,
	LearnMessage,
	LearnResourcesContext,
} from 'frontend-js-components-web';
import React, {useMemo, useState} from 'react';

import {NAME_OUTPUT_OBJECT_FIELD_EXTERNAL_REFERENCE_CODE} from '../../utils/constants';
import {TabProps} from './useObjectValidationForm';

interface ConditionsProps extends TabProps {
	creationLanguageId: Liferay.Language.Locale;
	learnResources: object;
	objectFields: ObjectField[];
	objectValidationRuleElements: SidebarCategory[];
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

export function Conditions({
	creationLanguageId,
	disabled,
	errors,
	learnResources,
	objectFields,
	objectValidationRuleElements,
	setValues,
	values,
}: ConditionsProps) {
	const [query, setQuery] = useState<string>('');

	const engine = values.engine;
	const ddmTooltip = {
		content: Liferay.Language.get(
			'use-the-expression-builder-to-define-the-format-of-a-valid-object-entry'
		),
		symbol: 'question-circle-full',
	};
	let placeholder;

	if (engine === 'groovy') {
		placeholder = Liferay.Language.get(
			'insert-a-groovy-script-to-define-your-validation'
		);
	}
	else if (engine === 'ddm') {
		placeholder = Liferay.Language.get(
			'add-elements-from-the-sidebar-to-define-your-validation'
		);
	}
	else {
		placeholder = '';
	}

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
			<ClayAlert
				className="lfr-objects__side-panel-content-container"
				displayType="info"
				title={`${Liferay.Language.get('info')}:`}
			>
				{Liferay.Language.get('create-validations-using-expressions')}
				&nbsp;
				<LearnResourcesContext.Provider value={learnResources}>
					<LearnMessage
						className="alert-link"
						resource="object-web"
						resourceKey="general"
					/>
				</LearnResourcesContext.Provider>
			</ClayAlert>
			<Card
				title={values.engineLabel!}
				tooltip={engine === 'ddm' ? ddmTooltip : null}
			>
				<CodeEditor
					error={errors.script}
					mode={engine}
					onChange={(script?: string, lineCount?: number) =>
						setValues({lineCount, script})
					}
					placeholder={placeholder}
					readOnly={disabled}
					sidebarElements={objectValidationRuleElements}
					value={values.script ?? ''}
				/>
			</Card>

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
		</>
	);
}
