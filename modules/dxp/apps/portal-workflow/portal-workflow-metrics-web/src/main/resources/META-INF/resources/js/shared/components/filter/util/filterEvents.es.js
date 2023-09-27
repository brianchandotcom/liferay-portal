/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const buildFallbackItems = (fallbackKeys) => {
	if (fallbackKeys) {
		return fallbackKeys.map((key) => ({
			active: true,
			key,
		}));
	}

	return null;
};

const handleClickOutside = (callback, wrapperRef) => (event) => {
	const clickOutside = wrapperRef && !wrapperRef.contains(event.target);

	if (clickOutside) {
		callback(event);
	}
};

const addClickOutsideListener = (listener) => {
	document.addEventListener('mousedown', listener);
};

const removeClickOutsideListener = (listener) => {
	document.removeEventListener('mousedown', listener);
};

export {
	addClickOutsideListener,
	buildFallbackItems,
	handleClickOutside,
	removeClickOutsideListener,
};
