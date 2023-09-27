/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openImageSelector} from '../../common/openImageSelector';

function createEditor(element, changeCallback, destroyCallback) {
	openImageSelector(
		(image) => changeCallback(image && image.url ? image.url : ''),
		destroyCallback
	);
}

function destroyEditor(_element) {}

function render(element, value) {
	if (value && (typeof value === 'string' || value?.url)) {
		element.style.backgroundImage = `url("${value?.url ?? value}")`;
		element.style.backgroundSize = 'cover';
	}
}

export default {
	createEditor,
	destroyEditor,
	render,
};
