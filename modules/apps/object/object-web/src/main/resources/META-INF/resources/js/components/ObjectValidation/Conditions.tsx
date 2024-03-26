/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {
	Card,
	CodeEditor,
	SidebarCategory,
	SingleSelect,
	stringUtils,
} from '@liferay/object-js-components-web';
import {
	ILearnResourceContext,
	LearnMessage,
	LearnResourcesContext,
} from 'frontend-js-components-web';
import React, {useMemo} from 'react';

import {NAME_OUTPUT_OBJECT_FIELD_EXTERNAL_REFERENCE_CODE} from '../../utils/constants';
import {DisabledGroovyScriptAlert} from '../DisabledGroovyScriptAlert';
import {ErrorMessage} from './ErrorMessage';
import {TabProps} from './useObjectValidationForm';

export interface ConditionsProps extends TabProps {
	creationLanguageId: Liferay.Language.Locale;
	customObjectFields: ObjectField[];
	disabledGroovyValidation: boolean;
	learnResources: ILearnResourceContext;
	objectValidationRuleElements: SidebarCategory[];
}

export function Conditions({
	creationLanguageId,
	customObjectFields,
	disabled,
	disabledGroovyValidation,
	errors,
	learnResources,
	objectValidationRuleElements,
	scriptManagementConfigurationPortletURL,
	selectedPartialValidationField,
	setValues,
	values,
}: ConditionsProps) {
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
						resourceKey="expression-builder-validations-reference"
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
					readOnly={disabled || disabledGroovyValidation}
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
				<SingleSelect
					error={errors.outputType}
					id="objectValidationConditions"
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
			</ErrorMessage>
		</>
	);
}
