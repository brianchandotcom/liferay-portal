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

import {useQuery} from '@apollo/client';
import {useForm} from 'react-hook-form';

import Form from '../../../components/Form';
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
import {withVisibleContent} from '../../../hoc/withVisibleContent';
import {FormModalOptions} from '../../../hooks/useFormModal';
import i18n from '../../../i18n';
import yupSchema, {yupResolver} from '../../../schema/yup';

type FactorOptionsForm = {
	factorCategoryId: string;
	id?: number;
	name: string;
};

type FactorOptionsProps = {
	modal: FormModalOptions;
};

const FactorOptionsFormModal: React.FC<FactorOptionsProps> = ({
	modal: {modalState, observer, onClose, onSubmit},
}) => {
	const {
		formState: {errors},
		handleSubmit,
		register,
	} = useForm<FactorOptionsForm>({
		defaultValues: modalState,
		resolver: yupResolver(yupSchema.factorOption),
	});

	const {data} = useQuery<
		CTypePagination<'factorCategories', TestrayFactorCategory>
	>(getFactorCategories);

	const factorCategories = data?.c.factorCategories.items || [];

	const _onSubmit = (form: FactorOptionsForm) => {
		onSubmit(
			{id: form.id, name: form.name},
			{
				createMutation: CreateFactorOption,
				updateMutation: UpdateFactorOption,
			}
		);
	};

	const inputProps = {
		errors,
		register,
		required: true,
	};

	return (
		<Modal
			last={
				<Form.Footer
					isModal
					onClose={onClose}
					onSubmit={handleSubmit(_onSubmit)}
				/>
			}
			observer={observer}
			size="lg"
			title={i18n.translate(
				modalState?.id ? 'edit-option' : 'new-option'
			)}
			visible
		>
			<Form.Input
				label={i18n.translate('name')}
				name="name"
				{...inputProps}
			/>

			<Form.Select
				label={i18n.translate('category')}
				name="category"
				options={factorCategories.map(({id: value, name: label}) => ({
					label,
					value,
				}))}
			/>
		</Modal>
	);
};

export default withVisibleContent(FactorOptionsFormModal);
