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

const DEFAULT_ALERT_CONTAINER_ID = 'ToastAlertContainer';

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

const getRootElement = ({container, containerId}) => {
	if (container || containerId) {
		container = container || document.getElementById(containerId);

		if (container) {
			const child = document.createElement('div');

			container.appendChild(child);

			return child;
		}
	}

	let alertFixed = document.getElementById(DEFAULT_ALERT_CONTAINER_ID);

	if (!alertFixed) {
		const alertContainer = document.createElement('div');
		alertContainer.className = 'alert-container container';
		document.body.appendChild(alertContainer);

		alertFixed = document.createElement('div');
		alertFixed.id = DEFAULT_ALERT_CONTAINER_ID;
		alertFixed.className = 'alert-notifications alert-notifications-fixed';
		alertContainer.appendChild(alertFixed);
	}

	// Creates a fragment for preventing React to unmount the alertContainer
	container = document.createElement('div');
	container.className = 'mb-3';

	alertFixed.appendChild(container);

	return container;
};

/**
 * Function that implements the Toast pattern, which allows to present feedback
 * to user actions as a toast message in the lower left corner of the page
 *
 * @param {number|boolean} autoClose Flag to indicate alert should automatically call onClose.
 * It also accepts a duration (in ms) which indicates how long to wait. If true is passed in, the
 * timeout will be 10000ms. See https://clayui.com/docs/components/alert.html for more details.
 * @param {HTMLElement} container Target element where the toast React component should be mounted.
 * @param {string} containerId The id of the element where the toast React component should be mounted.
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
	onClose = () => {},
	renderData = DEFAULT_RENDER_DATA,
	title,
	titleType = TYPES.TEXT,
	toastProps = {},
	type = 'success',
	variant,
}) {
	const rootElement = getRootElement({container, containerId});

	unmountComponentAtNode(rootElement);

	const onCloseFn = (event) => {
		if (onClose) {
			onClose({event});
		}

		if (!container || !containerId) {
			rootElement.parentNode.removeChild(rootElement);
		}

		unmountComponentAtNode(rootElement);
	};

	render(
		<>
			<ClayAlert
				autoClose={autoClose}
				displayType={type}
				onClick={(event) => onClick({event, onClose: onCloseFn})}
				onClose={onCloseFn}
				title={
					title && (
						<Text
							allowHTML={titleType === TYPES.HTML}
							string={title}
						/>
					)
				}
				variant={variant}
				{...toastProps}
			>
				<Text allowHTML={messageType === TYPES.HTML} string={message} />
			</ClayAlert>
		</>,
		renderData,
		rootElement
	);
}

export {openToast};
export default openToast;
