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
import {useForm} from 'react-hook-form';

import Form from '../../../components/Form';
import Container from '../../../components/Layout/Container';
import MarkdownPreview from '../../../components/Markdown';
import Modal from '../../../components/Modal';
import {CreateCase, UpdateCase} from '../../../graphql/mutations';
import {
	CTypePagination,
	TestrayCaseType,
	TestrayComponent,
	getCaseTypes,
	getComponents,
} from '../../../graphql/queries';
import {withVisibleContent} from '../../../hoc/withVisibleContent';
import {FormModalOptions} from '../../../hooks/useFormModal';
import i18n from '../../../i18n';
import yupSchema, {yupResolver} from '../../../schema/yup';
import {DescriptionType} from '../../../types';

type CaseFormData = {
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

type CaseModalProps = {
	modal: FormModalOptions;
	projectId: number;
};

const CaseModal: React.FC<CaseModalProps> = ({
	modal: {modalState, observer, onClose, onSubmit},
	projectId,
}) => {
	const {
		formState: {errors},
		handleSubmit,
		register,
		watch,
	} = useForm<CaseFormData>({
		defaultValues: modalState,
		resolver: yupResolver(yupSchema.case),
	});

	const {data: testrayComponentsData} = useQuery<
		CTypePagination<'components', TestrayComponent>
	>(getComponents);

	const {data: testrayCaseTypesData} = useQuery<
		CTypePagination<'caseTypes', TestrayCaseType>
	>(getCaseTypes);

	const testrayCaseTypes = testrayCaseTypesData?.c.caseTypes.items || [];
	const testrayComponents = testrayComponentsData?.c.components.items || [];

	const _onSubmit = (form: CaseFormData) => {
		onSubmit(
			{...form, projectId},
			{
				createMutation: CreateCase,
				updateMutation: UpdateCase,
			}
		);
	};

	const description = watch('description');
	const steps = watch('steps');

	const inputProps = {
		errors,
		register,
		required: true,
	};

	return (
		<Modal
			last={
				<Form.Footer
					onClose={onClose}
					onSubmit={handleSubmit(_onSubmit)}
				/>
			}
			observer={observer}
			size="full-screen"
			title={i18n.translate('new-case')}
			visible
		>
			<Container>
				<ClayForm>
					<Form.BaseRow title={i18n.translate('case-name')}>
						<Form.Input
							{...inputProps}
							label={i18n.translate('name')}
							name="name"
						/>
					</Form.BaseRow>

					<Form.BaseRow title={i18n.translate('details')}>
						<Form.Select
							{...inputProps}
							label="priority"
							name="priority"
							options={priorities}
							required={false}
						/>

						<Form.Select
							{...inputProps}
							label="type"
							name="type"
							options={testrayCaseTypes.map(
								({id: value, name: label}) => ({
									label,
									value,
								})
							)}
						/>

						<Form.Select
							{...inputProps}
							label={i18n.translate('main-component')}
							name="componentId"
							options={testrayComponents.map(
								({id: value, name: label}) => ({
									label,
									value,
								})
							)}
						/>

						<Form.Input
							{...inputProps}
							label={i18n.translate('enter-the-case-name')}
							name="estimatedDuration"
							required={false}
						/>
					</Form.BaseRow>

					<Form.BaseRow title={i18n.translate('description')}>
						<ClayForm.Group className="form-group-sm">
							<Form.Select
								{...inputProps}
								label={i18n.translate('description-type')}
								name="descriptionType"
								options={descriptionTypes}
								required={false}
							/>

							<Form.Input
								{...inputProps}
								label={i18n.translate('description')}
								name="description"
								required={false}
								type="textarea"
							/>
						</ClayForm.Group>

						<MarkdownPreview markdown={description} />
					</Form.BaseRow>

					<Form.BaseRow title={i18n.translate('steps')}>
						<Form.Select
							{...inputProps}
							label={i18n.translate('steps-type')}
							name="stepsType"
							options={descriptionTypes}
							required={false}
						/>

						<Form.Input
							{...inputProps}
							label={i18n.translate('steps')}
							name="steps"
							required={false}
							type="textarea"
						/>

						<MarkdownPreview markdown={steps} />
					</Form.BaseRow>
				</ClayForm>
			</Container>
		</Modal>
	);
};

export default withVisibleContent(CaseModal);
