/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
