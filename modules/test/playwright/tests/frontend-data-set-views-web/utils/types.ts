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
