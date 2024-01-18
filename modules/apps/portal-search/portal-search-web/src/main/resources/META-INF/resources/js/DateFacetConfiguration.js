/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function ({namespace: portletNamespace}) {
	const form = document.getElementById(`${portletNamespace}fm`);

	if (!form) {
		return;
	}

	function onSubmit(event) {
		event.preventDefault();

		const ranges = [];

		form.querySelectorAll('.range-form-row').forEach((item) => {
			if (!item.getAttribute('hidden')) {
				const label = item.querySelector('.label-input').value;

				const range = item.querySelector('.range-input').value;

				ranges.push({
					label,
					range,
				});
			}
		});

		form.querySelector('.ranges-input').value = JSON.stringify(ranges);

		submitForm(form);
	}

	form.addEventListener('submit', onSubmit);

	return {
		dispose() {
			form.removeEventListener('submit', onSubmit);
		},
	};
}
