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

import {fetch, openToast} from 'frontend-js-web';

export default function RepositoryBrowserComponent({
	namespace,
	parentFolderId,
	repositoryBrowserURL,
	repositoryId,
}) {
	const fileInput = document.getElementById(`${namespace}file`);

	const onInputChange = () => {
		const formData = new FormData();

		formData.append('file', fileInput.files[0]);

		const uploadFileURL = `${repositoryBrowserURL}?repositoryId=${repositoryId}&parentFolderId=${parentFolderId}`;

		fetch(uploadFileURL, {
			body: formData,
			method: 'PUT',
		})
			.then(() => window.location.reload())
			.catch(() => {
				openToast({
					message: Liferay.Language.get(
						'an-unexpected-error-occurred'
					),
					type: 'danger',
				});
			});
	};

	fileInput.addEventListener('change', onInputChange);

	return {
		dispose() {
			fileInput.removeEventListener('change', onInputChange);
		},
	};
}
