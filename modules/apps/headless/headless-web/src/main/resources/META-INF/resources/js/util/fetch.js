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

const fetch = (url, method = 'get', data, contentType) => {
	const request = {
		method: method.toUpperCase(),
		headers: {
			Authorization: 'Basic dGVzdEBsaWZlcmF5LmNvbTp0ZXN0',
		},
	};

	if (method === 'post' || method === 'put') {
		if (contentType === 'application/json') {
			request.body = JSON.stringify(data);
		}
		else if (contentType === 'multipart/form-data') {
			const formData = new FormData();

			for (const name of data) {
				formData.append(name, data[name]);
			}

			request.body = formData;
		}
	}

	return Liferay.Util.fetch(url, request).then((res) => {
		let retVal;

		if (method === 'delete' && res.status === 204) {
			retVal = 'Deleted Successfully';
		}
		else {
			retVal = res.json();
		}

		return retVal;
	});
};

export default fetch;
