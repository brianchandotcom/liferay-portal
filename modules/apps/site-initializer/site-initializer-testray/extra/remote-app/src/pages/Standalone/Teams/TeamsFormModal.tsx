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
import {withVisibleContent} from '../../../hoc/withVisibleContent';
import {FormModalOptions} from '../../../hooks/useFormModal';
import i18n from '../../../i18n';
import yupSchema, {yupResolver} from '../../../schema/yup';
import {createTeam, updateTeam} from '../../../services/rest';

type TeamForm = typeof yupSchema.team.__outputType;

type TeamProps = {
	modal: FormModalOptions;
	projectId: number;
};

const TeamFormModal: React.FC<TeamProps> = ({
	modal: {modalState, observer, onClose, onError, onSave, onSubmit},
	projectId,
}) => {
	const {
		formState: {errors},
		handleSubmit,
		register,
	} = useForm<TeamForm>({
		defaultValues: modalState,
		resolver: yupResolver(yupSchema.team),
	});

	const _onSubmit = (teamForm: TeamForm) => {
		onSubmit(
			{id: teamForm.id, name: teamForm.name, projectId},
			{
				create: createTeam,
				update: updateTeam,
			}
		)
			.then(onSave)
			.catch(onError);
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
			title={i18n.translate(modalState?.id ? 'edit-team' : 'new-team')}
			visible
		>
			<Form.Input
				errors={errors}
				label={i18n.translate('name')}
				name="name"
				register={register}
				required
			/>
		</Modal>
	);
};

export default withVisibleContent(TeamFormModal);
