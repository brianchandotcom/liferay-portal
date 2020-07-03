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
import {useModal} from '@clayui/modal';
import {useIsMounted} from 'frontend-js-react-web';
import PropTypes from 'prop-types';
import React, {useEffect, useMemo, useState} from 'react';

import {getLayoutDataItemPropTypes} from '../../prop-types/index';
import {
	BACKSPACE_KEYCODE,
	D_KEYCODE,
	S_KEYCODE,
} from '../config/constants/keycodes';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {useDispatch, useSelector} from '../store/index';
import deleteItem from '../thunks/deleteItem';
import duplicateItem from '../thunks/duplicateItem';
import {useIsActive, useSelectItem} from './Controls';
import SaveFragmentCompositionModal from './floating-toolbar/SaveFragmentCompositionModal';
import hasDropZoneChild from './layout-data-items/hasDropZoneChild';

export default function ItemActions({item}) {
	const [active, setActive] = useState(false);
	const dispatch = useDispatch();
	const isActive = useIsActive();
	const isMounted = useIsMounted();
	const selectItem = useSelectItem();

	const state = useSelector((state) => state);
	const {
		fragmentEntryLinks,
		layoutData,
		segmentsExperienceId,
		widgets,
	} = state;

	const [
		openSaveFragmentCompositionModal,
		setOpenSaveFragmentCompositionModal,
	] = useState(false);

	const {observer, onClose} = useModal({
		onClose: () => {
			if (isMounted()) {
				setOpenSaveFragmentCompositionModal(false);
			}
		},
	});

	useEffect(() => {
		const onKeyDown = (event) => {
			if (isActive(item.itemId) && !openSaveFragmentCompositionModal) {
				const itemAction = itemActions.find((itemAction) =>
					itemAction.isKeyCombination(event)
				);

				if (itemAction) {
					itemAction.action();

					event.preventDefault();
				}
			}
		};

		window.addEventListener('keydown', onKeyDown, true);

		return () => {
			window.removeEventListener('keydown', onKeyDown, true);
		};
	}, [isActive, item, itemActions, openSaveFragmentCompositionModal]);

	const itemActions = useMemo(() => {
		const actions = [];

		if (canBeDuplicated(fragmentEntryLinks, item, layoutData, widgets)) {
			actions.push({
				action: () =>
					dispatch(
						duplicateItem({
							itemId: item.itemId,
							segmentsExperienceId,
							selectItem,
						})
					),
				icon: 'paste',
				isKeyCombination: (event) => {
					const ctrlOrMeta =
						(event.ctrlKey && !event.metaKey) ||
						(!event.ctrlKey && event.metaKey);

					return ctrlOrMeta && event.keyCode === D_KEYCODE;
				},
				label: Liferay.Language.get('duplicate'),
			});
		}

		if (canBeSaved(item, layoutData)) {
			actions.push({
				action: () => setOpenSaveFragmentCompositionModal(true),
				icon: 'disk',
				isKeyCombination: (event) => {
					const ctrlOrMeta =
						(event.ctrlKey && !event.metaKey) ||
						(!event.ctrlKey && event.metaKey);

					return ctrlOrMeta && event.keyCode === S_KEYCODE;
				},
				label: Liferay.Language.get('save-composition'),
			});
		}

		if (canBeRemoved(item, layoutData)) {
			actions.push({
				action: () =>
					dispatch(
						deleteItem({
							itemId: item.itemId,
							selectItem,
							store: state,
						})
					),
				icon: 'times-circle',
				isKeyCombination: (event) =>
					event.keyCode === BACKSPACE_KEYCODE,
				label: Liferay.Language.get('delete'),
			});
		}

		return actions;
	}, [
		dispatch,
		fragmentEntryLinks,
		item,
		layoutData,
		segmentsExperienceId,
		selectItem,
		state,
		widgets,
	]);

	return itemActions?.length ? (
		<>
			<ClayDropDown
				active={active}
				alignmentPosition={Align.BottomRight}
				onActiveChange={setActive}
				trigger={
					<ClayButton
						displayType="unstyled"
						small
						title={Liferay.Language.get('remove')}
					>
						<ClayIcon
							className="page-editor__topper__icon"
							symbol="ellipsis-v"
						/>
					</ClayButton>
				}
			>
				<ClayDropDown.ItemList>
					{itemActions.map((itemAction) => (
						<ClayDropDown.Item
							key={itemAction.label}
							onClick={() => {
								setActive(false);

								itemAction.action();
							}}
							symbolLeft={itemAction.icon}
						>
							<p className="d-inline-block m-0 ml-2">
								{itemAction.label}
							</p>
						</ClayDropDown.Item>
					))}
				</ClayDropDown.ItemList>
			</ClayDropDown>

			{openSaveFragmentCompositionModal && (
				<SaveFragmentCompositionModal
					itemId={item.itemId}
					observer={observer}
					onClose={onClose}
				/>
			)}
		</>
	) : null;
}

ItemActions.propTypes = {
	item: PropTypes.oneOfType([getLayoutDataItemPropTypes()]),
};

function canBeDuplicated(fragmentEntryLinks, item, layoutData, widgets) {
	switch (item.type) {
		case LAYOUT_DATA_ITEM_TYPES.collection:
			return true;

		case LAYOUT_DATA_ITEM_TYPES.container:
		case LAYOUT_DATA_ITEM_TYPES.row:
			return !hasDropZoneChild(item, layoutData);

		case LAYOUT_DATA_ITEM_TYPES.fragment: {
			const fragmentEntryLink =
				fragmentEntryLinks[item.config.fragmentEntryLinkId];

			const portletId = fragmentEntryLink.editableValues.portletId;

			const widget = portletId && getWidget(widgets, portletId);

			return !widget || widget.instanceable;
		}

		default:
			return false;
	}
}

function canBeRemoved(item, layoutData) {
	switch (item.type) {
		case LAYOUT_DATA_ITEM_TYPES.column:
		case LAYOUT_DATA_ITEM_TYPES.dropZone:
			return false;

		default:
			return !hasDropZoneChild(item, layoutData);
	}
}

function canBeSaved(item, layoutData) {
	switch (item.type) {
		case LAYOUT_DATA_ITEM_TYPES.container:
		case LAYOUT_DATA_ITEM_TYPES.row:
			return !hasDropZoneChild(item, layoutData);

		default:
			return false;
	}
}

function getWidget(widgets, portletId) {
	let widget = null;

	for (let i = 0; i < widgets.length; i++) {
		const {categories = [], portlets = []} = widgets[i];
		const categoryPortlet = portlets.find(
			(portlet) => portlet.portletId === portletId
		);
		const subCategoryPortlet = getWidget(categories, portletId);

		widget = subCategoryPortlet || categoryPortlet;

		if (widget) {
			return widget;
		}
	}

	return widget;
}
