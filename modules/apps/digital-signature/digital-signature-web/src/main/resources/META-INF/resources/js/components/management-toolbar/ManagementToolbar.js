/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ManagementToolbar as FrontendManagementToolbar} from 'frontend-js-components-web';
import React, {useContext, useState} from 'react';

import ManagementToolbarFilterAndOrder from './ManagementToolbarFilterAndOrder';
import ManagementToolbarRight from './ManagementToolbarRight';
import ManagementToolbarSearch from './ManagementToolbarSearch';
import SearchContext from './SearchContext';

export default function ManagementToolbar({
	addButton,
	columns,
	disabled,
	filters,
}) {
	const [{keywords}, dispatch] = useContext(SearchContext);
	const [showMobile, setShowMobile] = useState(false);

	return (
		<FrontendManagementToolbar.Container>
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
		</FrontendManagementToolbar.Container>
	);
}
