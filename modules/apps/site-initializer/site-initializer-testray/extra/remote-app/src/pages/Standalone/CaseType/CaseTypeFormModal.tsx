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

import {useMutation} from '@apollo/client';
import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import {useEffect, useState} from 'react';

import Input from '../../../components/Input';
import Modal from '../../../components/Modal';
import {CreateCaseType, UpdateCaseType} from '../../../graphql/mutations';
import {FormModalOptions} from '../../../hooks/useFormModal';
import i18n from '../../../i18n';

type CaseTypeForm = {
	id?: number;
	name: string;
};

type CaseTypeFormProps = {
	form: CaseTypeForm;
	onChange: (event: any) => void;
	onSubmit: (event: any) => void;
};

const FormCaseType: React.FC<CaseTypeFormProps> = ({
	form,
	onChange,
	onSubmit,
}) => {
	return (
		<ClayForm onSubmit={onSubmit}>
			<Input
				label="Name"
				name="name"
				onChange={onChange}
				required
				value={form.name}
			/>
		</ClayForm>
	);
};

type CaseTypeProps = {
	modal: FormModalOptions;
};
const CaseTypeFormModal: React.FC<CaseTypeProps> = ({
	modal: {modalState, observer, onChange, onClose, onError, onSave, visible},
}) => {
	const [form, setForm] = useState<CaseTypeForm>({
		name: '',
	});

	const [onCreateCaseType] = useMutation(CreateCaseType);
	const [onUpdateCaseType] = useMutation(UpdateCaseType);

	useEffect(() => {
		if (visible && modalState) {
			setForm(modalState);
		}
	}, [visible, modalState]);

	const onSubmit = async (event?: any) => {
		event?.preventDefault();

		const variables: any = {
			CaseType: {
				name: form.name,
			},
		};

		try {
			if (form.id) {
				variables.caseTypeId = form.id;

				onUpdateCaseType({variables});
			}
			else {
				await onCreateCaseType({variables});
			}

			onSave();
		}
		catch (error) {
			onError(error);
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
						{i18n.translate('save')}
					</ClayButton>
				</ClayButton.Group>
			}
			observer={observer}
			size="lg"
			title={i18n.translate(form.id ? 'edit-case-type' : 'new-case-type')}
			visible={visible}
		>
			<FormCaseType
				form={form}
				onChange={onChange({form, setForm})}
				onSubmit={onSubmit}
			/>
		</Modal>
	);
};

export default CaseTypeFormModal;
