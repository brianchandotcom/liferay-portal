/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	selectedLocale: Liferay.Language.Locale;
	sidebarElements: SidebarCategory[];
	translations: LocalizedValue<string>;
}
interface IItem {
	label: Liferay.Language.Locale;
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
