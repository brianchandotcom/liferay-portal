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

import {FRAGMENT_ENTRY_TYPES} from '../../config/constants/fragmentEntryTypes';
import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';
import {formIsMapped} from '../formIsMapped';
import {hasFormParent} from '../hasFormParent';
import {isUnmappedCollection} from '../isUnmappedCollection';

const LAYOUT_DATA_CHECK_ALLOWED_CHILDREN = {
	[LAYOUT_DATA_ITEM_TYPES.root]: (child) =>
		[
			LAYOUT_DATA_ITEM_TYPES.collection,
			LAYOUT_DATA_ITEM_TYPES.dropZone,
			LAYOUT_DATA_ITEM_TYPES.container,
			LAYOUT_DATA_ITEM_TYPES.row,
			LAYOUT_DATA_ITEM_TYPES.fragment,
			LAYOUT_DATA_ITEM_TYPES.form,
		].includes(child.type),
	[LAYOUT_DATA_ITEM_TYPES.collection]: () => false,
	[LAYOUT_DATA_ITEM_TYPES.collectionItem]: (child) =>
		[
			LAYOUT_DATA_ITEM_TYPES.collection,
			LAYOUT_DATA_ITEM_TYPES.container,
			LAYOUT_DATA_ITEM_TYPES.row,
			LAYOUT_DATA_ITEM_TYPES.fragment,
		].includes(child.type),
	[LAYOUT_DATA_ITEM_TYPES.dropZone]: () => false,
	[LAYOUT_DATA_ITEM_TYPES.container]: (child) =>
		[
			LAYOUT_DATA_ITEM_TYPES.collection,
			LAYOUT_DATA_ITEM_TYPES.container,
			LAYOUT_DATA_ITEM_TYPES.dropZone,
			LAYOUT_DATA_ITEM_TYPES.row,
			LAYOUT_DATA_ITEM_TYPES.fragment,
			LAYOUT_DATA_ITEM_TYPES.form,
		].includes(child.type),
	[LAYOUT_DATA_ITEM_TYPES.form]: (child, parent) =>
		formIsMapped(parent)
			? [
					LAYOUT_DATA_ITEM_TYPES.collection,
					LAYOUT_DATA_ITEM_TYPES.container,
					LAYOUT_DATA_ITEM_TYPES.dropZone,
					LAYOUT_DATA_ITEM_TYPES.row,
					LAYOUT_DATA_ITEM_TYPES.fragment,
			  ].includes(child.type)
			: false,
	[LAYOUT_DATA_ITEM_TYPES.row]: (child) =>
		[LAYOUT_DATA_ITEM_TYPES.column].includes(child.type),
	[LAYOUT_DATA_ITEM_TYPES.column]: (child) =>
		[
			LAYOUT_DATA_ITEM_TYPES.collection,
			LAYOUT_DATA_ITEM_TYPES.container,
			LAYOUT_DATA_ITEM_TYPES.dropZone,
			LAYOUT_DATA_ITEM_TYPES.row,
			LAYOUT_DATA_ITEM_TYPES.fragment,
			LAYOUT_DATA_ITEM_TYPES.form,
		].includes(child.type),
	[LAYOUT_DATA_ITEM_TYPES.fragment]: () => false,
	[LAYOUT_DATA_ITEM_TYPES.fragmentDropZone]: (child) =>
		[
			LAYOUT_DATA_ITEM_TYPES.collection,
			LAYOUT_DATA_ITEM_TYPES.dropZone,
			LAYOUT_DATA_ITEM_TYPES.container,
			LAYOUT_DATA_ITEM_TYPES.row,
			LAYOUT_DATA_ITEM_TYPES.fragment,
			LAYOUT_DATA_ITEM_TYPES.form,
		].includes(child.type),
};

/**
 * Checks if the given child can be nested inside given parent
 * @param {object} child
 * @param {object} parent
 * @param {{current: object}} layoutDataRef
 * @return {boolean}
 */
export default function checkAllowedChild(child, parent, layoutDataRef) {
	if (isUnmappedCollection(parent) || isUnmappedForm(parent)) {
		return false;
	}

	if (child.type === LAYOUT_DATA_ITEM_TYPES.fragment) {
		if (
			child.fragmentEntryType === FRAGMENT_ENTRY_TYPES.input &&
			!hasFormParent(parent, layoutDataRef.current)
		) {
			return false;
		}

		if (parent.type === LAYOUT_DATA_ITEM_TYPES.form && child.isWidget) {
			return false;
		}
	}

	return LAYOUT_DATA_CHECK_ALLOWED_CHILDREN[parent.type](child, parent);
}

function isUnmappedForm(item) {
	return item.type === LAYOUT_DATA_ITEM_TYPES.form && !formIsMapped(item);
}
