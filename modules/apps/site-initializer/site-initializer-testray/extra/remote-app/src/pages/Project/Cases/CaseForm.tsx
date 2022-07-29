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

import ClayForm, {ClayCheckbox} from '@clayui/form';
import {useEffect} from 'react';
import {useForm} from 'react-hook-form';
import {useOutletContext, useParams} from 'react-router-dom';
import {KeyedMutator} from 'swr';

import Form from '../../../components/Form';
import Container from '../../../components/Layout/Container';
import MarkdownPreview from '../../../components/Markdown';
import {useHeader} from '../../../hooks';
import {useFetch} from '../../../hooks/useFetch';
import useFormActions from '../../../hooks/useFormActions';
import i18n from '../../../i18n';
import yupSchema, {yupResolver} from '../../../schema/yup';
import {
	APIResponse,
	TestrayCase,
	TestrayCaseType,
	TestrayComponent,
	TestrayProject,
	createCase,
	updateCase,
} from '../../../services/rest';
import {DescriptionType} from '../../../types';

type CaseFormData = {
	addAnother: boolean;
	caseTypeId: number;
	componentId: number;
	description: string;
	descriptionType: string;
	estimatedDuration: number;
	name: string;
	priority: number;
	steps: string;
	stepsType: string;
};

const priorities = [...new Array(5)].map((_, index) => ({
	label: String(index + 1),
	value: index + 1,
}));

const descriptionTypes = Object.values(
	DescriptionType
).map((descriptionType) => ({label: descriptionType, value: descriptionType}));

const CaseForm = () => {
	const {
		mutateCase,
		testrayCase,
		testrayProject,
	}: {
		mutateCase: KeyedMutator<any>;
		testrayCase: TestrayCase;
		testrayProject: TestrayProject;
	} = useOutletContext();

	const {setTabs} = useHeader({
		shouldUpdate: false,
	});

	const {data: testrayComponentsData} = useFetch<
		APIResponse<TestrayComponent>
	>('/components?fields=id,name');

	const {data: testrayCaseTypesData} = useFetch<APIResponse<TestrayCaseType>>(
		'/casetypes?fields=id,name'
	);

	const testrayCaseTypes = testrayCaseTypesData?.items || [];
	const testrayComponents = testrayComponentsData?.items || [];

	useEffect(() => {
		if (testrayProject) {
			setTimeout(() => {
				setTabs([]);
			}, 10);
		}
	}, [setTabs, testrayProject]);

	const {
		form: {onClose, onError, onSave, onSubmit},
	} = useFormActions();

	const {projectId} = useParams();
	const {
		formState: {errors},
		handleSubmit,
		register,
		setValue,
		watch,
	} = useForm<CaseFormData>({
		defaultValues: testrayCase
			? {
					...testrayCase,
					caseTypeId: testrayCase.caseType?.id,
					componentId: testrayCase.component?.id,
					priority: priorities[0].value,
			  }
			: {
					estimatedDuration: 0,
			  },
		resolver: yupResolver(yupSchema.case),
	});

	const _onSubmit = (form: CaseFormData) => {
		onSubmit(
			{...form, projectId},
			{
				create: createCase,
				update: updateCase,
			}
		)
			.then(mutateCase)
			.then(() => onSave())
			.catch(onError);
	};

	const caseTypeId = watch('caseTypeId');
	const componentId = watch('componentId');
	const description = watch('description');
	const steps = watch('steps');
	const addAnother = watch('addAnother');

	const inputProps = {
		errors,
		register,
		required: true,
	};

	return (
		<Container className="container">
			<ClayForm className="container pt-2">
				<Form.BaseRow title={i18n.translate('add-case')}>
					<Form.Input
						{...inputProps}
						label={i18n.translate('name')}
						name="name"
					/>
				</Form.BaseRow>

				<Form.BaseRow title={i18n.translate('details')}>
					<Form.Select
						{...inputProps}
						className="col-4"
						defaultOption={false}
						label="priority"
						name="priority"
						options={priorities}
						required={false}
					/>

					<Form.Select
						{...inputProps}
						label="type"
						name="caseTypeId"
						options={testrayCaseTypes.map(
							({id: value, name: label}) => ({
								label,
								value,
							})
						)}
						value={caseTypeId}
					/>

					<Form.Select
						{...inputProps}
						label="main-component"
						name="componentId"
						options={testrayComponents.map(
							({id: value, name: label}) => ({
								label,
								value,
							})
						)}
						value={componentId}
					/>

					<Form.Input
						{...inputProps}
						className="col-4"
						label={i18n.translate('estimated-duration')}
						name="estimatedDuration"
						required={false}
						type="number"
					/>
				</Form.BaseRow>

				<Form.BaseRow
					separator={false}
					title={i18n.translate('description')}
				>
					<Form.Select
						{...inputProps}
						className="col-2 ml-auto"
						defaultOption={false}
						name="descriptionType"
						options={descriptionTypes}
						required={false}
					/>
				</Form.BaseRow>

				<Form.Input
					{...inputProps}
					name="description"
					required={false}
					type="textarea"
				/>

				<MarkdownPreview markdown={description} />

				<Form.Divider />

				<Form.BaseRow separator={false} title={i18n.translate('steps')}>
					<Form.Select
						{...inputProps}
						className="col-2 ml-auto"
						defaultOption={false}
						name="stepsType"
						options={descriptionTypes}
						required={false}
					/>
				</Form.BaseRow>

				<Form.Input
					{...inputProps}
					name="steps"
					required={false}
					type="textarea"
				/>

				<MarkdownPreview markdown={steps} />

				<Form.Divider />

				<div className="my-5">
					<ClayCheckbox
						checked={addAnother}
						label={i18n.translate('add-another')}
						onChange={() => setValue('addAnother', !addAnother)}
					/>
				</div>

				<Form.Footer
					onClose={onClose}
					onSubmit={handleSubmit(_onSubmit)}
				/>
			</ClayForm>
		</Container>
	);
};

export default CaseForm;
