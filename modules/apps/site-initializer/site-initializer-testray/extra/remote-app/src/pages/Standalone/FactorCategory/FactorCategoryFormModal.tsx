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

import {useMutation} from '@apollo/client';
import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import {useEffect, useState} from 'react';

import Input from '../../../components/Input';
import Modal from '../../../components/Modal';
import {
	CreateFactorCategory,
	UpdateFactorCategory,
} from '../../../graphql/mutations';
import {FormModalOptions} from '../../../hooks/useFormModal';
import i18n from '../../../i18n';

type FactorCategoryForm = {
	id?: number;
	name: string;
};

type FactorCategoryFormProps = {
	form: FactorCategoryForm;
	onChange: (event: any) => void;
	onSubmit: (event: any) => void;
};

const FormFactorCategory: React.FC<FactorCategoryFormProps> = ({
	form,
	onChange,
	onSubmit,
}) => {
	return (
		<ClayForm onSubmit={onSubmit}>
			<Input
				label="Name"
				name="name"
				onChange={onChange}
				required
				value={form.name}
			/>
		</ClayForm>
	);
};

type FactorCategoryProps = {
	modal: FormModalOptions;
};
const FactorCategoryFormModal: React.FC<FactorCategoryProps> = ({
	modal: {modalState, observer, onChange, onClose, onError, onSave, visible},
}) => {
	const [form, setForm] = useState<FactorCategoryForm>({
		name: '',
	});

	const [onCreateFactorCategory] = useMutation(CreateFactorCategory);
	const [onUpdateFactorCategory] = useMutation(UpdateFactorCategory);

	useEffect(() => {
		if (visible && modalState) {
			setForm(modalState);
		}
	}, [visible, modalState]);

	const onSubmit = async (event?: any) => {
		event?.preventDefault();

		const variables: any = {
			FactorCategory: {
				name: form.name,
			},
		};

		try {
			if (form.id) {
				variables.factorCategoryId = form.id;

				await onUpdateFactorCategory({variables});
			}
			else {
				await onCreateFactorCategory({variables});
			}

			onSave();
		}
		catch (error) {
			onError(error);
		}
	};

	return (
		<Modal
			last={
				<ClayButton.Group spaced>
					<ClayButton displayType="secondary" onClick={onClose}>
						{i18n.translate('close')}
					</ClayButton>

					<ClayButton displayType="primary" onClick={onSubmit}>
						{i18n.translate('save')}
					</ClayButton>
				</ClayButton.Group>
			}
			observer={observer}
			size="lg"
			title={i18n.translate(form.id ? 'edit-category' : 'new-category')}
			visible={visible}
		>
			<FormFactorCategory
				form={form}
				onChange={onChange({form, setForm})}
				onSubmit={onSubmit}
			/>
		</Modal>
	);
};

export default FactorCategoryFormModal;
