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

import {
	FormError,
	invalidateLocalizableLabelRequired,
	invalidateRequired,
	useForm,
} from '@liferay/object-js-components-web';

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
		errors.name = Liferay.Language.get('only-41-characters-are-allowed');

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

		if (invalidateLocalizableLabelRequired(objectDefinition.label)) {
			errors.label = REQUIRED_MSG;
		}

		if (invalidateLocalizableLabelRequired(objectDefinition.pluralLabel)) {
			errors.pluralLabel = REQUIRED_MSG;
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
