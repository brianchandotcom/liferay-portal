/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

function handleSubmit({CMD, PUBLISH, namespace}) {
	const submitButton = document.getElementById(`${namespace}submitButton`);

	if (!submitButton) {
		return;
	}

	submitButton.addEventListener('click', (event) => {
		event.preventDefault();

		const cmdInput = document.getElementById(namespace + CMD);

		if (cmdInput) {
			cmdInput.value = PUBLISH;
		}

		submitForm(document.getElementById(`${namespace}fm`));
	});
}

export default function (context) {
	handleSubmit(context);
}
