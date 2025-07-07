/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openToast} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';

const displayCreateSuccessToast = (name: string) => {
	openToast({
		message: sub(Liferay.Language.get('x-was-created-successfully'), name),
		type: 'success',
	});
};

const displayEditSuccessToast = (name: string) => {
	openToast({
		message: sub(Liferay.Language.get('x-was-updated-successfully'), name),
		type: 'success',
	});
};

const displayRequestSuccessToast = () => {
	openToast({
		message: Liferay.Language.get('your-request-completed-successfully'),
		title: Liferay.Language.get('success'),
		type: 'success',
	});
};

const displaySystemErrorToast = () => {
	openToast({
		message: Liferay.Language.get('an-unexpected-system-error-occurred'),
		type: 'danger',
	});
};

const displayErrorToast = () => {
	openToast({
		message: Liferay.Language.get('an-unexpected-error-occurred'),
		title: Liferay.Language.get('error'),
		type: 'danger',
	});
};

const displayNameInUseErrorToast = () => {
	openToast({
		message: Liferay.Language.get(
			'please-enter-a-unique-name.-this-one-is-already-in-use'
		),
		title: Liferay.Language.get('error'),
		type: 'danger',
	});
};

export {
	displayCreateSuccessToast,
	displayEditSuccessToast,
	displayErrorToast,
	displayRequestSuccessToast,
	displaySystemErrorToast,
	displayNameInUseErrorToast,
};
