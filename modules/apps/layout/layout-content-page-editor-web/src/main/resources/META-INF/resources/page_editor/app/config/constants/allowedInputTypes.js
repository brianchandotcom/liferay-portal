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

/**
 * @type {import('../../../types/FormAllowedInputTypes').FormAllowedInputTypes}
 */
export const ALLOWED_INPUT_TYPES = {
	categories: [],
	date: ['date', 'datetime-local', 'text', 'textarea'],
	image: ['file', 'text'],
	number: ['month', 'number', 'range', 'time', 'week'],
	select: ['checkbox', 'radio', 'select'],
	tags: [],
	text: ['color', 'email', 'password', 'tel', 'text', 'textarea', 'url'],
	url: ['radio', 'select', 'text', 'url'],
};
