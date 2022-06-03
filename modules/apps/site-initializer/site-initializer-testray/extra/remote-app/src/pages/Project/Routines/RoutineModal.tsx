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

import {ClayCheckbox} from '@clayui/form';
import {useForm} from 'react-hook-form';

import Form from '../../../components/Form';
import Modal from '../../../components/Modal';
import {CreateRoutine, UpdateRoutine} from '../../../graphql/mutations';
import {withVisibleContent} from '../../../hoc/withVisibleContent';
import {FormModalComponent} from '../../../hooks/useFormModal';
import i18n from '../../../i18n';
import yupSchema, {yupResolver} from '../../../schema/yup';

type RoutineForm = {
	autoanalyze: boolean;
	id: number;
	name: string;
};

const RoutineModal: React.FC<FormModalComponent & {projectId: number}> = ({
	modal: {modalState, observer, onClose, onSubmit},
	projectId,
}) => {
	const {
		formState: {errors},
		handleSubmit,
		register,
		setValue,
		watch,
	} = useForm<RoutineForm>({
		defaultValues: {autoanalyze: false, ...modalState},
		resolver: yupResolver(yupSchema.routine),
	});

	const autoanalyze = watch('autoanalyze');

	const _onSubmit = (form: RoutineForm) => {
		onSubmit(
			{
				...form,
				projectId,
			},
			{
				createMutation: CreateRoutine,
				updateMutation: UpdateRoutine,
			}
		);
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
				modalState?.id ? 'edit-routine' : 'new-routine'
			)}
			visible
		>
			<Form.Input
				errors={errors}
				label={i18n.translate('name')}
				name="name"
				register={register}
				required
			/>

			<ClayCheckbox
				checked={autoanalyze}
				label={i18n.translate('autoanalyze')}
				onChange={() => setValue('autoanalyze', !autoanalyze)}
			/>
		</Modal>
	);
};

export default withVisibleContent(RoutineModal);
