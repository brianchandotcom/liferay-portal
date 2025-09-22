/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import LoadingIndicator from '@clayui/loading-indicator';
import React from 'react';

export const COMPLETED = 'COMPLETED';
export const FAILED = 'FAILED';
export const STARTED = 'STARTED';

export interface IBulkAction {
	DeleteBulkAction: {};
	KeywordBulkAction: {
		keywords: string[];
	};
	MoveBulkAction: {
		objectEntryFolderId: number;
	};
	PermissionBulkAction: {
		permission: IBulkActionPermission[];
	};
	StatusBulkAction: {
		status: number;
	};
	TaxonomyCategoryBulkAction: {
		taxonomyCategoryIds: number[];
	};
}

interface IBulkActionItem {
	classExternalReferenceCode: string;
	className: string;
	classPK: number;
	name: string;
}

interface IBulkActionBaseBody {
	bulkActionItems: IBulkActionItem[];
	filters?: [];
	searchQuery?: string;
	selectAll: boolean;
}

export type BulkActionDataDTO = {
	keywords?: string[];
	objectEntryFolderId?: number;
	permission?: IBulkActionPermission[];
	status?: string;
	taxonomyCategoryIds?: number[];
};

export interface IBulkActionPermission {
	actionIds: string;
	roleExternalReferenceCode: string;
	roleName: string;
	roleType: string;
}

export interface IBulkActionSelectedData {
	filter: [];
	items: IBulkActionSelectedDataItem[];
	keywords: [];
	objectEntryFolderId: number;
	permission: IBulkActionPermission[];
	searchQuery: string;
	selectAll: boolean;
	status: number;
	taxonomyCategoryIds: [];
	type: ActionId;
}

export interface IBulkActionSelectedDataItem {
	classExternalReferenceCode: string;
	className: string;
	classPK: number;
	name: string;
}

export interface IBulkActionTasks {
	actions: {};
	id: number;
	items: IBulkActionTaskItem[];
	lastPage: number;
	page: number;
	pageSize: number;
	totalCount: number;
}

export interface IBulkActionTaskItem {
	actionName: string;
	dateCreated: string;
	dateModified: string;
	executionStatus: string;
	id: number;
	numberOfItems: number;
	taskResult: string;
	totalCount: number;
}

export type ActionId = keyof IBulkAction;

type BulkActionPropByActionType<T extends keyof IBulkAction> = IBulkAction[T];

export type BulkActionPostDTO<T extends keyof IBulkAction> =
	IBulkActionBaseBody & {
		type: T;
	} & BulkActionPropByActionType<T>;

export const STATUS_PROPERTIES: Record<string, TStatusProperties> = {
	[COMPLETED]: {
		component: ClayIcon,
		icon: 'check-circle-full',
		iconClassName: 'text-success',
		label: 'completed',
		labelDisplayType: 'success',
		viewButtonClassName: 'border-success btn-xs text-success',
	},
	[FAILED]: {
		component: ClayIcon,
		icon: 'times-circle-full',
		iconClassName: 'text-danger',
		label: 'failed',
		labelDisplayType: 'danger',
		viewButtonClassName: 'border-danger btn-xs text-danger',
	},
	[STARTED]: {
		component: LoadingIndicator,
		displayType: 'primary',
		iconClassName: 'loading-animation',
		label: 'processing',
		labelDisplayType: 'info',
		viewButtonClassName: 'border-info btn-xs text-info',
	},
};

export type TStatusProperties = {
	component: React.ComponentType<any>;
	displayType?: string;
	icon?: string;
	iconClassName: string;
	label: string;
	labelDisplayType: 'danger' | 'info' | 'success';
	viewButtonClassName: string;
};
