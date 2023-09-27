/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {Translations} from './TranslationAdminContent';
interface IProps extends Translations {
	onClose: (languageIds: string[]) => void;
	visible?: boolean;
}
export default function TranslationAdminModal({
	activeLanguageIds: initialActiveLanguageIds,
	ariaLabels,
	availableLocales,
	defaultLanguageId,
	onClose,
	translations,
	visible: initialVisible,
}: IProps): JSX.Element;
export {};
