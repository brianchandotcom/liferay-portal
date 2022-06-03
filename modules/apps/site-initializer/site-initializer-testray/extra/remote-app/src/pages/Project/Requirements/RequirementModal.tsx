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
import ClayForm, {ClaySelectWithOption} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import classNames from 'classnames';
import {ReactNode, useEffect, useState} from 'react';

import Form from '../../../components/Form';
import Container from '../../../components/Layout/Container';
import MarkdownPreview from '../../../components/Markdown';
import Modal from '../../../components/Modal';
import {CreateRequirement, UpdateRequirement} from '../../../graphql/mutations';
import {
	CTypePagination,
	TestrayComponent,
	getComponents,
} from '../../../graphql/queries';
import {FormModalOptions} from '../../../hooks/useFormModal';
import i18n from '../../../i18n';

const requirementFormDefault = {
	componentId: '',
	description: '',
	descriptionType: '',
	id: undefined,
	key: '',
	linkTitle: '',
	linkURL: '',
	summary: '',
};

type RequirementsForm = typeof requirementFormDefault;

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

type RequirementsFormProps = {
	form: RequirementsForm;
	onChange: (event: any) => void;
};
const RequirementsForm: React.FC<RequirementsFormProps> = ({
	form,
	onChange,
}) => {
	const {data: testrayComponentsData} = useQuery<
		CTypePagination<'components', TestrayComponent>
	>(getComponents);

	const testrayComponents = testrayComponentsData?.c?.components.items || [];

	return (
		<Container>
			<ClayForm>
				<FormRow title={i18n.translate('requirements')}>
					<ClayForm.Group className="form-group-sm">
						<Form.Input
							label={i18n.translate('key')}
							name="key"
							onChange={onChange}
							required
							value={form.key}
						/>

						<Form.Input
							label={i18n.translate('summary')}
							name="summary"
							onChange={onChange}
							required
							value={form.summary}
						/>

						<Form.Input
							label={i18n.translate('link-url')}
							name="linkURL"
							onChange={onChange}
							required
							value={form.linkURL}
						/>

						<Form.Input
							label={i18n.translate('link-title')}
							name="linkTitle"
							onChange={onChange}
							required
							value={form.linkTitle}
						/>

						<label
							className={classNames(
								'font-weight-normal mx-0 mt-2 text-paragraph'
							)}
						>
							{i18n.translate('main-component')}
						</label>

						<ClaySelectWithOption
							className="rounded-xs"
							name="componentId"
							onChange={onChange}
							options={testrayComponents.map(({id, name}) => ({
								label: name,
								value: id,
							}))}
							required
							value={form.componentId}
						/>
					</ClayForm.Group>
				</FormRow>

				<FormRow title={i18n.translate('description')}>
					<ClayForm.Group className="form-group-sm">
						<ClaySelectWithOption
							className="mb-2 rounded-xs"
							name="descriptionType"
							onChange={onChange}
							options={descriptionTypes}
							value={form.descriptionType}
						/>

						<Form.Input
							name="description"
							onChange={onChange}
							required
							type="textarea"
							value={form.description}
						/>
					</ClayForm.Group>

					<MarkdownPreview markdown={form.description} />
				</FormRow>
			</ClayForm>
		</Container>
	);
};

type RequirementsModalProps = {
	modal: FormModalOptions;
	projectId: number;
};

const RequirementsModal: React.FC<RequirementsModalProps> = ({
	modal: {modalState, observer, onChange, onClose, onSubmit, visible},
	projectId,
}) => {
	const [form, setForm] = useState<RequirementsForm>(requirementFormDefault);

	useEffect(() => {
		if (visible && modalState) {
			setForm(modalState);
		}
	}, [visible, modalState]);

	const _onSubmit = () =>
		onSubmit(
			{
				componentId: Number(form.componentId),
				description: form.description,
				descriptionType: form.descriptionType,
				id: form.id,
				key: form.key,
				linkTitle: form.linkTitle,
				linkURL: form.linkURL,
				projectId,
				summary: form.summary,
			},
			{
				createMutation: CreateRequirement,
				updateMutation: UpdateRequirement,
			}
		).then(() => setForm(requirementFormDefault));

	return (
		<Modal
			last={
				<Form.Footer isModal onClose={onClose} onSubmit={_onSubmit} />
			}
			observer={observer}
			size="full-screen"
			title={i18n.translate(
				form.id ? 'edit-requirement' : 'new-requirement'
			)}
			visible={visible}
		>
			<RequirementsForm
				form={form}
				onChange={onChange({form, setForm})}
			/>
		</Modal>
	);
};

export default RequirementsModal;
