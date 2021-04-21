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

import {ADD_MAPPED_INFO_ITEM} from './types';

/**
 * @param {object} options
 * @param {string} options.className
 * @param {string} options.classNameId
 * @param {string} options.classPK
 * @param {string} options.title
 * @return {object}
 */
export default function addMappedInfoItem({
	className,
	classNameId,
	classPK,
	classTypeId,
	subtype,
	title,
	type,
}) {
	return {
		className,
		classNameId,
		classPK,
		classTypeId,
		itemSubtype: subtype,
		itemType: type,
		title,
		type: ADD_MAPPED_INFO_ITEM,
	};
}
