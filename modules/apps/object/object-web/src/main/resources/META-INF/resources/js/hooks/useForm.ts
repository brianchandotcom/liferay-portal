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

export default function useForm<T, K extends Partial<T> = T>({
	initialValues,
	onSubmit,
	validate,
}: IProps<T, K>): IUseForm<T, K> {
	const [values, setValues] = useState<K>(initialValues);
	const [errors, setErrors] = useState<{[key in keyof T]?: string}>({});

	const handleSubmit = (event: FormEvent) => {
		event.preventDefault();

		const errors = validate(values);

		if (Object.keys(errors).length) {
			setErrors(errors);
		}
		else {
			setErrors({});

			onSubmit((values as unknown) as T);
		}
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
		values,
	};
}

interface IProps<T, K extends Partial<T> = T> {
	initialValues: K;
	onSubmit: (values: T) => void;
	validate: (values: K) => {[key in keyof T]?: string};
}

interface IUseForm<T, K extends Partial<T> = T> {
	errors: {[key in keyof T]?: string};
	handleChange: ChangeEventHandler<HTMLInputElement>;
	handleSubmit: FormEventHandler<HTMLFormElement>;
	setValues: (values: Partial<T>) => void;
	values: K;
}
