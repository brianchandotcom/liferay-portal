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
import React, {useEffect, useState} from 'react';

import {
	downloadFileFromBlob,
	fetchFile,
} from '../utils/downloadSpreadsheetUtils';

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

let fetchController = null;

const DownloadSpreadsheetButton = ({fileURL, total}) => {
	const [loading, setLoading] = useState(false);
	const [toastMessage, setToastMessage] = useState(initialToastState);
	const [feedbackStatus, setFeedbackStatus] = useState(initialFeedbackState);

	let defaultDelayTimeout = null;
	let feedbackDelayTimeout = null;

	const handleSuccess = () => {
		setToastMessage((current) => ({
			...current,
			content: Liferay.Language.get('xls-was-successfully-generated'),
			type: 'success',
		}));

		setFeedbackStatus((current) => ({
			...current,
			content: Liferay.Language.get('xls-generated'),
			type: 'success',
		}));
	};

	const handleError = ({abortedRequest, error}) => {
		const type = abortedRequest ? 'warning' : 'danger';

		const toastContent = abortedRequest
			? Liferay.Language.get('xls-generation-has-been-cancelled')
			: `${Liferay.Language.get(
					'xls-generation-has-failed-try-again'
			  )}. ${error}`;

		const feedbackContent = abortedRequest
			? Liferay.Language.get('xls-generation-cancelled')
			: Liferay.Language.get('an-error-occurred');

		setToastMessage((current) => ({
			...current,
			content: toastContent,
			type,
		}));

		setFeedbackStatus((current) => ({
			...current,
			content: feedbackContent,
			type,
		}));
	};

	const handleUIState = () => {
		defaultDelayTimeout = setTimeout(() => {
			setLoading(false);

			setFeedbackStatus((current) => ({
				...current,
				show: true,
			}));

			setToastMessage((current) => ({
				...current,
				show: true,
			}));
		}, 500);

		feedbackDelayTimeout = setTimeout(() => {
			setFeedbackStatus(initialFeedbackState);
		}, 2500);
	};

	const buttonTextKey = loading
		? Liferay.Language.get('generating-xls')
		: feedbackStatus.show
		? feedbackStatus.content
		: Liferay.Language.get('export-xls');

	const clayIconSymbol =
		feedbackStatus.type === 'success'
			? 'check-circle'
			: feedbackStatus.type === 'danger'
			? 'exclamation-circle'
			: feedbackStatus.type === 'warning'
			? 'warning'
			: 'download';

	const disabledGenerateButton =
		loading || feedbackStatus.show || !parseInt(total, 10);

	const handleClick = async () => {
		setLoading(true);

		try {
			fetchController = new AbortController();
			const blob = await fetchFile({
				controller: fetchController,
				url: fileURL,
			});

			downloadFileFromBlob(blob);

			handleSuccess();
		} catch (error) {
			const abortedRequest = error.name === 'AbortError' ? true : false;

			handleError({abortedRequest, error});
		} finally {
			handleUIState();
		}
	};

	const handleCancelRequest = () => {
		fetchController.abort();
	};

	const handleToastClose = () => {
		setToastMessage(initialToastState);
	};

	useEffect(() => {
		return () => {
			clearTimeout(defaultDelayTimeout);
			clearTimeout(feedbackDelayTimeout);
		};
	}, []);

	return (
		<>
			<ClayTooltipProvider>
				<ClayButton
					borderless
					className={classnames('download-xls-button', {
						'download-xls-button--loading': loading,
						'text-danger':
							feedbackStatus.show &&
							feedbackStatus.type === 'danger',
						'text-success':
							feedbackStatus.show &&
							feedbackStatus.type === 'success',
						'text-warning':
							feedbackStatus.show &&
							feedbackStatus.type === 'warning',
					})}
					data-tooltip-align="top"
					disabled={disabledGenerateButton}
					displayType="secondary"
					onClick={handleClick}
					title={Liferay.Language.get(
						'download-your-data-in-a-xls-file'
					)}
				>
					<span className="inline-item inline-item-before">
						{loading ? (
							<ClayLoadingIndicator small />
						) : (
							<ClayIcon symbol={clayIconSymbol} />
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
						title={Liferay.Language.get('cancel-export')}
					/>
				</ClayTooltipProvider>
			)}

			{toastMessage.show && (
				<ClayAlert.ToastContainer>
					<ClayAlert
						autoClose={5000}
						className="download-xls-button__alert"
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

export default DownloadSpreadsheetButton;
