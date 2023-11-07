/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useState} from 'react';

const DEFAULT_SORT_CONFIG = {
	columnName: 'dateCreated',
	direction: 'descending'
};

export default function useSort() {

	const [sortConfig, setSortConfig] = useState(DEFAULT_SORT_CONFIG);

	const handleSortChange = (column) => {
		if (column === sortConfig.columnName) {
			setSortConfig({columnName: column, direction: (sortConfig.direction === 'descending') ? 'ascending' : 'descending'});
		} else {
			setSortConfig({columnName: column, direction: (sortConfig.direction)});
		}
	};

	return {handleSortChange, sortConfig};
}
