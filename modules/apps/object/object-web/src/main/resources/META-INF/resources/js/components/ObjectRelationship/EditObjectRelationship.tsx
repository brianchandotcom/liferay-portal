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
import React from 'react';

import {EditObjectRelationshipContent} from './EditObjectRelationshipContent';
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
	const onSubmit = async (objectRelationship: ObjectRelationship) => {
		try {
			if (!Liferay.FeatureFlags['LPS-187142']) {
				delete objectRelationship.edge;
			}

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

			openToast({message, type: 'danger'});
		}
	};

	const {errors, handleChange, handleSubmit, setValues, values} =
		useObjectRelationshipForm({
			initialValues,
			onSubmit,
			parameterRequired,
		});

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
				parameterRequired={parameterRequired}
				readOnly={readOnly}
				restContextPath={restContextPath}
				setValues={setValues}
				values={values}
			/>
		</SidePanelForm>
	);
}
