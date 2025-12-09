/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import React from 'react';

import SidebarPanelHeader from '../../common/components/SidebarPanelHeader';
import {LayoutDataItem} from '../../types/layout_data/LayoutData';
import {useSelectorCallback} from '../contexts/StoreContext';
import {useGetWidgets} from '../contexts/WidgetsContext';
import selectLayoutDataItemIcon from '../selectors/selectLayoutDataItemIcon';
import selectLayoutDataItemLabel from '../selectors/selectLayoutDataItemLabel';

export function FragmentSidebarHeader({item}: {item: LayoutDataItem}) {
	const getWidgets = useGetWidgets();

	const label = useSelectorCallback(
		(state) => selectLayoutDataItemLabel(state, item),
		[item.config]
	);

	const icon = useSelectorCallback(
		(state) => selectLayoutDataItemIcon(state, item, getWidgets),
		[item.config]
	);

	if (!label) {
		return null;
	}

	return (
		<SidebarPanelHeader
			iconLeft={
				icon ? (
					<ClayIcon
						className="mr-3 mt-0 text-secondary"
						symbol={icon}
					/>
				) : null
			}
			showCloseButton={false}
		>
			{label}
		</SidebarPanelHeader>
	);
}
