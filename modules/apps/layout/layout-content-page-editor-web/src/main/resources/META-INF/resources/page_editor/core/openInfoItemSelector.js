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

import {openSelectionModal} from 'frontend-js-web';

export function openInfoItemSelector(
	callback,
	eventName,
	itemSelectorURL,
	destroyedCallback = null
) {
	let _destroyedCallback = destroyedCallback;

	const invokeDestroyedCallbackOnce = () => {
		if (typeof _destroyedCallback === 'function') {
			_destroyedCallback();

			_destroyedCallback = null;
		}
	};

	openSelectionModal({
		onClose: invokeDestroyedCallbackOnce,
		onSelect: (selectedItem) => {
			if (selectedItem && selectedItem.value) {
				const infoItem = {
					...JSON.parse(selectedItem.value),
					type: selectedItem.returnType,
				};

				callback(infoItem);
			}

			invokeDestroyedCallbackOnce();
		},
		selectEventName: eventName,
		title: Liferay.Language.get('select'),
		url: itemSelectorURL,
	});
}
