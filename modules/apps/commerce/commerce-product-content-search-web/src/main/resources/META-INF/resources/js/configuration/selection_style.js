/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function ({namespace}) {
	const selectionStyleCustomRenderer = document.querySelectorAll(
		`[id^=${namespace}selectionStyle]`
	);
	const form = document.getElementById(`${namespace}fm`);

	const submit = () => {
		submitForm(form);
	};
	selectionStyleCustomRenderer.forEach((input) => {
		input.addEventListener('click', submit);
	});

	return {
		dispose() {
			selectionStyleCustomRenderer?.forEach((input) => {
				input.removeEventListener('click', submit);
			});
		},
	};
}
