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

import {openToast as liferayToast} from 'frontend-js-web';

export function openErrorToast({message}) {
	openToast(message, Liferay.Language.get('error'), 'danger');
}

export function openSuccessToast(
	message,
	title = Liferay.Language.get('success')
) {
	openToast(message, title, 'success');
}

export function openToast(message, title, type) {
	liferayToast({message, title, type});
}
