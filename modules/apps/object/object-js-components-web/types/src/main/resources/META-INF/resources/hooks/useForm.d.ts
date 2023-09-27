/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ChangeEventHandler, FormEventHandler} from 'react';
export declare function invalidateLocalizableLabelRequired(
	labels: LocalizedValue<string> | undefined
): boolean;
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
	handleValidate: () => FormError<T & P>;
	setValues: (values: Partial<T>) => void;
	validateSubmit: () => void;
	values: K;
}
export declare type FormError<T> = {
	[key in keyof T]?: string;
};
export declare function useForm<T, P = {}, K extends Partial<T> = Partial<T>>({
	initialValues,
	onSubmit,
	validate,
}: IProps<T, P, K>): IUseForm<T, P, K>;
export {};
