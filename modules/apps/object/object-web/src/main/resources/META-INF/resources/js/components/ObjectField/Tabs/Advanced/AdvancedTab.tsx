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

import {SidebarCategory} from '@liferay/object-js-components-web';
import React from 'react';

import {ObjectFieldErrors} from '../../ObjectFieldFormBase';
import {DefaultValueContainer} from './DefaultValueContainer';
import {ReadOnlyContainer} from './ReadOnlyContainer';

interface AdvancedTabProps {
	creationLanguageId: Liferay.Language.Locale;
	errors: ObjectFieldErrors;
	readOnlySidebarElements: SidebarCategory[];
	setValues: (value: Partial<ObjectField>) => void;
	sidebarElements: SidebarCategory[];
	values: Partial<ObjectField>;
}

export function AdvancedTab({
	creationLanguageId,
	errors,
	readOnlySidebarElements,
	setValues,
	sidebarElements,
	values,
}: AdvancedTabProps) {
	return (
		<>
			{Liferay.FeatureFlags['LPS-170122'] && (
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

			{Liferay.FeatureFlags['LPS-163716'] &&
				values.businessType === 'Picklist' && (
					<DefaultValueContainer
						creationLanguageId={creationLanguageId}
						errors={errors}
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
