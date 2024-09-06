/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	FormError,
	constantsUtils,
	invalidateRequired,
	useForm,
} from '@liferay/object-js-components-web';

import {defaultLanguageId} from '../../utils/constants';

interface UseObjectRelationshipFormProps {
	initialValues: Partial<ObjectRelationship>;
	onSubmit: (relationship: ObjectRelationship) => void;
	parameterRequired: boolean;
}

export function useObjectRelationshipForm({
	initialValues,
	onSubmit,
	parameterRequired,
}: UseObjectRelationshipFormProps) {
	const validate = (relationship: Partial<ObjectRelationship>) => {
		const errors: FormError<ObjectRelationship> = {};

		const label = relationship.label?.[defaultLanguageId];

		if (invalidateRequired(label)) {
			errors.label = constantsUtils.REQUIRED_MSG;
		}

		if (invalidateRequired(relationship.name ?? label)) {
			errors.name = constantsUtils.REQUIRED_MSG;
		}

		if (invalidateRequired(relationship.type)) {
			errors.type = constantsUtils.REQUIRED_MSG;
		}

		if (!relationship.objectDefinitionId1) {
			errors.objectDefinitionId1 = constantsUtils.REQUIRED_MSG;
		}

		if (!relationship.objectDefinitionId2) {
			errors.objectDefinitionId2 = constantsUtils.REQUIRED_MSG;
		}

		if (
			parameterRequired &&
			relationship.type === 'oneToMany' &&
			!relationship.parameterObjectFieldName
		) {
			errors.parameterObjectFieldName = constantsUtils.REQUIRED_MSG;
		}

		return errors;
	};

	const {
		errors,
		handleChange,
		handleSubmit,
		handleValidate,
		setValues,
		values,
	} = useForm({
		initialValues,
		onSubmit,
		validate,
	});

	return {
		errors,
		handleChange,
		handleSubmit,
		handleValidate,
		setValues,
		values,
	};
}
