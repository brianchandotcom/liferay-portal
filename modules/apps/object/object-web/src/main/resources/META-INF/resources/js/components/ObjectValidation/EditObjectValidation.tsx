/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTabs from '@clayui/tabs';
import {
	API,
	SidePanelForm,
	SidebarCategory,
	openToast,
	saveAndReload,
	stringUtils,
} from '@liferay/object-js-components-web';
import {ILearnResourceContext} from 'frontend-js-components-web';
import React, {useEffect, useState} from 'react';

import {BasicInfo, BasicInfoProps} from './BasicInfo';
import {Conditions, ConditionsProps} from './Conditions';
import {
	UniqueCompositeKey,
	UniqueCompositeKeyProps,
} from './UniqueCompositeKey';
import {
	ObjectValidationErrors,
	useObjectValidationForm,
} from './useObjectValidationForm';

interface EditObjectValidationProps {
	allowScriptContentToBeExecutedOrIncluded: boolean;
	baseResourceURL: string;
	creationLanguageId: Liferay.Language.Locale;
	learnResources: ILearnResourceContext;
	objectDefinitionExternalReferenceCode: string;
	objectDefinitionId: number;
	objectValidationRuleElements: SidebarCategory[];
	objectValidationRuleId: number;
	readOnly: boolean;
	scriptManagementConfigurationPortletURL: string;
}

export interface PartialValidationFields {
	id: number;
	label: string;
	name: string;
	value: string;
}

interface ErrorDetails extends Error {
	detail?: string;
}

type Tab = {
	Component: (
		params: BasicInfoProps | ConditionsProps | UniqueCompositeKeyProps
	) => JSX.Element;
	label: string;
};

const TABS = [
	{
		Component: BasicInfo,
		label: Liferay.Language.get('basic-info'),
	},
] as Tab[];

const initialValues: ObjectValidation = {
	active: false,
	engine: '',
	engineLabel: '',
	errorLabel: {},
	id: 0,
	name: {en_US: ''},
	script: '',
};

export default function EditObjectValidation({
	allowScriptContentToBeExecutedOrIncluded,
	baseResourceURL,
	creationLanguageId,
	learnResources,
	objectDefinitionExternalReferenceCode,
	objectDefinitionId,
	objectValidationRuleElements,
	objectValidationRuleId,
	readOnly,
	scriptManagementConfigurationPortletURL,
}: EditObjectValidationProps) {
	const [activeIndex, setActiveIndex] = useState<number>(0);
	const [errorMessage, setErrorMessage] = useState<ObjectValidationErrors>(
		{}
	);
	const [customObjectFields, setCustomObjectFields] = useState<ObjectField[]>(
		[]
	);
	const [selectedPartialValidationField, setSelectedPartialValidationField] =
		useState<string>();
	const [showUniqueCompositeKeyAlert, setShowUniqueCompositeKeyAlert] =
		useState(true);

	const onSubmit = async (objectValidation: ObjectValidation) => {
		delete objectValidation.lineCount;

		try {
			await API.save({
				item: objectValidation,
				url: `/o/object-admin/v1.0/object-validation-rules/${objectValidation.id}`,
			});
			saveAndReload();
			openToast({
				message: Liferay.Language.get(
					'the-object-validation-was-updated-successfully'
				),
			});
		}
		catch (error) {
			const {detail, message} = error as ErrorDetails;

			if (detail) {
				const {fieldName, message: detailMessage} = JSON.parse(
					detail as string
				) as {
					fieldName: keyof ObjectValidationErrors;
					message: string;
				};

				setErrorMessage({[fieldName]: detailMessage});
			}

			openToast({message, type: 'danger'});
		}
	};

	const {errors, handleChange, handleSubmit, setValues, values} =
		useObjectValidationForm({initialValues, onSubmit});

	if (TABS.length < 2) {
		if (values.engine === 'compositeKey') {
			TABS.push({
				Component: UniqueCompositeKey,
				label: Liferay.Language.get('unique-composite-key'),
			} as Tab);
		}
		else if (values.engine !== '') {
			TABS.push({
				Component: Conditions,
				label: Liferay.Language.get('conditions'),
			} as Tab);
		}
	}

	const disabled = readOnly || !!values?.system;
	const disabledGroovyValidation =
		!allowScriptContentToBeExecutedOrIncluded && values.engine === 'groovy';

	useEffect(() => {
		if (Object.keys(errors).length) {
			openToast({
				message: Liferay.Language.get(
					'please-fill-out-all-required-fields'
				),
				type: 'danger',
			});
		}
	}, [errors]);

	useEffect(() => {
		const makeFetch = async () => {
			const validationResponseJSON =
				await API.getObjectValidationRuleById<ObjectValidation>(
					objectValidationRuleId
				);

			const newObjectValidation: ObjectValidation = {
				...validationResponseJSON,
				script:
					validationResponseJSON.script === 'script_placeholder'
						? ''
						: validationResponseJSON.script,
			};

			const objectFieldsResponseJSON =
				await API.getObjectDefinitionObjectFields(objectDefinitionId);

			setCustomObjectFields(
				objectFieldsResponseJSON.filter(
					(objectField) => !objectField.system
				)
			);
			setValues(newObjectValidation);
		};

		makeFetch();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [objectDefinitionId, objectValidationRuleId]);

	useEffect(() => {
		if (values.objectValidationRuleSettings?.length) {
			const [partialValidationField] =
				values.objectValidationRuleSettings;

			const customObjectField = customObjectFields.find(
				(currentCustomObjectField) =>
					currentCustomObjectField.externalReferenceCode ===
					partialValidationField.value
			);

			setSelectedPartialValidationField(
				customObjectField?.externalReferenceCode ?? undefined
			);

			return;
		}

		setSelectedPartialValidationField(undefined);

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [values.objectValidationRuleSettings]);

	return (
		<SidePanelForm
			onSubmit={handleSubmit}
			title={stringUtils.getLocalizableLabel(
				creationLanguageId,
				values.name
			)}
		>
			<ClayTabs className="side-panel-iframe__tabs">
				{TABS.map(({label}, index) =>
					(values.engine?.startsWith('function#') ||
						values.engine?.startsWith('javaDelegate#')) &&
					index === 1 ? (
						<React.Fragment key={index} />
					) : (
						<ClayTabs.Item
							active={activeIndex === index}
							key={index}
							onClick={() => setActiveIndex(index)}
						>
							{label}
						</ClayTabs.Item>
					)
				)}
			</ClayTabs>

			<ClayTabs.Content activeIndex={activeIndex} fade>
				{TABS.map(({Component, label}, index) =>
					activeIndex === index ? (
						<ClayTabs.TabPane key={index}>
							<Component
								baseResourceURL={baseResourceURL}
								componentLabel={label}
								creationLanguageId={creationLanguageId}
								customObjectFields={customObjectFields ?? []}
								disabled={disabled}
								disabledGroovyValidation={
									disabledGroovyValidation
								}
								errors={
									Object.keys(errors).length !== 0
										? errors
										: errorMessage
								}
								handleChange={handleChange}
								learnResources={learnResources}
								objectDefinitionExternalReferenceCode={
									objectDefinitionExternalReferenceCode
								}
								objectValidationRuleElements={
									objectValidationRuleElements
								}
								scriptManagementConfigurationPortletURL={
									scriptManagementConfigurationPortletURL
								}
								selectedPartialValidationField={
									selectedPartialValidationField
								}
								setShowUniqueCompositeKeyAlert={
									setShowUniqueCompositeKeyAlert
								}
								setValues={setValues}
								showUniqueCompositeKeyAlert={
									showUniqueCompositeKeyAlert
								}
								values={values}
							/>
						</ClayTabs.TabPane>
					) : (
						<React.Fragment key={index} />
					)
				)}
			</ClayTabs.Content>
		</SidePanelForm>
	);
}
