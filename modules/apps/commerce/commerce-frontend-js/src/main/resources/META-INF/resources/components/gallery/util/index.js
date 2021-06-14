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

import {fetch} from 'frontend-js-web';

export function fetchImage(url) {
	return new Promise((resolve) => {
		const image = window.document.createElement('img');

		image.onload = () => {
			resolve(url);
		};

		image.src = url;
	});
}

export function updateGallery(formFields, namespace, viewCPAttachmentURL) {
	const ddmFormValues = JSON.stringify(formFields);
	const formData = new FormData();

	formData.append(`${namespace}ddmFormValues`, ddmFormValues);
	formData.append('groupId', themeDisplay.getScopeGroupId());

	return fetch(viewCPAttachmentURL, {
		body: formData,
		headers: new Headers({'x-csrf-token': Liferay.authToken}),
		method: 'post',
	}).then((response) => response.json());
}
