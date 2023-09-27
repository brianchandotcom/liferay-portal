/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getOpener} from 'frontend-js-web';
export default class SelectEntityHandler {
	constructor({buttonClass, containerId, eventName, returnType}) {
		this.buttonClass = buttonClass;
		this.eventName = eventName;
		this.returnType = returnType;

		this.container = document.getElementById(containerId);

		if (this.container) {
			this.container.addEventListener('click', this._handleClick);
		}
	}

	destroy() {
		this.container.removeEventListener('click', this._handleClick);
	}

	_handleClick = (event) => {
		const button = event.target.closest(this.buttonClass);

		if (button) {
			getOpener().Liferay.fire(this.eventName, {
				data: {
					returnType: this.returnType,
					value: {...button.dataset},
				},
			});
		}
	};
}
