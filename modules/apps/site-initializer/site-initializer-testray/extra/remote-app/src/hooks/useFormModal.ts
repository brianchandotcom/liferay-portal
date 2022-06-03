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

import {useModal} from '@clayui/modal';
import {Observer} from '@clayui/modal/src/types';
import {DocumentNode} from 'graphql';
import {Dispatch, useState} from 'react';

import client from '../graphql/apolloClient';
import i18n from '../i18n';
import {Liferay} from '../services/liferay';

type OnSubmitOptions = {
	createMutation: DocumentNode;
	updateMutation: DocumentNode;
};

export type FormModalOptions = {
	modalState: any;
	observer: Observer;
	onChange: (state: any) => (event: any) => void;
	onClose: () => void;
	onError: (error?: any) => void;
	onSave: (param?: any) => void;
	onSubmit: (data: any, options: OnSubmitOptions) => Promise<void>;
	open: (state?: any) => void;
	setVisible: Dispatch<boolean>;
	visible: boolean;
};

export type FormModal = {
	forceRefetch: number;
	modal: FormModalOptions;
};

export type FormModalComponent = Omit<FormModal, 'forceRefetch'>;

type UseFormModal = {
	isVisible?: boolean;
	onSave?: (param: any) => void;
};

const useFormModal = ({
	isVisible = false,
	onSave: onSaveModal = () => {},
}: UseFormModal = {}): FormModal => {
	const [modalState, setModalState] = useState();
	const [visible, setVisible] = useState(isVisible);
	const {observer, onClose} = useModal({
		onClose: () => setVisible(false),
	});

	const [forceRefetch, setForceRefetch] = useState(0);

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

		onClose();
		setForceRefetch(new Date().getTime());

		if (state) {
			setModalState(state);
			onSaveModal(state);
		}
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
		} catch (error) {
			onError(error);

			throw error;
		}
	};

	return {
		forceRefetch,
		modal: {
			modalState,
			observer,
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
			open: (state?: any) => {
				setModalState(state);

				setVisible(true);
			},
			setVisible,
			visible,
		},
	};
};

export default useFormModal;
