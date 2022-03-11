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

import {TYPES} from './context';

export type TName = {
	[key: string]: string;
};

export type TObjectField = {
	checked: boolean;
	filtered?: boolean | undefined;
	id: number;
	indexed: boolean;
	indexedAsKeyword: boolean;
	indexedLanguageId: string;
	label: TName;
	listTypeDefinitionId: boolean;
	name: string;
	required: boolean;
	type: string;
};

export type TObjectViewColumn = {
	fieldLabel: string;
	isDefaultSort: boolean;
	label: TName;
	objectFieldName: string;
	priority?: number;
};

export type TObjectViewSortColumn = {
	fieldLabel: string;
	label: TName;
	objectFieldName: string;
	priority?: number;
	sortOrder?: string;
};

export type TObjectView = {
	defaultObjectView: boolean;
	name: TName;
	objectViewColumns: TObjectViewColumn[];
	objectViewSortColumns: TObjectViewSortColumn[];
};

export type TState = {
	isFFObjectViewSortColumnConfigurationEnabled: boolean;
	isViewOnly: boolean;
	objectFields: TObjectField[];
	objectView: TObjectView;
	objectViewId: string;
};

export type TAction = {
	payload: {[key: string]: any};
	type: keyof typeof TYPES;
};
