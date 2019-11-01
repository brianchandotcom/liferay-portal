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

import dom from 'metal-dom';

export const getColumnIndex = node => {
	const rowNode = dom.closest(node, 'tr');

	if (!rowNode) {
		return -1;
	}

	const columnNode = dom.closest(node, 'td,th');

	if (!columnNode) {
		return -1;
	}

	const scopeId = `${Date.now()}`;

	rowNode.setAttribute('data-scope-uuid', scopeId);

	const columns = rowNode.querySelectorAll(
		`[data-scope-uuid="${scopeId}"] > ${columnNode.tagName}`
	);

	rowNode.removeAttribute('data-scope-uuid');

	return Array.prototype.indexOf.call(columns, columnNode) - 1;
};

export const getColumnNode = (container, index) => {
	return container.querySelector(
		`table tbody > tr:first-of-type > td:nth-of-type(${index + 2})`
	);
};

export const getColumns = container => {
	return container.querySelectorAll(`table tbody > tr:first-of-type > td`);
};

export const getFieldTypeLabel = (fieldTypes, fieldType) => {
	const fieldTypeObject = fieldTypes.find(({name}) => name === fieldType);

	if (fieldTypeObject) {
		return fieldTypeObject.label;
	}
	return fieldType;
};
