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

import {ReactNode} from 'react';
import {SidebarCategory} from './Sidebar';
import './CodeEditorLocalized.scss';
interface CodeEditorLocalizedProps {
	CustomSidebarContent?: ReactNode;
	ariaLabels?: {
		default: string;
		openLocalizations: string;
		translated: string;
		untranslated: string;
	};
	mode?: string;
	onSelectedLocaleChange: (val: IItem) => void;
	onTranslationsChange: (val: LocalizedValue<string>) => void;
	placeholder?: string;
	selectedLocale: Locale;
	sidebarElements: SidebarCategory[];
	translations: LocalizedValue<string>;
}
interface IItem {
	label: Locale;
	symbol: string;
}
export declare function CodeEditorLocalized({
	CustomSidebarContent,
	ariaLabels,
	mode,
	onSelectedLocaleChange,
	onTranslationsChange,
	placeholder,
	selectedLocale,
	sidebarElements,
	translations,
}: CodeEditorLocalizedProps): JSX.Element;
export {};
