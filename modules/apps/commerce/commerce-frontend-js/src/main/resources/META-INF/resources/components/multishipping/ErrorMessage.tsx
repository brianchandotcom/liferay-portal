/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import React from 'react';

import {IAPIResponseError, IFieldError} from './Types';

interface IErrorMessageProps {
	errors: IFieldError;
	name: string;
}

export function showError(error: IAPIResponseError) {
	Liferay.Util.openToast({
		message:
			error.detail ||
			error.errorDescription ||
			Liferay.Language.get('an-unexpected-system-error-occurred'),
		type: 'danger',
	});
}

export default function ErrorMessage({errors, name}: IErrorMessageProps) {
	return (
		<>
			{!!errors[name] && (
				<ClayForm.FeedbackItem>
					<ClayForm.FeedbackIndicator symbol="exclamation-full" />

					{errors[name]}
				</ClayForm.FeedbackItem>
			)}
		</>
	);
}
