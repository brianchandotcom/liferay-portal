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

import React, {useContext} from 'react';

import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../app/config/constants/editableFragmentEntryProcessor';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../app/config/constants/layoutDataItemTypes';
import {StoreContext} from '../../../app/store/index';
import SidebarPanelHeader from '../../../common/components/SidebarPanelHeader';

export default function PageStructureSidebar() {
	const {fragmentEntryLinks, layoutData} = useContext(StoreContext);

	const getName = (item, fragmentEntryLinks) => {
		let name;

		if (item.type === LAYOUT_DATA_ITEM_TYPES.fragment) {
			name = fragmentEntryLinks[item.config.fragmentEntryLinkId].name;
		} else if (item.type === LAYOUT_DATA_ITEM_TYPES.container) {
			name = Liferay.Language.get('container');
		} else if (item.type === LAYOUT_DATA_ITEM_TYPES.column) {
			name = Liferay.Language.get('column');
		} else if (item.type === LAYOUT_DATA_ITEM_TYPES.row) {
			name = Liferay.Language.get('row');
		}

		return name;
	};

	const isRemovable = item => {
		return item.type === LAYOUT_DATA_ITEM_TYPES.column ? false : true;
	};

	return (
		<>
			<SidebarPanelHeader>
				{Liferay.Language.get('page-structure')}
			</SidebarPanelHeader>
		</>
	);
}
