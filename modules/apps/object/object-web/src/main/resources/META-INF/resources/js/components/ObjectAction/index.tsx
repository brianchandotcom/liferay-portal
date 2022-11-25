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

import 'codemirror/mode/groovy/groovy';
import ClayTabs from '@clayui/tabs';
import {
	API,
	CustomItem,
	FormError,
	SidePanelForm,
	SidebarCategory,
	invalidateRequired,
	openToast,
	saveAndReload,
	useForm,
} from '@liferay/object-js-components-web';
import React, {useEffect, useMemo, useState} from 'react';

import ActionBuilder from './tabs/ActionBuilder';
import BasicInfo from './tabs/BasicInfo';

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

const REQUIRED_MSG = Liferay.Language.get('required');

const TABS = [
	Liferay.Language.get('basic-info'),
	Liferay.Language.get('action-builder'),
];

interface ActionProps {
	isApproved?: boolean;
	objectAction: Partial<ObjectAction>;
	objectActionCodeEditorElements: SidebarCategory[];
	objectActionExecutors: CustomItem[];
	objectActionTriggers: CustomItem[];
	objectDefinitionId: number;
	objectDefinitionsRelationshipsURL: string;
	readOnly?: boolean;
	requestParams: {
		method: 'POST' | 'PUT';
		url: string;
	};
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

interface IUseObjectActionForm {
	initialValues: Partial<ObjectAction>;
	onSubmit: (field: ObjectAction) => void;
}

export type ActionError = FormError<ObjectAction & ObjectActionParameters> & {
	predefinedValues?: {[key: string]: string};
};

export default function Action({
	isApproved,
	objectAction: initialValues,
	objectActionCodeEditorElements,
	objectActionExecutors,
	objectActionTriggers,
	objectDefinitionId,
	objectDefinitionsRelationshipsURL,
	readOnly,
	requestParams: {method, url},
	successMessage,
	systemObject,
	validateExpressionURL,
}: ActionProps) {
	const [backEndErrors, setBackEndErrors] = useState<Error>({});

	const onSubmit = async (objectAction: ObjectAction) => {
		if (objectAction.parameters) {
			delete objectAction?.parameters['lineCount'];
		}

		delete objectAction.objectDefinitionId;

		try {
			await API.save(url, objectAction, method);
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

	const {
		errors,
		handleChange,
		handleSubmit,
		setValues,
		values,
	} = useObjectActionForm({initialValues, onSubmit});

	const [activeIndex, setActiveIndex] = useState(0);

	return (
		<SidePanelForm
			onSubmit={handleSubmit}
			title={Liferay.Language.get('new-action')}
		>
			<ClayTabs className="side-panel-iframe__tabs">
				{TABS.map((label, index) => (
					<ClayTabs.Item
						active={activeIndex === index}
						key={index}
						onClick={() => setActiveIndex(index)}
					>
						{label}
					</ClayTabs.Item>
				))}
			</ClayTabs>

			<ClayTabs.Content activeIndex={activeIndex} fade>
				<ClayTabs.TabPane>
					<BasicInfo
						errors={
							Object.keys(errors).length ? errors : backEndErrors
						}
						handleChange={handleChange}
						isApproved={isApproved!}
						readOnly={readOnly}
						setValues={setValues}
						values={values}
					/>
				</ClayTabs.TabPane>

				<ClayTabs.TabPane>
					<ActionBuilder
						errors={
							Object.keys(errors).length ? errors : backEndErrors
						}
						isApproved={isApproved!}
						objectActionCodeEditorElements={
							objectActionCodeEditorElements
						}
						objectActionExecutors={objectActionExecutors}
						objectActionTriggers={objectActionTriggers}
						objectDefinitionId={
							objectDefinitionId ??
							initialValues.objectDefinitionId
						}
						objectDefinitionsRelationshipsURL={
							objectDefinitionsRelationshipsURL
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

function useObjectActionForm({initialValues, onSubmit}: IUseObjectActionForm) {
	const [fields, setFields] = useState<ObjectField[]>([]);

	const objectFieldsMap = useMemo(() => {
		const fieldMap = new Map<string, ObjectField>();

		fields.forEach((field) => {
			fieldMap.set(field.name, field);
		});

		return fieldMap;
	}, [fields]);

	const validate = (values: Partial<ObjectAction>) => {
		const errors: ActionError = {};

		if (
			Liferay.FeatureFlags['LPS-148804'] &&
			invalidateRequired(values.label?.[defaultLanguageId])
		) {
			errors.label = REQUIRED_MSG;
		}

		if (invalidateRequired(values.name)) {
			errors.name = REQUIRED_MSG;
		}

		if (invalidateRequired(values.objectActionTriggerKey)) {
			errors.objectActionTriggerKey = REQUIRED_MSG;
		}

		if (invalidateRequired(values.objectActionExecutorKey)) {
			errors.objectActionExecutorKey = REQUIRED_MSG;
		}
		else if (
			values.objectActionExecutorKey === 'webhook' &&
			invalidateRequired(values.parameters?.url)
		) {
			errors.url = REQUIRED_MSG;
		}
		else if (
			values.objectActionExecutorKey === 'groovy' &&
			!!values.parameters?.lineCount &&
			values.parameters.lineCount > 2987
		) {
			errors.script = Liferay.Language.get(
				'the-maximum-number-of-lines-available-is-2987'
			);
		}
		else if (values.objectActionExecutorKey === 'add-object-entry') {
			if (!values.parameters?.objectDefinitionId) {
				errors.objectDefinitionId = REQUIRED_MSG;
			}
		}

		if (
			values.objectActionExecutorKey === 'add-object-entry' ||
			values.objectActionExecutorKey === 'update-object-entry'
		) {
			if (values.parameters?.predefinedValues) {
				const predefinedValues = values.parameters?.predefinedValues;

				predefinedValues.forEach(({name, value}) => {
					if (
						objectFieldsMap.get(name)?.required &&
						invalidateRequired(value)
					) {
						if (!errors.predefinedValues) {
							errors.predefinedValues = {} as any;
						}
						errors.predefinedValues![name] = REQUIRED_MSG;
					}
				});
			}
		}
		else if (
			values.objectActionTriggerKey === 'standalone' &&
			invalidateRequired(values.errorMessage?.[defaultLanguageId])
		) {
			errors.errorMessage = REQUIRED_MSG;
		}

		if (
			typeof values.conditionExpression === 'string' &&
			invalidateRequired(values.conditionExpression)
		) {
			errors.conditionExpression = REQUIRED_MSG;
		}

		if (Object.keys(errors).length) {
			openToast({
				message: REQUIRED_MSG,
				type: 'danger',
			});
		}

		return errors;
	};

	const {errors, values, ...otherProps} = useForm<
		ObjectAction,
		ObjectActionParameters
	>({
		initialValues,
		onSubmit,
		validate,
	});

	useEffect(() => {
		if (values.parameters?.objectDefinitionId) {
			API.getObjectFields(values.parameters.objectDefinitionId).then(
				(fields) => {
					const filteredFields = fields.filter(
						({businessType, system}) =>
							businessType !== 'Aggregation' &&
							businessType !== 'Relationship' &&
							!system
					);

					setFields(filteredFields);
				}
			);
		}
		else {
			setFields([]);
		}
	}, [values.parameters?.objectDefinitionId]);

	return {errors: errors as ActionError, values, ...otherProps};
}
