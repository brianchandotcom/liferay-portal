/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import type {LayoutData} from '../../types/LayoutData';
import type {PageContent} from './addItem';
export default function updateRowColumns({
	itemId,
	layoutData,
	numberOfColumns,
	pageContents,
}: {
	itemId: string;
	layoutData: LayoutData;
	numberOfColumns: number;
	pageContents: PageContent[];
}): {
	readonly itemId: string;
	readonly layoutData: LayoutData;
	readonly numberOfColumns: number;
	readonly pageContents: PageContent[];
	readonly type: 'UPDATE_ROW_COLUMNS';
};
