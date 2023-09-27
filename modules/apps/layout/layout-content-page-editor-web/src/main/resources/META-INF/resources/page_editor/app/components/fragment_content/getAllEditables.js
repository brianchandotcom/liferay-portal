/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR} from '../../config/constants/backgroundImageFragmentEntryProcessor';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../config/constants/editableFragmentEntryProcessor';
import Processors from '../../processors/index';

/**
 * @param {HTMLElement} fragmentElement
 * @return {Array<{editableId: string, editableValueNamespace: string, element: HTMLElement, processor: object }>}
 */
export default function getAllEditables(fragmentElement) {
	const cleanedFragmentElement = fragmentElement.cloneNode(true);

	Array.from(
		cleanedFragmentElement.querySelectorAll('lfr-drop-zone')
	).forEach((dropZoneElement) => {
		dropZoneElement.parentElement.removeChild(dropZoneElement);
	});

	return [
		...Array.from(
			cleanedFragmentElement.querySelectorAll('lfr-editable')
		).map((editableElement) => {
			const editableId = editableElement.getAttribute('id');
			const type = editableElement.getAttribute('type');

			return {
				editableId,
				editableValueNamespace: EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
				element: fragmentElement.querySelector(
					`lfr-editable[id="${editableId}"]`
				),
				priority:
					parseInt(editableElement.dataset.lfrPriority, 10) ||
					Infinity,
				processor: Processors[type] || Processors.fallback,
				type,
			};
		}),

		...Array.from(
			cleanedFragmentElement.querySelectorAll('[data-lfr-editable-id]')
		).map((editableElement) => {
			const editableId = editableElement.dataset.lfrEditableId;
			const type = editableElement.dataset.lfrEditableType;

			return {
				editableId,
				editableValueNamespace: EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
				element: fragmentElement.querySelector(
					`[data-lfr-editable-id="${editableId}"]`
				),
				priority:
					parseInt(editableElement.dataset.lfrPriority, 10) ||
					Infinity,
				processor: Processors[type] || Processors.fallback,
				type,
			};
		}),

		...Array.from(
			cleanedFragmentElement.querySelectorAll(
				'[data-lfr-background-image-id]'
			)
		).map((editableElement) => {
			const editableId = editableElement.dataset.lfrBackgroundImageId;

			return {
				editableId,
				editableValueNamespace: BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR,
				element: fragmentElement.querySelector(
					`[data-lfr-background-image-id="${editableId}"]`
				),
				priority:
					parseInt(editableElement.dataset.lfrPriority, 10) ||
					Infinity,
				processor: Processors['background-image'],
				type: 'background-image',
			};
		}),
	];
}
