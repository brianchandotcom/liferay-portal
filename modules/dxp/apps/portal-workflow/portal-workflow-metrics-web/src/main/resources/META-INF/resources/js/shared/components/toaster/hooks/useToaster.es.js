/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
