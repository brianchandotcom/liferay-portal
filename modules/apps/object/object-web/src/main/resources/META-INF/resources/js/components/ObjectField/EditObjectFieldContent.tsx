/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTabs from '@clayui/tabs';
import {SidebarCategory} from '@liferay/object-js-components-web';
import classNames from 'classnames';
import {createResourceURL, fetch} from 'frontend-js-web';
import React, {ElementType, useEffect, useState} from 'react';

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
	onSubmit?: (editedObjectField?: Partial<ObjectField>) => void;
	setValues: (values: Partial<ObjectField>) => void;
	values: Partial<ObjectField>;
}

const TABS = [Liferay.Language.get('basic-info')];

export function EditObjectFieldContent({
	baseResourceURL,
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
	onSubmit,
	readOnly,
	setValues,
	values,
	workflowStatuses,
}: EditObjectFieldContentProps) {
	const [activeIndex, setActiveIndex] = useState(0);
	const [objectFieldTypes, setObjectFieldTypes] = useState<ObjectFieldType[]>(
		[]
	);
	const [objectRelationshipId, setObjectRelationshipId] = useState(0);
	const [readOnlySidebarElements, setReadOnlySidebarElements] = useState<
		SidebarCategory[]
	>([]);
	const [sidebarElements, setSidebarElements] = useState<SidebarCategory[]>(
		[]
	);

	if (
		(isDefaultStorageType || values.businessType === 'Picklist') &&
		TABS.length < 2
	) {
		TABS.push(Liferay.Language.get('advanced'));
	}

	useEffect(() => {
		const makeFetch = async () => {
			if (values.id !== 0) {
				const url = createResourceURL(baseResourceURL, {
					objectFieldId: values?.id,
					p_p_resource_id:
						'/object_definitions/get_object_field_info',
				}).href;

				const objectFieldInfoResponse = await fetch(url, {
					method: 'GET',
				});

				const objectFieldInfoJSON = (await objectFieldInfoResponse.json()) as {
					objectFieldTypes: ObjectFieldType[];
					objectRelationshipId: number;
					readOnlySidebarElements: SidebarCategory[];
					sidebarElements: SidebarCategory[];
				};

				if (values.businessType === 'Relationship') {
					setObjectRelationshipId(
						objectFieldInfoJSON.objectRelationshipId
					);
				}

				setObjectFieldTypes(objectFieldInfoJSON.objectFieldTypes);
				setReadOnlySidebarElements(
					objectFieldInfoJSON.readOnlySidebarElements
				);
				setSidebarElements(objectFieldInfoJSON.sidebarElements);
			}
		};

		makeFetch();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [baseResourceURL, values.id]);

	return (
		<>
			{isDefaultStorageType || values.businessType === 'Picklist' ? (
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
								baseResourceURL={baseResourceURL}
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
								objectRelationshipId={objectRelationshipId}
								onSubmit={onSubmit}
								readOnly={readOnly}
								setValues={setValues}
								sidebarElements={sidebarElements}
								values={values}
								workflowStatuses={workflowStatuses}
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
								onSubmit={onSubmit}
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
					baseResourceURL={baseResourceURL}
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
					objectRelationshipId={objectRelationshipId}
					onSubmit={onSubmit}
					readOnly={readOnly}
					setValues={setValues}
					sidebarElements={sidebarElements}
					values={values}
					workflowStatuses={workflowStatuses}
				/>
			)}
		</>
	);
}
