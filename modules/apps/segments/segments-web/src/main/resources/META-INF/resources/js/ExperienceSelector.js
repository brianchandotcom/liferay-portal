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
	const dropdown = document.getElementById(`${namespace}dropdown`);
	const dropdownToggle = document.getElementById(
		`${namespace}dropdownToggle`
	);

	const handleClick = () => {
		if (dropdownToggle.getAttribute('aria-expanded') === 'false') {
			dropdownToggle.setAttribute('aria-expanded', 'true');
			dropdown.classList.add('show');
		}
		else {
			dropdownToggle.setAttribute('aria-expanded', 'false');
			dropdown.classList.remove('show');
		}
	};

	if (dropdown && dropdownToggle) {
		dropdownToggle.addEventListener('click', handleClick);
	}

	return {
		dispose() {
			if (dropdownToggle) {
				dropdownToggle.removeEventListener('click', handleClick);
			}
		},
	};
}
