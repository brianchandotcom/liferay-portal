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
import {render} from 'frontend-js-react-web';
import {unmountComponentAtNode} from 'react-dom';
import React from 'react';

const DEFAULT_ALERT_CONTAINER_ID = 'alertContainer';

const DEFAULT_RENDER_DATA = {
	portletId: 'UNKNOWN_PORTLET_ID'
};

const TOAST_AUTO_CLOSE_INTERVAL = 5000;

const getDefaultAlertContainer = () => {
	let container = document.getElementById(DEFAULT_ALERT_CONTAINER_ID);

	if (!container) {
		container = document.createElement('div');
		container.id = DEFAULT_ALERT_CONTAINER_ID;
		document.body.appendChild(container);
	}

	return container;
};

const Toast = ({displayType, message, onClose, title, toastProps, variant}) => {
	return (
		<ClayAlert.ToastContainer>
			<ClayAlert
				autoClose={TOAST_AUTO_CLOSE_INTERVAL}
				displayType={displayType}
				onClose={onClose}
				title={title}
				variant={variant}
				{...toastProps}
			>
				{message}
			</ClayAlert>
		</ClayAlert.ToastContainer>
	);
};

/**
 * Function that implements the Toast pattern, which allows to present feedback
 * to user actions as a toast message in the lower left corner of the page
 *
 * @param {string} message The message to show in the toast notification
 * @param {string} title The title associated with the message
 * @param {string} displayType The displayType of notification to show. It can be one of the
 * following: 'danger', 'info', 'success', 'warning'
 * @return {ClayToast} The Alert toast created
 * @review
 */

function openToast({
	containerId,
	message = '',
	renderData = DEFAULT_RENDER_DATA,
	title = Liferay.Language.get('success'),
	toastProps = {},
	type = 'success',
	variant
}) {
	const container =
		document.getElementById(containerId) || getDefaultAlertContainer();

	unmountComponentAtNode(container);

	const onClose = () => unmountComponentAtNode(container);

	const ToastComponent = () => (
		<Toast
			displayType={type}
			message={message}
			onClose={onClose}
			title={title}
			toastProps={toastProps}
			variant={variant}
		/>
	);

	render(ToastComponent, renderData, container);
}

export {openToast};
export default openToast;
