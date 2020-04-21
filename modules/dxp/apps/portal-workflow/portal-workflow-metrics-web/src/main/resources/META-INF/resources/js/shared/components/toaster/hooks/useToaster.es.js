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

import {useContext} from 'react';

import {ToasterContext} from '../ToasterProvider.es';

const useToaster = () => {
	const {addToast, ...context} = useContext(ToasterContext);

	const toaster = {
		danger: (message, title, autoClose = 5000) => {
			addToast({
				autoClose,
				message: message || Liferay.Language.get('error'),
				title: title || `${Liferay.Language.get('error')}:`,
				type: 'danger',
			});
		},
		info: (message, title, autoClose = 5000) => {
			addToast({
				autoClose,
				message,
				title,
				type: 'info',
			});
		},
		success: (message, title, autoClose = 5000) => {
			addToast({
				autoClose,
				message: message || Liferay.Language.get('success'),
				title: title || `${Liferay.Language.get('success')}:`,
				type: 'success',
			});
		},
		warning: (message, title, autoClose = 5000) => {
			addToast({
				autoClose,
				message,
				title,
				type: 'warning',
			});
		},
	};

	return {
		...context,
		...toaster,
		addToast,
	};
};

export {useToaster};
