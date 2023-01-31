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
import {openToast} from 'frontend-js-web';
import React, {useState} from 'react';

function ToastAlert10Secs({
	autoClose,
	buttonText,
	displayType,
	message,
	onButtonClick,
	onClose,
	title,
}) {
	return (
		<ClayAlert.ToastContainer id="ToastAlertContainer">
			<ClayAlert
				autoClose={autoClose}
				displayType={displayType}
				onClose={() => onClose()}
				title={title}
			>
				<a href={themeDisplay.getURLHome()}>{message}</a>

				<ClayAlert.Footer>
					<ClayButton.Group>
						<ClayButton alert onClick={() => onButtonClick()}>
							{buttonText}
						</ClayButton>
					</ClayButton.Group>
				</ClayAlert.Footer>
			</ClayAlert>
		</ClayAlert.ToastContainer>
	);
}

export default function ClaySampleToastAlert() {
	const [showAlert, setShowAlert] = useState(false);
	const onClick5Seconds = () => {
		openToast({
			message: Liferay.Language.get(
				'your-request-completed-successfully'
			),
			type: 'info',
		});
	};

	const onClickFail = () => {
		openToast({
			autoClose: 20000,
			message: Liferay.Language.get('an-unexpected-error-occurred'),
			title: Liferay.Language.get('error'),
			type: 'danger',
		});
	};

	const onClickSuccess = () => {
		openToast({
			autoClose: false,
			message: Liferay.Language.get(
				'your-request-completed-successfully'
			),
			title: Liferay.Language.get('success'),
			type: 'success',
		});
	};

	return (
		<div data-qa-id="claySampleToastAlert">
			<h3>TOAST ALERT MESSAGE</h3>

			{showAlert && (
				<ToastAlert10Secs
					autoClose={10000}
					buttonText="Button"
					displayType="info"
					message="This toast alert with action button should disappear after 10 seconds"
					onButtonClick={() => {
						Liferay.Util.navigate(themeDisplay.getURLHome());
					}}
					onClose={() => setShowAlert(false)}
					title="Info:"
				/>
			)}

			<div className="sheet-footer">
				<ClayButton.Group spaced>
					<ClayButton onClick={onClickSuccess} type="submit">
						{Liferay.Language.get('success-submit')}
					</ClayButton>

					<ClayButton
						displayType="secondary"
						onClick={onClickFail}
						type="submit"
					>
						{Liferay.Language.get('fail-submit')}
					</ClayButton>

					<ClayButton onClick={onClick5Seconds} type="submit">
						{Liferay.Language.get('disappear-after-5-secs')}
					</ClayButton>

					<ClayButton
						onClick={() => setShowAlert(true)}
						type="submit"
					>
						{Liferay.Language.get('disappear-after-10-secs')}
					</ClayButton>
				</ClayButton.Group>
			</div>
		</div>
	);
}
