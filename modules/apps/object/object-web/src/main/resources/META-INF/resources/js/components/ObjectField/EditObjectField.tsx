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

import ClayTabs from '@clayui/tabs';
import {
	API,
	SidePanelForm,
	SidebarCategory,
	openToast,
	saveAndReload,
} from '@liferay/object-js-components-web';
import React, {useEffect, useState} from 'react';

import './EditObjectField.scss';
import {AdvancedTab} from './Tabs/Advanced/AdvancedTab';
import {BasicInfo} from './Tabs/BasicInfo/BasicInfo';
import {useObjectFieldForm} from './useObjectFieldForm';

interface EditObjectFieldProps {
	creationLanguageId: Liferay.Language.Locale;
	filterOperators: TFilterOperators;
	forbiddenChars: string[];
	forbiddenLastChars: string[];
	forbiddenNames: string[];
	isApproved: boolean;
	isDefaultStorageType: boolean;
	objectDefinitionExternalReferenceCode: string;
	objectField: ObjectField;
	objectFieldId: number;
	objectFieldTypes: ObjectFieldType[];
	objectName: string;
	objectRelationshipId: number;
	readOnly: boolean;
	readOnlySidebarElements: SidebarCategory[];
	sidebarElements: SidebarCategory[];
	workflowStatusJSONArray: LabelValueObject[];
}

const TABS = [Liferay.Language.get('basic-info')];

const initialValues: Partial<ObjectField> = {
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
	creationLanguageId,
	filterOperators,
	forbiddenChars,
	forbiddenLastChars,
	forbiddenNames,
	isApproved,
	isDefaultStorageType,
	objectDefinitionExternalReferenceCode,
	objectFieldId,
	objectFieldTypes,
	objectName,
	objectRelationshipId,
	readOnly,
	readOnlySidebarElements,
	sidebarElements,
	workflowStatusJSONArray,
}: EditObjectFieldProps) {
	const [activeIndex, setActiveIndex] = useState(0);

	const onSubmit = async ({id, ...objectField}: ObjectField) => {
		if (Liferay.FeatureFlags['LPS-163716']) {
			delete objectField.defaultValue;
		}

		delete objectField.listTypeDefinitionId;
		delete objectField.system;

		try {
			await API.save(
				`/o/object-admin/v1.0/object-fields/${id}`,
				objectField
			);

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

	const {
		errors,
		handleChange,
		handleSubmit,
		setValues,
		values,
	} = useObjectFieldForm({
		forbiddenChars,
		forbiddenLastChars,
		forbiddenNames,
		initialValues,
		onSubmit,
	});

	if (
		(Liferay.FeatureFlags['LPS-170122'] ||
			(Liferay.FeatureFlags['LPS-163716'] &&
				values.businessType === 'Picklist')) &&
		TABS.length < 2
	) {
		TABS.push(Liferay.Language.get('advanced'));
	}

	useEffect(() => {
		const makeFetch = async () => {
			const objectFieldResponse = await API.getObjectField(objectFieldId);

			setValues(objectFieldResponse);
		};

		makeFetch();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [objectFieldId]);

	return (
		<SidePanelForm
			className="lfr-objects__edit-object-field"
			onSubmit={handleSubmit}
			readOnly={readOnly}
			title={Liferay.Language.get('field')}
		>
			{Liferay.FeatureFlags['LPS-170122'] ||
			(Liferay.FeatureFlags['LPS-163716'] &&
				values.businessType === 'Picklist') ? (
				<>
					<ClayTabs className="side-panel-iframe__tabs">
						{TABS.map((label, index) => (
							<ClayTabs.Item
								active={activeIndex === index}
								key={index}
								onClick={() => setActiveIndex(index)}
							>
								{label}
							</ClayTabs.Item>
						))}
					</ClayTabs>

					<ClayTabs.Content activeIndex={activeIndex} fade>
						<ClayTabs.TabPane>
							<BasicInfo
								creationLanguageId={creationLanguageId}
								errors={errors}
								filterOperators={filterOperators}
								handleChange={handleChange}
								isApproved={isApproved}
								isDefaultStorageType={isDefaultStorageType}
								objectDefinitionExternalReferenceCode={
									objectDefinitionExternalReferenceCode
								}
								objectFieldTypes={objectFieldTypes}
								objectName={objectName}
								objectRelationshipId={objectRelationshipId}
								readOnly={readOnly}
								setValues={setValues}
								values={values}
								workflowStatusJSONArray={
									workflowStatusJSONArray
								}
							/>
						</ClayTabs.TabPane>

						<ClayTabs.TabPane>
							<AdvancedTab
								creationLanguageId={creationLanguageId}
								errors={errors}
								readOnlySidebarElements={
									readOnlySidebarElements
								}
								setValues={setValues}
								sidebarElements={sidebarElements}
								values={values}
							/>
						</ClayTabs.TabPane>
					</ClayTabs.Content>
				</>
			) : (
				<BasicInfo
					creationLanguageId={creationLanguageId}
					errors={errors}
					filterOperators={filterOperators}
					handleChange={handleChange}
					isApproved={isApproved}
					isDefaultStorageType={isDefaultStorageType}
					objectDefinitionExternalReferenceCode={
						objectDefinitionExternalReferenceCode
					}
					objectFieldTypes={objectFieldTypes}
					objectName={objectName}
					objectRelationshipId={objectRelationshipId}
					readOnly={readOnly}
					setValues={setValues}
					values={values}
					workflowStatusJSONArray={workflowStatusJSONArray}
				/>
			)}
		</SidePanelForm>
	);
}
