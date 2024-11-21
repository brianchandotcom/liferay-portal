/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	API,
	Card,
	SidePanelForm,
	openToast,
	saveAndReload,
} from '@liferay/object-js-components-web';
import React, {FormEvent, useState} from 'react';

import {EditObjectRelationshipContent} from './EditObjectRelationshipContent';
import {Alert} from './ObjectRelationshipFormBase';
import {useObjectRelationshipForm} from './useObjectRelationshipForm';

interface EditObjectRelationshipProps {
	baseResourceURL: string;
	hasUpdateObjectDefinitionPermission: boolean;
	objectDefinitionExternalReferenceCode: string;
	objectRelationship: ObjectRelationship;
	objectRelationshipDeletionTypes: LabelValueObject[];
	parameterRequired: boolean;
	restContextPath: string;
}

export default function EditObjectRelationship({
	baseResourceURL,
	hasUpdateObjectDefinitionPermission,
	objectDefinitionExternalReferenceCode,
	objectRelationship: initialValues,
	objectRelationshipDeletionTypes,
	parameterRequired,
	restContextPath,
}: EditObjectRelationshipProps) {
	const [alert, setAlert] = useState<Alert>({
		displayType: 'info',
		message: Liferay.Language.get(
			'when-enabled,-permissions-are-inherited,-all-api-endpoints-are-grouped-under-the-parent,-and-the-relationship-field-is-always-mandatory'
		),
	});

	const {errors, handleChange, handleValidate, setValues, values} =
		useObjectRelationshipForm({
			initialValues,
			onSubmit: () => {},
			parameterRequired,
		});

	const onSubmit = async (
		objectRelationship: Partial<ObjectRelationship> = values
	) => {
		try {
			await API.putObjectRelationship(objectRelationship);
			saveAndReload();

			openToast({
				message: Liferay.Language.get(
					'the-object-relationship-was-updated-successfully'
				),
			});
		}
		catch (error: unknown) {
			const {message} = error as Error;

			if (!Liferay.FeatureFlags['LPS-187142']) {
				openToast({message, type: 'danger'});
			}
			else {
				setAlert({displayType: 'warning', message});
			}
		}
	};

	const handleSubmit = (event: FormEvent) => {
		event.preventDefault();

		const validationErrors = handleValidate();

		if (!Object.keys(validationErrors).length) {
			onSubmit(values);
		}
	};

	const readOnly =
		!hasUpdateObjectDefinitionPermission ||
		values.reverse ||
		initialValues.system;

	return (
		<SidePanelForm
			customLabel={{
				displayType: values.reverse ? 'info' : 'success',
				message: values.reverse
					? Liferay.Language.get('child')
					: Liferay.Language.get('parent'),
			}}
			onSubmit={handleSubmit}
			readOnly={readOnly}
			title={Liferay.Language.get('relationship')}
		>
			<EditObjectRelationshipContent
				alert={alert}
				baseResourceURL={baseResourceURL}
				containerWrapper={Card}
				errors={errors}
				handleChange={handleChange}
				objectDefinitionExternalReferenceCode={
					objectDefinitionExternalReferenceCode
				}
				objectRelationshipDeletionTypes={
					objectRelationshipDeletionTypes
				}
				onSubmit={onSubmit}
				parameterRequired={parameterRequired}
				readOnly={readOnly}
				restContextPath={restContextPath}
				setValues={setValues}
				values={values}
			/>
		</SidePanelForm>
	);
}
