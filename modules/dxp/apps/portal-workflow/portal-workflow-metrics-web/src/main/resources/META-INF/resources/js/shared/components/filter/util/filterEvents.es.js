/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

const buildFallbackItems = fallbackKeys => {
	if (fallbackKeys) {
		return fallbackKeys.map(key => ({
			active: true,
			key,
		}));
	}

	return null;
};

const handleClickOutside = (callback, wrapperRef) => event => {
	const clickOutside = wrapperRef && !wrapperRef.contains(event.target);

	if (clickOutside) {
		callback(event);
	}
};

const addClickOutsideListener = listener => {
	document.addEventListener('mousedown', listener);
};

const removeClickOutsideListener = listener => {
	document.removeEventListener('mousedown', listener);
};

export {
	addClickOutsideListener,
	buildFallbackItems,
	handleClickOutside,
	removeClickOutsideListener,
};
