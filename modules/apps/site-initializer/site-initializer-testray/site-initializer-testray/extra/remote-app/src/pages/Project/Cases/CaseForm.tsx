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
import {useForm} from 'react-hook-form';
import {useOutletContext, useParams} from 'react-router-dom';
import {KeyedMutator} from 'swr';
import SearchBuilder from '~/core/SearchBuilder';
import {withPagePermission} from '~/hoc/withPagePermission';

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
	testrayCaseImpl,
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
		mutateTestrayCase,
		testrayCase,
	}: {
		mutateTestrayCase: KeyedMutator<any>;
		testrayCase: TestrayCase;
		testrayProject: TestrayProject;
	} = useOutletContext();

	const {projectId} = useParams();

	useHeader({
		tabs: [],
		timeout: 100,
	});

	const {data: testrayComponentsData} = useFetch<
		APIResponse<TestrayComponent>
	>('/components', {
		params: {
			fields: 'id,name',
			filter: SearchBuilder.eq('projectId', projectId as string),
			pageSize: 1000,
		},
	});

	const {data: testrayCaseTypesData} = useFetch<APIResponse<TestrayCaseType>>(
		'/casetypes',
		{
			params: {
				fields: 'id,name',
				pageSize: 1000,
			},
		}
	);

	const testrayCaseTypes = testrayCaseTypesData?.items || [];
	const testrayComponents = testrayComponentsData?.items || [];

	const {
		form: {onClose, onError, onSave, onSubmit, onSuccess},
	} = useFormActions();

	const {
		formState: {errors, isSubmitting},
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
					priority: testrayCase.priority,
			  }
			: {
					addAnother: false,
					estimatedDuration: 0,
			  },
		resolver: yupResolver(yupSchema.case),
	});

	const _onSubmit = (form: CaseFormData) => {
		const addAnother = form?.addAnother === true;

		return onSubmit(
			{...form, projectId},
			{
				create: (data) => testrayCaseImpl.create(data),
				update: (id, data) => testrayCaseImpl.update(id, data),
			}
		)
			.then(mutateTestrayCase)
			.then(() => {
				if (addAnother) {
					setValue('name', i18n.sub('copy-x', form.name));

					return onSuccess();
				}

				return onSave();
			})
			.catch(onError);
	};

	const addAnother = watch('addAnother');
	const caseTypeId = watch('caseTypeId');
	const componentId = watch('componentId');
	const description = watch('description');
	const steps = watch('steps');

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

				{!testrayCase && (
					<div className="my-5">
						<ClayCheckbox
							checked={addAnother}
							label={i18n.translate('add-another')}
							onChange={() => setValue('addAnother', !addAnother)}
						/>
					</div>
				)}

				<Form.Footer
					onClose={onClose}
					onSubmit={handleSubmit(_onSubmit)}
					primaryButtonProps={{loading: isSubmitting}}
				/>
			</ClayForm>
		</Container>
	);
};

export default withPagePermission(CaseForm, {
	createPath: '/project/:projectId/cases/create',
	restImpl: testrayCaseImpl,
});
