/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	API,
	REQUIRED_MSG,
	invalidateRequired,
	openToast,
	useForm,
} from '@liferay/object-js-components-web';
import {useEffect, useMemo, useState} from 'react';

import {defaultLanguageId} from '../../utils/constants';
import {ActionError} from './index';

interface UseObjectActionFormProps {
	initialValues: Partial<ObjectAction>;
	onSubmit: (field: ObjectAction) => void;
}

export function useObjectActionForm({
	initialValues,
	onSubmit,
}: UseObjectActionFormProps) {
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

		if (invalidateRequired(values.label?.[defaultLanguageId])) {
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
			if (!values.parameters?.objectDefinitionExternalReferenceCode) {
				errors.objectDefinitionExternalReferenceCode = REQUIRED_MSG;
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

		if (
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
		if (values.parameters?.objectDefinitionExternalReferenceCode) {
			const makeFetch = async () => {
				const response = await API.getObjectDefinitionByExternalReferenceCodeObjectFields(
					values.parameters
						?.objectDefinitionExternalReferenceCode as string
				);

				const filteredFields = response.filter(
					({businessType, system}) =>
						businessType !== 'Aggregation' &&
						businessType !== 'Relationship' &&
						!system
				);

				setFields(filteredFields);
			};

			makeFetch();
		}
		else {
			setFields([]);
		}
	}, [values.parameters?.objectDefinitionExternalReferenceCode]);

	return {errors: errors as ActionError, values, ...otherProps};
}
