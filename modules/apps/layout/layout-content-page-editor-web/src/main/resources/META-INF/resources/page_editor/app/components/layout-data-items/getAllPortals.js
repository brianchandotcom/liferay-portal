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

import React from 'react';

import Layout from '../Layout';

/**
 * @param {HTMLElement} fragmentElement
 * @return {Array<{editableId: string, editableValueNamespace: string, element: HTMLElement, processor: object }>}
 */

export default function getAllPortals(element) {
	return Array.from(element.querySelectorAll('lfr-drop-zone')).map(
		(dropZoneElement) => {
			const dropZoneId = dropZoneElement.dataset.lfrDropZoneId || '';
			const mainItemId = dropZoneElement.getAttribute('uuid') || '';
			const priority = dropZoneElement.dataset.lfrPriority || Infinity;

			const Component = () =>
				mainItemId ? <Layout mainItemId={mainItemId} /> : null;

			Component.displayName = `DropZone(${mainItemId})`;

			return {
				Component,
				dropZoneId,
				element: dropZoneElement,
				mainItemId,
				priority,
			};
		}
	);
}
