/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayManagementToolbar from '@clayui/management-toolbar';
import React, {useContext, useState} from 'react';

import ManagementToolbarFilterAndOrder from './ManagementToolbarFilterAndOrder.es';
import ManagementToolbarRight from './ManagementToolbarRight.es';
import ManagementToolbarSearch from './ManagementToolbarSearch';
import SearchContext from './SearchContext.es';

export default ({addButton, columns, disabled, filters}) => {
	const [{keywords}, dispatch] = useContext(SearchContext);
	const [showMobile, setShowMobile] = useState(false);

	return (
		<ClayManagementToolbar>
			<ManagementToolbarFilterAndOrder
				columns={columns}
				disabled={disabled}
				filters={filters}
			/>

			<ManagementToolbarSearch
				disabled={disabled}
				onSubmit={(searchText) =>
					dispatch({keywords: searchText, type: 'SEARCH'})
				}
				searchText={keywords}
				setShowMobile={setShowMobile}
				showMobile={showMobile}
			/>

			<ManagementToolbarRight
				addButton={addButton}
				setShowMobile={setShowMobile}
			/>
		</ClayManagementToolbar>
	);
};
