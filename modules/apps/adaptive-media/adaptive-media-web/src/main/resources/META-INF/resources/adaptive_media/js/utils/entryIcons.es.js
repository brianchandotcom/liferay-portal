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

export function disableEntryIcon(element) {
	element.parentElement.classList.add('disabled');

	element.setAttribute('data-href', element.getAttribute('href'));
	element.setAttribute('data-onclick', element.getAttribute('onclick'));

	element.removeAttribute('href');
	element.removeAttribute('onclick');
}

export function enableEntryIcon(element) {
	element.parentElement.classList.remove('disabled');

	element.setAttribute('href', element.getAttribute('data-href'));
	element.setAttribute('onclick', element.getAttribute('data-onclick'));

	element.removeAttribute('data-href');
	element.removeAttribute('data-onclick');
}
