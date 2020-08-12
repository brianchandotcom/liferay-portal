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
import {buildFragment} from 'metal-dom';
import React from 'react';
import {unmountComponentAtNode} from 'react-dom';

const DEFAULT_ALERT_CONTAINER_ID = 'ToastAlertContainer';

const DEFAULT_RENDER_DATA = {
	portletId: 'UNKNOWN_PORTLET_ID',
};

const DEFAULT_TOAST_TYPE_TITLES = {
	danger: Liferay.Language.get('error'),
	info: Liferay.Language.get('info'),
	success: Liferay.Language.get('success'),
	warning: Liferay.Language.get('warning'),
};

const TOAST_AUTO_CLOSE_INTERVAL = 5000;

const TPL_ALERT_CONTAINER = `
	<div class="alert-container container">
		<div class="alert-notifications alert-notifications-fixed" id=${DEFAULT_ALERT_CONTAINER_ID}></div>
	</div>
`;

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
		alertFixed = buildFragment(TPL_ALERT_CONTAINER).querySelector(
			'.alert-container.container'
		);

		alertFixed = document.body.appendChild(alertFixed);
	}

	// Creates a fragment to prevent React from unmounting the alert container

	container = document.createElement('div');
	container.className = 'mb-3';

	const fragmentContainer = document.querySelector(
		`.alert-notifications.alert-notifications-fixed`
	);

	fragmentContainer.appendChild(container);

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
	onClick = () => {},
	onClose = () => {},
	renderData = DEFAULT_RENDER_DATA,
	title,
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

		if (!event || !event.defaultPrevented) {
			if (!container || !containerId) {
				rootElement.parentNode.removeChild(rootElement);
			}

			unmountComponentAtNode(rootElement);
		}
	};

	let titleHTML =
		title === undefined ? DEFAULT_TOAST_TYPE_TITLES[type] : title;

	if (titleHTML) {
		titleHTML = titleHTML.replace(/:$/, '');
		titleHTML = `<strong class="lead">${titleHTML}:</strong>`;
	}
	else {
		titleHTML = '';
	}

	render(
		<ClayAlert
			autoClose={autoClose}
			displayType={type}
			onClick={(event) => onClick({event, onClose: onCloseFn})}
			onClose={onCloseFn}
			variant={variant}
			{...toastProps}
		>
			<div
				dangerouslySetInnerHTML={{
					__html: `${titleHTML}${message}`,
				}}
			/>
		</ClayAlert>,
		renderData,
		rootElement
	);
}

export {openToast};
export default openToast;
