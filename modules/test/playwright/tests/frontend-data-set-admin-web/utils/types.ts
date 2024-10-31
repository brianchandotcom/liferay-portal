/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export enum EActionType {
	CREATION = 'creation',
	ITEM = 'item',
}

export enum ECreationActionTarget {
	LINK = 'link',
	MODAL = 'modal',
	SIDE_PANEL = 'sidePanel',
}

export enum EItemActionTarget {
	ASYNC = 'async',
	HEADLESS = 'headless',
	LINK = 'link',
	MODAL = 'modal',
	SIDE_PANEL = 'sidePanel',
}

export enum EAsyncActionMethod {
	DELETE = 'DELETE',
	GET = 'GET',
	PATCH = 'PATCH',
	POST = 'POST',
	PUT = 'PUT',
}

export enum EModalActionVariant {
	FULL_SCREEN = 'full-screen',
	LARGE = 'lg',
	SMALL = 'sm',
}

export enum EConfirmationMessageType {
	INFO = 'info',
	SECONDARY = 'secondary',
	SUCCESS = 'success',
	DANGER = 'danger',
	WARNING = 'warning',
}

export type VisualizationMode = 'Cards' | 'List' | 'Table';
interface IBaseAction {
	headlessActionKey?: string;
	icon: string;
	label: string;
	title?: string;
	url?: string;
	variant?: EModalActionVariant;
}

export interface ICreationAction extends IBaseAction {
	type: ECreationActionTarget;
}

export interface IItemAction extends IBaseAction {
	confirmationMessage?: string;
	confirmationMessageType?: string;
	errorStatusMessage?: string;
	method?: EAsyncActionMethod;
	requestBody?: string;
	successStatusMessage?: string;
	type: EItemActionTarget;
}

interface IBaseFilter {
	filterBy: string;
	name: string;
	useFieldSelectionModal?: boolean;
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
