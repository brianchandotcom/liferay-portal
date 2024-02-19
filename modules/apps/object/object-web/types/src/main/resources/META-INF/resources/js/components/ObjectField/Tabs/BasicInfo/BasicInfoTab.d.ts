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
	dbObjectFieldRequired?: boolean;
	errors: ObjectFieldErrors;
	filterOperators: TFilterOperators;
	handleChange: React.ChangeEventHandler<HTMLInputElement>;
	isDefaultStorageType: boolean;
	modelBuilder?: boolean;
	objectDefinition?: ObjectDefinition;
	objectFieldBusinessTypes: ObjectFieldBusinessType[];
	objectRelationshipId: number;
	onSubmit?: (editedObjectField?: Partial<ObjectField>) => void;
	readOnly: boolean;
	setDbObjectFieldRequired?: (value: boolean) => void;
	setValues: (values: Partial<ObjectField>) => void;
	sidebarElements: SidebarCategory[];
	values: Partial<ObjectField>;
	workflowStatuses: LabelValueObject[];
}
export declare function BasicInfoTab({
	baseResourceURL,
	containerWrapper: ContainerWrapper,
	dbObjectFieldRequired,
	errors,
	filterOperators,
	handleChange,
	isDefaultStorageType,
	modelBuilder,
	objectDefinition,
	objectFieldBusinessTypes,
	objectRelationshipId,
	onSubmit,
	readOnly,
	setDbObjectFieldRequired,
	setValues,
	sidebarElements,
	values,
	workflowStatuses,
}: BasicInfoTabProps): JSX.Element;
export {};
