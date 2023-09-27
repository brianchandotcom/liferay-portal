/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function ({namespace, workflowAction}) {
	document
		.getElementById(`${namespace}publishButton`)
		.addEventListener('click', (event) => {
			event.preventDefault();

			document.getElementById(
				`${namespace}workflowAction`
			).value = workflowAction;

			submitForm(document.getElementById(`${namespace}fm`));
		});
}
