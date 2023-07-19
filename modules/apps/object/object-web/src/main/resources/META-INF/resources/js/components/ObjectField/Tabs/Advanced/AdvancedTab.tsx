/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SidebarCategory} from '@liferay/object-js-components-web';
import React from 'react';

import {ObjectFieldErrors} from '../../ObjectFieldFormBase';
import {DefaultValueContainer} from './DefaultValueContainer';
import {ReadOnlyContainer} from './ReadOnlyContainer';

interface AdvancedTabProps {
	creationLanguageId: Liferay.Language.Locale;
	errors: ObjectFieldErrors;
	isDefaultStorageType: boolean;
	learnResources: object;
	readOnlySidebarElements: SidebarCategory[];
	setValues: (value: Partial<ObjectField>) => void;
	sidebarElements: SidebarCategory[];
	values: Partial<ObjectField>;
}

export function AdvancedTab({
	creationLanguageId,
	errors,
	isDefaultStorageType,
	learnResources,
	readOnlySidebarElements,
	setValues,
	sidebarElements,
	values,
}: AdvancedTabProps) {
	return (
		<>
			{Liferay.FeatureFlags['LPS-170122'] && isDefaultStorageType && (
				<ReadOnlyContainer
					disabled={
						values.system ||
						values.businessType === 'Aggregation' ||
						values.businessType === 'Formula'
					}
					readOnlySidebarElements={readOnlySidebarElements}
					requiredField={values.required as boolean}
					setValues={setValues}
					values={values}
				/>
			)}

			{values.businessType === 'Picklist' && (
				<DefaultValueContainer
					creationLanguageId={creationLanguageId}
					errors={errors}
					learnResources={learnResources}
					objectFieldBusinessType={
						values.businessType as ObjectFieldBusinessType
					}
					objectFieldSettings={
						values.objectFieldSettings as ObjectFieldSetting[]
					}
					setValues={setValues}
					sidebarElements={sidebarElements}
					values={values}
				/>
			)}
		</>
	);
}
