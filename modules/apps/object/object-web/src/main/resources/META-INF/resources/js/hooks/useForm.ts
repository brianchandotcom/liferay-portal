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

import React, {useState} from 'react';

type TFormEvent = React.FormEventHandler<HTMLFormElement>;

type TUseFormProps = {
	initialValues: {};
	validate: (values: any) => {};
	onSubmit: (values: any) => void;
};

type TGenericObject = {
	[key: string]: string;
};

type TUseForm = (
	props: TUseFormProps
) => {
	errors: TGenericObject;
	handleChange: TFormEvent;
	handleSubmit: TFormEvent;
	values: TGenericObject;
};

const useForm: TUseForm = ({initialValues, onSubmit, validate}) => {
	const [values, setValues] = useState(initialValues);
	const [errors, setErrors] = useState({});

	const handleSubmit: TFormEvent = (event) => {
		event.preventDefault();

		const errors = validate(values);

		if (Object.keys(errors).length) {
			setErrors(errors);
		}
		else {
			setErrors({});

			onSubmit(values);
		}
	};

	const handleChange: TFormEvent = ({target: {name, value}}: any) => {
		setValues({
			...values,
			[name]: value,
		});
	};

	return {
		errors,
		handleChange,
		handleSubmit,
		values,
	};
};

export default useForm;
