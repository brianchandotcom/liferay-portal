/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {FormError} from '@liferay/object-js-components-web';
export declare function useListTypeForm({
	initialValues,
	onSubmit,
}: IUseListTypeForm): {
	errors: FormError<ListTypeDefinition>;
	handleChange: import('react').ChangeEventHandler<HTMLInputElement>;
	handleSubmit: import('react').FormEventHandler<HTMLFormElement>;
	setValues: (values: Partial<ListTypeDefinition>) => void;
	values: Partial<ListTypeDefinition>;
};
interface IUseListTypeForm {
	initialValues: Partial<ListTypeDefinition>;
	onSubmit: (picklist: ListTypeDefinition) => void;
}
export declare type ObjectValidationErrors = FormError<ListTypeDefinition>;
export {};
