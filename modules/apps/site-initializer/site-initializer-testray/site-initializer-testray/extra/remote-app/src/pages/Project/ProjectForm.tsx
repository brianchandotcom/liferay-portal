/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useForm} from 'react-hook-form';
import {useOutletContext} from 'react-router-dom';
import {KeyedMutator} from 'swr';
import {withPagePermission} from '~/hoc/withPagePermission';

import Form from '../../components/Form';
import Container from '../../components/Layout/Container';
import {useHeader} from '../../hooks';
import useFormActions from '../../hooks/useFormActions';
import i18n from '../../i18n';
import yupSchema, {yupResolver} from '../../schema/yup';
import {testrayProjectImpl} from '../../services/rest/TestrayProject';
import {TestrayProject} from '../../services/rest/types';

type ProjectFormType = typeof yupSchema.project.__outputType;

type OutletContext = {
	mutateTestrayProject: KeyedMutator<TestrayProject>;
	testrayProject?: TestrayProject;
};

const ProjectForm = () => {
	const {
		form: {onClose, onError, onSave, onSubmit},
	} = useFormActions();

	const {mutateTestrayProject, testrayProject} =
		useOutletContext<OutletContext>() || {};

	useHeader({
		tabs: [],
		timeout: 100,
	});

	const {
		formState: {errors, isSubmitting},
		handleSubmit,
		register,
	} = useForm<ProjectFormType>({
		defaultValues: {
			description: testrayProject?.description,
			id: testrayProject?.id.toString(),
			name: testrayProject?.name,
		},
		resolver: yupResolver(yupSchema.project),
	});

	const _onSubmit = (project: ProjectFormType) =>
		onSubmit(project, {
			create: (data) => testrayProjectImpl.create(data),
			update: (id, data) => testrayProjectImpl.update(id, data),
		})
			.then((response) => {
				if (project.id) {
					mutateTestrayProject(response);
				}
			})
			.then(onSave)
			.catch(onError);

	const inputProps = {
		errors,
		register,
	};

	return (
		<Container className="container">
			<Form.Input
				{...inputProps}
				label={i18n.translate('name')}
				name="name"
				required
			/>

			<Form.Input
				{...inputProps}
				label={i18n.translate('description')}
				name="description"
			/>

			<Form.Footer
				onClose={onClose}
				onSubmit={handleSubmit(_onSubmit)}
				primaryButtonProps={{loading: isSubmitting}}
			/>
		</Container>
	);
};

export default withPagePermission(ProjectForm, {
	createPath: '/project/create',
	restImpl: testrayProjectImpl,
});
