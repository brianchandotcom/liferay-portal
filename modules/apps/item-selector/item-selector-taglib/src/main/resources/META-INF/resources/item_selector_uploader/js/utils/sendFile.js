/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const noop = () => {};

function parse(req) {
	let result;

	try {
		result = JSON.parse(req.responseText);
	}
	catch (error) {
		result = req.responseText;
	}

	return result;
}

export default function sendFile({
	file,
	fileFieldName,
	onProgress = noop,
	onSuccess = noop,
	onError = noop,
	url,
}) {
	const formData = new FormData();
	const request = new XMLHttpRequest();

	request.upload.addEventListener('progress', (event) => {
		onProgress(Math.round((event.loaded * 100) / event.total));
	});

	request.addEventListener('readystatechange', () => {
		if (request.readyState === 4) {
			const response = parse(request);

			onProgress(null);

			if (request.status >= 200 && request.status < 300) {
				onSuccess(response);
			}
			else {
				onError(response);
			}
		}
	});

	formData.append(fileFieldName, file);
	request.open('POST', url);
	request.send(formData);

	return request;
}
