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

import {useEffect} from 'react';
import i18n from '../I18n';
import {Liferay} from '../services/liferay';
import isOperationType from '../utils/isOperationType';

const DEFAULT_ERROR = {
	message: i18n.translate('an-unexpected-error-occurred'),
	title: i18n.translate('error'),
	type: 'danger',
};

const DEFAULT_SUCCESS = {
	message: i18n.translate('your-request-completed-successfully'),
	title: i18n.translate('success'),
	type: 'success',
};

export default function useGlobalNetworkIndicator(networkStatus) {
	useEffect(() => {
		const {error: errorStatus, success} = networkStatus;

		if (errorStatus?.networkError) {
			const displayServerError =
				errorStatus.operation.getContext().displayServerError ?? true;

			if (displayServerError) {
				Liferay.Util.openToast({
					message:
						errorStatus?.networkError.result?.title ||
						DEFAULT_ERROR.message,
					type: DEFAULT_ERROR.type,
				});
			}
		}

		if (errorStatus?.response) {
			const displayErrors =
				errorStatus.operation.getContext().displayErrors ?? true;

			if (displayErrors) {
				errorStatus.response.forEach((error) => {
					let errorToast = DEFAULT_ERROR;

					if (displayErrors && displayErrors[error.exception.errno]) {
						const displayError =
							displayErrors[error.exception.errno];

						errorToast = {
							message:
								displayError.message || DEFAULT_ERROR.message,
							title: displayError.title || DEFAULT_ERROR.title,
							type: displayError.type || DEFAULT_ERROR.type,
						};
					}

					Liferay.Util.openToast(errorToast);
				});
			}
		}

		if (success) {
			const displaySuccess =
				success.operation.getContext().displaySuccess ?? true;

			const isValidMutation =
				displaySuccess &&
				isOperationType(success.operation, 'mutation');

			if (isValidMutation) {
				Liferay.Util.openToast({
					message: displaySuccess?.message || DEFAULT_SUCCESS.message,
					title: displaySuccess?.title || DEFAULT_SUCCESS.title,
					type: displaySuccess?.type || DEFAULT_SUCCESS.type,
				});
			}
		}
	}, [networkStatus]);
}
