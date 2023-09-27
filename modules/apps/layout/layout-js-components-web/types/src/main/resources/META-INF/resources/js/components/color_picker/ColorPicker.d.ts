/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import './ColorPicker.scss';
export declare const DEFAULT_TOKEN_LABEL: string;
export interface Token {
	editorType: string;
	label: string;
	name: string;
	tokenCategoryLabel: string;
	tokenSetLabel: string;
	value: string;
}
export interface Field {
	cssProperty?: string;
	dataType?: string;
	defaultValue?: string;
	description?: string;
	icon?: string;
	inherited?: boolean;
	label: string;
	name: string;
	type?: string;
	typeOptions?: {
		showLengthField?: boolean;
	};
	value?: string;
}
interface Props {
	activeItemId?: string;
	canDetachTokenValues?: boolean;
	defaultTokenLabel?: string;
	defaultTokenValue?: string;
	editedTokenValues: Record<string, Token>;
	field: Field;
	onValueSelect: (fieldName: string, value: string) => void;
	restoreButtonLabel?: string;
	showLabel?: boolean;
	tokenValues: Record<string, Token>;
	value: string;
}
export default function ColorPicker({
	activeItemId,
	canDetachTokenValues,
	defaultTokenLabel,
	defaultTokenValue,
	editedTokenValues,
	field,
	onValueSelect,
	showLabel,
	tokenValues,
	restoreButtonLabel,
	value,
}: Props): JSX.Element;
export {};
