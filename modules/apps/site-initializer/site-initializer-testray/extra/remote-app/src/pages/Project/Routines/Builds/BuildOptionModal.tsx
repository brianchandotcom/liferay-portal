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

import Form from '../../../../components/Form';
import Modal from '../../../../components/Modal';
import {withVisibleContent} from '../../../../hoc/withVisibleContent';
import {FormModalComponent} from '../../../../hooks/useFormModal';
import i18n from '../../../../i18n';
import yupSchema, {yupResolver} from '../../../../schema/yup';

type BuildOptionModalForm = {
	autoanalyze: boolean;
	id: number;
	name: string;
};

const BuildOptionModal: React.FC<FormModalComponent> = ({
	modal: {modalState, observer, onClose},
}) => {
	const {
		formState: {errors},
		handleSubmit,
		register,
	} = useForm<BuildOptionModalForm>({
		defaultValues: {autoanalyze: false, ...modalState},
		resolver: yupResolver(yupSchema.option),
	});

	const _onSubmit = (form: BuildOptionModalForm) => {
		// eslint-disable-next-line no-console
		console.log(form);
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
			title={i18n.translate('new-option')}
			visible
		>
			<Form.Input
				errors={errors}
				label={i18n.translate('name')}
				name="name"
				register={register}
				required
			/>

			<Form.Select
				errors={errors}
				label={i18n.translate('type')}
				name="type"
				options={[]}
				register={register}
				required
			/>
		</Modal>
	);
};

export default withVisibleContent(BuildOptionModal);
