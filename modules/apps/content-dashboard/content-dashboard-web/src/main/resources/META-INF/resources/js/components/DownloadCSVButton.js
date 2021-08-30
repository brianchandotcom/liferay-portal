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
import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {ClayTooltipProvider} from '@clayui/tooltip';
import classnames from 'classnames';
import React, {useState} from 'react';

const initialToastState = {
	content: null,
	show: false,
	type: null,
};

const initialFeedbackState = {
	content: null,
	show: false,
	type: null,
};

const DownloadCSVButton = () => {
	const [loading, setLoading] = useState(false);
	const [toastMessage, setToastMessage] = useState(initialToastState);
	const [feedbackStatus, setFeedbackStatus] = useState(initialFeedbackState);

	const handleSuccess = () => {
		setToastMessage({
			content: Liferay.Language.get('csv-was-successfully-generated'),
			show: true,
			type: 'success',
		});

		setFeedbackStatus({
			content: Liferay.Language.get('csv-generated'),
			show: false,
			type: 'success',
		});
	};

	const handleError = () => {
		setToastMessage({
			content: Liferay.Language.get(
				'csv-generation-has-failed-try-again'
			),
			show: true,
			type: 'danger',
		});

		setFeedbackStatus({
			content: Liferay.Language.get('an-error-occurred'),
			show: false,
			type: 'error',
		});
	};

	const handleUIState = () => {
		setLoading(false);
		setFeedbackStatus({
			...feedbackStatus,
			show: true,
		});

		setTimeout(() => setFeedbackStatus(initialFeedbackState), 2000);
	};

	const buttonTextKey = loading
		? Liferay.Language.get('generating-csv')
		: feedbackStatus.show
		? feedbackStatus.content
		: Liferay.Language.get('csv');

	const disabledGenerateButton = loading || feedbackStatus.show;

	const handleClick = () => {
		setLoading(true);

		try {

			// todo implementation AJAX

			handleSuccess();
		}
		catch {
			handleError();
		}
		finally {
			handleUIState();
		}
	};

	const handleCancelRequest = () => {
		setLoading(false);
	};

	const handleToastClose = () => {
		setToastMessage(initialToastState);
	};

	return (
		<>
			<ClayTooltipProvider>
				<ClayButton
					borderless
					className={classnames('download-csv-button', {
						'download-csv-button--loading': loading,
						'text-error':
							feedbackStatus.show &&
							feedbackStatus.type === 'error',
						'text-success':
							feedbackStatus.show &&
							feedbackStatus.type === 'success',
					})}
					data-tooltip-align="top"
					disabled={disabledGenerateButton}
					displayType="secondary"
					onClick={handleClick}
					title={Liferay.Language.get(
						'download-your-data-in-a-csv-file'
					)}
				>
					<span className="inline-item inline-item-before">
						{loading ? (
							<ClayLoadingIndicator small />
						) : (
							<ClayIcon symbol="download" />
						)}
					</span>
					{buttonTextKey}
				</ClayButton>
			</ClayTooltipProvider>

			{loading && (
				<ClayTooltipProvider>
					<ClayButtonWithIcon
						borderless
						className="ml-2"
						data-tooltip-align="top"
						displayType="secondary"
						onClick={handleCancelRequest}
						symbol="times-circle"
						title={Liferay.Language.get('cancel-csv')}
					/>
				</ClayTooltipProvider>
			)}

			{toastMessage.show && (
				<ClayAlert.ToastContainer>
					<ClayAlert
						autoClose={5000}
						className="download-csv-button__alert"
						displayType={toastMessage.type}
						onClose={handleToastClose}
					>
						{toastMessage.content}
					</ClayAlert>
				</ClayAlert.ToastContainer>
			)}
		</>
	);
};

export default DownloadCSVButton;
