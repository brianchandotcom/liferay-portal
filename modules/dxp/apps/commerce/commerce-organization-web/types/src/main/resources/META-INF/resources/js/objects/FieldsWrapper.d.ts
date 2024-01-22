/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

declare const FieldsWrapper: ({
	mode,
	namespace,
	objectData,
	objectExternalReferenceCode,
	onObjectDataChange,
	onObjectDefinitionLoad,
}: TFieldsWrapperProps) => JSX.Element;
export declare type TGenericFieldProps = {
	onChange({
		hasError,
		name,
		value,
	}: {
		hasError: boolean;
		name: string;
		value: boolean | null | number | string;
	}): void;
	disabled?: boolean;
	id: string | number;
	label: string;
	mode?: string;
	name: string;
	namespace: string;
	originalField: TObjectField;
	readOnly?: boolean;
	required?: boolean;
	specificProps?: any;
	value?: any;
};
declare type TFieldsWrapperProps = {
	onObjectDataChange?({data, hasError}: TOnObjectDataChange): void;
	onObjectDefinitionLoad?({data}: {data: TObjectField[]}): void;
	mode?: string;
	namespace: string;
	objectData: {
		[key: string]: any;
	};
	objectExternalReferenceCode: string;
};
export declare type TOnObjectDataChange = {
	data: any;
	hasError: boolean;
	name: string;
};
declare type TObjectField = {
	businessType: string;
	externalReferenceCode: string;
	id: string;
	label: {
		[key: string]: string;
	};
	listTypeDefinitionId: number;
	localized: boolean;
	name: string;
	objectFieldSettings: [];
	readOnly: string;
	readOnlyConditionExpression: string;
	required: boolean;
	system: boolean;
};
export default FieldsWrapper;
