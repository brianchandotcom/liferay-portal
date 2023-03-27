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

import {useDragLayer} from '../contexts/KeyboardDndContext';

function getMovementText(dragLayer) {
	if (!dragLayer) {
		return '';
	}

	return sub(
		dragLayer.eventKey === 'ArrowDown'
			? Liferay.Language.get('x-moved-down')
			: Liferay.Language.get('x-moved-up'),
		`${dragLayer.menuItemTitle} (${dragLayer.menuItemType})`
	);
}

export default function KeyboardMovementText() {
	const dragLayer = useDragLayer();

	const [internalText, setInternalText] = useState(() =>
		getMovementText(dragLayer)
	);

	useEffect(() => {
		setInternalText(getMovementText(dragLayer));

		const handler = setTimeout(() => {
			setInternalText(null);
		}, 500);

		return () => {
			clearTimeout(handler);
		};
	}, [dragLayer]);

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
