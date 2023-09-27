/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {FormError} from '@liferay/object-js-components-web';
interface UseObjectDetailsFormProps {
	initialValues: Partial<ObjectDefinition>;
	onSubmit: (field: ObjectDefinition) => void;
}
export declare type ObjectDetailsErrors = FormError<Partial<ObjectDefinition>>;
export declare function useObjectDetailsForm({
	initialValues,
	onSubmit,
}: UseObjectDetailsFormProps): {
	errors: FormError<ObjectDefinition>;
	handleChange: import('react').ChangeEventHandler<HTMLInputElement>;
	handleSubmit: import('react').FormEventHandler<HTMLFormElement>;
	handleValidate: () => FormError<ObjectDefinition>;
	setValues: (values: Partial<ObjectDefinition>) => void;
	values: Partial<ObjectDefinition>;
};
export {};
