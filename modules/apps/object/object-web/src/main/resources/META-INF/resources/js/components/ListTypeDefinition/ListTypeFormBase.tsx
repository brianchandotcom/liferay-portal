/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	FormError,
	REQUIRED_MSG,
	invalidateRequired,
	useForm,
} from '@liferay/object-js-components-web';

import {defaultLanguageId} from '../../utils/constants';

export function useListTypeForm({initialValues, onSubmit}: IUseListTypeForm) {
	const validate = (picklist: Partial<ListTypeDefinition>) => {
		const errors: ObjectValidationErrors = {};
		const label = picklist.name_i18n?.[defaultLanguageId];

		if (invalidateRequired(label)) {
			errors.name_i18n = REQUIRED_MSG;
		}
		if (invalidateRequired(picklist.externalReferenceCode)) {
			errors.externalReferenceCode = REQUIRED_MSG;
		}

		return errors;
	};

	const {errors, handleChange, handleSubmit, setValues, values} = useForm<
		ListTypeDefinition
	>({
		initialValues,
		onSubmit,
		validate,
	});

	return {errors, handleChange, handleSubmit, setValues, values};
}
interface IUseListTypeForm {
	initialValues: Partial<ListTypeDefinition>;
	onSubmit: (picklist: ListTypeDefinition) => void;
}

export type ObjectValidationErrors = FormError<ListTypeDefinition>;
