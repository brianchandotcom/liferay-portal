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

export const submitEmailContent = ({
	addresses,
	message,
	portletNamespace,
	shareFormInstanceURL,
	subject,
}) => {
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
};
