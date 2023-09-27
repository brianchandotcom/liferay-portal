/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	FormError,
	invalidateLocalizableLabelRequired,
	invalidateRequired,
	useForm,
} from '@liferay/object-js-components-web';
import {sub} from 'frontend-js-web';

import {
	checkIfFirstLetterIsUppercase,
	specialCharactersInString,
} from '../../utils/string';

interface UseObjectDetailsFormProps {
	initialValues: Partial<ObjectDefinition>;
	onSubmit: (field: ObjectDefinition) => void;
}

export type ObjectDetailsErrors = FormError<Partial<ObjectDefinition>>;

const REQUIRED_MSG = Liferay.Language.get('required');

const getNameErrors = (
	errors: FormError<Partial<ObjectDefinition>>,
	name: string
) => {
	if (invalidateRequired(name)) {
		errors.name = REQUIRED_MSG;

		return;
	}

	if (specialCharactersInString(name)) {
		errors.name = Liferay.Language.get(
			'name-must-only-contain-letters-and-digits'
		);

		return;
	}

	if (name.length > 41) {
		errors.name = sub(
			Liferay.Language.get(
				'only-x-characters-are-allowed-in-the-x-field'
			),
			['41', 'name']
		);

		return;
	}

	if (!checkIfFirstLetterIsUppercase(name)) {
		errors.name = Liferay.Language.get(
			'the-first-character-of-a-name-must-be-an-upper-case-letter'
		);

		return;
	}
};

export function useObjectDetailsForm({
	initialValues,
	onSubmit,
}: UseObjectDetailsFormProps) {
	const validate = (objectDefinition: Partial<ObjectDefinition>) => {
		const errors: ObjectDetailsErrors = {};

		if (!objectDefinition.system) {
			if (invalidateLocalizableLabelRequired(objectDefinition.label)) {
				errors.label = REQUIRED_MSG;
			}

			if (
				invalidateLocalizableLabelRequired(objectDefinition.pluralLabel)
			) {
				errors.pluralLabel = REQUIRED_MSG;
			}
		}

		if (
			objectDefinition.accountEntryRestricted &&
			!objectDefinition.accountEntryRestrictedObjectFieldName
		) {
			errors.accountEntryRestrictedObjectFieldName = Liferay.Language.get(
				'if-activated-the-account-restriction-field-must-be-selected'
			);
		}

		getNameErrors(errors, objectDefinition.name as string);

		return errors;
	};

	const {
		errors,
		handleChange,
		handleSubmit,
		handleValidate,
		setValues,
		values,
	} = useForm<ObjectDefinition>({
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
