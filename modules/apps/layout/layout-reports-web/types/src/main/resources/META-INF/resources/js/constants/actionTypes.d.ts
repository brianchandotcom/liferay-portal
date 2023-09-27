/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LayoutReportsData, LayoutReportsIssues} from './types/LayoutReports';
import {SelectedItem} from './types/SelectedItem';
export declare const LOAD_DATA: 'LOAD_DATA';
export declare const SET_DATA: 'SET_DATA';
export declare const SET_ERROR: 'SET_ERROR';
export declare const SET_ISSUES: 'SET_ISSUES';
export declare const SET_LANGUAGE_ID: 'SET_LANGUAGE_ID';
export declare const SET_SELECTED_ITEM: 'SET_SELECTED_ITEM';
interface LoadDataAction {
	type: typeof LOAD_DATA;
}
interface SetDataAction {
	data: LayoutReportsData;
	loading: boolean;
	type: typeof SET_DATA;
}
interface SetErrorAction {
	error: string;
	type: typeof SET_ERROR;
}
interface SetIssuesAction {
	languageId: string;
	layoutReportsIssues: LayoutReportsIssues;
	type: typeof SET_ISSUES;
}
interface SetLanguageIdAction {
	languageId: string;
	type: typeof SET_LANGUAGE_ID;
}
interface SetSelectedItemAction {
	item: SelectedItem;
	type: typeof SET_SELECTED_ITEM;
}
export declare type Action =
	| LoadDataAction
	| SetDataAction
	| SetErrorAction
	| SetIssuesAction
	| SetLanguageIdAction
	| SetSelectedItemAction;
export {};
