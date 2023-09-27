/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function ({namespace}) {
	const form = document.getElementById(`${namespace}fm`);
	const cpContentListRendererKeySelect = document.getElementById(
		`${namespace}cpContentListRendererKeySelect`
	);

	const submit = () => {
		submitForm(form);
	};
	cpContentListRendererKeySelect.addEventListener('change', submit);

	return {
		dispose() {
			cpContentListRendererKeySelect.removeEventListener(
				'change',
				submit
			);
		},
	};
}
