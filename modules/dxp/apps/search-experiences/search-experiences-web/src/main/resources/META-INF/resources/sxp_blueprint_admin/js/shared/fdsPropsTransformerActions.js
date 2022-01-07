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

import {fetch} from 'frontend-js-web';

import {DEFAULT_ERROR} from '../utils/constants';
import {openErrorToast, openSuccessToast} from '../utils/toasts';

export function download(url, parameters, title) {
	fetch(url, parameters)
		.then((response) => {
			if (!response.ok) {
				throw DEFAULT_ERROR;
			}

			return response.blob();
		})
		.then((responseBlob) => {
			const downloadElement = document.createElement('a');

			downloadElement.download = title + '.json';
			downloadElement.href = URL.createObjectURL(responseBlob);

			document.body.appendChild(downloadElement);

			downloadElement.click();

			openSuccessToast();
		})
		.catch(() => {
			openErrorToast();
		});
}
