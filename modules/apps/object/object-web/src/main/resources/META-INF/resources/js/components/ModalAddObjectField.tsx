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
import ClayForm, {ClayToggle} from '@clayui/form';
import ClayModal, {ClayModalProvider, useModal} from '@clayui/modal';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useMemo, useState} from 'react';

import useForm from '../hooks/useForm';
import {ERRORS} from '../utils/errors';
import {toCamelCase} from '../utils/string';
import CustomSelect from './Form/CustomSelect/CustomSelect';
import Input from './Form/Input';
import Select from './Form/Select';

const userComputer = {
	description: Liferay.Language.get(
		'files-can-be-stored-in-an-object-entry-or-in-a-specific-folder-in-documents-and-media'
	),
	label: Liferay.Language.get('upload-directly-from-users-computer'),
};

const attachmentSources = [userComputer];

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId() as Liferay.Language.Locale;

const headers = new Headers({
	'Accept': 'application/json',
	'Content-Type': 'application/json',
});

async function fetchPickList() {
	const result = await fetch(
		'/o/headless-admin-list-type/v1.0/list-type-definitions',
		{
			headers,
			method: 'GET',
		}
	);

	const {items = []} = (await result.json()) as {
		items: IPicklist[] | undefined;
	};

	return items.map(({id, name}) => ({id, name}));
}

function ModalAddObjectField({
	apiURL,
	objectFieldTypes,
	observer,
	onClose,
}: IModal) {
	const businessTypeMap = useMemo(() => {
		const businessTypeMap = new Map<string, ObjectFieldType>();

		objectFieldTypes.forEach((type) => {
			businessTypeMap.set(type.businessType, type);
		});

		return businessTypeMap;
	}, [objectFieldTypes]);

	const [error, setError] = useState<string>('');
	const [picklist, setPicklist] = useState<IPicklist[]>([]);

	const initialValues: Partial<ObjectField> = {
		indexed: true,
		indexedAsKeyword: false,
		listTypeDefinitionId: 0,
		required: false,
	};

	const onSubmit = async (field: ObjectField) => {
		const response = await fetch(apiURL, {
			body: JSON.stringify({
				...field,
				name:
					field.name ||
					toCamelCase(field.label[defaultLanguageId] as string),
			}),
			headers,
			method: 'POST',
		});

		if (response.status === 401) {
			window.location.reload();
		}
		else if (response.ok) {
			onClose();

			window.location.reload();
		}
		else {
			const {type} = (await response.json()) as any;
			const isMapped = Object.prototype.hasOwnProperty.call(ERRORS, type);
			const errorMessage = isMapped
				? ERRORS[type]
				: Liferay.Language.get('an-error-occurred');

			setError(errorMessage);
		}
	};

	const validate = (field: Partial<ObjectField>) => {
		const errors: any = {};

		if (!field.label?.[defaultLanguageId]) {
			errors.label = Liferay.Language.get('required');
		}

		if (
			!(
				field.name ??
				toCamelCase(field.label?.[defaultLanguageId] as string)
			)
		) {
			errors.name = Liferay.Language.get('required');
		}

		if (!field.businessType) {
			errors.businessType = Liferay.Language.get('required');
		}

		if (field.businessType === 'Picklist' && !field.listTypeDefinitionId) {
			errors.listTypeDefinitionId = Liferay.Language.get('required');
		}

		return errors;
	};

	const {errors, handleChange, handleSubmit, setValues, values} = useForm({
		initialValues,
		onSubmit,
		validate,
	});

	const handleTypeChange = async (option: ObjectFieldType) => {
		if (option.businessType === 'Picklist') {
			setPicklist(await fetchPickList());
		}

		const objectFieldSettings: ObjectFieldSetting[] | undefined =
			option.businessType === 'Attachment'
				? [
						{
							name: 'acceptedFileExtensions',
							required: true,
							value: 'jpeg, jpg, pdf, png',
						},
						{
							name: 'fileSource',
							required: true,
							value: 'userComputer',
						},
						{
							name: 'maximumFileSize',
							required: true,
							value: 100,
						},
				  ]
				: undefined;

		setValues({
			DBType: option.dbType,
			businessType: option.businessType,
			objectFieldSettings,
		});
	};

	return (
		<ClayModal observer={observer}>
			<ClayForm onSubmit={handleSubmit}>
				<ClayModal.Header>
					{Liferay.Language.get('new-field')}
				</ClayModal.Header>

				<ClayModal.Body>
					{error && (
						<ClayAlert displayType="danger">{error}</ClayAlert>
					)}

					<Input
						error={errors.label}
						id="objectFieldLabel"
						label={Liferay.Language.get('label')}
						name="label"
						onChange={({target: {value}}) => {
							setValues({label: {[defaultLanguageId]: value}});
						}}
						required
						value={values.label?.[defaultLanguageId]}
					/>

					<Input
						error={errors.name || errors.label}
						id="objectFieldName"
						label={Liferay.Language.get('field-name')}
						name="name"
						onChange={handleChange}
						required
						value={
							values.name ??
							toCamelCase(values.label?.[defaultLanguageId] ?? '')
						}
					/>

					<CustomSelect<ObjectFieldType>
						error={errors.businessType}
						label={Liferay.Language.get('type')}
						onChange={handleTypeChange}
						options={objectFieldTypes}
						required
						value={
							businessTypeMap.get(values.businessType ?? '')
								?.label
						}
					/>

					{values.businessType === 'Attachment' && (
						<CustomSelect
							label={Liferay.Language.get('request-files')}
							options={attachmentSources}
							required
							value={userComputer.label}
						/>
					)}

					{values.businessType === 'Picklist' && (
						<Select
							error={errors.listTypeDefinitionId}
							label={Liferay.Language.get('picklist')}
							onChange={({target: {value}}: any) =>
								setValues({
									listTypeDefinitionId: Number(
										picklist[Number(value) - 1].id
									),
								})
							}
							options={picklist.map(({name}) => name)}
							required
						/>
					)}

					<ClayToggle
						label={Liferay.Language.get('mandatory')}
						onToggle={() => setValues({required: !values.required})}
						toggled={values.required}
					/>
				</ClayModal.Body>

				<ClayModal.Footer
					last={
						<ClayButton.Group key={1} spaced>
							<ClayButton
								displayType="secondary"
								onClick={() => onClose()}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton displayType="primary" type="submit">
								{Liferay.Language.get('save')}
							</ClayButton>
						</ClayButton.Group>
					}
				/>
			</ClayForm>
		</ClayModal>
	);
}

export default function ModalWithProvider({apiURL, objectFieldTypes}: IProps) {
	const [isVisible, setVisibility] = useState<boolean>(false);
	const {observer, onClose} = useModal({onClose: () => setVisibility(false)});

	useEffect(() => {
		Liferay.on('addObjectField', () => setVisibility(true));

		return () => Liferay.detach('addObjectField');
	}, []);

	return (
		<ClayModalProvider>
			{isVisible && (
				<ModalAddObjectField
					apiURL={apiURL}
					objectFieldTypes={objectFieldTypes}
					observer={observer}
					onClose={onClose}
				/>
			)}
		</ClayModalProvider>
	);
}

interface IModal extends IProps {
	observer: any;
	onClose: () => void;
}

interface IPicklist {
	id: string;
	name: string;
}

interface IProps {
	apiURL: string;
	objectFieldTypes: ObjectFieldType[];
}
