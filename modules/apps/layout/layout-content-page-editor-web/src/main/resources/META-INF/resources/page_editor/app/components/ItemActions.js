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

import {getLayoutDataItemPropTypes} from '../../prop-types/index';
import {useSelectItem} from '../contexts/ControlsContext';
import {useDispatch, useSelector} from '../contexts/StoreContext';
import {useWidgets} from '../contexts/WidgetsContext';
import deleteItem from '../thunks/deleteItem';
import duplicateItem from '../thunks/duplicateItem';
import canBeDuplicated from '../utils/canBeDuplicated';
import canBeRemoved from '../utils/canBeRemoved';
import canBeSaved from '../utils/canBeSaved';
import updateItemStyle from '../utils/updateItemStyle';
import SaveFragmentCompositionModal from './SaveFragmentCompositionModal';

export default function ItemActions({item}) {
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

		items.push({
			action: () => {
				updateItemStyle({
					dispatch,
					itemId: item.itemId,
					segmentsExperienceId,
					selectedViewportSize,
					styleName: 'display',
					styleValue: 'none',
				});
			},
			icon: 'hidden',
			label: Liferay.Language.get('hide-fragment'),
		});

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
				icon: 'paste',
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
				icon: 'times-circle',
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

ItemActions.propTypes = {
	item: PropTypes.oneOfType([getLayoutDataItemPropTypes()]),
};
