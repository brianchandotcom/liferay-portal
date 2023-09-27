/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
