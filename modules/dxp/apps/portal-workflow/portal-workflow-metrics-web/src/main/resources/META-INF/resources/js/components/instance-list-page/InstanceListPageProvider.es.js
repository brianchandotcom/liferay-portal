/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {createContext, useState} from 'react';

const InstanceListContext = createContext(null);

export default function InstanceListPageProvider({children}) {
	const [instanceId, setInstanceId] = useState();
	const [selectAll, setSelectAll] = useState(false);
	const [selectedItems, setSelectedItems] = useState([]);

	const value = {
		instanceId,
		selectAll,
		selectedInstance: selectedItems[0],
		selectedItems,
		setInstanceId,
		setSelectAll,
		setSelectedItems,
	};

	return (
		<InstanceListContext.Provider value={value}>
			{children}
		</InstanceListContext.Provider>
	);
}

export {InstanceListContext};
