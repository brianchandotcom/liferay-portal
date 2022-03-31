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
import ClayForm from '@clayui/form';
import {useEffect, useState} from 'react';

import Input from '../../components/Input';
import Modal from '../../components/Modal';
import {
	CreateProject,
	UpdateProject,
} from '../../graphql/mutations/testrayProject';
import {FormModalOptions} from '../../hooks/useFormModal';
import i18n from '../../i18n';

const projectFormData = {
	description: '',
	id: undefined,
	name: '',
};

type ProjectForm = typeof projectFormData;

type ProjectFormProps = {
	form: ProjectForm;
	onChange: (event: any) => void;
	onSubmit: (event: any) => void;
};

const FormNewProject: React.FC<ProjectFormProps> = ({
	form,
	onChange,
	onSubmit,
}) => {
	return (
		<ClayForm onSubmit={onSubmit}>
			<ClayForm.Group>
				<Input
					label="Name"
					name="name"
					onChange={onChange}
					required
					value={form.name}
				/>
			</ClayForm.Group>

			<ClayForm.Group>
				<Input
					label="Description"
					name="description"
					onChange={onChange}
					required
					type="textarea"
					value={form.description}
				/>
			</ClayForm.Group>
		</ClayForm>
	);
};

type NewProjectProps = {
	modal: FormModalOptions;
};
const ProjectModal: React.FC<NewProjectProps> = ({
	modal: {modalState, observer, onChange, onClose, onSubmit, visible},
}) => {
	const [form, setForm] = useState<ProjectForm>(projectFormData);

	useEffect(() => {
		if (visible && modalState) {
			setForm(modalState);
		}
	}, [visible, modalState]);

	const _onSubmit = () =>
		onSubmit(
			{description: form.description, id: form.id, name: form.name},
			{
				createMutation: CreateProject,
				updateMutation: UpdateProject,
			}
		);

	return (
		<Modal
			last={
				<ClayButton.Group spaced>
					<ClayButton displayType="secondary" onClick={onClose}>
						{i18n.translate('close')}
					</ClayButton>

					<ClayButton displayType="primary" onClick={_onSubmit}>
						{i18n.translate('save')}
					</ClayButton>
				</ClayButton.Group>
			}
			observer={observer}
			size="lg"
			title={i18n.translate(form.id ? 'edit-project' : 'new-project')}
			visible={visible}
		>
			<FormNewProject
				form={form}
				onChange={onChange({form, setForm})}
				onSubmit={_onSubmit}
			/>
		</Modal>
	);
};

export default ProjectModal;
