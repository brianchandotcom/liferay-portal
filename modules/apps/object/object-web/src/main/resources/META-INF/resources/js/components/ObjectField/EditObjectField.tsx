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
import {ILearnResourceContext} from 'frontend-js-components-web';
import React, {useEffect} from 'react';

import {EditObjectFieldContent} from './EditObjectFieldContent';
import {useObjectFieldForm} from './useObjectFieldForm';

import './EditObjectField.scss';

export interface EditObjectFieldProps {
	baseResourceURL: string;
	creationLanguageId: Liferay.Language.Locale;
	filterOperators: TFilterOperators;
	forbiddenChars: string[];
	forbiddenLastChars: string[];
	forbiddenNames: string[];
	isDefaultStorageType: boolean;
	isRootDescendantNode: boolean;
	learnResources: ILearnResourceContext;
	objectDefinitionExternalReferenceCode: string;
	objectFieldId: number;
	readOnly: boolean;
	workflowStatuses: LabelValueObject[];
}

export const objectFieldInitialValues: Partial<ObjectField> = {
	DBType: '',
	businessType: 'Text',
	externalReferenceCode: '',
	id: 0,
	indexed: true,
	indexedAsKeyword: false,
	indexedLanguageId: 'en_US',
	label: {en_US: ''},
	listTypeDefinitionId: 0,
	name: '',
	objectFieldSettings: [],
	readOnlyConditionExpression: '',
	relationshipType: '',
	required: false,
	state: false,
	system: false,
};

export default function EditObjectField({
	baseResourceURL,
	creationLanguageId,
	filterOperators,
	forbiddenChars,
	forbiddenLastChars,
	forbiddenNames,
	isDefaultStorageType,
	isRootDescendantNode,
	learnResources,
	objectDefinitionExternalReferenceCode,
	objectFieldId,
	readOnly,
	workflowStatuses,
}: EditObjectFieldProps) {
	const onSubmit = async ({id, ...objectField}: ObjectField) => {
		delete objectField.defaultValue;
		delete objectField.listTypeDefinitionId;
		delete objectField.system;

		try {
			await API.save({
				item: objectField,
				url: `/o/object-admin/v1.0/object-fields/${id}`,
			});

			saveAndReload();
			openToast({
				message: Liferay.Language.get(
					'the-object-field-was-updated-successfully'
				),
			});
		}
		catch (error) {
			openToast({message: (error as Error).message, type: 'danger'});
		}
	};

	const {errors, handleChange, handleSubmit, setValues, values} =
		useObjectFieldForm({
			forbiddenChars,
			forbiddenLastChars,
			forbiddenNames,
			initialValues: objectFieldInitialValues,
			onSubmit,
		});

	useEffect(() => {
		if (errors.defaultValue) {
			openToast({
				message: Liferay.Language.get(
					'please-fill-out-all-required-fields'
				),
				type: 'danger',
			});
		}
	}, [errors]);

	return (
		<SidePanelForm
			className="lfr-objects__edit-object-field"
			onSubmit={handleSubmit}
			readOnly={readOnly}
			title={Liferay.Language.get('field')}
		>
			<EditObjectFieldContent
				baseResourceURL={baseResourceURL}
				containerWrapper={Card}
				creationLanguageId={creationLanguageId}
				errors={errors}
				filterOperators={filterOperators}
				handleChange={handleChange}
				isDefaultStorageType={isDefaultStorageType}
				isRootDescendantNode={isRootDescendantNode}
				learnResources={learnResources}
				objectDefinitionExternalReferenceCode={
					objectDefinitionExternalReferenceCode
				}
				objectFieldId={objectFieldId}
				readOnly={readOnly}
				setValues={setValues}
				values={values}
				workflowStatuses={workflowStatuses}
			/>
		</SidePanelForm>
	);
}
