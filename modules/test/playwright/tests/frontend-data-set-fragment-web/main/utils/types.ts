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

export type VisualizationMode = 'Cards' | 'List' | 'Table';
