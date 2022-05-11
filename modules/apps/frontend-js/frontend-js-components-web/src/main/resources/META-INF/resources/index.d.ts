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

import {ReactElement} from 'react';

import type {Atom} from '@liferay/frontend-js-state-web';

export const activeLanguageIdsAtom: Atom<any>;

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
