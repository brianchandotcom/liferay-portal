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
 * @return {Array<{editableId: string, editableValueNamespace: string, element: HTMLElement, processor: object }>}
 */
export default function getAllEditables(element) {
	return [
		...Array.from(element.querySelectorAll('lfr-editable')).map(
			element => ({
				editableId: element.getAttribute('id'),
				editableValueNamespace: EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
				element,
				processor:
					Processors[element.getAttribute('type')] ||
					Processors.fallback,
				type: element.getAttribute('type'),
			})
		),

		...Array.from(element.querySelectorAll('[data-lfr-editable-id]')).map(
			element => ({
				editableId: element.dataset.lfrEditableId,
				editableValueNamespace: EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
				element,
				processor:
					Processors[element.dataset.lfrEditableType] ||
					Processors.fallback,
				type: element.dataset.lfrEditableType,
			})
		),

		...Array.from(
			element.querySelectorAll('[data-lfr-background-image-id]')
		).map(element => ({
			editableId: element.dataset.lfrBackgroundImageId,
			editableValueNamespace: BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR,
			element,
			processor: Processors['background-image'],
			type: 'background-image',
		})),
	];
}
