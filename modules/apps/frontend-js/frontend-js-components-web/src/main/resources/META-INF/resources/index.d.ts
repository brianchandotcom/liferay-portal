/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactElement} from 'react';

import type {Atom} from '@liferay/frontend-js-state-web';

export const activeLanguageIdsAtom: Atom<any>;

export {default as ClientExtension} from './ClientExtension';

export {default as ManagementToolbar} from './management_toolbar/ManagementToolbar';

export {default as Treeview} from './treeview/Treeview';

export function TranslationAdminModal(
	activeLanguageIds: string[],
	arialLabels: {
		default: string;
		manageTranslations: string;
		managementToolbar: string;
		notTranslated: string;
		tranlated: string;
	},
	availableLocales: object[],
	defaultLanguageId: string,
	onActiveLanguageIdsChange: () => void,
	translations: object,
	visible: boolean
): ReactElement;

export function TranslationAdminSelector(
	activeLanguageIds: string[],
	adminMode: boolean,
	ariaLabels: {
		default: string;
		manageTranslations: string;
		managementToolbar: string;
		notTranslated: string;
		tranlated: string;
	},
	availableLocales: object[],
	defaultLanguageId: string,
	showOnlyFlags: boolean,
	small: boolean,
	translations: object
): ReactElement;
