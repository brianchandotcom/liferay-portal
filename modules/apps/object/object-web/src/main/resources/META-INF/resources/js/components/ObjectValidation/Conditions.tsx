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
	SidebarCategory,
	filterArrayByQuery,
	getLocalizableLabel,
} from '@liferay/object-js-components-web';
import {LearnMessage, LearnResourcesContext} from 'frontend-js-components-web';
import React, {useMemo, useState} from 'react';

import {NAME_OUTPUT_OBJECT_FIELD_EXTERNAL_REFERENCE_CODE} from '../../utils/constants';
import {ErrorMessage} from './ErrorMessage';
import {TabProps} from './useObjectValidationForm';

export interface ConditionsProps extends TabProps {
	creationLanguageId: Liferay.Language.Locale;
	customObjectFields: ObjectField[];
	learnResources: ObjectWebLearnResources;
	objectValidationRuleElements: SidebarCategory[];
}

export function Conditions({
	creationLanguageId,
	customObjectFields,
	disabled,
	errors,
	learnResources,
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

	const filteredCustomObjectFields = useMemo(() => {
		if (customObjectFields) {
			return filterArrayByQuery({
				array: customObjectFields,
				query,
				str: 'label',
			});
		}
	}, [customObjectFields, query]);

	const getSelectedPartialValidationField = () => {
		if (values.objectValidationRuleSettings?.length) {
			const [
				partialValidationField,
			] = values.objectValidationRuleSettings;

			const customObjectField = customObjectFields.find(
				(currentCustomObjectField) =>
					currentCustomObjectField.externalReferenceCode ===
					partialValidationField.value
			);

			return getLocalizableLabel(
				creationLanguageId,
				customObjectField?.label,
				customObjectField?.name
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

			<ErrorMessage
				disabled={disabled}
				errors={errors}
				setValues={setValues}
				values={values}
			>
				<AutoComplete<ObjectField>
					emptyStateMessage={Liferay.Language.get(
						'no-fields-were-found'
					)}
					error={errors.outputType}
					id="objectValidationConditions"
					items={filteredCustomObjectFields ?? []}
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
			</ErrorMessage>
		</>
	);
}
