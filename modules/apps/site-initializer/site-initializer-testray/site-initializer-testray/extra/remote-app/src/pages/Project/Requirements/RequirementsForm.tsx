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

import ClayForm from '@clayui/form';
import {FocusEvent, useEffect, useMemo} from 'react';
import {useForm} from 'react-hook-form';
import {useOutletContext, useParams} from 'react-router-dom';
import {KeyedMutator} from 'swr';
import {withPagePermission} from '~/hoc/withPagePermission';

import Form from '../../../components/Form';
import Container from '../../../components/Layout/Container';
import MarkdownPreview from '../../../components/Markdown';
import SearchBuilder from '../../../core/SearchBuilder';
import {useHeader} from '../../../hooks';
import {useFetch} from '../../../hooks/useFetch';
import useFormActions from '../../../hooks/useFormActions';
import i18n from '../../../i18n';
import yupSchema, {yupResolver} from '../../../schema/yup';
import {
	APIResponse,
	TestrayComponent,
	TestrayRequirement,
	testrayRequirementsImpl,
} from '../../../services/rest';

type RequirementsFormType = typeof yupSchema.requirement.__outputType;

type OutletContext = {
	mutateTestrayRequirement: KeyedMutator<TestrayRequirement>;
	testrayRequirement: TestrayRequirement;
};

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

const RequirementsForm = () => {
	const {
		form: {onClose, onError, onSave, onSubmit},
	} = useFormActions();
	useHeader({tabs: [], timeout: 100});
	const {projectId, requirementId} = useParams();
	const {
		mutateTestrayRequirement,
		testrayRequirement,
	}: OutletContext = useOutletContext();
	const {
		formState: {errors, isSubmitting},
		handleSubmit,
		register,
		setValue,
		watch,
	} = useForm<RequirementsFormType>({
		defaultValues: requirementId ? (testrayRequirement as any) : {},
		resolver: yupResolver(yupSchema.requirement),
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

	const testrayComponents = useMemo(
		() => testrayComponentsData?.items ?? [],
		[testrayComponentsData?.items]
	);

	const _onSubmit = (form: RequirementsFormType) => {
		if (!form.id) {
			form.key = `R-${Math.ceil(Math.random() * 1000)}`;
		}

		return onSubmit(
			{...form, projectId},
			{
				create: (data) => testrayRequirementsImpl.create(data),
				update: (data, id) => testrayRequirementsImpl.update(data, id),
			}
		)
			.then(mutateTestrayRequirement)
			.then(onSave)
			.catch(onError);
	};

	const descriptionType = watch('description');
	const componentId = watch('componentId');

	const inputProps = {
		errors,
		register,
		required: true,
	};

	const onBlurLinkTitle = ({
		target: {value},
	}: FocusEvent<HTMLInputElement>) => {
		const linkTitleSplit = value.split('/');
		const linkTitle = linkTitleSplit[linkTitleSplit.length - 1];

		setValue('linkTitle', linkTitle);
	};

	useEffect(() => {
		if (testrayComponents.length) {
			setValue('componentId', String(testrayComponents[0].id));
		}
	}, [testrayComponents, setValue]);

	return (
		<Container className="container">
			<ClayForm className="container pt-2">
				<Form.Input
					{...inputProps}
					label={i18n.translate('summary')}
					name="summary"
				/>

				<Form.Input
					{...inputProps}
					label={i18n.translate('link-url')}
					name="linkURL"
					onBlur={onBlurLinkTitle}
				/>

				<Form.Input
					{...inputProps}
					label={i18n.translate('link-title')}
					name="linkTitle"
				/>

				<Form.Select
					{...inputProps}
					defaultOption={false}
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

				<Form.Divider />

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
					required
					type="textarea"
				/>

				{descriptionType && (
					<MarkdownPreview markdown={descriptionType} />
				)}

				<Form.Divider />

				<Form.Footer
					onClose={onClose}
					onSubmit={handleSubmit(_onSubmit)}
					primaryButtonProps={{loading: isSubmitting}}
				/>
			</ClayForm>
		</Container>
	);
};

export default withPagePermission(RequirementsForm, {
	createPath: '/project/:projectId/requirements/create',
	restImpl: testrayRequirementsImpl,
});
