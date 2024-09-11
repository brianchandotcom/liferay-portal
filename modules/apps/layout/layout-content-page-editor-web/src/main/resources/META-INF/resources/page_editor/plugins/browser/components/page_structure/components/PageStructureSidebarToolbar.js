/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import {FeatureIndicator, ManagementToolbar} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React from 'react';

import hasDropZoneChild from '../../../../../app/components/layout_data_items/hasDropZoneChild';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../app/config/constants/layoutDataItemTypes';
import {VIEWPORT_SIZES} from '../../../../../app/config/constants/viewportSizes';
import {useSetCopiedItemIds} from '../../../../../app/contexts/ClipboardContext';
import {useSelectMultipleItems} from '../../../../../app/contexts/ControlsContext';
import {
	useDispatch,
	useSelector,
} from '../../../../../app/contexts/StoreContext';
import deleteItem from '../../../../../app/thunks/deleteItem';
import duplicateItem from '../../../../../app/thunks/duplicateItem';
import canBeDuplicated from '../../../../../app/utils/canBeDuplicated';
import canBeRemoved from '../../../../../app/utils/canBeRemoved';
import isInputFragment from '../../../../../app/utils/isInputFragment';
import updateItemStyle from '../../../../../app/utils/updateItemStyle';

import './PageStructureSidebarToolbar.scss';

export default function PageStructureSidebarToolbar({activeItemIds}) {
	const dispatch = useDispatch();
	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);
	const layoutData = useSelector((state) => state.layoutData);
	const selectItems = useSelectMultipleItems();
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);
	const setCopiedItemIds = useSetCopiedItemIds();
	const widgets = useSelector((state) => state.widgets);

	const itemsCanBeDeleted = () =>
		activeItemIds.every((activeItemId) =>
			canBeRemoved(layoutData.items[activeItemId], layoutData)
		);

	const itemsCanBeDuplicated = () =>
		activeItemIds.every((activeItemId) =>
			canBeDuplicated(
				fragmentEntryLinks,
				layoutData.items[activeItemId],
				layoutData,
				widgets
			)
		);

	const itemsCanBeUpdated = () =>
		activeItemIds.every((activeItemId) => {
			const item = layoutData.items[activeItemId];

			return (
				item.type !== LAYOUT_DATA_ITEM_TYPES.dropZone &&
				!hasDropZoneChild(item, layoutData) &&
				!isInputFragment(item, fragmentEntryLinks)
			);
		});

	const firstActiveItemIsHidden =
		layoutData.items[activeItemIds[0]]?.config?.styles?.display === 'none';

	const dropdownItems = [
		{
			label: sub(
				firstActiveItemIsHidden
					? Liferay.Language.get('show-x')
					: Liferay.Language.get('hide-x'),
				Liferay.Language.get('fragments')
			),
			onClick: () => {
				if (itemsCanBeUpdated()) {
					updateItemStyle({
						dispatch,
						itemIds: activeItemIds,
						selectedViewportSize,
						styleName: 'display',
						styleValue: firstActiveItemIsHidden ? 'block' : 'none',
					});
				}
			},
			symbolLeft: firstActiveItemIsHidden ? 'view' : 'hidden',
		},
		{
			type: 'divider',
		},
		{
			isBetaFeature: true,
			label: Liferay.Language.get('copy'),
			onClick: () => setCopiedItemIds(activeItemIds),
			symbolLeft: 'copy',
		},
		{
			isBetaFeature: true,
			label: Liferay.Language.get('cut'),
			onClick: () => {
				if (itemsCanBeDeleted()) {
					setCopiedItemIds(activeItemIds);
					dispatch(
						deleteItem({
							itemIds: activeItemIds,
							selectItems,
						})
					);
				}
			},
			symbolLeft: 'cut',
		},
		{
			label: Liferay.Language.get('duplicate'),
			onClick: () => {
				if (itemsCanBeDuplicated()) {
					dispatch(
						duplicateItem({
							itemIds: activeItemIds,
							selectItems,
						})
					);
				}
			},
			symbolLeft: 'copy',
		},
		{
			className: 'keyboard-only',
			isBetaFeature: true,
			label: sub(
				Liferay.Language.get('move-x-items'),
				activeItemIds.length
			),
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
				<ClayDropDown
					closeOnClick
					hasLeftSymbols
					trigger={
						<ClayButtonWithIcon
							aria-label={sub(
								Liferay.Language.get('actions-for-x'),
								Liferay.Language.get('selected-items')
							)}
							className="text-secondary"
							displayType="unstyled"
							size="sm"
							symbol="ellipsis-v"
							title={Liferay.Language.get('actions')}
						/>
					}
				>
					<ClayDropDown.ItemList items={dropdownItems}>
						{(item) =>
							item.type === 'divider' ? (
								<ClayDropDown.Divider />
							) : (
								<ClayDropDown.Item
									className={item.className}
									onClick={() => item.onClick()}
									symbolLeft={item.symbolLeft}
								>
									{item.label}

									{item.isBetaFeature ? (
										<span className="ml-2">
											<FeatureIndicator type="beta" />
										</span>
									) : null}
								</ClayDropDown.Item>
							)
						}
					</ClayDropDown.ItemList>
				</ClayDropDown>
			) : null}
		</ManagementToolbar.Container>
	);
}
