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
import React, {useState} from 'react';

import Input from '../../../components/Input';
import Modal from '../../../components/Modal';
import {CreateTestrayRoutine} from '../../../graphql/mutations';
import {FormModalOptions} from '../../../hooks/useFormModal';
import i18n from '../../../i18n';

type RoutineModalProps = {
	modal: FormModalOptions;
};

type RoutineFormData = {
	autoanalyze: boolean;
	name: string;
};

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
	modal: {observer, onClose, onError, onSave, visible},
}) => {
	const [onCreateTestrayRoutine] = useMutation(CreateTestrayRoutine);

	const [form, setForm] = useState<RoutineFormData>({
		autoanalyze: false,
		name: '',
	});

	const onChange = (event: any) => {
		const {
			target: {checked, name, type, ...target},
		} = event;

		let {value} = target;

		if (type === 'checkbox') {
			value = checked;
		}

		setForm({
			...form,
			[name]: value,
		});
	};

	const onSubmit = async () => {
		try {
			await onCreateTestrayRoutine({
				variables: {
					TestrayRoutine: form,
				},
			});

			onSave();
		}
		catch (error) {
			onError();
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
						{i18n.translate('add-routine')}
					</ClayButton>
				</ClayButton.Group>
			}
			observer={observer}
			size="lg"
			title={i18n.translate('new-routine')}
			visible={visible}
		>
			<RoutineForm form={form} onChange={onChange} />
		</Modal>
	);
};

export default RoutineModal;
