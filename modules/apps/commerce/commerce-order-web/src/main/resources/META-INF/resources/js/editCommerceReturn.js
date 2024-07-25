/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function editCommerceReturn({namespace}) {
	let refreshPage = false;

	Liferay.on('return-item-updated', () => {
		refreshPage = true;
	});

	Liferay.on('side-panel-closed', () => {
		if (refreshPage) {
			window.location.reload();
		}
	});

	const form = document.getElementById(`${namespace}fm`);

	const markAsCompletedButton = document.getElementById(
		`${namespace}markAsCompletedButton`
	);

	if (form && markAsCompletedButton) {
		markAsCompletedButton.addEventListener('click', (event) => {
			event.preventDefault();

			const cmd = form.querySelector(`#${namespace}cmd`);

			cmd.value = 'markAsCompleted';

			submitForm(form);
		});
	}
}
