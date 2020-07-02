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

import {POSITIONS} from './constants/positions';

const addLoadingAnimation = (targetItem, targetPosition) => {
	const itemIsDropzone = targetItem.classList.contains('portlet-dropzone');
	const loading = document.createElement('div');
	loading.classList.add('loading-animation');

	if (itemIsDropzone) {
		targetItem.appendChild(loading);
	}
	else {
		const parent = targetItem.parentElement;
		const item =
			targetPosition === POSITIONS.top
				? targetItem
				: targetItem.nextSibling;

		parent.insertBefore(loading, item);
	}

	return loading;
};

export default addLoadingAnimation;
