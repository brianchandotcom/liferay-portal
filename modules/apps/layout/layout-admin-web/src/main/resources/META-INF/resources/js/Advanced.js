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

export default function ({namespace}) {
	const selectedFrameType = document.getElementById(
		`${namespace}TypeSettingsProperties_selectFrame`
	);

	const specificFrame = document.getElementById(
		`${namespace}TypeSettingsProperties_target`
	);

	const specificFrameWrapper = specificFrame.parentElement;

	selectedFrameType.addEventListener('change', (event) => {
		if (event.target.value === 'open-in-a-specific-frame') {
			specificFrame.value = '';
			specificFrameWrapper.classList.remove('hide');
		}
		else {
			specificFrameWrapper.classList.add('hide');
			specificFrame.value = '_blank';
		}
	});
}
