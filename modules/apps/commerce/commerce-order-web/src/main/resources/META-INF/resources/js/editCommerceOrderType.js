/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function ({namespace, workflowAction}) {
	document
		.getElementById(`${namespace}publishButton`)
		.addEventListener('click', (event) => {
			event.preventDefault();

			const form = document.getElementById(`${namespace}fm`);

			if (!form) {
				throw new Error(`Form with id: ${namespace}fm not found!`);
			}

			const workflowActionInput = document.getElementById(
				`${namespace}workflowAction`
			);

			if (workflowActionInput) {
				workflowActionInput.value = workflowAction;
			}

			submitForm(form);
		});
}
