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

import {useQuery} from '@apollo/client';
import ClayButton from '@clayui/button';
import ClayForm, {ClayCheckbox} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import {ReactNode, useEffect} from 'react';
import {useForm} from 'react-hook-form';
import {useOutletContext, useParams} from 'react-router-dom';

import Input from '../../../components/Input';
import InputSelect from '../../../components/Input/InputSelect';
import Container from '../../../components/Layout/Container';
import MarkdownPreview from '../../../components/Markdown';
import {CreateCase, UpdateCase} from '../../../graphql/mutations';
import {
	CTypePagination,
	TestrayCase,
	TestrayCaseType,
	TestrayComponent,
	TestrayProject,
	getCaseTypes,
	getComponents,
} from '../../../graphql/queries';
import {useHeader} from '../../../hooks';
import useFormActions from '../../../hooks/useFormActions';
import i18n from '../../../i18n';
import yupSchema, {yupResolver} from '../../../schema/yup';
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

const FormRow: React.FC<{
	children: ReactNode;
	separator?: boolean;
	title: string;
}> = ({children, separator = true, title}) => (
	<>
		<ClayLayout.Row justify="start">
			<ClayLayout.Col size={3} sm={12} xl={2}>
				<h5 className="font-weight-bold">{title}</h5>
			</ClayLayout.Col>

			<ClayLayout.Col size={3} sm={12} xl={10}>
				{children}
			</ClayLayout.Col>
		</ClayLayout.Row>

		{separator && <hr />}
	</>
);

const CaseForm = () => {
	const {
		testrayCase,
		testrayProject,
	}: {
		testrayCase: TestrayCase;
		testrayProject: TestrayProject;
	} = useOutletContext();

	const {setHeading, setTabs} = useHeader({
		shouldUpdate: false,
	});

	const {data: testrayComponentsData} = useQuery<
		CTypePagination<'components', TestrayComponent>
	>(getComponents);

	const {data: testrayCaseTypesData} = useQuery<
		CTypePagination<'caseTypes', TestrayCaseType>
	>(getCaseTypes);

	const testrayCaseTypes = testrayCaseTypesData?.c.caseTypes.items || [];
	const testrayComponents = testrayComponentsData?.c.components.items || [];

	useEffect(() => {
		if (testrayProject) {
			setTimeout(() => {
				setHeading([
					{
						category: i18n.translate('project').toUpperCase(),
						path: `/project/${testrayProject.id}/routines`,
						title: testrayProject.name,
					},
					{
						category: i18n.translate('project').toUpperCase(),
						title: i18n.translate('add-case'),
					},
				]);

				setTabs([]);
			}, 10);
		}
	}, [setHeading, setTabs, testrayProject]);

	const {
		form: {onClose, onSubmit},
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
			  }
			: {},
		resolver: yupResolver(yupSchema.case),
	});

	const _onSubmit = (form: CaseFormData) => {
		onSubmit(
			{...form, projectId},
			{
				createMutation: CreateCase,
				updateMutation: UpdateCase,
			}
		);
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
				<FormRow title={i18n.translate('add-case')}>
					<Input
						{...inputProps}
						label={i18n.translate('name')}
						name="name"
					/>
				</FormRow>

				<FormRow title={i18n.translate('details')}>
					<InputSelect
						{...inputProps}
						className="col-4"
						label="priority"
						name="priority"
						options={priorities}
						required={false}
					/>

					<InputSelect
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

					<InputSelect
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

					<Input
						{...inputProps}
						className="col-4"
						label={i18n.translate('estimed-duration')}
						name="estimatedDuration"
						required={false}
					/>
				</FormRow>

				<FormRow
					separator={false}
					title={i18n.translate('description')}
				>
					<InputSelect
						{...inputProps}
						className="col-2 ml-auto"
						defaultOption={false}
						name="descriptionType"
						options={descriptionTypes}
						required={false}
					/>
				</FormRow>

				<Input
					{...inputProps}
					name="description"
					required={false}
					type="textarea"
				/>

				<MarkdownPreview markdown={description} />

				<hr />

				<FormRow separator={false} title={i18n.translate('steps')}>
					<InputSelect
						{...inputProps}
						className="col-2 ml-auto"
						defaultOption={false}
						name="stepsType"
						options={descriptionTypes}
						required={false}
					/>
				</FormRow>

				<Input
					{...inputProps}
					name="steps"
					required={false}
					type="textarea"
				/>

				<MarkdownPreview markdown={steps} />

				<hr />

				<div className="my-5">
					<ClayCheckbox
						checked={addAnother}
						label={i18n.translate('add-another')}
						onChange={() => setValue('addAnother', !addAnother)}
					/>
				</div>

				<div>
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={() => onClose()}
						>
							{i18n.translate('close')}
						</ClayButton>

						<ClayButton
							displayType="primary"
							onClick={handleSubmit(_onSubmit)}
						>
							{i18n.translate('save')}
						</ClayButton>
					</ClayButton.Group>
				</div>
			</ClayForm>
		</Container>
	);
};

export default CaseForm;
