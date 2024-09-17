/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEventListener} from '@liferay/frontend-js-react-web';
import {useContext, useEffect, useRef} from 'react';

import {ITEM_ACTIVATION_ORIGINS} from '../../config/constants/itemActivationOrigins';
import {ITEM_TYPES} from '../../config/constants/itemTypes';
import {MULTI_SELECT_TYPES} from '../../config/constants/multiSelectTypes';
import {
	useHoverItem,
	useMultiSelectType,
	useSelectItem,
} from '../../contexts/ControlsContext';
import {LayoutKeyboardContext} from '../../contexts/LayoutKeyboardContext';

export function useLayoutKeyboardNavigation(item) {
	const elementRef = useRef(null);

	const hoverItem = useHoverItem();
	const multiSelectType = useMultiSelectType();
	const selectItem = useSelectItem();

	const {itemList, setTargetId, targetId} = useContext(LayoutKeyboardContext);

	// Focus when changing target, and if the multiselection in range is
	// activated in range the element is selected, if not it is hovered.

	useEffect(() => {
		if (targetId === item.itemId) {
			elementRef.current.focus();

			if (multiSelectType === MULTI_SELECT_TYPES.range) {
				selectItem(item.itemId, {
					origin: ITEM_ACTIVATION_ORIGINS.keyboard,
				});
			}
			else {
				hoverItem(item.itemId);
			}
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [hoverItem, item, selectItem, targetId]);

	// Hover and set target when focusing first item

	useEventListener(
		'focus',
		() => {
			setTargetId(item.itemId);
			hoverItem(item.itemId);
		},
		false,
		elementRef.current
	);

	// Null hover when focus goes outside the editor

	useEventListener(
		'focusout',
		() => hoverItem(null),
		false,
		elementRef.current
	);

	// Change target or select item with keyboard

	useEventListener(
		'keydown',
		(event) => {
			const {key} = event;

			if (!['ArrowDown', 'ArrowUp', 'Enter'].includes(key)) {
				return;
			}

			event.stopPropagation();

			// Calculate next item and set target

			if (key === 'ArrowDown' || key === 'ArrowUp') {
				const nextId = getNextId(key, item.itemId, itemList);

				if (nextId) {
					setTargetId(nextId);
				}
			}

			// Select item

			else if (key === 'Enter') {
				const editableId = event.target.dataset.lfrEditableId;
				if (editableId) {
					event.preventDefault();
					selectItem(
						`${item.config.fragmentEntryLinkId}-${editableId}`,
						{
							itemType: ITEM_TYPES.editable,
							origin: ITEM_ACTIVATION_ORIGINS.keyboard,
						}
					);
				}
				else {
					selectItem(item.itemId, {
						origin: ITEM_ACTIVATION_ORIGINS.keyboard,
					});
				}
			}
		},
		false,
		elementRef.current
	);

	// When there's a target, it's the focusable item.
	// Otherwise, the first item is

	const isTarget = item.itemId === targetId;
	const isFirst = item.itemId === itemList[0];

	return {
		elementRef,
		isFocusable: targetId ? isTarget : isFirst,
	};
}

function getNextId(key, itemId, itemList) {
	const index = itemList.findIndex((element) => element === itemId);

	return itemList[key === 'ArrowDown' ? index + 1 : index - 1];
}
