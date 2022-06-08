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
import ClayForm from '@clayui/form';
import {FocusEvent, useEffect} from 'react';
import {useForm} from 'react-hook-form';
import {useOutletContext, useParams} from 'react-router-dom';

import Form from '../../../components/Form';
import Container from '../../../components/Layout/Container';
import MarkdownPreview from '../../../components/Markdown';
import {CreateRequirement, UpdateRequirement} from '../../../graphql/mutations';
import {
	CTypePagination,
	TestrayComponent,
	TestrayRequirement,
	getComponents,
	getRequirements,
} from '../../../graphql/queries';
import {useHeader} from '../../../hooks';
import useFormActions from '../../../hooks/useFormActions';
import i18n from '../../../i18n';
import yupSchema, {yupResolver} from '../../../schema/yup';

type RequirementsFormType = {
	componentId: number;
	description: string;
	descriptionType: string;
	id: number;
	linkTitle: string;
	linkURL: string;
	summary: string;
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

const RequirementsForm: React.FC = () => {
	const {
		form: {onClose, onSubmitAndSave},
	} = useFormActions();
	const {projectId} = useParams();

	const {setTabs} = useHeader({shouldUpdate: false});

	const context: {requirement?: TestrayRequirement} = useOutletContext();

	const {
		formState: {errors},
		handleSubmit,
		register,
		setValue,
		watch,
	} = useForm<RequirementsFormType>({
		defaultValues: context?.requirement
			? ({
					...context?.requirement,
					componentId: context?.requirement?.component?.id,
			  } as any)
			: {},
		resolver: yupResolver(yupSchema.requirement),
	});

	const {data: testrayComponentsData} = useQuery<
		CTypePagination<'components', TestrayComponent>
	>(getComponents);

	// eslint-disable-next-line react-hooks/exhaustive-deps
	const testrayComponents = testrayComponentsData?.c?.components.items || [];

	const _onSubmit = (form: RequirementsFormType) => {
		onSubmitAndSave(
			{...form, projectId},
			{
				createMutation: CreateRequirement,
				updateMutation: UpdateRequirement,
			},
			{
				refetchQueries: [{query: getRequirements}],
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

	const onBlurLinkTitle = ({
		target: {value},
	}: FocusEvent<HTMLInputElement>) => {
		const linkTitleSplit = value.split('/');
		const linkTitle = linkTitleSplit[linkTitleSplit.length - 1];

		setValue('linkTitle', linkTitle);
	};

	useEffect(() => {
		if (testrayComponents.length) {
			setValue('componentId', testrayComponents[0].id);
		}
	}, [testrayComponents, setValue]);

	useEffect(() => {
		if (!context.requirement) {
			setTimeout(() => {
				setTabs([]);
			}, 10);
		}
	}, [context.requirement, setTabs]);

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

				<MarkdownPreview markdown={descriptionType} />

				<Form.Divider />

				<Form.Footer
					onClose={onClose}
					onSubmit={handleSubmit(_onSubmit)}
				/>
			</ClayForm>
		</Container>
	);
};

export default RequirementsForm;
