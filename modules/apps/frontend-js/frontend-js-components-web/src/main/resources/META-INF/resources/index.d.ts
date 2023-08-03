/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactElement} from 'react';

import type {Atom} from '@liferay/frontend-js-state-web';
export type LocalizedValue<T> = Liferay.Language.LocalizedValue<T>;
export const activeLanguageIdsAtom: Atom<any>;

export {default as ClientExtension} from './ClientExtension';
export {default as FieldBase} from './forms/common/FieldBase';
export {default as FieldFeedback} from './forms/common/FieldFeedback';
export {default as InputLocalized} from './forms/input/InputLocalized';
export {
	default as LearnMessage,
	LearnResourcesContext,
} from './learn_message/LearnMessage';

export {default as useId} from './hooks/useId';
export {default as useSessionState} from './hooks/useSessionState';

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
