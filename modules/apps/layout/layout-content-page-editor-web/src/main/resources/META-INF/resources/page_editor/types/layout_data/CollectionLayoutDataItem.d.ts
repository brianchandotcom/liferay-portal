/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	BaseLayoutDataItem,
	CommonStyles,
	ResponsiveConfig,
} from './BaseLayoutDataItem';

export type CollectionLayoutDataItem = BaseLayoutDataItem<
	'collection',
	CommonStyles & {
		collection?: {
			itemType: string;
			key: string;
			title: string;
			type: string;
		};
		displayAllItems?: boolean;
		displayAllPages?: boolean;
		emptyCollectionOptions?: {displayMessage?: boolean};
		gutters?: boolean;
		listItemStyle?: '';
		listStyle?: 'flex-row';
		numberOfItems?: number;
		numberOfItemsPerPage?: number;
		numberOfPages?: number;
		paginationType?: 'simple';
		showAllItems?: boolean;
		templateKey?: string;
	} & ResponsiveConfig<{
			numberOfColumns?: number;
		}>
>;
