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
import {useState} from 'react';

import Input from '../../components/Input';
import Modal from '../../components/Modal';
import {CreateTestrayProject} from '../../graphql/mutations/TestrayProject';
import {FormModalOptions} from '../../hooks/useFormModal';
import i18n from '../../i18n';
import {Liferay} from '../../services/liferay/liferay';

type NewProjectForm = {
	description: string;
	name: string;
};

type NewProjectFormProps = {
	form: NewProjectForm;
	onChange: (event: any) => void;
	onSubmit: (event: any) => void;
};

const FormNewProject: React.FC<NewProjectFormProps> = ({
	onChange,
	onSubmit,
}) => {
	return (
		<ClayForm onSubmit={onSubmit}>
			<ClayForm.Group>
				<Input label="Name" name="name" onChange={onChange} required />
			</ClayForm.Group>

			<ClayForm.Group>
				<Input
					label="Description"
					name="description"
					onChange={onChange}
					required
					type="textarea"
				/>
			</ClayForm.Group>
		</ClayForm>
	);
};

type NewProjectProps = {
	modal: FormModalOptions;
};
const ProjectModal: React.FC<NewProjectProps> = ({
	modal: {observer, onClose, onSave, visible},
}) => {
	const [form, setForm] = useState<NewProjectForm>({
		description: '',

		name: '',
	});

	function onChange({target}: any): void {
		const {name, value} = target;

		setForm({
			...form,
			[name]: value,
		});
	}

	const [onCreateTestrayProject] = useMutation(CreateTestrayProject);

	const onSubmit = async () => {
		const newForm: NewProjectForm = {
			...form,

			description: form.description,

			name: form.name,
		};

		try {
			await onCreateTestrayProject({
				variables: {
					TestrayProject: newForm,
				},
			});

			Liferay.Util.openToast({message: 'TestrayProject Registered'});

			onSave();
		}
		catch (error) {
			Liferay.Util.openToast({
				message: (error as any).message,
				type: 'danger',
			});
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
						{i18n.translate('add-project')}
					</ClayButton>
				</ClayButton.Group>
			}
			observer={observer}
			size="lg"
			title={i18n.translate('new-project')}
			visible={visible}
		>
			<FormNewProject
				form={form}
				onChange={onChange}
				onSubmit={onSubmit}
			/>
		</Modal>
	);
};

export default ProjectModal;
