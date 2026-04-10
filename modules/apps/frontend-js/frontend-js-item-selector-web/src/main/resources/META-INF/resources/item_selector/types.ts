/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IItemSelectorModalProps} from './ItemSelectorModal';

export enum EEntityFieldType {
	COLLECTION = 'collection',
	DATE = 'date',
	DATE_TIME = 'date-time',
	INTEGER = 'integer',
	STRING = 'string',
}

export interface IBaseFilterConfig {
	entityFieldType: EEntityFieldType | `${EEntityFieldType}`;
	id: string;
	label: string;
	type: 'selection' | 'dateRange' | 'clientExtension';
}

export interface ISelectionFilterConfig extends IBaseFilterConfig {
	apiURL?: string;
	autocompleteEnabled?: boolean;
	itemKey?: string;
	itemLabel?: string;
	items?: Array<{label: string; value: string}>;
	multiple?: boolean;
	type: 'selection';
}

export interface IDateRangeFilterConfig extends IBaseFilterConfig {
	type: 'dateRange';
}

export type TFilterConfig = ISelectionFilterConfig | IDateRangeFilterConfig;

export interface IGroupedFilterConfig {
	filters: string[];
	label: string;
}

export type TDetachedItemSelectorModal<T> = Omit<
	IItemSelectorModalProps<T>,
	'observer' | 'onOpenChange' | 'open'
>;
