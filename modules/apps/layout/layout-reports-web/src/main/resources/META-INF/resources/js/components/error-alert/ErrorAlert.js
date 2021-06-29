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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayLink from '@clayui/link';
import React, {useContext, useState} from 'react';

import {
	API_KEY_ERROR_CODE,
	API_KEY_INVALID_STATUS,
	SERVER_ERROR_CODE,
} from '../../constants/pageSpeed';
import {StoreStateContext} from '../../context/StoreContext';
import ErrorAlertExtendedInfo from './ErrorAlertExtendedInfo';

const ErrorAlert = () => {
	const [showErrorInfo, setShowErrorInfo] = useState(false);
	const expandInfoHandler = () => {
		setShowErrorInfo((currentValue) => !currentValue);
	};

	const {data, error} = useContext(StoreStateContext);

	const {configureGooglePageSpeedURL, privateLayout} = data;
	const {code: statusCode, status} = error;

	const isApiKeyError =
		statusCode === API_KEY_ERROR_CODE && status === API_KEY_INVALID_STATUS;
	const isServerError = statusCode === SERVER_ERROR_CODE;
	const unknownError = !isApiKeyError && !isServerError;

	const unknownError =
		statusCode >= API_KEY_ERROR_CODE && !isApiKeyError && !isServerError;

	const title = isApiKeyError
		? Liferay.Language.get('incorrect-api-key')
		: isServerError
		? Liferay.Language.get("this-page-can't-be-audited")
		: Liferay.Language.get('an-unexpected-error-occurred');

	const actionTitle = showErrorInfo
		? Liferay.Language.get('hide-error-details')
		: Liferay.Language.get('show-error-details');

	const expandInfoHandler = () => {
		setShowErrorInfo((currentValue) => !currentValue);
	};

	return (
		<ClayAlert
			displayType={apiKeyError || unknownError ? 'danger' : 'warning'}
			variant="stripe"
		>
			<p>
				<span
					className={isServerError ? 'font-weight-bold' : undefined}
				>
					{title}
				</span>
				{pageCanNotBeAudited && (
					<span className="ml-1">
						{Liferay.Language.get(
							'page-not-accessible-from-internet'
						)}
					</span>
				)}
				{(apiKeyError || serverError) && (
					<ClayButton
						className="font-weight-bold link-primary ml-1"
						displayType="unstyled"
						onClick={expandInfoHandler}
					>
						{actionTitle}
					</ClayButton>
				)}
			</p>
			{showErrorInfo && <ErrorAlertExtendedInfo error={error} />}
			{apiKeyError && configureGooglePageSpeedURL && (
				<ClayAlert.Footer>
					<ClayLink
						className="alert-btn btn btn-outline-danger"
						href={configureGooglePageSpeedURL}
					>
						{Liferay.Language.get('set-api-key')}
					</ClayLink>
				</ClayAlert.Footer>
			)}
			{userHasNotPrivileges && apiKeyError && (
				<p>
					{Liferay.Language.get(
						'you-can-set-the-api-key-from-site-settings-pages-google-pagespeed-insights'
					)}
				</p>
			)}
		</ClayAlert>
	);
};

export default ErrorAlert;
