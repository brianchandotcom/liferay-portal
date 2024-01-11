/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SidebarCategory} from '@liferay/object-js-components-web';
import {ILearnResourceContext} from 'frontend-js-components-web';
import React, {ElementType} from 'react';

import {ObjectFieldErrors} from '../../ObjectFieldFormBase';
import {DefaultValueContainer} from './DefaultValueContainer';
import {ReadOnlyContainer} from './ReadOnlyContainer';

interface AdvancedTabProps {
	containerWrapper: ElementType;
	creationLanguageId: Liferay.Language.Locale;
	errors: ObjectFieldErrors;
	isDefaultStorageType: boolean;
	isRootDescendantNode: boolean;
	learnResources: ILearnResourceContext;
	modelBuilder?: boolean;
	onSubmit?: () => void;
	readOnlySidebarElements: SidebarCategory[];
	setValues: (value: Partial<ObjectField>) => void;
	sidebarElements: SidebarCategory[];
	values: Partial<ObjectField>;
}

export function AdvancedTab({
	containerWrapper: ContainerWrapper,
	creationLanguageId,
	errors,
	isDefaultStorageType,
	isRootDescendantNode,
	learnResources,
	modelBuilder = false,
	onSubmit,
	readOnlySidebarElements,
	setValues,
	sidebarElements,
	values,
}: AdvancedTabProps) {
	const disabledReadyOnly =
		values.businessType === 'Aggregation' ||
		values.businessType === 'AutoIncrement' ||
		values.businessType === 'Formula' ||
		(values.businessType === 'Relationship' && isRootDescendantNode) ||
		values.required ||
		values.system;

	return (
		<>
			{isDefaultStorageType && (
				<ContainerWrapper
					collapsable
					defaultExpanded
					disabled={disabledReadyOnly}
					displayTitle={Liferay.Language.get('read-only')}
					displayType="unstyled"
					title={Liferay.Language.get('read-only')}
				>
					<ReadOnlyContainer
						disabled={disabledReadyOnly}
						modelBuilder={modelBuilder}
						onSubmit={onSubmit}
						readOnlySidebarElements={readOnlySidebarElements}
						requiredField={values.required as boolean}
						setValues={setValues}
						values={values}
					/>
				</ContainerWrapper>
			)}

			{values.businessType === 'Picklist' && (
				<ContainerWrapper
					collapsable
					defaultExpanded
					disabled={false}
					displayTitle={Liferay.Language.get('default-value')}
					displayType="unstyled"
					title={Liferay.Language.get('default-value')}
				>
					<DefaultValueContainer
						creationLanguageId={creationLanguageId}
						errors={errors}
						learnResources={learnResources}
						modelBuilder={modelBuilder}
						onSubmit={onSubmit}
						setValues={setValues}
						sidebarElements={sidebarElements}
						values={values}
					/>
				</ContainerWrapper>
			)}
		</>
	);
}
