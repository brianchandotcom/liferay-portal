/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const displayCreateSuccessToast = (name: string) => {
	Liferay.Util.openToast({
		message: Liferay.Util.sub(
			Liferay.Language.get('x-was-created-successfully'),
			name
		),
		type: 'success',
	});
};

const displayEditSuccessToast = (name: string) => {
	Liferay.Util.openToast({
		message: Liferay.Util.sub(
			Liferay.Language.get('x-was-updated-successfully'),
			name
		),
		type: 'success',
	});
};

const displayErrorToast = () => {
	Liferay.Util.openToast({
		message: Liferay.Language.get('an-unexpected-system-error-occurred'),
		type: 'danger',
	});
};

export {displayCreateSuccessToast, displayEditSuccessToast, displayErrorToast};
