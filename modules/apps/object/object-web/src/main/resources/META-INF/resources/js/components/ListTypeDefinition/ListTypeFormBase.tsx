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
	REQUIRED_MSG,
	invalidateRequired,
	useForm,
} from '@liferay/object-js-components-web';

import {defaultLanguageId} from '../../utils/constants';

export function useListTypeForm({initialValues, onSubmit}: IUseListTypeForm) {
	const validate = (picklist: Partial<PickList>) => {
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
		PickList
	>({
		initialValues,
		onSubmit,
		validate,
	});

	return {errors, handleChange, handleSubmit, setValues, values};
}
interface IUseListTypeForm {
	initialValues: Partial<PickList>;
	onSubmit: (picklist: PickList) => void;
}

export type ObjectValidationErrors = FormError<PickList>;
