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

import ClayButton from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React, {useMemo, useState} from 'react';

import {getLayoutDataItemPropTypes} from '../../../prop-types/index';
import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';
import {useSelectItem} from '../../contexts/ControlsContext';
import {useDispatch, useSelector} from '../../contexts/StoreContext';
import {useWidgets} from '../../contexts/WidgetsContext';
import deleteItem from '../../thunks/deleteItem';
import duplicateItem from '../../thunks/duplicateItem';
import canBeDuplicated from '../../utils/canBeDuplicated';
import canBeRemoved from '../../utils/canBeRemoved';
import canBeSaved from '../../utils/canBeSaved';
import hideFragment from '../../utils/hideFragment';
import SaveFragmentCompositionModal from '../SaveFragmentCompositionModal';
import hasDropZoneChild from '../layout-data-items/hasDropZoneChild';

export default function TopperItemActions({item}) {
	const [active, setActive] = useState(false);
	const dispatch = useDispatch();
	const selectItem = useSelectItem();
	const widgets = useWidgets();

	const {
		fragmentEntryLinks,
		layoutData,
		segmentsExperienceId,
		selectedViewportSize,
	} = useSelector((state) => state);

	const [openSaveModal, setOpenSaveModal] = useState(false);

	const dropdownItems = useMemo(() => {
		const items = [];

		if (
			item.type !== LAYOUT_DATA_ITEM_TYPES.dropZone &&
			!hasDropZoneChild(item, layoutData)
		) {
			items.push({
				action: () => {
					hideFragment({
						dispatch,
						itemId: item.itemId,
						segmentsExperienceId,
						selectedViewportSize,
					});
				},
				icon: 'hidden',
				label: Liferay.Language.get('hide-fragment'),
			});
		}

		if (canBeSaved(item, layoutData)) {
			items.push({
				action: () => setOpenSaveModal(true),
				icon: 'disk',
				label: Liferay.Language.get('save-composition'),
			});
		}

		if (items.length) {
			items.push({
				type: 'separator',
			});
		}

		if (canBeDuplicated(fragmentEntryLinks, item, layoutData, widgets)) {
			items.push({
				action: () =>
					dispatch(
						duplicateItem({
							itemId: item.itemId,
							segmentsExperienceId,
							selectItem,
						})
					),
				icon: 'copy',
				label: Liferay.Language.get('duplicate'),
			});

			items.push({
				type: 'separator',
			});
		}

		if (canBeRemoved(item, layoutData)) {
			items.push({
				action: () =>
					dispatch(
						deleteItem({
							itemId: item.itemId,
							selectItem,
						})
					),
				icon: 'trash',
				label: Liferay.Language.get('delete'),
			});
		}

		return items;
	}, [
		dispatch,
		fragmentEntryLinks,
		item,
		layoutData,
		segmentsExperienceId,
		selectedViewportSize,
		selectItem,
		widgets,
	]);

	if (!dropdownItems.length) {
		return null;
	}

	return (
		<>
			<ClayDropDown
				active={active}
				alignmentPosition={Align.BottomRight}
				menuElementAttrs={{
					containerProps: {
						className: 'cadmin',
					},
				}}
				onActiveChange={setActive}
				trigger={
					<ClayButton
						displayType="unstyled"
						small
						title={Liferay.Language.get('options')}
					>
						<ClayIcon
							className="page-editor__topper__icon"
							symbol="ellipsis-v"
						/>
					</ClayButton>
				}
			>
				<ClayDropDown.ItemList>
					{dropdownItems.map((dropdownItem, index, array) =>
						dropdownItem.type === 'separator' ? (
							index !== array.length - 1 && (
								<ClayDropDown.Divider key={index} />
							)
						) : (
							<React.Fragment key={index}>
								<ClayDropDown.Item
									onClick={() => {
										setActive(false);

										dropdownItem.action();
									}}
									symbolLeft={dropdownItem.icon}
								>
									<p className="d-inline-block m-0 ml-4">
										{dropdownItem.label}
									</p>
								</ClayDropDown.Item>
							</React.Fragment>
						)
					)}
				</ClayDropDown.ItemList>
			</ClayDropDown>

			{openSaveModal && (
				<SaveFragmentCompositionModal
					onCloseModal={() => setOpenSaveModal(false)}
				/>
			)}
		</>
	);
}

TopperItemActions.propTypes = {
	item: PropTypes.oneOfType([getLayoutDataItemPropTypes()]),
};
