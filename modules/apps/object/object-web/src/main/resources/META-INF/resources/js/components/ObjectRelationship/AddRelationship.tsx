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
import {
	ObjectRelationshipFormBase,
	ObjectRelationshipType,
	useObjectRelationshipForm,
} from './ObjectRelationshipFormBase';
import SelectRelationship from './SelectRelationship';

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

function ModalAddObjectRelationship({
	ffOneToOneRelationshipConfigurationEnabled,
	objectDefinitionExternalReferenceCode,
	observer,
	onClose,
	parameterRequired,
}: IProps) {
	const [error, setError] = useState<string>('');

	const initialValues: Partial<ObjectRelationship> = {
		objectDefinitionExternalReferenceCode1: objectDefinitionExternalReferenceCode,
	};

	const onSubmit = async ({label, name, ...others}: ObjectRelationship) => {
		try {
			await API.save(
				`/o/object-admin/v1.0/object-definitions/by-external-reference-code/${objectDefinitionExternalReferenceCode}/object-relationships`,
				{
					...others,
					label,
					name: name ?? toCamelCase(label[defaultLanguageId]!, true),
				},
				'POST'
			);

			onClose();
			window.location.reload();
		}
		catch ({message}) {
			setError(message as string);
		}
	};

	const {
		errors,
		handleChange,
		handleSubmit,
		setValues,
		values,
	} = useObjectRelationshipForm({initialValues, onSubmit, parameterRequired});

	return (
		<ClayModal observer={observer}>
			<ClayForm onSubmit={handleSubmit}>
				<ClayModal.Header>
					{Liferay.Language.get('new-relationship')}
				</ClayModal.Header>

				<ClayModal.Body>
					{error && (
						<ClayAlert displayType="danger">{error}</ClayAlert>
					)}

					<Input
						error={errors.label}
						label={Liferay.Language.get('label')}
						onChange={({target: {value}}) =>
							setValues({label: {[defaultLanguageId]: value}})
						}
						required
						value={values.label?.[defaultLanguageId]}
					/>

					<ObjectRelationshipFormBase
						errors={errors}
						ffOneToOneRelationshipConfigurationEnabled={
							ffOneToOneRelationshipConfigurationEnabled
						}
						handleChange={handleChange}
						setValues={setValues}
						values={{
							...values,
							name:
								values.name ??
								toCamelCase(
									values.label?.[defaultLanguageId] ?? '',
									true
								),
						}}
					/>

					{parameterRequired &&
						values.type === ObjectRelationshipType.ONE_TO_MANY && (
							<SelectRelationship
								error={errors.parameterObjectFieldName}
								objectDefinitionExternalReferenceCode={
									values.objectDefinitionExternalReferenceCode2 as string
								}
								onChange={(parameterObjectFieldName) =>
									setValues({parameterObjectFieldName})
								}
								value={values.parameterObjectFieldName}
							/>
						)}
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

export default function AddRelationship({
	ffOneToOneRelationshipConfigurationEnabled,
	objectDefinitionExternalReferenceCode,
	parameterRequired,
}: IProps) {
	const [visibleModal, setVisibleModal] = useState<boolean>(false);
	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false),
	});

	useEffect(() => {
		Liferay.on('addObjectRelationship', () => setVisibleModal(true));

		return () => {
			Liferay.detach('addObjectRelationship');
		};
	}, []);

	return (
		<ClayModalProvider>
			{visibleModal && (
				<ModalAddObjectRelationship
					ffOneToOneRelationshipConfigurationEnabled={
						ffOneToOneRelationshipConfigurationEnabled
					}
					objectDefinitionExternalReferenceCode={
						objectDefinitionExternalReferenceCode
					}
					observer={observer}
					onClose={onClose}
					parameterRequired={parameterRequired}
				/>
			)}
		</ClayModalProvider>
	);
}

interface IProps {
	ffOneToOneRelationshipConfigurationEnabled: boolean;
	objectDefinitionExternalReferenceCode: string;
	observer: Observer;
	onClose: () => void;
	parameterRequired: boolean;
}
