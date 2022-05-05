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

import {DocumentNode} from 'graphql';
import {useState} from 'react';
import {useNavigate} from 'react-router-dom';

import client from '../graphql/apolloClient';
import i18n from '../i18n';
import {Liferay} from '../services/liferay/liferay';

type OnSubmitOptions = {
	createMutation: DocumentNode;
	updateMutation: DocumentNode;
};

export type FormOptions = {
	formState: any;
	onChange: (state: any) => (event: any) => void;
	onClose: (path: string) => void;
	onError: (error?: any) => void;
	onSave: (param?: any) => void;
	onSubmit: (data: any, options: OnSubmitOptions) => Promise<void>;
};

export type Form = {
	forceRefetch: number;
	form: FormOptions;
};

export type FormComponent = Omit<Form, 'forceRefetch'>;

const useFormActions = (): Form => {
	const [formState, setFormState] = useState();
	const [forceRefetch, setForceRefetch] = useState(0);
	const navigate = useNavigate();

	const onClose = () => {
		navigate(-1);
	};

	const onError = (error: any) => {
		console.error(error);

		Liferay.Util.openToast({
			message: i18n.translate('an-unexpected-error-occurred'),
			type: 'danger',
		});
	};

	const onSave = (state?: any) => {
		Liferay.Util.openToast({
			message: i18n.translate('your-request-completed-successfully'),
			type: 'success',
		});

		setForceRefetch(new Date().getTime());

		if (state) {
			setFormState(state);
			onSave(state);
		}
		navigate(-1);
	};

	const onSubmit = async (
		data: any,
		{createMutation, updateMutation}: OnSubmitOptions
	) => {
		const variables: any = {
			data,
		};

		if (data.id) {
			variables.id = data.id;
		}

		delete variables.data.id;

		try {
			await client.mutate({
				mutation: variables.id ? updateMutation : createMutation,
				variables,
			});

			onSave();
		}
		catch (error) {
			onError(error);

			throw error;
		}
	};

	return {
		forceRefetch,
		form: {
			formState,
			onChange: ({form, setForm}: any) => (event: any) => {
				const {
					target: {checked, name, type},
				} = event;

				let {value} = event.target;

				if (type === 'checkbox') {
					value = checked;
				}

				setForm({
					...form,
					[name]: value,
				});
			},
			onClose,
			onError,
			onSave,
			onSubmit,
		},
	};
};

export default useFormActions;
