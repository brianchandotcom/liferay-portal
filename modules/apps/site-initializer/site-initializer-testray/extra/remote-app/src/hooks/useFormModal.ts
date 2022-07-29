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
import {Dispatch, useState} from 'react';
import {KeyedMutator} from 'swr';

import useFormActions, {FormOptions} from './useFormActions';

type onSaveOptions = {
	forceRefetch?: boolean;
};

export type FormModalOptions = {
	modalState: any;
	observer: Observer;
	onClose: () => void;
	onSave: (param?: any, options?: onSaveOptions) => void;
	open: (state?: any) => void;
	setVisible: Dispatch<boolean>;
	visible: boolean;
} & FormOptions;

export type FormModal = {
	forceRefetch: number;
	modal: FormModalOptions;
	mutate?: KeyedMutator<any>;
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
	const {form} = useFormActions();
	const [modalState, setModalState] = useState();
	const [visible, setVisible] = useState(isVisible);
	const {observer, onClose} = useModal({
		onClose: () => setVisible(false),
	});

	const [forceRefetch, setForceRefetch] = useState(0);

	const onSave = (
		state?: any,
		options: onSaveOptions = {forceRefetch: true}
	) => {
		form.onSuccess();

		if (visible) {
			onClose();
		}

		if (options.forceRefetch) {
			setForceRefetch(new Date().getTime());
		}

		if (state) {
			setModalState(state);
			onSaveModal(state);
		}
	};

	return {
		forceRefetch,
		modal: {
			...form,
			modalState,
			observer,
			onClose,
			onSave,
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
