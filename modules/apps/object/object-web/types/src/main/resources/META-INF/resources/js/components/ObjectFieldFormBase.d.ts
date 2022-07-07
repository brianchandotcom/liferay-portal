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

import {FormError} from '@liferay/object-js-components-web';
import React, {ChangeEventHandler, ReactNode} from 'react';
import './ObjectFieldFormBase.scss';
export default function ObjectFieldFormBase({
	children,
	disabled,
	editingField,
	errors,
	handleChange,
	objectDefinitionId,
	objectField: values,
	objectFieldTypes,
	objectName,
	onAggregationFilterChange,
	onRelationshipChange,
	setValues,
}: IProps): JSX.Element;
export declare function useObjectFieldForm({
	forbiddenChars,
	forbiddenLastChars,
	forbiddenNames,
	initialValues,
	onSubmit,
}: IUseObjectFieldForm): {
	errors: FormError<
		ObjectField & {
			function: any;
			acceptedFileExtensions: any;
			fileSource: any;
			maximumFileSize: any;
			maxLength: any;
			showCounter: any;
			showFilesInDocumentsAndMedia: any;
			storageDLFolderPath: any;
			relationship: any;
			summarizeField: any;
			filters: any;
			stateFlow: any;
		}
	>;
	handleChange: React.ChangeEventHandler<HTMLInputElement>;
	handleSubmit: React.FormEventHandler<HTMLFormElement>;
	setValues: (values: Partial<ObjectField>) => void;
	values: Partial<ObjectField>;
};
interface IUseObjectFieldForm {
	forbiddenChars?: string[];
	forbiddenLastChars?: string[];
	forbiddenNames?: string[];
	initialValues: Partial<ObjectField>;
	onSubmit: (field: ObjectField) => void;
}
interface IProps {
	children?: ReactNode;
	disabled?: boolean;
	editingField?: boolean;
	errors: ObjectFieldErrors;
	handleChange: ChangeEventHandler<HTMLInputElement>;
	objectDefinitionId: number;
	objectField: Partial<ObjectField>;
	objectFieldTypes: ObjectFieldType[];
	objectName: string;
	onAggregationFilterChange?: (aggregationFilterArray: []) => void;
	onRelationshipChange?: (objectDefinitionId2: number) => void;
	setValues: (values: Partial<ObjectField>) => void;
}
export declare type ObjectFieldErrors = FormError<
	ObjectField &
		{
			[key in ObjectFieldSettingName]: any;
		}
>;
export {};
