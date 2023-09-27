/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
