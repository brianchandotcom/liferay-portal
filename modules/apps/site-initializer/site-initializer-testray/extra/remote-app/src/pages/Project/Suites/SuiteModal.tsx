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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import {ClayCheckbox} from '@clayui/form';
import React from 'react';
import {useForm} from 'react-hook-form';

import Input from '../../../components/Input';
import Modal from '../../../components/Modal';
import {CreateSuite} from '../../../graphql/mutations';
import {withVisibleContent} from '../../../hoc/withVisibleContent';
import useFormModal, {FormModalOptions} from '../../../hooks/useFormModal';
import i18n from '../../../i18n';
import yupSchema, {yupResolver} from '../../../schema/yup';
import SuiteSelectCasesModal from './SuiteSelectCasesModal';

type SuiteFormData = {
	caseParameters?: string;
	description: string;
	name: string;
	projectId?: string;
	smartSuite: boolean;
};

type SuiteModalProps = {
	modal: FormModalOptions;
	projectId: number;
};

const SuiteModal: React.FC<SuiteModalProps> = ({
	modal: {modalState, observer, onClose, onSubmit},
	projectId,
}) => {
	const {
		formState: {errors},
		handleSubmit,
		register,
		setValue,
		watch,
	} = useForm<SuiteFormData>({
		defaultValues: {smartSuite: false, ...modalState},
		resolver: yupResolver(yupSchema.suite),
	});

	const smartSuite = watch('smartSuite');

	const _onSubmit = (form: SuiteFormData) => {
		onSubmit(
			{...form, projectId},
			{
				createMutation: CreateSuite,
				updateMutation: CreateSuite,
			}
		);
	};

	const inputProps = {
		errors,
		register,
		required: true,
	};

	const {modal} = useFormModal({
		isVisible: false,
		onSave: (value) => setValue('caseParameters', value),
	});

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
						{i18n.translate('add-suite')}
					</ClayButton>
				</ClayButton.Group>
			}
			observer={observer}
			size="lg"
			title={i18n.translate('new-suite')}
			visible
		>
			<div>
				<Input
					label={i18n.translate('name')}
					name="name"
					{...inputProps}
				/>

				<Input
					label={i18n.translate('description')}
					name="description"
					type="textarea"
					{...inputProps}
				/>

				<ClayCheckbox
					checked={smartSuite}
					label={i18n.translate('smart-suite')}
					onChange={() => setValue('smartSuite', !smartSuite)}
				/>

				<ClayButton.Group className="mb-4">
					<ClayButton
						disabled={smartSuite}
						displayType="secondary"
						onClick={modal.open}
					>
						{i18n.translate('select-cases')}
					</ClayButton>

					<ClayButton
						className="ml-3"
						disabled={!smartSuite}
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
						smartSuite ? 'select-case-parameters' : 'select-cases'
					}
				/>
			</div>
		</Modal>
	);
};

export default withVisibleContent(SuiteModal);
