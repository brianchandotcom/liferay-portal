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
import ClayForm from '@clayui/form';
import ClayLayout from '@clayui/layout';
import {ReactNode} from 'react';
import {useForm} from 'react-hook-form';
import {useOutletContext, useParams} from 'react-router-dom';

import Input from '../../../components/Input';
import InputSelect from '../../../components/Input/InputSelect';
import Container from '../../../components/Layout/Container';
import MarkdownPreview from '../../../components/Markdown';
import {CreateRequirement, UpdateRequirement} from '../../../graphql/mutations';
import {
	CTypePagination,
	TestrayComponent,
	TestrayRequirement,
	getComponents,
} from '../../../graphql/queries';
import useFormActions from '../../../hooks/useFormActions';
import i18n from '../../../i18n';
import yupSchema, {yupResolver} from '../../../schema/yup';

const requirementFormDefault = {
	componentId: 0,
	description: '',
	descriptionType: '',
	id: undefined,
	key: '',
	linkTitle: '',
	linkURL: '',
	summary: '',
};

type RequirementsFormType = typeof requirementFormDefault;

const descriptionTypes = [
	{
		label: 'Markdown',
		value: 'markdown',
	},
	{
		label: 'Plain Text',
		value: 'plaintext',
	},
];

const FormRow: React.FC<{children: ReactNode; title: string}> = ({
	children,
	title,
}) => (
	<>
		<ClayLayout.Row justify="start">
			<ClayLayout.Col size={3} sm={12} xl={3}>
				<h5 className="font-weight-normal">{title}</h5>
			</ClayLayout.Col>

			<ClayLayout.Col size={3} sm={12} xl={9}>
				{children}
			</ClayLayout.Col>
		</ClayLayout.Row>

		<hr />
	</>
);

const RequirementsForm: React.FC = () => {
	const {
		form: {onClose, onSubmit},
	} = useFormActions();
	const {projectId} = useParams();

	const context: {requirement?: TestrayRequirement} = useOutletContext();

	const {
		formState: {errors},
		handleSubmit,
		register,
		watch,
	} = useForm<RequirementsFormType>({
		defaultValues: context?.requirement
			? ({
					...context?.requirement,
					componentId: context?.requirement?.component?.id,
			  } as any)
			: requirementFormDefault,
		resolver: yupResolver(yupSchema.requirement),
	});

	const {data: testrayComponentsData} = useQuery<
		CTypePagination<'components', TestrayComponent>
	>(getComponents);

	const testrayComponents = testrayComponentsData?.c?.components.items || [];

	const _onSubmit = (form: RequirementsFormType) => {
		onSubmit(
			{...form, projectId},
			{
				createMutation: CreateRequirement,
				updateMutation: UpdateRequirement,
			}
		);
	};

	const descriptionType = watch('description');
	const componentId = watch('componentId');

	const inputProps = {
		errors,
		register,
		required: true,
	};

	return (
		<Container className="container" title="">
			<ClayForm>
				<FormRow title={i18n.translate('requirements')}>
					<ClayForm.Group className="form-group-sm">
						<Input
							{...inputProps}
							label={i18n.translate('key')}
							name="key"
						/>

						<Input
							{...inputProps}
							label={i18n.translate('summary')}
							name="summary"
						/>

						<Input
							{...inputProps}
							label={i18n.translate('link-url')}
							name="linkURL"
						/>

						<Input
							{...inputProps}
							label={i18n.translate('link-title')}
							name="linkTitle"
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
					</ClayForm.Group>
				</FormRow>

				<FormRow title={i18n.translate('description')}>
					<InputSelect
						{...inputProps}
						name="descriptionType"
						options={descriptionTypes}
					/>

					<Input
						{...inputProps}
						name="description"
						required
						type="textarea"
					/>

					<MarkdownPreview markdown={descriptionType} />
				</FormRow>

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

export default RequirementsForm;
