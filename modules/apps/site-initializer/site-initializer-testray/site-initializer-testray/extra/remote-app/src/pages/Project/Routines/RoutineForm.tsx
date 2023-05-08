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
import {useOutletContext} from 'react-router-dom';
import {KeyedMutator} from 'swr';
import {withPagePermission} from '~/hoc/withPagePermission';

import Form from '../../../components/Form';
import Container from '../../../components/Layout/Container';
import {useHeader} from '../../../hooks';
import useFormActions from '../../../hooks/useFormActions';
import i18n from '../../../i18n';
import yupSchema, {yupResolver} from '../../../schema/yup';
import {
	TestrayProject,
	TestrayRoutine,
	testrayRoutineImpl,
} from '../../../services/rest';

type RoutineFormType = typeof yupSchema.routine.__outputType;

type OutletContext = {
	mutateTestrayRoutine: KeyedMutator<TestrayRoutine>;
	testrayProject: TestrayProject;
	testrayRoutine?: TestrayRoutine;
};

const RoutineForm = () => {
	useHeader({headerActions: {actions: []}, tabs: [], timeout: 150});

	const {
		mutateTestrayRoutine,
		testrayProject,
		testrayRoutine,
	} = useOutletContext<OutletContext>();

	const {
		formState: {errors, isSubmitting},
		handleSubmit,
		register,
		setValue,
		watch,
	} = useForm<RoutineFormType>({
		defaultValues: {autoanalyze: false, ...testrayRoutine},
		resolver: yupResolver(yupSchema.routine),
	});

	const {
		form: {onClose, onError, onSave, onSubmit},
	} = useFormActions();

	const _onSubmit = (form: RoutineFormType) =>
		onSubmit(
			{
				...form,
				projectId: testrayProject.id,
			},
			{
				create: (...params) => testrayRoutineImpl.create(...params),
				update: (...params) => testrayRoutineImpl.update(...params),
			}
		)
			.then(mutateTestrayRoutine)
			.then(onSave)
			.catch(onError);

	const autoanalyze = watch('autoanalyze');

	return (
		<Container className="container">
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

			<Form.Footer
				onClose={onClose}
				onSubmit={handleSubmit(_onSubmit)}
				primaryButtonProps={{loading: isSubmitting}}
			/>
		</Container>
	);
};

export default withPagePermission(RoutineForm, {
	createPath: 'project/:projectId/routines/create',
	restImpl: testrayRoutineImpl,
});
