/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import {ManagementToolbar} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React from 'react';

import duplicateItem from '../../../../../app/actions/duplicateItem';
import {VIEWPORT_SIZES} from '../../../../../app/config/constants/viewportSizes';
import {useSelectMultipleItems} from '../../../../../app/contexts/ControlsContext';
import {
	useDispatch,
	useSelector,
} from '../../../../../app/contexts/StoreContext';
import deleteItem from '../../../../../app/thunks/deleteItem';
import updateItemStyle from '../../../../../app/utils/updateItemStyle';

export default function PageStructureSidebarToolbar({activeItemIds}) {
	const dispatch = useDispatch();
	const layoutData = useSelector((state) => state.layoutData);
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);
	const selectItems = useSelectMultipleItems();

	const firstActiveItem = layoutData.items[activeItemIds[0]];

	const dropdownItems = [
		{
			label: sub(
				firstActiveItem.config.styles.display === 'none'
					? Liferay.Language.get('show-x')
					: Liferay.Language.get('hide-x'),
				Liferay.Language.get('fragments')
			),
			onClick: () =>
				updateItemStyle({
					dispatch,
					itemIds: activeItemIds,
					selectedViewportSize,
					styleName: 'display',
					styleValue:
						firstActiveItem.config.styles.display === 'none'
							? 'block'
							: 'none',
				}),
			symbolLeft: 'view',
		},
		{
			type: 'divider',
		},
		{
			label: Liferay.Language.get('duplicate'),
			onClick: (event) => {
				event.stopPropagation();

				dispatch(
					duplicateItem({
						itemIds: activeItemIds,
						selectItems,
					})
				);
			},
			symbolLeft: 'copy',
		},
		{
			label: Liferay.Language.get('delete'),
			onClick: () => {
				dispatch(
					deleteItem({
						itemIds: activeItemIds,
						selectItems,
					})
				);
			},
			symbolLeft: 'trash',
		},
	];

	return (
		<ManagementToolbar.Container
			active
			onClick={(event) => event.stopPropagation()}
		>
			{sub(
				Liferay.Language.get('x-items-selected'),
				activeItemIds.length
			)}

			{selectedViewportSize === VIEWPORT_SIZES.desktop ? (
				<ClayDropDownWithItems
					items={dropdownItems}
					trigger={
						<ClayButtonWithIcon
							aria-label={sub(
								Liferay.Language.get('actions-for-x'),
								Liferay.Language.get('selected-items')
							)}
							displayType="unstyled"
							size="sm"
							symbol="ellipsis-v"
							title={Liferay.Language.get('actions')}
						/>
					}
				/>
			) : null}
		</ManagementToolbar.Container>
	);
}
