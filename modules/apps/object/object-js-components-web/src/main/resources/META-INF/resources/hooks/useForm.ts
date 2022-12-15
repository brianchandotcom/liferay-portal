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

import {ChangeEventHandler, FormEvent, FormEventHandler, useState} from 'react';

export function invalidateRequired(text: string | void) {
	return !text?.trim();
}

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

export function useForm<T, P = {}, K extends Partial<T> = Partial<T>>({
	initialValues,
	onSubmit,
	validate,
}: IProps<T, P, K>): IUseForm<T, P, K> {
	const [values, setValues] = useState<K>(initialValues);
	const [errors, setErrors] = useState<FormError<T & P>>({});

	const validateSubmit = () => {
		const errors = validate(values);

		if (Object.keys(errors).length) {
			setErrors(errors);
		}
		else {
			setErrors({});

			onSubmit((values as unknown) as T);
		}
	};

	const handleSubmit = (event: FormEvent) => {
		event.preventDefault();

		validateSubmit();
	};

	const handleChange: ChangeEventHandler<HTMLInputElement> = ({
		target: {name, value},
	}) => setValues((values) => ({...values, [name]: value}));

	return {
		errors,
		handleChange,
		handleSubmit,
		setValues: (values: Partial<T>) =>
			setValues((currentValues) => ({...currentValues, ...values})),
		validateSubmit,
		values,
	};
}

export type FormError<T> = {
	[key in keyof T]?: string;
};
