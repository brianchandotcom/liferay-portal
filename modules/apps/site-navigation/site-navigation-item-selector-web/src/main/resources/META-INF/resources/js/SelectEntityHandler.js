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
			Liferay.Util.getOpener().Liferay.fire(this.eventName, {
				data: {
					returnType: this.returnType,
					value: {...button.dataset},
				},
			});
		}
	};
}
