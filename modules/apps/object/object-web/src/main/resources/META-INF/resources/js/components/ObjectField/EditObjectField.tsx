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
import {SidePanelForm} from '@liferay/object-js-components-web';
import React, {useState} from 'react';

import './EditObjectField.scss';
import {BasicInfo} from './Tabs/BasicInfo/BasicInfo';

interface EditObjectFieldProps {
	filterOperators: TFilterOperators;
	forbiddenChars: string[];
	forbiddenLastChars: string[];
	forbiddenNames: string[];
	isApproved: boolean;
	isDefaultStorageType: boolean;
	objectDefinitionId: number;
	objectField: ObjectField;
	objectFieldTypes: ObjectFieldType[];
	objectName: string;
	objectRelationshipId: number;
	readOnly: boolean;
	workflowStatusJSONArray: LabelValueObject[];
}

const TABS = [
	Liferay.Language.get('basic-info'),
	Liferay.Language.get('advanced'),
];

export default function EditObjectField({
	filterOperators,
	forbiddenChars,
	forbiddenLastChars,
	forbiddenNames,
	isApproved,
	isDefaultStorageType,
	objectDefinitionId,
	objectField,
	objectFieldTypes,
	objectName,
	objectRelationshipId,
	readOnly,
	workflowStatusJSONArray,
}: EditObjectFieldProps) {
	const [activeIndex, setActiveIndex] = useState(0);

	return (
		<SidePanelForm
			className="lfr-objects__edit-object-field"
			onSubmit={() => {}}
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
						filterOperators={filterOperators}
						forbiddenChars={forbiddenChars}
						forbiddenLastChars={forbiddenLastChars}
						forbiddenNames={forbiddenNames}
						isApproved={isApproved}
						isDefaultStorageType={isDefaultStorageType}
						objectDefinitionId={objectDefinitionId}
						objectField={objectField}
						objectFieldTypes={objectFieldTypes}
						objectName={objectName}
						objectRelationshipId={objectRelationshipId}
						readOnly={readOnly}
						workflowStatusJSONArray={workflowStatusJSONArray}
					/>
				</ClayTabs.TabPane>

				<ClayTabs.TabPane>
					{/* TODO Advanced TAB Component */}
				</ClayTabs.TabPane>
			</ClayTabs.Content>
		</SidePanelForm>
	);
}
