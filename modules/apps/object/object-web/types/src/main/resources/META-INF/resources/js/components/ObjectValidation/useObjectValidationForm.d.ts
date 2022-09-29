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

import {FormError} from '@liferay/object-js-components-web';
import {ChangeEventHandler} from 'react';
interface IUseObjectValidationForm {
	initialValues: Partial<ObjectValidation>;
	onSubmit: (validation: ObjectValidation) => void;
}
export declare type ObjectValidationErrors = FormError<ObjectValidation>;
export interface TabProps {
	disabled: boolean;
	errors: ObjectValidationErrors;
	handleChange: ChangeEventHandler<HTMLInputElement>;
	setValues: (values: Partial<ObjectValidation>) => void;
	values: Partial<ObjectValidation>;
}
export declare function useObjectValidationForm({
	initialValues,
	onSubmit,
}: IUseObjectValidationForm): {
	errors: FormError<ObjectValidation>;
	handleChange: ChangeEventHandler<HTMLInputElement>;
	handleSubmit: import('react').FormEventHandler<HTMLFormElement>;
	setValues: (values: Partial<ObjectValidation>) => void;
	values: Partial<ObjectValidation>;
};
export {};
