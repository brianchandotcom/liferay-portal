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

import {stringify} from '../util/util';

const stringOrNull = (string) => {
	return string ? `'${string}'` : null;
};

const javascriptExample = ({contentType, data, method, url}) =>
	`const contentType = ${stringOrNull(contentType)};
const data = ${stringify(data)};
const method = '${method.toUpperCase()}';

const request = {
	method: method
};

if (method === 'POST' || method === 'PUT') {
	if (contentType === 'application/json') {
		request.body = JSON.stringify(data);

		const headers = new Headers();

		headers.append('Content-Type', contentType);

		request.headers = headers;
	}
	else if (contentType === 'multipart/form-data') {
		const formData  = new FormData();

		for(const name in data) {
			formData.append(name, data[name]);
		}

		request.body = formData;
	}
}

Liferay.Util.fetch(
	'${url}',
	request
).then(
	res => {
		let retVal;

		if (method == 'delete' && res.status == 204) {
			retVal = 'Deleted Successfully';
		}
		else {
			retVal = res.json();
		}

		return retVal;
	}
).then(res => {
	console.log('res', res);
});`;

export default javascriptExample;
