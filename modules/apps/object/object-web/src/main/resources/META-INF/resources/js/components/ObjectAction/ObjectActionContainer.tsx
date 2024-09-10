/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTabs from '@clayui/tabs';
import {
	API,
	FormError,
	SidePanelForm,
	SidebarCategory,
	openToast,
	saveAndReload,
} from '@liferay/object-js-components-web';
import React, {useState} from 'react';

import ActionBuilder from './tabs/ActionBuilder';
import BasicInfo from './tabs/BasicInfo';
import {useObjectActionForm} from './useObjectActionForm';

const TABS = [
	{
		key: 'basic-info',
		label: Liferay.Language.get('basic-info'),
	},
	{
		key: 'action-builder',
		label: Liferay.Language.get('action-builder'),
	},
];

interface ObjectActionContainerProps {
	allowScriptContentToBeExecutedOrIncluded: boolean;
	editingObjectAction?: boolean;
	isApproved?: boolean;
	objectAction: Partial<ObjectAction>;
	objectActionCodeEditorElements: SidebarCategory[];
	objectActionExecutors: ObjectActionTriggerExecutorItem[];
	objectActionTriggers: ObjectActionTriggerExecutorItem[];
	objectDefinitionExternalReferenceCode: string;
	objectDefinitionId: number;
	objectDefinitionsRelationshipsURL: string;
	readOnly?: boolean;
	requestParams: {
		method: 'POST' | 'PUT';
		url: string;
	};
	scriptManagementConfigurationPortletURL: string;
	successMessage: string;
	systemObject: boolean;
	title: string;
	validateExpressionURL: string;
}

interface ErrorMessage {
	fieldName: keyof ObjectAction;
	message?: string;
	messages?: ErrorMessage[];
}

interface Error {
	[key: string]: string | Error;
}

export type ActionError = FormError<ObjectAction & ObjectActionParameters> & {
	predefinedValues?: {[key: string]: string};
};

export function ObjectActionContainer({
	allowScriptContentToBeExecutedOrIncluded,
	editingObjectAction = false,
	isApproved,
	objectAction: initialValues,
	objectActionCodeEditorElements,
	objectActionExecutors,
	objectActionTriggers,
	objectDefinitionExternalReferenceCode,
	objectDefinitionId,
	objectDefinitionsRelationshipsURL,
	readOnly,
	requestParams: {method, url},
	scriptManagementConfigurationPortletURL,
	successMessage,
	systemObject,
	validateExpressionURL,
}: ObjectActionContainerProps) {
	const [activeIndex, setActiveIndex] = useState(0);
	const [backEndErrors, setBackEndErrors] = useState<Error>({});

	const onSubmit = async (objectAction: ObjectAction) => {
		if (objectAction.parameters) {
			delete objectAction?.parameters['lineCount'];
		}

		delete objectAction.objectDefinitionId;

		try {
			await API.save({item: objectAction, method, url});
			saveAndReload();
			openToast({message: successMessage});
		}
		catch (error) {
			const {detail} = error as {detail?: string};
			const details = JSON.parse(detail as string);
			const newErrors: Error = {};

			const parseError = (details: ErrorMessage[], errors: Error) => {
				details.forEach(({fieldName, message, messages}) => {
					if (message) {
						errors[fieldName] = message;
					}
					else {
						errors[fieldName] = {};
						parseError(
							messages as ErrorMessage[],
							errors[fieldName] as Error
						);
					}
				});
			};

			parseError(details, newErrors);

			setBackEndErrors(newErrors);

			const errorMessages = new Set<string>();

			const getErrorMessage = (errors: Error) => {
				Object.values(errors).forEach((value) => {
					if (typeof value === 'string') {
						if (!errorMessages.has(value)) {
							errorMessages.add(value);
						}
					}
					else {
						getErrorMessage(value);
					}
				});
			};

			if (newErrors) {
				getErrorMessage(newErrors);
				errorMessages.forEach((message) => {
					openToast({
						message,
						type: 'danger',
					});
				});
			}
		}
	};

	const {errors, handleChange, handleSubmit, setValues, values} =
		useObjectActionForm({initialValues, onSubmit});

	const disableGroovyAction =
		!allowScriptContentToBeExecutedOrIncluded &&
		values.objectActionExecutorKey === 'groovy';

	let newObjectActionExecutors = [...objectActionExecutors];

	if (!allowScriptContentToBeExecutedOrIncluded) {
		const shouldFilterGroovyExecutor =
			!editingObjectAction ||
			(editingObjectAction &&
				values.objectActionExecutorKey !== 'groovy');

		if (shouldFilterGroovyExecutor) {
			newObjectActionExecutors = objectActionExecutors.filter(
				(objectActionExecutor) =>
					objectActionExecutor.value !== 'groovy'
			);
		}
	}

	return (
		<SidePanelForm
			onSubmit={handleSubmit}
			title={Liferay.Language.get('new-action')}
		>
			<ClayTabs>
				{TABS.map(({key, label}, index) => (
					<ClayTabs.Item
						active={activeIndex === index}
						key={index}
						onClick={() => {
							setActiveIndex(index);

							if (key === 'action-builder') {
								Liferay.fire('reloadFDS');
							}
						}}
					>
						{label}
					</ClayTabs.Item>
				))}
			</ClayTabs>

			<ClayTabs.Content activeIndex={activeIndex} fade>
				<ClayTabs.TabPane>
					<BasicInfo
						disableGroovyAction={disableGroovyAction}
						errors={
							Object.keys(errors).length ? errors : backEndErrors
						}
						handleChange={handleChange}
						isApproved={isApproved!}
						readOnly={readOnly}
						scriptManagementConfigurationPortletURL={
							scriptManagementConfigurationPortletURL
						}
						setValues={setValues}
						values={values}
					/>
				</ClayTabs.TabPane>

				<ClayTabs.TabPane>
					<ActionBuilder
						disableGroovyAction={disableGroovyAction}
						errors={
							Object.keys(errors).length ? errors : backEndErrors
						}
						isApproved={isApproved!}
						objectActionCodeEditorElements={
							objectActionCodeEditorElements
						}
						objectActionExecutors={newObjectActionExecutors}
						objectActionTriggers={objectActionTriggers}
						objectDefinitionExternalReferenceCode={
							objectDefinitionExternalReferenceCode
						}
						objectDefinitionId={
							objectDefinitionId ??
							initialValues.objectDefinitionId
						}
						objectDefinitionsRelationshipsURL={
							objectDefinitionsRelationshipsURL
						}
						scriptManagementConfigurationPortletURL={
							scriptManagementConfigurationPortletURL
						}
						setValues={setValues}
						systemObject={systemObject}
						validateExpressionURL={validateExpressionURL}
						values={values}
					/>
				</ClayTabs.TabPane>
			</ClayTabs.Content>
		</SidePanelForm>
	);
}
