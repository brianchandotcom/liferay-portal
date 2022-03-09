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
import {CreateTestrayProject} from '../../graphql/mutations/TestrayProject';
import i18n from '../../i18n';
import {Liferay} from '../../services/liferay/liferay';

type NewProjectProps = {
	onClose: () => void;
};

const NewProject: React.FC<NewProjectProps> = ({onClose}) => {
	const [project, setProject] = useState({
		description: '',
		name: '',
	});

	function handleChange({target}: any): void {
		const {name, value} = target;

		setProject({
			...project,
			[name]: value,
		});
	}

	const [createTestrayProject] = useMutation(CreateTestrayProject);

	const onSubmit = async (event: any) => {
		event.preventDefault();

		try {
			await createTestrayProject({
				variables: {
					TestrayProject: {
						description: project.description,
						name: project.name,
					},
				},
			});

			Liferay.Util.openToast({
				message: 'Project created',
				type: 'success',
			});
		}
		catch (error) {
			Liferay.Util.openToast({
				message: `Error ${error}`,
				type: 'danger',
			});
		}

		onClose();
	};

	return (
		<ClayForm onSubmit={onSubmit}>
			<ClayForm.Group>
				<Input
					label="Name"
					name="name"
					onChange={(element) => handleChange(element)}
					required
				/>
			</ClayForm.Group>

			<ClayForm.Group>
				<Input
					label="Description"
					name="description"
					onChange={(element) => handleChange(element)}
					required
					type="textarea"
				/>
			</ClayForm.Group>

			<ClayForm.Group className="d-flex mt-5">
				<div className="mr-3">
					<ClayButton displayType="primary" type="submit">
						{i18n.translate('add')}
					</ClayButton>
				</div>

				<div>
					<ClayButton displayType="secondary">
						{i18n.translate('cancel')}
					</ClayButton>
				</div>
			</ClayForm.Group>
		</ClayForm>
	);
};

export default NewProject;
