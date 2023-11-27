/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SidebarCategory} from '@liferay/object-js-components-web';
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
	baseResourceURL: string;
	containerWrapper: ElementType;
	errors: ObjectFieldErrors;
	filterOperators: TFilterOperators;
	handleChange: React.ChangeEventHandler<HTMLInputElement>;
	isApproved: boolean;
	isDefaultStorageType: boolean;
	modelBuilder?: boolean;
	objectDefinitionExternalReferenceCode: string;
	objectFieldTypes: ObjectFieldType[];
	objectRelationshipId: number;
	onSubmit?: (editedObjectField?: Partial<ObjectField>) => void;
	readOnly: boolean;
	setValues: (values: Partial<ObjectField>) => void;
	sidebarElements: SidebarCategory[];
	values: Partial<ObjectField>;
	workflowStatuses: LabelValueObject[];
}
export declare function BasicInfoTab({
	baseResourceURL,
	containerWrapper: ContainerWrapper,
	errors,
	filterOperators,
	handleChange,
	isApproved,
	isDefaultStorageType,
	modelBuilder,
	objectDefinitionExternalReferenceCode,
	objectFieldTypes,
	objectRelationshipId,
	onSubmit,
	readOnly,
	setValues,
	sidebarElements,
	values,
	workflowStatuses,
}: BasicInfoTabProps): JSX.Element;
export {};
