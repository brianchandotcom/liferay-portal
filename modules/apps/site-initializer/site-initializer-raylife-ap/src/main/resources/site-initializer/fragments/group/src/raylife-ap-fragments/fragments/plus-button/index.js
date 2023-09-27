/* eslint-disable no-undef */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const buttonNameElement = fragmentElement.querySelector('#plus-button span');
const buttonElement = fragmentElement.querySelector('#plus-button');
const buttonName = configuration.buttonName;
const eventName = configuration.eventName;

const handleClick = () => {
	const EVENT_OPTION = {
		async: true,
		fireOn: true,
	};

	const eventPublish = Liferay.publish('openModalEvent', EVENT_OPTION);

	eventPublish.fire({
		show: true,
	});
};

buttonNameElement.innerText = buttonName;
buttonElement.onclick = () => {
	if (eventName) {
		handleClick();
	}
};
