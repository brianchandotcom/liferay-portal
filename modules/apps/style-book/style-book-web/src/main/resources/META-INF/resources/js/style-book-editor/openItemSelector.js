/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openSelectionModal} from 'frontend-js-web';

import {config} from './config';

export default function openItemSelector({
	callback,
	destroyedCallback = null,
	itemSelectorURL,
}) {
	openSelectionModal({
		onClose: destroyedCallback,
		onSelect: (selectedItem) => {
			callback(selectedItem);
		},
		selectEventName: `${config.namespace}selectPreviewItem`,
		title: Liferay.Language.get('select'),
		url: itemSelectorURL,
	});
}
