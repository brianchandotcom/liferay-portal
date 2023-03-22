/* eslint-disable no-undef */
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

const queryParams = new URLSearchParams(window.location.search);
const hasNewSuccess = Boolean(queryParams.get('new-success'));
const hasEditSuccess = Boolean(queryParams.get('edit-success'));

if (hasNewSuccess) {
	Liferay.Util.openToast({
		message: 'Your MDF Request was successfully submited.',
		type: 'success',
	});
}

if (hasEditSuccess) {
	Liferay.Util.openToast({
		message: 'Your MDF Request was successfully edited.',
		type: 'success',
	});
}
