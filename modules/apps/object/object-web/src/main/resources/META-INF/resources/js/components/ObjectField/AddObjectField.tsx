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
import ClayForm from '@clayui/form';
import ClayModal, {ClayModalProvider, useModal} from '@clayui/modal';
import {Observer} from '@clayui/modal/lib/types';
import {API, Input} from '@liferay/object-js-components-web';
import React, {useEffect, useState} from 'react';

import {toCamelCase} from '../../utils/string';
import ObjectFieldFormBase from './ObjectFieldFormBase';
import {useObjectFieldForm} from './useObjectFieldForm';

interface IModal extends IProps {
	observer: Observer;
	onClose: () => void;
}

interface IProps {
	apiURL: string;
	objectDefinitionId: number;
	objectFieldTypes: ObjectFieldType[];
	objectName: string;
}

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

function ModalAddObjectField({
	apiURL,
	objectDefinitionId,
	objectFieldTypes,
	objectName,
	observer,
	onClose,
}: IModal) {
	const [error, setError] = useState<string>('');

	const initialValues: Partial<ObjectField> = {
		indexed: true,
		indexedAsKeyword: false,
		indexedLanguageId: null,
		listTypeDefinitionExternalReferenceCode: '',
		listTypeDefinitionId: 0,
		required: false,
	};

	const onSubmit = async (field: Partial<ObjectField>) => {
		if (field.label) {
			field = {
				...field,
				name:
					field.name ||
					toCamelCase(field.label[defaultLanguageId] as string),
			};

			delete field.listTypeDefinitionId;

			try {
				await API.save(apiURL, field, 'POST');

				onClose();
				window.location.reload();
			}
			catch (error) {
				setError((error as Error).message);
			}
		}
	};

	const {
		errors,
		handleChange,
		handleSubmit,
		setValues,
		values,
	} = useObjectFieldForm({
		initialValues,
		onSubmit,
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
						label={Liferay.Language.get('label')}
						name="label"
						onChange={({target: {value}}) => {
							setValues({label: {[defaultLanguageId]: value}});
						}}
						required
						value={values.label?.[defaultLanguageId]}
					/>

					<ObjectFieldFormBase
						errors={errors}
						handleChange={handleChange}
						objectDefinitionId={objectDefinitionId}
						objectField={values}
						objectFieldTypes={objectFieldTypes}
						objectName={objectName}
						setValues={setValues}
					/>
				</ClayModal.Body>

				<ClayModal.Footer
					last={
						<ClayButton.Group spaced>
							<ClayButton
								displayType="secondary"
								onClick={() => onClose()}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton type="submit">
								{Liferay.Language.get('save')}
							</ClayButton>
						</ClayButton.Group>
					}
				/>
			</ClayForm>
		</ClayModal>
	);
}

export default function AddObjectField({
	apiURL,
	objectDefinitionId,
	objectFieldTypes,
	objectName,
}: IProps) {
	const [isVisible, setVisibility] = useState<boolean>(false);
	const {observer, onClose} = useModal({onClose: () => setVisibility(false)});

	useEffect(() => {
		Liferay.on('addObjectField', () => setVisibility(true));

		return () => Liferay.detach('addObjectField');
	}, []);

	const applyFeatureFlag = () => {
		return objectFieldTypes.filter((objectFieldType) => {
			if (
				!Liferay.FeatureFlags['LPS-164948'] &&
				!Liferay.FeatureFlags['LPS-158776']
			) {
				return (
					objectFieldType.businessType !== 'Formula' &&
					objectFieldType.businessType !== 'MultiSelectPicklist'
				);
			}

			if (!Liferay.FeatureFlags['LPS-164948']) {
				return objectFieldType.businessType !== 'Formula';
			}

			if (!Liferay.FeatureFlags['LPS-158776']) {
				return objectFieldType.businessType !== 'MultiSelectPicklist';
			}
		});
	};

	return (
		<ClayModalProvider>
			{isVisible && (
				<ModalAddObjectField
					apiURL={apiURL}
					objectDefinitionId={objectDefinitionId}
					objectFieldTypes={
						!Liferay.FeatureFlags['LPS-164948'] ||
						!Liferay.FeatureFlags['LPS-158776']
							? applyFeatureFlag()
							: objectFieldTypes
					}
					objectName={objectName}
					observer={observer}
					onClose={onClose}
				/>
			)}
		</ClayModalProvider>
	);
}
