/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

declare type Errors = {
	errorMessage?: string;
	fields?: Record<string, string>;
};
declare type Field = {
	defaultValue: string;
	label: string;
	name: string;
	value: string;
};
declare type FieldsProps = {
	errors: Errors;
	fields: Field[];
	url: string;
};
export default function SeparatorFields({
	errors,
	fields,
	url,
}: FieldsProps): JSX.Element;
declare type FieldProps = {
	errors: Errors;
	field: Field;
	url: string;
};
declare function Field({errors, field, url}: FieldProps): JSX.Element;
export {};
