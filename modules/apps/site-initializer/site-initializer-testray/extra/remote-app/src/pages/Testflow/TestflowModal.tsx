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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm, {ClayCheckbox} from '@clayui/form';
import React, {useState} from 'react';

import Input, {AutoComplete} from '../../components/Input';
import Modal from '../../components/Modal';
import {getBuilds, getProjects, getRoutines} from '../../graphql/queries';
import {FormModalOptions} from '../../hooks/useFormModal';
import i18n from '../../i18n';

type TestflowModalProps = {
	modal: FormModalOptions;
};

const TestflowForm = () => {
	const [form, setForm] = useState({
		testrayBuildId: 0,
		testrayProjectId: 0,
		testrayRoutineId: 0,
	});

	const onChange = (event: any) => {
		const {
			target: {checked, name, type, ...target},
		} = event;

		let {value} = target;

		if (type === 'checkbox') {
			value = checked;
		}

		setForm({
			...form,
			[name]: value,
		});
	};

	return (
		<>
			<AutoComplete
				gqlQuery={getProjects}
				label="Project"
				objectName="projects"
				onSearch={(keyword) => `contains(name, '${keyword}')`}
			/>

			<AutoComplete
				gqlQuery={getRoutines}
				label="Routine"
				objectName="routines"
				onSearch={(keyword) => `contains(name, '${keyword}')`}
			/>

			<AutoComplete
				gqlQuery={getBuilds}
				label="Build"
				objectName="builds"
				onSearch={(keyword) => `contains(name, '${keyword}')`}
				transformData={(data) => data?.Builds?.items || []}
			/>

			<Input label="Name" name="name" required />

			<div className="my-4">
				<ClayForm.Group>
					<label>{i18n.translate('case-type')}</label>

					<ClayCheckbox
						checked
						label={i18n.translate('automated-functional-test')}
						name="automatedFunctionalTest"
						onChange={onChange}
					/>

					<ClayCheckbox
						checked
						label={i18n.translate('automated-functional-test')}
						name="automatedFunctionalTest"
						onChange={onChange}
					/>

					<ClayCheckbox
						checked
						label={i18n.translate('automated-functional-test')}
						name="automatedFunctionalTest"
						onChange={onChange}
					/>
				</ClayForm.Group>
			</div>

			<h3>{i18n.translate('users')}</h3>
			<hr />

			<ClayButton.Group className="mb-4">
				<ClayButton displayType="secondary">
					{i18n.translate('assign-users')}
				</ClayButton>

				<ClayButton className="ml-3" displayType="secondary">
					{i18n.translate('assign-user-groups')}
				</ClayButton>
			</ClayButton.Group>

			<ClayAlert displayType="info">There are no linked users.</ClayAlert>
		</>
	);
};

const TestflowModal: React.FC<TestflowModalProps> = ({
	modal: {observer, onClose, visible},
}) => {
	const onSubmit = () => {};

	return (
		<Modal
			last={
				<ClayButton.Group spaced>
					<ClayButton displayType="secondary" onClick={onClose}>
						{i18n.translate('close')}
					</ClayButton>

					<ClayButton displayType="primary" onClick={onSubmit}>
						{i18n.translate('analyse')}
					</ClayButton>
				</ClayButton.Group>
			}
			observer={observer}
			size="lg"
			title={i18n.translate('new-task')}
			visible={visible}
		>
			<TestflowForm />
		</Modal>
	);
};

export default TestflowModal;
