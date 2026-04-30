/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetchParams} from 'commerce-frontend-js';

const readFileAsBase64 = (file) =>
	new Promise((resolve, reject) => {
		const reader = new FileReader();

		reader.onerror = () => reject(reader.error);
		reader.onload = () => {
			const result = reader.result || '';
			const commaIndex = result.indexOf(',');

			resolve(commaIndex >= 0 ? result.slice(commaIndex + 1) : result);
		};

		reader.readAsDataURL(file);
	});

export default function (context) {
	Liferay.provide(
		window,
		`${context.namespace}editCommerceOrderAttachment`,
		(event, form) => {
			event.preventDefault();

			const formData = new FormData(form);

			const fileInput = form.querySelector(
				`#${context.namespace}attachmentFile`
			);
			const newFile = fileInput?.files?.[0];

			const priorityValue = formData.get(`${context.namespace}priority`);

			const buildBody = (attachment) => {
				const body = {
					priority: priorityValue ? Number(priorityValue) : 0,
					restricted:
						formData.get(`${context.namespace}restricted`) === 'on',
					title: formData.get(`${context.namespace}title`),
					type: formData.get(`${context.namespace}type`),
				};

				if (attachment) {
					body.attachment = attachment;
				}

				return body;
			};

			const updateAttachment = (attachment) =>
				Liferay.Util.fetch(context.addURL, {
					body: JSON.stringify(buildBody(attachment)),
					headers: fetchParams.headers,
					method: 'POST',
				}).then((response) => {
					if (!response.ok) {
						return response.json().then((data) => {
							return Promise.reject(data);
						});
					}

					return Liferay.Util.fetch(context.editURL, {
						headers: fetchParams.headers,
						method: 'DELETE',
					}).then((response) => {
						if (!response.ok) {
							return response.json().then((data) => {
								return Promise.reject(data);
							});
						}
					});
				});

			const saveAttachment = (attachment) =>
				Liferay.Util.fetch(
					context.mode === 'edit' ? context.editURL : context.addURL,
					{
						body: JSON.stringify(buildBody(attachment)),
						headers: fetchParams.headers,
						method: context.mode === 'edit' ? 'PATCH' : 'POST',
					}
				).then((response) => {
					if (!response.ok) {
						return response.json().then((data) => {
							return Promise.reject(data);
						});
					}
				});

			const handleError = (error) => {
				window.top.Liferay.Util.openToast({
					message:
						error.message ||
						Liferay.Language.get('an-unexpected-error-occurred'),
					type: 'danger',
				});
			};

			const handleSuccess = () => {
				window.top.Liferay.Util.openToast({
					message: Liferay.Language.get(
						'your-request-completed-successfully'
					),
					type: 'success',
				});

				window.top.Liferay.fire('fds-update-display', {
					id: context.fdsId,
				});
				window.top.Liferay.fire('close-side-panel');
			};

			if (newFile) {
				return readFileAsBase64(newFile)
					.then((base64) => {
						if (context.mode === 'edit') {
							return updateAttachment(base64);
						}
						else {
							return saveAttachment(base64);
						}
					})
					.then(handleSuccess)
					.catch(handleError);
			}

			return saveAttachment().then(handleSuccess).catch(handleError);
		}
	);
}
