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
import ClayForm, {ClayCheckbox, ClaySelectWithOption} from '@clayui/form';
import React, {useState} from 'react';

import Input from '../../../components/Input';
import Modal from '../../../components/Modal';
import {CreateRoutine} from '../../../graphql/mutations';
import {
	CTypePagination,
	TestrayRoutine,
	getRoutines,
} from '../../../graphql/queries';
import {
	TestrayProductVersion,
	getProductVersions,
} from '../../../graphql/queries/testrayProductVersion';
import {FormModalOptions} from '../../../hooks/useFormModal';
import i18n from '../../../i18n';

type RoutineBuildModalProps = {
	modal: FormModalOptions;
};

type BuildFormData = {
	autoanalyze: boolean;
	name: string;
};

type BuildFormProps = {
	form: BuildFormData;
	onChange: (event: any) => void;
};

const emptyOption = {
	label: i18n.translate('choose-an-option'),
	value: '',
};

const BuildForm: React.FC<BuildFormProps> = ({form, onChange}) => {
	const {data: routinesData} = useQuery<
		CTypePagination<'routines', TestrayRoutine>
	>(getRoutines);
	const {data: productVersionsData} = useQuery<
		CTypePagination<'productVersions', TestrayProductVersion>
	>(getProductVersions);

	const routines = routinesData?.c.routines.items || [];
	const productVersions = productVersionsData?.c.productVersions.items || [];

	return (
		<div>
			<Input
				label={i18n.translate('name')}
				name="name"
				onChange={onChange}
				required
				value={form.name}
			/>

			<ClayForm.Group>
				<label>{i18n.translate('routine')}</label>

				<ClaySelectWithOption
					name="routineId"
					onChange={onChange}
					options={[
						emptyOption,
						...routines.map(({id, name}) => ({
							label: name,
							value: id,
						})),
					]}
				/>
			</ClayForm.Group>

			<ClayForm.Group>
				<label>{i18n.translate('product-version')}</label>

				<ClaySelectWithOption
					name="productVersionId"
					onChange={onChange}
					options={[
						emptyOption,
						...productVersions.map(({id, name}) => ({
							label: name,
							value: id,
						})),
					]}
				/>
			</ClayForm.Group>

			<Input
				label={i18n.translate('git-hash')}
				name="gitHash"
				onChange={onChange}
				required
			/>

			<Input
				label={i18n.translate('description')}
				name="description"
				onChange={onChange}
				required
				type="textarea"
			/>

			<div className="mt-2">
				<ClayCheckbox
					checked={form.autoanalyze}
					label={i18n.translate('template')}
					name="template"
					onChange={onChange}
				/>
			</div>

			<ClayButton.Group className="mb-4">
				<ClayButton displayType="secondary">
					{i18n.translate('select-cases')}
				</ClayButton>

				<ClayButton className="ml-3" displayType="secondary">
					{i18n.translate('select-case-parameters')}
				</ClayButton>
			</ClayButton.Group>
		</div>
	);
};

const RoutineBuildModal: React.FC<RoutineBuildModalProps> = ({
	modal: {observer, onChange, onClose, onError, onSave, visible},
}) => {
	const [onCreateRoutine] = useMutation(CreateRoutine);

	const [form, setForm] = useState<BuildFormData>({
		autoanalyze: false,
		name: '',
	});

	const onSubmit = async () => {
		try {
			await onCreateRoutine({
				variables: {
					Build: form,
				},
			});

			onSave();
		} catch (error) {
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
						{i18n.translate('save')}
					</ClayButton>
				</ClayButton.Group>
			}
			observer={observer}
			size="lg"
			title={i18n.translate('new-build')}
			visible={visible}
		>
			<BuildForm form={form} onChange={onChange({form, setForm})} />
		</Modal>
	);
};

export default RoutineBuildModal;
