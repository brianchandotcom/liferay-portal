/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export interface Color {
	disabled?: boolean;
	label: string;
	name: string;
	value: string;
}

export type ColorCategory = Record<string, Color[]>;

export type ColorCategoryMap = Record<string, ColorCategory>;

export type Field = {
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
};

export type Token = {
	editorType: string;
	label: string;
	name: string;
	tokenCategoryLabel: string;
	tokenSetLabel: string;
	value: string;
};
