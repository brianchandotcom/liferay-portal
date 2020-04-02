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
	).forEach(dropZoneElement => {
		dropZoneElement.parentElement.removeChild(dropZoneElement);
	});

	return [
		...Array.from(
			cleanedFragmentElement.querySelectorAll('lfr-editable')
		).map(editableElement => ({
			editableId: editableElement.getAttribute('id'),
			editableValueNamespace: EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
			element: editableElement,
			processor:
				Processors[editableElement.getAttribute('type')] ||
				Processors.fallback,
			type: editableElement.getAttribute('type'),
		})),

		...Array.from(
			cleanedFragmentElement.querySelectorAll('[data-lfr-editable-id]')
		).map(editableElement => ({
			editableId: editableElement.dataset.lfrEditableId,
			editableValueNamespace: EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
			element: editableElement,
			processor:
				Processors[editableElement.dataset.lfrEditableType] ||
				Processors.fallback,
			type: editableElement.dataset.lfrEditableType,
		})),

		...Array.from(
			cleanedFragmentElement.querySelectorAll(
				'[data-lfr-background-image-id]'
			)
		).map(editableElement => ({
			editableId: editableElement.dataset.lfrBackgroundImageId,
			editableValueNamespace: BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR,
			element: editableElement,
			processor: Processors['background-image'],
			type: 'background-image',
		})),
	];
}
