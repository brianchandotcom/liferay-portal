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
import React, {useEffect, useState} from 'react';

import useForm from '../hooks/useForm';
import {ERRORS} from '../utils/errors';
import {toCamelCase} from '../utils/string';
import CustomSelect from './Form/CustomSelect/CustomSelect';
import Input from './Form/Input';
import Select from './Form/Select';

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

const headers = new Headers({
	'Accept': 'application/json',
	'Content-Type': 'application/json',
});

const ModalAddObjectField: React.FC<IProps> = ({
	apiURL,
	objectFieldBusinessTypes,
	observer,
	onClose,
}) => {
	const [error, setError] = useState<string>('');
	const [picklist, setPicklist] = useState<TPicklist[]>([]);
	const [
		selectedObjectBusinessTypeLabel,
		setSelectedObjectBusinessTypeLabel,
	] = useState('');

	const initialValues: TInitialValues = {
		businessType: '',
		dbType: '',
		label: '',
		listTypeDefinitionId: 0,
		name: undefined,
		required: false,
	};

	const getObjectFieldType = (
		key: keyof IObjectFieldBusinessType,
		value: string
	) => {
		return objectFieldBusinessTypes.find(
			(objectFieldType) => objectFieldType[key] === value
		) as IObjectFieldBusinessType;
	};

	const onSubmit = async ({
		businessType,
		label,
		listTypeDefinitionId,
		name,
		required,
	}: TInitialValues) => {
		const objectFieldType = getObjectFieldType(
			'businessType',
			businessType
		);

		const response = await fetch(apiURL, {
			body: JSON.stringify({
				DBType: objectFieldType.dbType,
				businessType: objectFieldType.businessType,
				indexed: true,
				indexedAsKeyword: false,
				indexedLanguageId: null,
				label: {
					[defaultLanguageId]: label,
				},
				listTypeDefinitionId,
				name: name || toCamelCase(label),
				required,
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

	const validate = (values: TInitialValues) => {
		const errors: any = {};

		if (!values.label) {
			errors.label = Liferay.Language.get('required');
		}

		if (!(values.name ?? toCamelCase(values.label))) {
			errors.name = Liferay.Language.get('required');
		}

		if (!values.businessType) {
			errors.type = Liferay.Language.get('required');
		}

		if (
			values.businessType === 'Picklist' &&
			!values.listTypeDefinitionId
		) {
			errors.listTypeDefinitionId = Liferay.Language.get('required');
		}

		return errors;
	};

	const {errors, handleChange, handleSubmit, setValues, values} = useForm({
		initialValues,
		onSubmit,
		validate,
	});

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
						onChange={handleChange}
						required
						value={values.label}
					/>

					<Input
						error={errors.name || errors.label}
						id="objectFieldName"
						label={Liferay.Language.get('field-name')}
						name="name"
						onChange={handleChange}
						required
						value={values.name ?? toCamelCase(values.label)}
					/>

					<CustomSelect
						error={errors.businessType}
						label={Liferay.Language.get('type')}
						onChange={async (type: any) => {
							if (type.businessType === 'Picklist') {
								const result = await fetch(
									'/o/headless-admin-list-type/v1.0/list-type-definitions',
									{
										headers,
										method: 'GET',
									}
								);

								const {
									items = [],
								} = (await result.json()) as {
									items: [];
								};

								setPicklist(
									items.map(({id, name}: TPicklist) => ({
										id,
										name,
									}))
								);
							}

							setSelectedObjectBusinessTypeLabel(type.label);

							setValues({businessType: type.businessType});
						}}
						options={objectFieldBusinessTypes}
						required
						value={selectedObjectBusinessTypeLabel}
					>
						{({description, label}) => (
							<>
								<div>{label}</div>
								<span className="text-small">
									{description}
								</span>
							</>
						)}
					</CustomSelect>

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
};

interface IProps extends React.HTMLAttributes<HTMLElement> {
	apiURL: string;
	objectFieldBusinessTypes: IObjectFieldBusinessType[];
	observer: any;
	onClose: () => void;
}

interface IObjectFieldBusinessType {
	businessType: string;
	dbType: string;
	description: string;
	label: string;
}

type TPicklist = {
	id: string;
	name: string;
};

type TInitialValues = {
	businessType: string;
	dbType: string;
	label: string;
	listTypeDefinitionId: number;
	name?: string;
	required: boolean;
};

const ModalWithProvider: React.FC<IProps> = ({
	apiURL,
	objectFieldBusinessTypes,
}) => {
	const [visibleModal, setVisibleModal] = useState<boolean>(false);
	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false),
	});

	useEffect(() => {
		Liferay.on('addObjectField', () => setVisibleModal(true));

		return () => {
			Liferay.detach('addObjectField');
		};
	}, []);

	return (
		<ClayModalProvider>
			{visibleModal && (
				<ModalAddObjectField
					apiURL={apiURL}
					objectFieldBusinessTypes={objectFieldBusinessTypes}
					observer={observer}
					onClose={onClose}
				/>
			)}
		</ClayModalProvider>
	);
};

export default ModalWithProvider;
