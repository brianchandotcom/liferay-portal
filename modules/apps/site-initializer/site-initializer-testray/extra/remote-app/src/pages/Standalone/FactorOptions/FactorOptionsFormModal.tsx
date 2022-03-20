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

import {useMutation, useQuery} from '@apollo/client';
import ClayButton from '@clayui/button';
import ClayForm, {ClaySelectWithOption} from '@clayui/form';
import {useEffect, useState} from 'react';

import Input from '../../../components/Input';
import Modal from '../../../components/Modal';
import {
	CreateFactorOption,
	UpdateFactorOption,
} from '../../../graphql/mutations';
import {
	CTypePagination,
	TestrayFactorCategory,
	getFactorCategories,
} from '../../../graphql/queries';
import {FormModalOptions} from '../../../hooks/useFormModal';
import i18n from '../../../i18n';

type FactorOptionsForm = {
	id?: number;
	name: string;
};

type FactorOptionsFormProps = {
	form: FactorOptionsForm;
	onChange: (event: any) => void;
	onSubmit: (event: any) => void;
};

const FormFactorOptions: React.FC<FactorOptionsFormProps> = ({
	form,
	onChange,
	onSubmit,
}) => {
	const {data} = useQuery<
		CTypePagination<'factorCategories', TestrayFactorCategory>
	>(getFactorCategories);

	const factorCategories = data?.c.factorCategories.items || [];

	return (
		<ClayForm onSubmit={onSubmit}>
			<Input
				label="Name"
				name="name"
				onChange={onChange}
				required
				value={form.name}
			/>

			<label htmlFor="category-type">
				{i18n.translate('category-type')}
			</label>

			<ClaySelectWithOption
				id="category-type"
				options={factorCategories.map(({id, name}) => ({
					label: name,
					value: id,
				}))}
			/>
		</ClayForm>
	);
};

type FactorOptionsProps = {
	modal: FormModalOptions;
};
const FactorOptionsFormModal: React.FC<FactorOptionsProps> = ({
	modal: {modalState, observer, onChange, onClose, onError, onSave, visible},
}) => {
	const [form, setForm] = useState<FactorOptionsForm>({
		name: '',
	});

	const [onCreateFactorOption] = useMutation(CreateFactorOption);
	const [onUpdateFactorOption] = useMutation(UpdateFactorOption);

	useEffect(() => {
		if (visible && modalState) {
			setForm(modalState);
		}
	}, [visible, modalState]);

	const onSubmit = async (event?: any) => {
		event?.preventDefault();

		const variables: any = {
			FactorOption: {
				name: form.name,
			},
		};

		try {
			if (form.id) {
				variables.factorOptionId = form.id;

				await onUpdateFactorOption({variables});
			} else {
				await onCreateFactorOption({variables});
			}

			onSave();
		} catch (error) {
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
			title={i18n.translate(
				form.id ? 'edit-factor-option' : 'new-factor-option'
			)}
			visible={visible}
		>
			<FormFactorOptions
				form={form}
				onChange={onChange({form, setForm})}
				onSubmit={onSubmit}
			/>
		</Modal>
	);
};

export default FactorOptionsFormModal;
