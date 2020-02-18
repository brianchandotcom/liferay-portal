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

import getAllEditables from './getAllEditables';
import getEditableElementId from './getEditableElementId';
import getEditableUniqueId from './getEditableUniqueId';

/**
 * @param {HTMLElement} fragmentElement
 * @param {string} fragmentEntryLinkId
 * @param {function} isActive
 */
export default function getActiveEditableElement(
	fragmentElement,
	fragmentEntryLinkId,
	isActive
) {
	return fragmentElement
		? getAllEditables(fragmentElement).find(editableElement =>
				isActive(
					getEditableUniqueId(
						fragmentEntryLinkId,
						getEditableElementId(editableElement)
					)
				)
		  )
		: null;
}
