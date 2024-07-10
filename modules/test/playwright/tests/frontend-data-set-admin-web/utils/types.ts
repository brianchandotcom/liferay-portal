/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
export type AsyncActionMethod = 'DELETE' | 'GET' | 'PATCH' | 'POST';
export type CreationActionTypes = 'link' | 'modal' | 'sidePanel';
export type ItemActionTypes =
	| 'async'
	| 'headless'
	| 'link'
	| 'modal'
	| 'sidePanel';
export type ModalVariantTypes = 'full-screen' | 'lg' | 'sm';
export type VisualizationMode = 'Cards' | 'List' | 'Table';
interface IBaseAction {
	icon: string;
	name: string;
	title?: string;
	url?: string;
	variant?: ModalVariantTypes;
}

export interface ICreationAction extends IBaseAction {
	type: CreationActionTypes;
}

export interface IItemAction extends IBaseAction {
	confirmationMessage?: string;
	method?: AsyncActionMethod;
	permissionKey?: string;
	type: ItemActionTypes;
}

interface IBaseFilter {
	filterBy: string;
	name: string;
}

export interface IDateRangeFilter extends IBaseFilter {
	from?: string;
	to?: string;
}

export interface ISelectionFilter extends IBaseFilter {
	filterMode?: 'Include' | 'Exclude';
	preselectedValues: string[];
	selectionType: 'Multiple' | 'Single';
}

export interface ISelectionFilterPicklist extends ISelectionFilter {
	source: string;
	sourceType: 'Object Picklist';
}

export interface ISelectionFilterApiHeadless extends ISelectionFilter {
	itemKey: string;
	itemLabel: string;
	restApplication: string;
	restEndpoint: string;
	restSchema: string;
	sourceType: 'API REST Application';
}

export interface IClientExtensionFilter extends IBaseFilter {
	clientExtension: string;
}

export interface IDateRangeFilter extends IBaseFilter {
	from?: string;
	to?: string;
}
