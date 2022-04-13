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
import classNames from 'classnames';
import {useForm} from 'react-hook-form';

import Input from '../../../components/Input';
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

const FormRow: React.FC<{title: string}> = ({children, title}) => (
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
						<ClayForm.Group className="form-group-sm">
							<Input
								label={i18n.translate('name')}
								name="name"
								{...inputProps}
							/>
						</ClayForm.Group>
					</FormRow>

					<FormRow title={i18n.translate('details')}>
						<ClayForm.Group className="form-group-sm">
							<label
								className={classNames(
									'font-weight-normal mx-0 text-paragraph'
								)}
								htmlFor="priority"
							>
								{i18n.translate('priority')}
							</label>

							<select
								className="form-control"
								id="priority"
								{...register('priority')}
							>
								<option>
									{i18n.translate('choose-an-option')}
								</option>

								{priorities.map(({label, value}) => (
									<option key={label} value={value}>
										{value}
									</option>
								))}
							</select>

							<label
								className={classNames(
									'font-weight-normal mx-0 text-paragraph'
								)}
								htmlFor="type"
							>
								{i18n.translate('type')}
							</label>

							<select
								className="form-control"
								id="type"
								{...register('caseTypeId')}
							>
								<option>
									{i18n.translate('choose-an-option')}
								</option>

								{testrayCaseTypes.map(({id, name}) => (
									<option key={id} value={id}>
										{name}
									</option>
								))}
							</select>

							<label
								className={classNames(
									'font-weight-normal mx-0 text-paragraph'
								)}
								htmlFor="componentId"
							>
								{i18n.translate('main-component')}
							</label>

							<select
								className="form-control"
								id="componentId"
								{...register('componentId')}
							>
								<option>
									{i18n.translate('choose-an-option')}
								</option>

								{testrayComponents.map(({id, name}) => (
									<option key={id} value={id}>
										{name}
									</option>
								))}
							</select>

							<Input
								label={i18n.translate('enter-the-case-name')}
								name="estimatedDuration"
								{...inputProps}
							/>
						</ClayForm.Group>
					</FormRow>

					<FormRow title={i18n.translate('description')}>
						<ClayForm.Group className="form-group-sm">
							<label
								className={classNames(
									'font-weight-normal mx-0 text-paragraph'
								)}
								htmlFor="descriptionType"
							>
								{i18n.translate('description-type')}
							</label>

							<select
								className="form-control"
								id="descriptionType"
								{...register('descriptionType')}
							>
								<option>
									{i18n.translate('choose-an-option')}
								</option>

								{descriptionTypes.map(({label, value}) => (
									<option key={label} value={value}>
										{value}
									</option>
								))}
							</select>

							<Input
								label={i18n.translate('description')}
								name="description"
								type="textarea"
								{...inputProps}
							/>
						</ClayForm.Group>

						<MarkdownPreview markdown={description} />
					</FormRow>

					<FormRow title={i18n.translate('steps')}>
						<ClayForm.Group className="form-group-sm">
							<label
								className={classNames(
									'font-weight-normal mx-0 text-paragraph'
								)}
								htmlFor="stepsType"
							>
								{i18n.translate('steps-type')}
							</label>

							<select
								className="form-control"
								id="stepsType"
								{...register('stepsType')}
							>
								<option>
									{i18n.translate('choose-an-option')}
								</option>

								{descriptionTypes.map(({label, value}) => (
									<option key={label} value={value}>
										{value}
									</option>
								))}
							</select>

							<Input
								label={i18n.translate('steps')}
								name="steps"
								type="textarea"
								{...inputProps}
							/>
						</ClayForm.Group>

						<MarkdownPreview markdown={steps} />
					</FormRow>
				</ClayForm>
			</Container>
		</Modal>
	);
};

export default withVisibleContent(CaseModal);
