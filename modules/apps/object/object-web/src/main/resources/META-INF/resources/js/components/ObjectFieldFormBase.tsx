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

import {ClayToggle} from '@clayui/form';
import {fetch} from 'frontend-js-web';
import React, {ChangeEventHandler, ReactNode, useMemo, useState} from 'react';

import useForm from '../hooks/useForm';
import {toCamelCase} from '../utils/string';
import CustomSelect from './Form/CustomSelect/CustomSelect';
import Input from './Form/Input';
import Select from './Form/Select';

const REQUIRED_MSG = Liferay.Language.get('required');

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
		'/o/headless-admin-list-type/v1.0/list-type-definitions?pageSize=-1',
		{
			headers,
			method: 'GET',
		}
	);

	const {items = []} = (await result.json()) as {
		items: IPickList[] | undefined;
	};

	return items.map(({id, name}) => ({id, name}));
}

export default function ObjectFieldFormBase({
	children,
	disabled,
	errors,
	handleChange,
	objectField: values,
	objectFieldTypes,
	setValues,
}: IProps) {
	const businessTypeMap = useMemo(() => {
		const businessTypeMap = new Map<string, ObjectFieldType>();

		objectFieldTypes.forEach((type) => {
			businessTypeMap.set(type.businessType, type);
		});

		return businessTypeMap;
	}, [objectFieldTypes]);

	const [pickList, setPickList] = useState<IPickList[]>([]);

	const handleTypeChange = async (option: ObjectFieldType) => {
		if (option.businessType === 'Picklist') {
			setPickList(await fetchPickList());
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

		const indexedAsKeyword =
			option.dbType === 'String' && values.indexedAsKeyword;

		const indexedLanguageId =
			option.dbType === 'String' && !values.indexedAsKeyword
				? values.indexedLanguageId
				: null;

		setValues({
			DBType: option.dbType,
			businessType: option.businessType,
			indexedAsKeyword,
			indexedLanguageId,
			objectFieldSettings,
		});
	};

	return (
		<>
			<Input
				disabled={disabled}
				error={errors.name || errors.label}
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
				disabled={disabled}
				error={errors.businessType}
				label={Liferay.Language.get('type')}
				onChange={handleTypeChange}
				options={objectFieldTypes}
				required
				value={businessTypeMap.get(values.businessType ?? '')?.label}
			/>

			{values.businessType === 'Attachment' && (
				<CustomSelect
					disabled={disabled}
					label={Liferay.Language.get('request-files')}
					options={attachmentSources}
					required
					value={userComputer.label}
				/>
			)}

			{values.businessType === 'Picklist' && (
				<Select
					disabled={disabled}
					error={errors.listTypeDefinitionId}
					label={Liferay.Language.get('picklist')}
					onChange={({target: {value}}: any) =>
						setValues({
							listTypeDefinitionId: Number(pickList[value].id),
						})
					}
					options={pickList.map(({name}) => name)}
					required
				/>
			)}
			{children}
			<ClayToggle
				disabled={disabled}
				label={Liferay.Language.get('mandatory')}
				onToggle={(required) => setValues({required})}
				toggled={values.required}
			/>
		</>
	);
}

export function useObjectFieldForm({
	initialValues,
	onSubmit,
}: IUseObjectFieldForm) {
	const validate = (field: Partial<ObjectField>) => {
		const errors: any = {};

		if (!field.label?.[defaultLanguageId]) {
			errors.label = REQUIRED_MSG;
		}

		if (
			!(
				field.name ??
				toCamelCase(field.label?.[defaultLanguageId] as string)
			)
		) {
			errors.name = REQUIRED_MSG;
		}

		if (!field.businessType) {
			errors.businessType = REQUIRED_MSG;
		}
		else if (field.businessType === 'Picklist') {
			if (!field.listTypeDefinitionId) {
				errors.listTypeDefinitionId = REQUIRED_MSG;
			}
		}

		return errors;
	};

	const {errors, handleChange, handleSubmit, setValues, values} = useForm({
		initialValues,
		onSubmit,
		validate,
	});

	return {errors, handleChange, handleSubmit, setValues, values};
}

interface IUseObjectFieldForm {
	initialValues: Partial<ObjectField>;
	onSubmit: (field: ObjectField) => void;
}
interface IPickList {
	id: string;
	name: string;
}

interface IProps {
	children?: ReactNode;
	disabled?: boolean;
	errors: {[key in keyof ObjectField]?: string};
	handleChange: ChangeEventHandler<HTMLInputElement>;
	objectField: Partial<ObjectField>;
	objectFieldTypes: ObjectFieldType[];
	setValues: (values: Partial<ObjectField>) => void;
}
