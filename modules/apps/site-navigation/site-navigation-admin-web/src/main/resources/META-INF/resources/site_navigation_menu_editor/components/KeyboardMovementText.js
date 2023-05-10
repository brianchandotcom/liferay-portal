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

import {sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {useItems} from '../contexts/ItemsContext';
import {useDragLayer} from '../contexts/KeyboardDndContext';
import getFlatItems from '../utils/getFlatItems';

export function getMovementText(dragLayer, items) {
	const {
		eventKey,
		menuItemTitle,
		menuItemType,
		order,
		parentSiteNavigationMenuItemId,
	} = dragLayer || {};

	if (!menuItemTitle) {
		return '';
	}

	if (eventKey === 'Enter') {
		return sub(
			Liferay.Language.get(
				'use-up-and-down-arrows-to-move-x-and-press-enter-to-place-it-in-desired-position'
			),
			`${menuItemTitle} (${menuItemType})`
		);
	}

	const siblingsItems = items.filter(
		(item) =>
			item.parentSiteNavigationMenuItemId ===
			parentSiteNavigationMenuItemId
	);

	if (!siblingsItems.length) {
		const parent = items.find(
			(item) =>
				item.siteNavigationMenuItemId === parentSiteNavigationMenuItemId
		);

		return sub(Liferay.Language.get('targeting-inside-of-x'), parent.title);
	}

	const sibling = siblingsItems[order - 1];

	if (sibling) {
		return sub(Liferay.Language.get('targeting-x-of-x'), [
			Liferay.Language.get('bottom'),
			sibling.title,
		]);
	}

	return sub(Liferay.Language.get('targeting-x-of-x'), [
		Liferay.Language.get('top'),
		siblingsItems[order]?.title,
	]);
}

export default function KeyboardMovementText() {
	const dragLayer = useDragLayer();

	const items = getFlatItems(useItems());

	const [internalText, setInternalText] = useState(() =>
		getMovementText(dragLayer, items)
	);

	useEffect(() => {
		setInternalText(getMovementText(dragLayer, items));

		const handler = setTimeout(() => {
			setInternalText(null);
		}, 500);

		return () => {
			clearTimeout(handler);
		};
	}, [dragLayer]); //eslint-disable-line

	return internalText ? (
		<span
			aria-live="assertive"
			aria-relevant="additions"
			className="sr-only"
			role="log"
		>
			{internalText}
		</span>
	) : null;
}
