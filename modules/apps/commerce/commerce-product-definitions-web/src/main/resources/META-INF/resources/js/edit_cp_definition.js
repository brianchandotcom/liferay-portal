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
