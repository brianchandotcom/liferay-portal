/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LayoutDataItemType} from '../../app/config/constants/layoutDataItemTypes';

export type ResponsiveConfig<T> = T & {
	landscapeMobile: {[Key in keyof T]?: T[Key]};
	portraitMobile: {[Key in keyof T]?: T[Key]};
	tablet: {[Key in keyof T]?: T[Key]};
};

export type LanguageId = Liferay.Language.Locale;

export type TranslatedConfig<T> = Record<LanguageId, T>;

export type BackgroundImage = {classPK: string; url: string};

interface BaseCommonStyles {
	cssClasses?: string[];
	customCSS?: string;
	fontSize?: string;
	paddingBottom?: string;
	paddingLeft?: string;
	paddingRight?: string;
	paddingTop?: string;
	styles?: {
		backgroundImage?: {classPK?: string};
		display?: 'none' | 'block';
		height?: string;
		maxHeight?: string;
		maxWidth?: string;
		minHeight?: string;
		minWidth?: string;
		overflow?: 'visible';
		width?: string;
	};
	textColor?: string;
}

export type CommonStyles = ResponsiveConfig<BaseCommonStyles>;

export interface BaseLayoutDataItem<
	ItemType extends LayoutDataItemType,
	Config,
> {
	children: string[];
	config: Config & {name?: string};
	itemId: string;
	parentId: string;
	type: ItemType;
}
