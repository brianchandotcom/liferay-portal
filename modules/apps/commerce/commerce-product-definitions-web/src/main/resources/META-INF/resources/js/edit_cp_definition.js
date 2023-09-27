/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openConfirmModal} from 'frontend-js-web';

function handlePublish({WORKFLOW_ACTION_PUBLISH, namespace}) {
	const publishButton = document.getElementById(`${namespace}publishButton`);

	publishButton.addEventListener('click', (event) => {
		event.preventDefault();

		const workflowActionInput = document.getElementById(
			`${namespace}workflowAction`
		);

		if (workflowActionInput) {
			workflowActionInput.value = WORKFLOW_ACTION_PUBLISH;
		}

		submitForm(document.getElementById(`${namespace}fm`));
	});
}

function handleSaveAsDraft({
	message,
	namespace,
	showConfirmationMessage,
	title,
}) {
	const saveAsDraftButton = document.getElementById(
		`${namespace}saveAsDraftButton`
	);

	if (saveAsDraftButton) {
		saveAsDraftButton.addEventListener('click', (event) => {
			event.preventDefault();

			const form = document.getElementById(`${namespace}fm`);

			const input = document.createElement('input');

			input.name = `${namespace}saveAsDraft`;
			input.type = 'hidden';
			input.value = 'true';

			form.appendChild(input);

			if (showConfirmationMessage) {
				openConfirmModal({
					message,
					onConfirm: (confirmed) => {
						if (confirmed) {
							submitForm(form);
						}
					},
					title,
				});
			}
			else {
				submitForm(form);
			}
		});
	}
}

export default function (context) {
	handlePublish(context);

	handleSaveAsDraft(context);
}
