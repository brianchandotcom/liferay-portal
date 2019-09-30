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

import {render} from 'frontend-js-react-web';
import ClayAlert from '@clayui/alert';
import dom from 'metal-dom';
import React, {useState, useLayoutEffect} from 'react';
import {unmountComponentAtNode} from 'react-dom';

const DEFAULT_RENDER_DATA = {
	portletId: 'UNKNOWN_PPID'
};

const Toast = ({
	displayType,
	message,
	onUnmount,
	spritemap,
	title,
	toastProps,
	variant
}) => {
	const [showDismissible, setShowDismissible] = useState(true);

	useLayoutEffect(() => {
		return () => {
			onUnmount();
		};
	}, [onUnmount, showDismissible]);

	if (showDismissible) {
		return (
			<ClayAlert.ToastContainer>
				<ClayAlert
					autoClose={5000}
					displayType={displayType}
					onClose={() => setShowDismissible(false)}
					spritemap={spritemap}
					title={title}
					variant={variant}
					{...toastProps}
				>
					{message}
				</ClayAlert>
			</ClayAlert.ToastContainer>
		);
	}

	return null;
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
	const containerElement = document.getElementById(containerId);
	let alertContainer = document.getElementById('alertContainer');

	if (!containerElement) {
		if (!alertContainer) {
			alertContainer = document.createElement('div');
			alertContainer.id = 'alertContainer';

			dom.enterDocument(alertContainer);
		}
	}

	const container = containerElement ? containerElement : alertContainer;

	const ToastComponent = () => (
		<Toast
			displayType={type}
			message={message}
			onUnmount={() => {
				unmountComponentAtNode(container);
			}}
			title={title}
			toastProps={toastProps}
			variant={variant}
		/>
	);

	if (containerElement) {
		unmountComponentAtNode(container);
	}

	render(ToastComponent, renderData, container);
}

export {openToast};
export default openToast;
