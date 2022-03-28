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
import {ClayCheckbox} from '@clayui/form';
import React, {useEffect, useState} from 'react';

import Input from '../../../components/Input';
import Modal from '../../../components/Modal';
import {CreateRoutine, UpdateRoutine} from '../../../graphql/mutations';
import {FormModalOptions} from '../../../hooks/useFormModal';
import i18n from '../../../i18n';

const routineFormData = {
	autoanalyze: false,
	id: 0,
	name: '',
};

type RoutineModalProps = {
	modal: FormModalOptions;
};

type RoutineFormData = typeof routineFormData;

type RoutineFormProps = {
	form: RoutineFormData;
	onChange: (event: any) => void;
};

const RoutineForm: React.FC<RoutineFormProps> = ({form, onChange}) => {
	return (
		<div>
			<Input
				label={i18n.translate('name')}
				name="name"
				onChange={onChange}
				required
				value={form.name}
			/>

			<div className="mt-2">
				<ClayCheckbox
					checked={form.autoanalyze}
					label={i18n.translate('autoanalyze')}
					name="autoanalyze"
					onChange={onChange}
				/>
			</div>
		</div>
	);
};

const RoutineModal: React.FC<RoutineModalProps> = ({
	modal: {modalState, observer, onChange, onClose, onError, onSave, visible},
}) => {
	const [onCreateRoutine] = useMutation(CreateRoutine);
	const [onUpdateRoutine] = useMutation(UpdateRoutine);

	const [form, setForm] = useState<RoutineFormData>(routineFormData);

	useEffect(() => {
		if (visible && modalState) {
			setForm(modalState);
		}
	}, [visible, modalState]);

	const onSubmit = async () => {
		const variables: any = {
			Routine: {
				autoanalyze: form.autoanalyze,
				name: form.name,
			},
		};

		try {
			if (form.id) {
				variables.routineId = form.id;

				await onUpdateRoutine({variables});
			}
			else {
				await onCreateRoutine({variables});
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
			title={i18n.translate(form.id ? 'edit-routine' : 'new-routine')}
			visible={visible}
		>
			<RoutineForm form={form} onChange={onChange({form, setForm})} />
		</Modal>
	);
};

export default RoutineModal;
