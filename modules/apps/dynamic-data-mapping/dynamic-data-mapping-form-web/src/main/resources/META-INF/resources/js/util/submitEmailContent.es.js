/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {convertToFormData, makeFetch} from 'data-engine-js-components-web';
import {openToast} from 'frontend-js-web';

const openNotification = ({error, message}) => {
	const openToastParams = {message};

	if (error) {
		openToastParams.title = Liferay.Language.get('error');
		openToastParams.type = 'danger';
	}
	else {
		openToastParams.title = Liferay.Language.get('success');
	}

	openToast(openToastParams);
};

export function submitEmailContent({
	addresses,
	message,
	portletNamespace,
	shareFormInstanceURL,
	subject,
}) {
	if (!addresses || !addresses.length) {
		return;
	}

	const data = {
		[`${portletNamespace}addresses`]: addresses
			.map(({label}) => label)
			.join(','),
		[`${portletNamespace}message`]: message,
		[`${portletNamespace}subject`]: subject,
	};

	makeFetch({
		body: convertToFormData(data),
		method: 'POST',
		url: shareFormInstanceURL,
	})
		.then((response) => {
			return response.successMessage
				? openNotification({message: response.successMessage})
				: openNotification({
						error: true,
						message: response.errorMessage,
				  });
		})
		.catch((error) => {
			throw new Error(error);
		});
}
