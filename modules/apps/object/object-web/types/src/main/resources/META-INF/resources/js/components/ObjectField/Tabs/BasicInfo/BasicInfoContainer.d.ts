/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {ObjectFieldErrors} from '../../ObjectFieldFormBase';
import {AggregationFilters} from './BasicInfoTab';
interface BasicInfoContainerProps {
	creationLanguageId2?: Liferay.Language.Locale;
	errors: ObjectFieldErrors;
	handleChange: React.ChangeEventHandler<HTMLInputElement>;
	isApproved: boolean;
	objectDefinitionExternalReferenceCode: string;
	objectFieldTypes: ObjectFieldType[];
	objectName: string;
	objectRelationshipId: number;
	readOnly: boolean;
	setAggregationFilters: (values: AggregationFilters[]) => void;
	setObjectDefinitionExternalReferenceCode2: (value: string) => void;
	setValues: (values: Partial<ObjectField>) => void;
	values: Partial<ObjectField>;
}
export declare function BasicInfoContainer({
	creationLanguageId2,
	errors,
	handleChange,
	isApproved,
	objectDefinitionExternalReferenceCode,
	objectFieldTypes,
	objectName,
	objectRelationshipId,
	readOnly,
	setAggregationFilters,
	setObjectDefinitionExternalReferenceCode2,
	setValues,
	values,
}: BasicInfoContainerProps): JSX.Element;
export {};
