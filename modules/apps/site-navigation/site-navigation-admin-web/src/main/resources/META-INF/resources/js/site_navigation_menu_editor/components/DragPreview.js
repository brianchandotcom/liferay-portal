/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';
import {useDragLayer} from 'react-dnd';

import {useConstants} from '../contexts/ConstantsContext';
import {useItems} from '../contexts/ItemsContext';
import {useDragLayer as useKeyboardDragLayer} from '../contexts/KeyboardDndContext';
import getDescendantsCount from '../utils/getDescendantsCount';

const PREVIEW_HEIGHT = 64;
const HANDLER_OFFSET = 10;

const getItemStyles = (wrapperElement, currentOffset, element, rtl) => {
	if (!currentOffset || !element) {
		return {
			display: 'none',
		};
	}

	const elementRect = element.getBoundingClientRect();
	const wrapperRect = wrapperElement.getBoundingClientRect();

	const x = rtl
		? currentOffset.x -
		  elementRect.width +
		  wrapperElement.scrollLeft -
		  HANDLER_OFFSET
		: currentOffset.x +
		  wrapperElement.scrollLeft -
		  wrapperRect.left -
		  HANDLER_OFFSET;

	const y =
		currentOffset.y +
		wrapperElement.scrollTop -
		wrapperRect.top -
		PREVIEW_HEIGHT * 0.5;

	const transform = `translate(${x}px, ${y}px)`;

	return {
		left: 0,
		right: 'auto',
		transform,
	};
};

export default function DragPreview({wrapperRef}) {
	const [itemElement, setItemElement] = useState();

	const {languageId, portletNamespace} = useConstants();

	const items = useItems();
	const rtl = Liferay.Language.direction[languageId] === 'rtl';

	const dragLayer = useDragLayer((monitor) => ({
		currentOffset: monitor.getClientOffset(),
		isDragging: monitor.isDragging(),
		itemId: monitor.getItem()?.id,
		namespace: monitor.getItem()?.namespace,
	}));

	const keyboardDragLayer = useKeyboardDragLayer();

	const currentOffset =
		dragLayer.currentOffset || keyboardDragLayer?.currentOffset;
	const isDragging = dragLayer.isDragging || Boolean(keyboardDragLayer);
	const itemId =
		dragLayer.itemId || keyboardDragLayer?.siteNavigationMenuItemId;
	const itemNamespace =
		dragLayer.namespace || (keyboardDragLayer ? portletNamespace : null);

	const [label, setLabel] = useState();

	useEffect(() => {
		const item = items.find(
			(item) => item.siteNavigationMenuItemId === itemId
		);

		if (item) {
			const descendantsCount = getDescendantsCount(items, itemId);

			if (descendantsCount) {
				const translationKey =
					descendantsCount > 1
						? Liferay.Language.get('x-and-x-children')
						: Liferay.Language.get('x-and-x-child');

				setLabel(sub(translationKey, item.title, descendantsCount));
			}
			else {
				setLabel(item.title);
			}
		}
	}, [itemId, items]);

	useEffect(() => {
		if (keyboardDragLayer) {
			itemElement?.scrollIntoView({
				behavior: 'auto',
				block: 'center',
				inline: 'center',
			});
		}
	});

	if (
		!isDragging ||
		itemNamespace !== portletNamespace ||
		!wrapperRef.current
	) {
		return null;
	}

	return (
		<div className="site-navigation__drag-preview">
			<div className="site-navigation__drag-preview__mask" />

			<div
				className="site-navigation__drag-preview__content"
				ref={setItemElement}
				style={getItemStyles(
					wrapperRef.current,
					currentOffset,
					itemElement,
					rtl
				)}
			>
				{keyboardDragLayer ? (
					<div className="site-navigation__drag-preview__border" />
				) : null}

				<div className="site-navigation__drag-preview__label">
					{label}
				</div>
			</div>
		</div>
	);
}
