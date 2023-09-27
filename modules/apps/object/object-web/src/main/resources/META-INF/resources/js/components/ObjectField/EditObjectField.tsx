/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import {BasicInfoTab} from './Tabs/BasicInfo/BasicInfoTab';
import {useObjectFieldForm} from './useObjectFieldForm';

interface EditObjectFieldProps {
	creationLanguageId: Liferay.Language.Locale;
	filterOperators: TFilterOperators;
	forbiddenChars: string[];
	forbiddenLastChars: string[];
	forbiddenNames: string[];
	isApproved: boolean;
	isDefaultStorageType: boolean;
	learnResources: object;
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
	learnResources,
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
			values.businessType === 'Picklist') &&
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
			{(Liferay.FeatureFlags['LPS-170122'] && isDefaultStorageType) ||
			values.businessType === 'Picklist' ? (
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
							<BasicInfoTab
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
								isDefaultStorageType={isDefaultStorageType}
								learnResources={learnResources}
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
				<BasicInfoTab
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
