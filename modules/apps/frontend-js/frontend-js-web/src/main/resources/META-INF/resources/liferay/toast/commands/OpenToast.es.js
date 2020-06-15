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
import React from 'react';
import {unmountComponentAtNode} from 'react-dom';

const DEFAULT_ALERT_CONTAINER_ID = 'alertContainer';

const DEFAULT_RENDER_DATA = {
	portletId: 'UNKNOWN_PORTLET_ID',
};

const TOAST_AUTO_CLOSE_INTERVAL = 5000;

const TYPES = {
	HTML: 'html',
	TEXT: 'text',
};

const Text = ({allowHTML, string = null}) => {
	if (allowHTML) {
		return <div dangerouslySetInnerHTML={{__html: string}} />;
	}

	return string;
};

const getDefaultAlertContainer = () => {
	let container = document.getElementById(DEFAULT_ALERT_CONTAINER_ID);

	if (!container) {
		container = document.createElement('div');
		container.id = DEFAULT_ALERT_CONTAINER_ID;
		document.body.appendChild(container);
	}

	return container;
};

function AlertContainer({children, hasWrapper = true}) {
	if (hasWrapper) {
		return <ClayAlert.ToastContainer>{children}</ClayAlert.ToastContainer>;
	}

	return <>children</>;
}

/**
 * Function that implements the Toast pattern, which allows to present feedback
 * to user actions as a toast message in the lower left corner of the page
 *
 * @param {number|boolean} autoClose Flag to indicate alert should automatically call onClose.
 * It also accepts a duration (in ms) which indicates how long to wait. If true is passed in, the
 * timeout will be 10000ms. See https://clayui.com/docs/components/alert.html for more details.
 * @param {HTMLElement} container A container to be used to the Alert being positioned relatively.
 * @param {string} containerId A containerId of the element to be opened relatively.
 * @param {string|HTML} message The message to show in the toast notification
 * @param {string|HTML} title The title associated with the message
 * @param {string} displayType The displayType of notification to show. It can be one of the
 * following: 'danger', 'info', 'success', 'warning'
 * @return {ClayToast} The Alert toast created
 * @review
 */

function openToast({
	autoClose = TOAST_AUTO_CLOSE_INTERVAL,
	container,
	containerId,
	message = '',
	messageType = TYPES.TEXT,
	onClick = () => {},
	renderData = DEFAULT_RENDER_DATA,
	title,
	titleType = TYPES.TEXT,
	toastProps = {},
	type = 'success',
	variant,
}) {
	const componentContainer =
		container ||
		document.getElementById(containerId) ||
		getDefaultAlertContainer();

	unmountComponentAtNode(componentContainer);

	const onClose = () => unmountComponentAtNode(componentContainer);

	render(
		<AlertContainer hasWrapper={!containerId}>
			<ClayAlert
				autoClose={autoClose}
				displayType={type}
				onClick={(event) => onClick({event, onClose})}
				onClose={onClose}
				title={
					<Text allowHTML={titleType === TYPES.HTML} string={title} />
				}
				variant={variant}
				{...toastProps}
			>
				<Text allowHTML={messageType === TYPES.HTML} string={message} />
			</ClayAlert>
		</AlertContainer>,
		renderData,
		componentContainer
	);
}

export {openToast};
export default openToast;
