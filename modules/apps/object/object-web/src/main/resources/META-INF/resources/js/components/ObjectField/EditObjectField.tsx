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
	openToast,
	saveAndReload,
} from '@liferay/object-js-components-web';
import React, {useState} from 'react';

import './EditObjectField.scss';
import {AdvancedTab} from './Tabs/Advanced/AdvancedTab';
import {BasicInfo} from './Tabs/BasicInfo/BasicInfo';
import {useObjectFieldForm} from './useObjectFieldForm';

interface EditObjectFieldProps {
	filterOperators: TFilterOperators;
	forbiddenChars: string[];
	forbiddenLastChars: string[];
	forbiddenNames: string[];
	isApproved: boolean;
	isDefaultStorageType: boolean;
	objectDefinitionExternalReferenceCode: string;
	objectField: ObjectField;
	objectFieldTypes: ObjectFieldType[];
	objectName: string;
	objectRelationshipId: number;
	readOnly: boolean;
	workflowStatusJSONArray: LabelValueObject[];
}

const TABS = [Liferay.Language.get('basic-info')];

export default function EditObjectField({
	filterOperators,
	forbiddenChars,
	forbiddenLastChars,
	forbiddenNames,
	isApproved,
	isDefaultStorageType,
	objectDefinitionExternalReferenceCode,
	objectField,
	objectFieldTypes,
	objectName,
	objectRelationshipId,
	readOnly,
	workflowStatusJSONArray,
}: EditObjectFieldProps) {
	const [activeIndex, setActiveIndex] = useState(0);

	const onSubmit = async ({id, ...objectField}: ObjectField) => {
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
		initialValues: objectField,
		onSubmit,
	});

	if (Liferay.FeatureFlags['LPS-159913'] && TABS.length < 2) {
		TABS.push(Liferay.Language.get('advanced'));
	}

	return (
		<SidePanelForm
			className="lfr-objects__edit-object-field"
			onSubmit={handleSubmit}
			readOnly={readOnly}
			title={Liferay.Language.get('field')}
		>
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
				</ClayTabs.TabPane>

				{Liferay.FeatureFlags['LPS-159913'] ? (
					<ClayTabs.TabPane>
						<AdvancedTab setValues={setValues} values={values} />
					</ClayTabs.TabPane>
				) : (
					<></>
				)}
			</ClayTabs.Content>
		</SidePanelForm>
	);
}
