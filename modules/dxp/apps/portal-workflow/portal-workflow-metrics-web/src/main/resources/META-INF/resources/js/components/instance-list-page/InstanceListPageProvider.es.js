/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import React, {createContext, useState} from 'react';

const InstanceListContext = createContext(null);

const InstanceListPageProvider = ({children}) => {
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
};

export {InstanceListContext};
export default InstanceListPageProvider;
