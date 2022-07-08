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
	apiURL,
	ffOneToOneRelationshipConfigurationEnabled,
	objectDefinitionId,
	observer,
	onClose,
	parameterRequired,
}: IProps) {
	const [error, setError] = useState<string>('');

	const initialValues: Partial<ObjectRelationship> = {
		objectDefinitionId1: Number(objectDefinitionId),
	};

	const onSubmit = async ({label, name, ...others}: ObjectRelationship) => {
		try {
			await API.save(
				apiURL,
				{
					...others,
					label,
					name: name ?? toCamelCase(label[defaultLanguageId]!),
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
									values.label?.[defaultLanguageId] ?? ''
								),
						}}
					/>

					{parameterRequired &&
						values.type === ObjectRelationshipType.ONE_TO_MANY && (
							<SelectRelationship
								error={errors.parameterObjectFieldId}
								objectDefinitionId={values.objectDefinitionId2}
								onChange={(parameterObjectFieldId) =>
									setValues({parameterObjectFieldId})
								}
								value={values.parameterObjectFieldId}
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
	apiURL,
	ffOneToOneRelationshipConfigurationEnabled,
	objectDefinitionId,
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
					apiURL={apiURL}
					ffOneToOneRelationshipConfigurationEnabled={
						ffOneToOneRelationshipConfigurationEnabled
					}
					objectDefinitionId={objectDefinitionId}
					observer={observer}
					onClose={onClose}
					parameterRequired={parameterRequired}
				/>
			)}
		</ClayModalProvider>
	);
}

interface IProps {
	apiURL: string;
	ffOneToOneRelationshipConfigurationEnabled: boolean;
	objectDefinitionId: number;
	observer: any;
	onClose: () => void;
	parameterRequired: boolean;
}
