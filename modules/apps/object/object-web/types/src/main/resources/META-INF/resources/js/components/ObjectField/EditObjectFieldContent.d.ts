/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {ElementType} from 'react';
import {EditObjectFieldProps} from './EditObjectField';
import {ObjectFieldErrors} from './ObjectFieldFormBase';
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
export declare function EditObjectFieldContent({
	containerWrapper,
	creationLanguageId,
	errors,
	filterOperators,
	handleChange,
	isApproved,
	isDefaultStorageType,
	learnResources,
	modelBuilder,
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
}: EditObjectFieldContentProps): JSX.Element;
export {};
