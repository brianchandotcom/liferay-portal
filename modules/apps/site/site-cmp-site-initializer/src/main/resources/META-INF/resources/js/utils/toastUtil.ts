/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openToast} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';

import {getFormattedLabel} from './getFormattedText';

const displayAssignSuccessToast = (title: string, value: string) => {
	openToast({
		message: sub(
			Liferay.Language.get('x-was-successfully-assigned-to-x'),
			getFormattedLabel(title),
			value
		),
		type: 'success',
	});
};

const displayErrorToast = (errorMessage?: string) => {
	openToast({
		message:
			errorMessage ||
			Liferay.Language.get('an-unexpected-error-occurred'),
		title: Liferay.Language.get('error'),
		type: 'danger',
	});
};

const displayStateSuccessToast = () => {
	openToast({
		message: Liferay.Language.get('state-was-successfully-updated'),
		type: 'success',
	});
};

export {displayAssignSuccessToast, displayErrorToast, displayStateSuccessToast};
