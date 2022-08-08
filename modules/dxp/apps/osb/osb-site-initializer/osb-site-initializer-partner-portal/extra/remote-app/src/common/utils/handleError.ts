/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {LiferayError} from '../enums/liferayError';
import {Liferay} from '../services/liferay';

const DEFAULT_MESSAGE = 'An unexpected error occured.';

type ToastErrorMessage = {
	[key in LiferayError]?: string;
};

const toastMessages: ToastErrorMessage = {};

export default function handleError(status: LiferayError) {
	Liferay.Util.openToast({
		message: toastMessages[status] || DEFAULT_MESSAGE,
		type: 'danger',
	});
}
