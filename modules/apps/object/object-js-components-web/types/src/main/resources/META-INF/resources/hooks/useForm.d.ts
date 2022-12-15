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

import {ChangeEventHandler, FormEventHandler} from 'react';
export declare function invalidateRequired(text: string | void): boolean;
interface IProps<T, P = {}, K extends Partial<T> = Partial<T>> {
	initialValues: K;
	onSubmit: (values: T) => void;
	validate: (values: K) => FormError<T & P>;
}
interface IUseForm<T, P = {}, K extends Partial<T> = Partial<T>> {
	errors: FormError<T & P>;
	handleChange: ChangeEventHandler<HTMLInputElement>;
	handleSubmit: FormEventHandler<HTMLFormElement>;
	setValues: (values: Partial<T>) => void;
	validateSubmit: () => void;
	values: K;
}
export declare function useForm<T, P = {}, K extends Partial<T> = Partial<T>>({
	initialValues,
	onSubmit,
	validate,
}: IProps<T, P, K>): IUseForm<T, P, K>;
export declare type FormError<T> = {
	[key in keyof T]?: string;
};
export {};
