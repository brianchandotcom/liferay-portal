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

import ClayButton from '@clayui/button';
import {useForm} from 'react-hook-form';

import Input from '../../components/Input';
import Modal from '../../components/Modal';
import {
	CreateProject,
	UpdateProject,
} from '../../graphql/mutations/testrayProject';
import {withVisibleContent} from '../../hoc/withVisibleContent';
import {FormModalComponent} from '../../hooks/useFormModal';
import i18n from '../../i18n';
import yupSchema, {yupResolver} from '../../schema/yup';

type ProjectForm = {
	description: string;
	id?: string;
	name: string;
};

const ProjectModal: React.FC<FormModalComponent> = ({
	modal: {modalState, observer, onClose, onSubmit},
}) => {
	const {
		formState: {errors},
		handleSubmit,
		register,
	} = useForm<ProjectForm>({
		defaultValues: modalState,
		resolver: yupResolver(yupSchema.project),
	});

	const _onSubmit = (form: ProjectForm) =>
		onSubmit(
			{description: form.description, id: form.id, name: form.name},
			{
				createMutation: CreateProject,
				updateMutation: UpdateProject,
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
				<ClayButton.Group spaced>
					<ClayButton displayType="secondary" onClick={onClose}>
						{i18n.translate('close')}
					</ClayButton>

					<ClayButton
						displayType="primary"
						onClick={handleSubmit(_onSubmit)}
					>
						{i18n.translate('save')}
					</ClayButton>
				</ClayButton.Group>
			}
			observer={observer}
			size="lg"
			title={i18n.translate(
				modalState?.id ? 'edit-project' : 'new-project'
			)}
			visible
		>
			<Input label={i18n.translate('name')} name="name" {...inputProps} />

			<Input
				label={i18n.translate('description')}
				name="description"
				{...inputProps}
			/>
		</Modal>
	);
};

export default withVisibleContent(ProjectModal);
