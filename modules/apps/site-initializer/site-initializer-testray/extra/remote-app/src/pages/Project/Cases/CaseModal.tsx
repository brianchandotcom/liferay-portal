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

import {useMutation, useQuery} from '@apollo/client';
import ClayButton from '@clayui/button';
import ClayForm, {ClaySelectWithOption} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import classNames from 'classnames';
import {useState} from 'react';

import Input from '../../../components/Input';
import Container from '../../../components/Layout/Container';
import MarkdownPreview from '../../../components/Markdown';
import Modal from '../../../components/Modal';
import {CreateCase} from '../../../graphql/mutations';
import {
	CTypePagination,
	TestrayCaseType,
	TestrayComponent,
	getCaseTypes,
	getComponents,
} from '../../../graphql/queries';
import {FormModalOptions} from '../../../hooks/useFormModal';
import i18n from '../../../i18n';
import {Liferay} from '../../../services/liferay/liferay';
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

const emptyOption = {
	label: i18n.translate('choose-an-option'),
	value: '',
};

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

type CaseFormProps = {
	form: CaseFormData;
	onChange: (event: any) => void;
	testrayCaseTypes: TestrayCaseType[];
	testrayComponents: TestrayComponent[];
};

const CaseForm: React.FC<CaseFormProps> = ({
	form,
	onChange,
	testrayCaseTypes,
	testrayComponents,
}) => {
	return (
		<>
			<Container>
				<ClayForm>
					<FormRow title={i18n.translate('case-name')}>
						<ClayForm.Group className="form-group-sm">
							<Input
								name="name"
								onChange={onChange}
								placeholder={i18n.translate(
									'enter-the-case-name'
								)}
								required
								value={form.name}
							/>
						</ClayForm.Group>
					</FormRow>

					<FormRow title={i18n.translate('details')}>
						<ClayForm.Group className="form-group-sm">
							<label
								className={classNames(
									'font-weight-normal mx-0 text-paragraph'
								)}
							>
								{i18n.translate('priority')}
							</label>

							<ClaySelectWithOption
								className="rounded-xs"
								name="priority"
								onChange={onChange}
								options={priorities.map(({label, value}) => ({
									label,
									value,
								}))}
								value={form.priority}
							/>

							<label
								className={classNames(
									'font-weight-normal mx-0 mt-2 text-paragraph'
								)}
							>
								{i18n.translate('type')}
							</label>

							<ClaySelectWithOption
								name="caseTypeId"
								onChange={onChange}
								options={[
									emptyOption,
									...testrayCaseTypes.map(({id, name}) => ({
										label: name,
										value: id,
									})),
								]}
								value={form.caseTypeId}
							/>

							<label
								className={classNames(
									'font-weight-normal mx-0 mt-2 text-paragraph'
								)}
							>
								{i18n.translate('main-component')}
							</label>

							<ClaySelectWithOption
								name="componentId"
								onChange={onChange}
								options={[
									emptyOption,
									...testrayComponents.map(({id, name}) => ({
										label: name,
										value: id,
									})),
								]}
								value={form.componentId}
							/>

							<Input
								label={i18n.translate('estimated-duration')}
								name="estimatedDuration"
								onChange={onChange}
								placeholder={i18n.translate(
									'enter-the-case-name'
								)}
								required
								value={form.estimatedDuration}
							/>
						</ClayForm.Group>
					</FormRow>

					<FormRow title={i18n.translate('description')}>
						<ClayForm.Group className="form-group-sm">
							<ClaySelectWithOption
								className="mb-2"
								name="descriptionType"
								onChange={onChange}
								options={descriptionTypes}
								value={form.descriptionType}
							/>

							<Input
								name="description"
								onChange={onChange}
								required
								type="textarea"
								value={form.description}
							/>
						</ClayForm.Group>

						<MarkdownPreview markdown={form.description} />
					</FormRow>

					<FormRow title={i18n.translate('steps')}>
						<ClayForm.Group className="form-group-sm">
							<ClaySelectWithOption
								className="mb-2"
								name="stepsType"
								onChange={onChange}
								options={descriptionTypes}
								value={form.stepsType}
							/>

							<Input
								name="steps"
								onChange={onChange}
								required
								type="textarea"
								value={form.steps}
							/>
						</ClayForm.Group>
					</FormRow>
				</ClayForm>
			</Container>
		</>
	);
};

type CaseModalProps = {
	modal: FormModalOptions;
};

const CaseModal: React.FC<CaseModalProps> = ({
	modal: {observer, onClose, onSave, visible},
}) => {
	const [form, setForm] = useState<CaseFormData>({
		caseTypeId: 0,
		componentId: 0,
		description: '',
		descriptionType: '',
		estimatedDuration: 0,
		name: '',
		priority: 0,
		steps: '',
		stepsType: '',
	});

	const [onCreateCase] = useMutation(CreateCase);

	const {data: testrayComponentsData} = useQuery<
		CTypePagination<'components', TestrayComponent>
	>(getComponents);

	const {data: testrayCaseTypesData} = useQuery<
		CTypePagination<'caseTypes', TestrayCaseType>
	>(getCaseTypes);

	const testrayComponents = testrayComponentsData?.c.components.items || [];

	const testrayCaseTypes = testrayCaseTypesData?.c.caseTypes.items || [];

	const onChange = (event: any) => {
		const {
			target: {name, value},
		} = event;

		setForm({
			...form,
			[name]: value,
		});
	};

	const onSubmit = async () => {
		const newForm: CaseFormData = {
			...form,
			caseTypeId: Number(form.caseTypeId),
			componentId: Number(form.componentId),
			estimatedDuration: Number(form.estimatedDuration),
			priority: Number(form.priority),
		};

		try {
			await onCreateCase({
				variables: {
					TestrayCase: newForm,
				},
			});
			onSave();
			Liferay.Util.openToast({message: 'TestrayCase Registered'});
		} catch (error) {
			Liferay.Util.openToast({
				message: (error as any).message,
				type: 'danger',
			});
		}
	};

	return (
		<Modal
			last={
				<ClayButton.Group spaced>
					<ClayButton displayType="secondary" onClick={onClose}>
						{i18n.translate('close')}
					</ClayButton>

					<ClayButton displayType="primary" onClick={onSubmit}>
						{i18n.translate('add-case')}
					</ClayButton>
				</ClayButton.Group>
			}
			observer={observer}
			size="full-screen"
			title={i18n.translate('new-case')}
			visible={visible}
		>
			<CaseForm
				form={form}
				onChange={onChange}
				testrayCaseTypes={testrayCaseTypes}
				testrayComponents={testrayComponents}
			/>
		</Modal>
	);
};

export default CaseModal;
