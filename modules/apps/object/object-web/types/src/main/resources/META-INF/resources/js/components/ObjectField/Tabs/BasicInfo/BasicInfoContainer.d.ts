/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {ObjectFieldErrors} from '../../ObjectFieldFormBase';
import {AggregationFilters} from './BasicInfoTab';
import '../../EditObjectFieldContent.scss';
interface BasicInfoContainerProps {
	baseResourceURL: string;
	creationLanguageId2?: Liferay.Language.Locale;
	errors: ObjectFieldErrors;
	handleChange: React.ChangeEventHandler<HTMLInputElement>;
	isApproved: boolean;
	modelBuilder?: boolean;
	objectDefinition: Partial<ObjectDefinition>;
	objectDefinitionExternalReferenceCode: string;
	objectDefinitionName: string;
	objectFieldTypes: ObjectFieldType[];
	objectRelationshipId: number;
	onSubmit?: () => void;
	readOnly: boolean;
	setAggregationFilters: (values: AggregationFilters[]) => void;
	setObjectDefinitionExternalReferenceCode2: (value: string) => void;
	setValues: (values: Partial<ObjectField>) => void;
	values: Partial<ObjectField>;
}
export declare function BasicInfoContainer({
	baseResourceURL,
	creationLanguageId2,
	errors,
	handleChange,
	isApproved,
	modelBuilder,
	objectDefinition,
	objectDefinitionExternalReferenceCode,
	objectDefinitionName,
	objectFieldTypes,
	objectRelationshipId,
	onSubmit,
	readOnly,
	setAggregationFilters,
	setObjectDefinitionExternalReferenceCode2,
	setValues,
	values,
}: BasicInfoContainerProps): JSX.Element;
export {};
