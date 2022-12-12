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

import {ERROR_MESSAGE} from './constants';

enum Status {
	BadRequest = 400,
	Forbidden = 403,
	InternalServerError = 500,
	MultipleChoices = 300,
	NoContent = 204,
	NotFound = 404,
	Unauthorized = 401,
}

enum Message {
	Error = 'Request Error',
	Unauthorized = 'Unauthorized Access',
}

const error = (message: Message): {error: Error} => ({
	error: new Error(message),
});

type TStatus = {
	[key in Status]: {error: Error} | {ok: boolean};
};

const STATUS: TStatus = {
	[Status.NoContent]: {ok: true},
	[Status.BadRequest]: error(Message.Error),
	[Status.InternalServerError]: error(Message.Error),
	[Status.Unauthorized]: error(Message.Unauthorized),
	[Status.Forbidden]: error(Message.Error),
	[Status.NotFound]: error(Message.Error),
	[Status.MultipleChoices]: error(Message.Error),
};

async function request(
	path: string,
	config: RequestInit,
	message: string = ERROR_MESSAGE
) {
	const endpoint = `/o/analytics-settings-rest/v1.0${path}`;

	const response = await fetch(endpoint, {
		...config,
		headers: {'Content-Type': 'application/json'},
	});

	const status = STATUS[response.status as Status];

	if (response.status === Status.Forbidden) {
		window.location.reload();
	}
	else if (response.status === Status.NoContent) {
		return status;
	}
	else if (response.status >= Status.MultipleChoices || status) {
		Liferay.Util.openToast({
			message,
			type: 'danger',
		});

		return status;
	}
	else if (config.method === 'GET') {
		return response.json();
	}

	return response;
}

export default request;
