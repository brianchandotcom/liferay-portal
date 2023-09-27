/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

type UseFormModal<T> = {
	isVisible?: boolean;
	onBeforeSave?: (state: T, act: () => void) => void;
	onSave?: (param: T) => void;
};

const useFormModal = <T = any>({
	isVisible = false,
	onBeforeSave,
	onSave: onSaveModal = () => {},
}: UseFormModal<T> = {}): FormModal => {
	const {form} = useFormActions();
	const [modalState, setModalState] = useState<T>();
	const [visible, setVisible] = useState(isVisible);
	const {observer, onClose} = useModal({
		onClose: () => setVisible(false),
	});

	const [forceRefetch, setForceRefetch] = useState(0);

	const onSave = (
		state?: T,
		options: onSaveOptions = {forceRefetch: true}
	) => {
		const act = () => {
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

		if (onBeforeSave) {
			return onBeforeSave(state as T, act);
		}

		act();
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
