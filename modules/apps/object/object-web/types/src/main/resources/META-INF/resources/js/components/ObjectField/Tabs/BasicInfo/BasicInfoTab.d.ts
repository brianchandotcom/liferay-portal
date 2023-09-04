/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {ElementType} from 'react';
import {ObjectFieldErrors} from '../../ObjectFieldFormBase';
export interface AggregationFilters {
	defaultSort?: boolean;
	fieldLabel?: string;
	filterBy?: string;
	filterType?: string;
	label: LocalizedValue<string>;
	objectFieldBusinessType?: string;
	objectFieldName: string;
	priority?: number;
	sortOrder?: string;
	type?: string;
	value?: string;
	valueList?: LabelValueObject[];
}
interface BasicInfoTabProps {
	containerWrapper: ElementType;
	errors: ObjectFieldErrors;
	filterOperators: TFilterOperators;
	handleChange: React.ChangeEventHandler<HTMLInputElement>;
	isApproved: boolean;
	isDefaultStorageType: boolean;
	modelBuilder?: boolean;
	objectDefinitionExternalReferenceCode: string;
	objectFieldTypes: ObjectFieldType[];
	objectName: string;
	objectRelationshipId: number;
	readOnly: boolean;
	setValues: (values: Partial<ObjectField>) => void;
	values: Partial<ObjectField>;
	workflowStatusJSONArray: LabelValueObject[];
}
export declare function BasicInfoTab({
	containerWrapper: ContainerWrapper,
	errors,
	filterOperators,
	handleChange,
	isApproved,
	isDefaultStorageType,
	modelBuilder,
	objectDefinitionExternalReferenceCode,
	objectFieldTypes,
	objectName,
	objectRelationshipId,
	readOnly,
	setValues,
	values,
	workflowStatusJSONArray,
}: BasicInfoTabProps): JSX.Element;
export {};
