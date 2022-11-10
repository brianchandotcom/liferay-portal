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
