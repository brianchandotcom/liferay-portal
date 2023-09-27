/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ChangeEventHandler, FormEvent, FormEventHandler, useState} from 'react';

export function invalidateLocalizableLabelRequired(
	labels: LocalizedValue<string> | undefined
) {
	const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

	if (!labels) {
		return true;
	}

	return !labels[defaultLanguageId];
}

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
	handleValidate: () => FormError<T & P>;
	setValues: (values: Partial<T>) => void;
	validateSubmit: () => void;
	values: K;
}

export type FormError<T> = {
	[key in keyof T]?: string;
};

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

	const handleValidate = () => {
		const errors = validate(values);

		if (Object.keys(errors).length) {
			setErrors(errors);
		}
		else {
			setErrors({});
		}

		return errors;
	};

	return {
		errors,
		handleChange,
		handleSubmit,
		handleValidate,
		setValues: (values: Partial<T>) =>
			setValues((currentValues) => ({...currentValues, ...values})),
		validateSubmit,
		values,
	};
}
