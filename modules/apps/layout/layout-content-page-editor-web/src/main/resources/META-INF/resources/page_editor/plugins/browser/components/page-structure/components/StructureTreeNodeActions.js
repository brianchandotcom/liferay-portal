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
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {openToast} from 'frontend-js-web';
import React, {useMemo, useRef, useState} from 'react';

import SaveFragmentCompositionModal from '../../../../../app/components/SaveFragmentCompositionModal';
import hasDropZoneChild from '../../../../../app/components/layout-data-items/hasDropZoneChild';
import {FRAGMENT_ENTRY_TYPES} from '../../../../../app/config/constants/fragmentEntryTypes';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../app/config/constants/layoutDataItemTypes';
import {useSelectItem} from '../../../../../app/contexts/ControlsContext';
import {
	useDispatch,
	useSelector,
} from '../../../../../app/contexts/StoreContext';
import {useWidgets} from '../../../../../app/contexts/WidgetsContext';
import deleteItem from '../../../../../app/thunks/deleteItem';
import duplicateItem from '../../../../../app/thunks/duplicateItem';
import canBeDuplicated from '../../../../../app/utils/canBeDuplicated';
import canBeRemoved from '../../../../../app/utils/canBeRemoved';
import canBeRenamed from '../../../../../app/utils/canBeRenamed';
import canBeSaved from '../../../../../app/utils/canBeSaved';
import {
	FORM_ERROR_TYPES,
	getFormValidationData,
} from '../../../../../app/utils/getFormValidationData';
import updateItemStyle from '../../../../../app/utils/updateItemStyle';
import useHasRequiredChild from '../../../../../app/utils/useHasRequiredChild';

export default function StructureTreeNodeActions({
	item,
	setEditingName,
	visible,
}) {
	const [active, setActive] = useState(false);

	const [openSaveModal, setOpenSaveModal] = useState(false);

	const alignElementRef = useRef();
	const dropdownRef = useRef();

	return (
		<>
			<ClayButton
				aria-expanded={active}
				aria-haspopup="true"
				className={classNames(
					'page-editor__page-structure__tree-node__actions-button',
					{
						'page-editor__page-structure__tree-node__actions-button--visible': visible,
					}
				)}
				displayType="unstyled"
				onClick={() => setActive((active) => !active)}
				ref={alignElementRef}
				small
				title={Liferay.Language.get('options')}
			>
				<ClayIcon symbol="ellipsis-v" />
			</ClayButton>

			<ClayDropDown.Menu
				active={active}
				alignElementRef={alignElementRef}
				containerProps={{
					className: 'cadmin',
				}}
				onSetActive={setActive}
				ref={dropdownRef}
			>
				{active && (
					<ActionList
						item={item}
						setActive={setActive}
						setEditingName={setEditingName}
						setOpenSaveModal={setOpenSaveModal}
					/>
				)}
			</ClayDropDown.Menu>

			{openSaveModal && (
				<SaveFragmentCompositionModal
					onCloseModal={() => setOpenSaveModal(false)}
				/>
			)}
		</>
	);
}

const ActionList = ({item, setActive, setEditingName, setOpenSaveModal}) => {
	const dispatch = useDispatch();
	const hasRequiredChild = useHasRequiredChild(item.itemId);
	const selectItem = useSelectItem();
	const widgets = useWidgets();

	const {
		fragmentEntryLinks,
		layoutData,
		segmentsExperienceId,
		selectedViewportSize,
	} = useSelector((state) => state);

	const isInputFragment =
		item.type === LAYOUT_DATA_ITEM_TYPES.fragment &&
		fragmentEntryLinks[item.config.fragmentEntryLinkId]
			.fragmentEntryType === FRAGMENT_ENTRY_TYPES.input;

	const isHidden = item.config.styles.display === 'none';

	const dropdownItems = useMemo(() => {
		const items = [];

		if (
			item.type !== LAYOUT_DATA_ITEM_TYPES.dropZone &&
			!hasDropZoneChild(item, layoutData) &&
			!isInputFragment
		) {
			items.push({
				action: () => {
					updateItemStyle({
						dispatch,
						itemId: item.itemId,
						segmentsExperienceId,
						selectedViewportSize,
						styleName: 'display',
						styleValue: isHidden ? 'block' : 'none',
					});

					if (hasRequiredChild()) {
						const {message} = getFormValidationData({
							type: FORM_ERROR_TYPES.hiddenFragment,
						});

						openToast({
							message,
							type: 'warning',
						});
					}
				},
				icon: isHidden ? 'view' : 'hidden',
				label: isHidden
					? Liferay.Language.get('show-fragment')
					: Liferay.Language.get('hide-fragment'),
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
		}

		if (canBeRenamed(item)) {
			items.push({
				action: () => {
					setEditingName(true);
				},
				label: Liferay.Language.get('rename'),
			});
		}

		items.push({
			type: 'separator',
		});

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
		hasRequiredChild,
		isInputFragment,
		item,
		layoutData,
		segmentsExperienceId,
		selectedViewportSize,
		selectItem,
		widgets,
		setOpenSaveModal,
		isHidden,
		setEditingName,
	]);

	return (
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
	);
};
