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

import Input from '../../components/Input';
import Modal from '../../components/Modal';
import {
	CreateProject,
	UpdateProject,
} from '../../graphql/mutations/testrayProject';
import {FormModalOptions} from '../../hooks/useFormModal';
import i18n from '../../i18n';

type NewProjectForm = {
	description: string;
	id?: number;
	name: string;
};

type NewProjectFormProps = {
	form: NewProjectForm;
	onChange: (event: any) => void;
	onSubmit: (event: any) => void;
};

const FormNewProject: React.FC<NewProjectFormProps> = ({
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
	modal: {modalState, observer, onChange, onClose, onError, onSave, visible},
}) => {
	const [form, setForm] = useState<NewProjectForm>({
		description: '',
		name: '',
	});

	const [onCreateProject] = useMutation(CreateProject);
	const [onUpdateProject] = useMutation(UpdateProject);

	useEffect(() => {
		if (visible && modalState) {
			setForm(modalState);
		}
	}, [visible, modalState]);

	const onSubmit = async () => {
		const variables: any = {
			Project: {
				description: form.description,
				name: form.name,
			},
		};

		try {
			if (form.id) {
				variables.projectId = form.id;

				onUpdateProject({variables});
			}
			else {
				await onCreateProject({variables});
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
			title={i18n.translate(form.id ? 'edit-project' : 'new-project')}
			visible={visible}
		>
			<FormNewProject
				form={form}
				onChange={onChange({form, setForm})}
				onSubmit={onSubmit}
			/>
		</Modal>
	);
};

export default ProjectModal;
