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

import {MutationOptions} from '@apollo/client';
import {DocumentNode} from 'graphql';
import {useState} from 'react';
import {useNavigate} from 'react-router-dom';

import client from '../graphql/apolloClient';
import i18n from '../i18n';
import {Liferay} from '../services/liferay';

type OnSubmitOptions = {
	createMutation: DocumentNode;
	updateMutation: DocumentNode;
};

type OnSubmitOptionsRest<T = any> = {
	create: (data: any) => Promise<T>;
	update: (id: number, data: any) => Promise<T>;
};

export type FormOptions = {
	onChange: (
		state: any
	) => (event: React.FormEvent<HTMLInputElement>) => void;
	onClose: () => void;
	onError: (error?: any) => void;
	onSave: (param?: any) => void;
	onSubmit: (
		data: any,
		onSubmitOptions: OnSubmitOptions,
		mutationOptions?: Omit<MutationOptions, 'mutation'>
	) => Promise<any>;
	onSubmitAndSave: (
		data: any,
		onSubmitOptions: OnSubmitOptions,
		mutationOptions?: Omit<MutationOptions, 'mutation'>
	) => Promise<void>;
	onSubmitRest: <T = any>(
		data: any,
		options: OnSubmitOptionsRest<T>
	) => Promise<T>;
	onSuccess: () => void;
};

export type Form = {
	forceRefetch: number;
	form: FormOptions;
};

export type FormComponent = Omit<Form, 'forceRefetch'>;

const onError = (error: any) => {
	console.error(error);

	Liferay.Util.openToast({
		message: i18n.translate('an-unexpected-error-occurred'),
		type: 'danger',
	});
};

const onSuccess = () => {
	Liferay.Util.openToast({
		message: i18n.translate('your-request-completed-successfully'),
		type: 'success',
	});
};

const useFormActions = (): Form => {
	const [forceRefetch, setForceRefetch] = useState(0);
	const navigate = useNavigate();

	const onClose = () => {
		navigate(-1);
	};

	const onSave = (state?: any) => {
		onSuccess();

		setForceRefetch(new Date().getTime());

		if (state) {
			onSave(state);
		}

		navigate(-1);
	};

	const onSubmit = async (
		data: any,
		{createMutation, updateMutation}: OnSubmitOptions,
		options?: Omit<MutationOptions, 'mutation'>
	) => {
		const variables: any = {
			data,
		};

		if (data.id) {
			variables.id = data.id;
		}

		delete variables.data.id;

		try {
			return client.mutate({
				mutation: variables.id ? updateMutation : createMutation,
				variables,
				...options,
			});
		} catch (error) {
			onError(error);

			throw error;
		}
	};

	const onSubmitRest = async <T = any>(
		data: any,
		{create, update}: OnSubmitOptionsRest<T>
	): Promise<T> => {
		const form = {...data};

		delete form.id;

		try {
			const fn = data.id
				? () => update(data.id, form)
				: () => create(form);

			const response = await fn();

			return response;
		} catch (error) {
			onError(error);

			throw error;
		}
	};

	const onSubmitAndSave = async (
		data: any,
		onSubmitOptions: OnSubmitOptions,
		options?: Omit<MutationOptions, 'mutation'>
	) => {
		await onSubmit(data, onSubmitOptions, options);
		await onSave();
	};

	return {
		forceRefetch,
		form: {
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
			onSubmitAndSave,
			onSubmitRest,
			onSuccess,
		},
	};
};

export default useFormActions;
