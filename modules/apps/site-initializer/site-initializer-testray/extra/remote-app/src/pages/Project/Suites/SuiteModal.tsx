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
import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import {ClayCheckbox} from '@clayui/form';
import React, {useState} from 'react';

import Input from '../../../components/Input';
import Modal from '../../../components/Modal';
import {CreateSuite} from '../../../graphql/mutations';
import useFormModal, {FormModalOptions} from '../../../hooks/useFormModal';
import i18n from '../../../i18n';
import {Liferay} from '../../../services/liferay/liferay';
import SuiteSelectCasesModal from './SuiteSelectCasesModal';

type SuiteModalProps = {
	modal: FormModalOptions;
};

type SuiteFormData = {
	caseParameters?: string;
	description: string;
	name: string;
	smartSuite: boolean;
};

type SuiteFormProps = {
	form: SuiteFormData;
	onChange: (event: any) => void;
};

const SuiteForm: React.FC<SuiteFormProps> = ({form, onChange}) => {
	const {modal} = useFormModal({
		isVisible: false,
		onSave: (value) => onChange({target: {name: 'caseParameters', value}}),
	});

	return (
		<div>
			<Input
				label={i18n.translate('name')}
				name="name"
				onChange={onChange}
				required
				value={form.name}
			/>

			<Input
				className="mb-4"
				label={i18n.translate('description')}
				name="description"
				onChange={onChange}
				type="textarea"
				value={form.description}
			/>

			<ClayCheckbox
				checked={form.smartSuite}
				label={i18n.translate('smart-suite')}
				name="smartSuite"
				onChange={onChange}
			/>

			<ClayButton.Group className="mb-4">
				<ClayButton
					disabled={form.smartSuite}
					displayType="secondary"
					onClick={modal.open}
				>
					{i18n.translate('select-cases')}
				</ClayButton>

				<ClayButton
					className="ml-3"
					disabled={!form.smartSuite}
					displayType="secondary"
					onClick={modal.open}
				>
					{i18n.translate('select-case-parameters')}
				</ClayButton>
			</ClayButton.Group>

			<ClayAlert>There are no linked cases.</ClayAlert>

			<SuiteSelectCasesModal
				modal={modal}
				type={
					form.smartSuite ? 'select-case-parameters' : 'select-cases'
				}
			/>
		</div>
	);
};

const SuiteModal: React.FC<SuiteModalProps> = ({
	modal: {observer, onChange, onClose, onSave, visible},
}) => {
	const [onCreateSuite] = useMutation(CreateSuite);

	const [form, setForm] = useState<SuiteFormData>({
		caseParameters: '',
		description: '',
		name: '',
		smartSuite: false,
	});

	const onSubmit = async () => {
		try {
			const newForm: Partial<SuiteFormData> = {
				...form,
				caseParameters: JSON.stringify(form.caseParameters),
			};

			delete newForm.smartSuite;

			await onCreateSuite({
				variables: {
					Suite: newForm,
				},
			});

			onSave();

			Liferay.Util.openToast({message: 'TestraySuite Registered'});
		}
		catch (error) {
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
						{i18n.translate('add-suite')}
					</ClayButton>
				</ClayButton.Group>
			}
			observer={observer}
			size="lg"
			title={i18n.translate('new-suite')}
			visible={visible}
		>
			<SuiteForm form={form} onChange={onChange({form, setForm})} />
		</Modal>
	);
};

export default SuiteModal;
