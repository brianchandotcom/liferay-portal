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

import {useForm} from 'react-hook-form';

import Form from '../../../components/Form';
import Modal from '../../../components/Modal';
import {
	CreateFactorCategory,
	UpdateFactorCategory,
} from '../../../graphql/mutations/testrayFactorCategory';
import {withVisibleContent} from '../../../hoc/withVisibleContent';
import {FormModalComponent} from '../../../hooks/useFormModal';
import i18n from '../../../i18n';
import yupSchema, {yupResolver} from '../../../schema/yup';

type FactorCategoryForm = {
	id?: number;
	name: string;
};

const FactorCategoryFormModal: React.FC<FormModalComponent> = ({
	modal: {modalState, observer, onClose, onSubmit},
}) => {
	const {
		formState: {errors},
		handleSubmit,
		register,
	} = useForm<FactorCategoryForm>({
		defaultValues: modalState,
		resolver: yupResolver(yupSchema.factorCategory),
	});

	const _onSubmit = (form: FactorCategoryForm) =>
		onSubmit(
			{id: form.id, name: form.name},
			{
				createMutation: CreateFactorCategory,
				updateMutation: UpdateFactorCategory,
			}
		);

	const inputProps = {
		errors,
		register,
		required: true,
	};

	return (
		<Modal
			last={
				<Form.Footer
					onClose={onClose}
					onSubmit={handleSubmit(_onSubmit)}
				/>
			}
			observer={observer}
			size="lg"
			title={i18n.translate(
				modalState?.id ? 'edit-category' : 'new-category'
			)}
			visible
		>
			<Form.Input
				label={i18n.translate('name')}
				name="name"
				{...inputProps}
			/>
		</Modal>
	);
};

export default withVisibleContent(FactorCategoryFormModal);
