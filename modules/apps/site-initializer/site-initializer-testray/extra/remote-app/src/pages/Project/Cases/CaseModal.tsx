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

import Input from '../../../components/Input';
import InputSelect from '../../../components/Input/InputSelect';
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
				<ClayButton.Group spaced>
					<ClayButton displayType="secondary" onClick={onClose}>
						{i18n.translate('close')}
					</ClayButton>

					<ClayButton
						displayType="primary"
						onClick={handleSubmit(_onSubmit)}
					>
						{i18n.translate('add-case')}
					</ClayButton>
				</ClayButton.Group>
			}
			observer={observer}
			size="full-screen"
			title={i18n.translate('new-case')}
			visible
		>
			<Container>
				<ClayForm>
					<FormRow title={i18n.translate('case-name')}>
						<Input
							{...inputProps}
							label={i18n.translate('name')}
							name="name"
						/>
					</FormRow>

					<FormRow title={i18n.translate('details')}>
						<InputSelect
							{...inputProps}
							label="priority"
							name="priority"
							options={priorities}
							required={false}
						/>

						<InputSelect
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
						/>

						<Input
							{...inputProps}
							label={i18n.translate('enter-the-case-name')}
							name="estimatedDuration"
							required={false}
						/>
					</FormRow>

					<FormRow title={i18n.translate('description')}>
						<ClayForm.Group className="form-group-sm">
							<InputSelect
								{...inputProps}
								label="description-type"
								name="descriptionType"
								options={descriptionTypes}
								required={false}
							/>

							<Input
								{...inputProps}
								label={i18n.translate('description')}
								name="description"
								required={false}
								type="textarea"
							/>
						</ClayForm.Group>

						<MarkdownPreview markdown={description} />
					</FormRow>

					<FormRow title={i18n.translate('steps')}>
						<InputSelect
							{...inputProps}
							label="steps-type"
							name="stepsType"
							options={descriptionTypes}
							required={false}
						/>

						<Input
							{...inputProps}
							label={i18n.translate('steps')}
							name="steps"
							required={false}
							type="textarea"
						/>

						<MarkdownPreview markdown={steps} />
					</FormRow>
				</ClayForm>
			</Container>
		</Modal>
	);
};

export default withVisibleContent(CaseModal);
