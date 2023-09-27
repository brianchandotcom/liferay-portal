/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openSelectionModal} from 'frontend-js-web';

export function openItemSelector({
	callback,
	eventName,
	itemSelectorURL,
	destroyedCallback = null,
	modalProps = {},
	transformValueCallback,
}) {
	openSelectionModal({
		onClose: destroyedCallback,
		onSelect: (selection) => {
			let infoItem = {
				...selection,
			};

			let value;

			if (selection.value) {
				if (typeof selection.value === 'string') {
					try {
						value = JSON.parse(selection.value);
					}
					catch (error) {}
				}
				else if (
					selection.value &&
					typeof selection.value === 'object'
				) {
					value = selection.value;
				}

				if (value) {
					delete infoItem.value;
					infoItem = {...infoItem, ...value};
				}
			}
			else if (typeof selection === 'object') {
				infoItem = Object.values(selection)[0];
			}

			infoItem = transformValueCallback(infoItem);

			infoItem = callback(infoItem);
		},
		selectEventName: eventName,
		title: Liferay.Language.get('select'),
		url: itemSelectorURL,
		...modalProps,
	});
}
