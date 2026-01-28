/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React from 'react';

import SidebarPanelHeader from '../../common/components/SidebarPanelHeader';
import {LayoutDataItem} from '../../types/layout_data/LayoutData';
import {
	useDispatch,
	useSelector,
	useSelectorCallback,
} from '../contexts/StoreContext';
import {useGetWidgets} from '../contexts/WidgetsContext';
import selectLayoutDataItemIcon from '../selectors/selectLayoutDataItemIcon';
import selectLayoutDataItemLabel from '../selectors/selectLayoutDataItemLabel';
import isInputFragment from '../utils/isInputFragment';
import openSwapFragmentModal from '../utils/openSwapFragmentModal';

export function FragmentSidebarHeader({item}: {item: LayoutDataItem}) {
	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);

	const dispatch = useDispatch();
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

	const isSwappable = isInputFragment(item, fragmentEntryLinks);

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
			iconRight={
				isSwappable ? (
					<ClayButton
						aria-label={Liferay.Language.get('swap-fragment')}
						borderless
						displayType="secondary"
						monospaced
						onClick={() =>
							openSwapFragmentModal({
								dispatch,
								fragmentEntryLinks,
								item,
							})
						}
						size="sm"
						title={Liferay.Language.get('swap-fragment')}
					>
						<ClayIcon symbol="change" />
					</ClayButton>
				) : null
			}
			showCloseButton={false}
		>
			{label}
		</SidebarPanelHeader>
	);
}
