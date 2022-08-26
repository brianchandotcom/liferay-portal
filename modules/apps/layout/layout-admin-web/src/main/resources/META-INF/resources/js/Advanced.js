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

export default function ({defaultTarget = '', namespace}) {
	const targetType = document.getElementById(`${namespace}targetType`);

	targetType.addEventListener('change', (event) => {
		const target = document.getElementById(`${namespace}target`);

		const specificFrameWrapper = target.parentElement;

		if (event.target.value === 'useNewTab') {
			specificFrameWrapper.classList.add('hide');
			target.value = '_blank';
		}
		else {
			specificFrameWrapper.classList.remove('hide');

			target.value = defaultTarget;
		}
	});
}
