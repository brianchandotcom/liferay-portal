/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getRandomString from '../../../utils/getRandomString';

type Props = {
	columns?: Array<{
		pageElements?: PageElement[];
		size: number;
	}>;
	id?: string;
};

export default function getGridDefinition({
	columns = getDefaultColumns(),
	id = getRandomString(),
}: Props = {}): PageElement {
	const pageElements = [];

	for (const column of columns) {
		pageElements.push(
			getColumnDefinition(column.size, column.pageElements)
		);
	}

	return {
		definition: {
			gutters: true,
			numberOfColumns: columns.length,
		},
		id,
		pageElements,
		type: 'Row',
	};
}

function getColumnDefinition(
	size: number,
	pageElements?: PageElement[]
): PageElement {
	return {
		definition: {
			size,
		},
		id: getRandomString(),
		pageElements,
		type: 'Column',
	};
}

function getDefaultColumns() {
	const col = {pageElements: [], size: 4};

	return [col, col, col];
}
