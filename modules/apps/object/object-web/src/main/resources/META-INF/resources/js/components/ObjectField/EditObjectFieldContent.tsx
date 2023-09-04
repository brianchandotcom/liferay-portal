/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTabs from '@clayui/tabs';
import classNames from 'classnames';
import React, {ElementType, useState} from 'react';

import {EditObjectFieldProps} from './EditObjectField';
import {ObjectFieldErrors} from './ObjectFieldFormBase';
import {AdvancedTab} from './Tabs/Advanced/AdvancedTab';
import {BasicInfoTab} from './Tabs/BasicInfo/BasicInfoTab';

import './EditObjectFieldContent.scss';

interface EditObjectFieldContentProps
	extends Omit<
		EditObjectFieldProps,
		| 'forbiddenChars'
		| 'forbiddenLastChars'
		| 'forbiddenNames'
		| 'objectFieldId'
	> {
	containerWrapper: ElementType;
	errors: ObjectFieldErrors;
	handleChange: React.ChangeEventHandler<HTMLInputElement>;
	modelBuilder?: boolean;
	setValues: (values: Partial<ObjectField>) => void;
	values: Partial<ObjectField>;
}

const TABS = [Liferay.Language.get('basic-info')];

export function EditObjectFieldContent({
	containerWrapper,
	creationLanguageId,
	errors,
	filterOperators,
	handleChange,
	isApproved,
	isDefaultStorageType,
	learnResources,
	modelBuilder = false,
	objectDefinitionExternalReferenceCode,
	objectFieldTypes,
	objectName,
	objectRelationshipId,
	readOnly,
	readOnlySidebarElements,
	setValues,
	sidebarElements,
	values,
	workflowStatusJSONArray,
}: EditObjectFieldContentProps) {
	const [activeIndex, setActiveIndex] = useState(0);

	if (
		(Liferay.FeatureFlags['LPS-170122'] ||
			values.businessType === 'Picklist') &&
		TABS.length < 2
	) {
		TABS.push(Liferay.Language.get('advanced'));
	}

	return (
		<>
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
						<ClayTabs.TabPane
							className={classNames({
								'lfr-objects__edit-object-field-content-panel': modelBuilder,
							})}
						>
							<BasicInfoTab
								containerWrapper={containerWrapper}
								errors={errors}
								filterOperators={filterOperators}
								handleChange={handleChange}
								isApproved={isApproved}
								isDefaultStorageType={isDefaultStorageType}
								modelBuilder={modelBuilder}
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

						<ClayTabs.TabPane
							className={classNames({
								'lfr-objects__edit-object-field-content-panel': modelBuilder,
							})}
						>
							<AdvancedTab
								containerWrapper={containerWrapper}
								creationLanguageId={creationLanguageId}
								errors={errors}
								isDefaultStorageType={isDefaultStorageType}
								learnResources={learnResources}
								modelBuilder={modelBuilder}
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
					containerWrapper={containerWrapper}
					errors={errors}
					filterOperators={filterOperators}
					handleChange={handleChange}
					isApproved={isApproved}
					isDefaultStorageType={isDefaultStorageType}
					modelBuilder={modelBuilder}
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
		</>
	);
}
