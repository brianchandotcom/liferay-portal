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
import {CreateTestrayProject} from '../../graphql/mutations/TestrayProject';
import {Liferay} from '../../services/liferay/liferay';

type NewProjectProps = {
	onClose: any;
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

	const [save, {data, error}] = useMutation(CreateTestrayProject);

	useEffect(() => {
		error &&
			Liferay.Util.openToast({
				message: `Error ${error}`,
				type: 'danger',
			});
		if (data) {
			Liferay.Util.openToast({
				message: 'Project created',
				type: 'success',
			});
			onClose();
		}
	}, [project, data, error, onClose]);

	return (
		<ClayForm>
			<ClayForm.Group>
				<Input
					label="Name"
					name="name"
					onChange={(element) => handleChange(element)}
					required
				></Input>
			</ClayForm.Group>

			<ClayForm.Group className="">
				<Input
					label="Description"
					name="description"
					onChange={(element) => handleChange(element)}
					required
					type="textarea"
				></Input>
			</ClayForm.Group>

			<ClayForm.Group className="d-flex mt-5">
				<div className="mr-3">
					<ClayButton
						displayType="primary"
						onClick={() =>
							save({
								variables: {
									description: project.description,
									name: project.name,
								},
							})
						}
					>
						Add
					</ClayButton>
				</div>

				<div>
					<ClayButton displayType="secondary">Cancel</ClayButton>
				</div>
			</ClayForm.Group>
		</ClayForm>
	);
};

export default NewProject;
