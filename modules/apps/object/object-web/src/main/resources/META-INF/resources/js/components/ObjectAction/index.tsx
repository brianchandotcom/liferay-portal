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
import ClayAlert from '@clayui/alert';
import ClayTabs from '@clayui/tabs';
import {
	CustomItem,
	FormError,
	SidePanelForm,
	invalidateRequired,
	openToast,
	saveAndReload,
	useForm,
} from '@liferay/object-js-components-web';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useMemo, useState} from 'react';

import {getObjectFields} from '../../utils/api';
import {HEADERS} from '../../utils/constants';
import ActionBuilder from './tabs/ActionBuilder';
import BasicInfo from './tabs/BasicInfo';

const REQUIRED_MSG = Liferay.Language.get('required');

const TABS = [
	Liferay.Language.get('basic-info'),
	Liferay.Language.get('action-builder'),
];

export default function Action({
	objectAction: initialValues,
	objectActionExecutors,
	objectActionTriggers,
	objectDefinitionsRelationshipsURL,
	readOnly,
	requestParams: {method, url},
	successMessage,
	validateExpressionURL,
}: IProps) {
	const [warningAlert, setWarningAlert] = useState(false);

	const [backEndErrors, setBackEndErrors] = useState<Error>({});

	const onSubmit = async (objectAction: ObjectAction) => {
		if (objectAction.parameters) {
			delete objectAction?.parameters['lineCount'];
		}

		const response = await fetch(url, {
			body: JSON.stringify(objectAction),
			headers: HEADERS,
			method,
		});

		if (response.status === 401) {
			window.location.reload();
		}
		else if (response.ok) {
			saveAndReload();
			openToast({message: successMessage});

			return;
		}

		const {detail}: {detail: string} = await response.json();

		const newErrors: Error = {};

		const details = JSON.parse(detail);

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

			{warningAlert && (
				<ClayAlert
					className="lfr-objects__side-panel-content-container"
					displayType="warning"
					onClose={() => setWarningAlert(false)}
					title={`${Liferay.Language.get('warning')}:`}
				>
					{Liferay.Language.get(
						'required-fields-must-have-predefined-values'
					)}
				</ClayAlert>
			)}

			<ClayTabs.Content activeIndex={activeIndex} fade>
				<ClayTabs.TabPane>
					<BasicInfo
						errors={errors}
						handleChange={handleChange}
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
						objectActionExecutors={objectActionExecutors}
						objectActionTriggers={objectActionTriggers}
						objectDefinitionsRelationshipsURL={
							objectDefinitionsRelationshipsURL
						}
						setValues={setValues}
						setWarningAlert={setWarningAlert}
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
			else if (values.parameters?.predefinedValues) {
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
			getObjectFields(values.parameters.objectDefinitionId).then(
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

interface IProps {
	objectAction: Partial<ObjectAction>;
	objectActionExecutors: CustomItem[];
	objectActionTriggers: CustomItem[];
	objectDefinitionsRelationshipsURL: string;
	readOnly?: boolean;
	requestParams: {
		method: 'GET' | 'POST' | 'DELETE' | 'PUT';
		url: string;
	};
	successMessage: string;
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
