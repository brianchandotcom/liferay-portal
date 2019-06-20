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

import {ClayAlert} from 'clay-alert';

class Notifications {
	static closeAlert() {
		if (this._alert && !this._alert.isDisposed()) {
			this._alert.emit('hide');
			this._alert = null;
		}

		clearTimeout(this._hideTimeout);
	}

	static showAlert(
		message = '',
		title = '',
		style = 'success',
		hideDelay = 3000
	) {
		const {portletNamespace, spritemap} = Liferay.DDM.FormSettings;

		this.closeAlert();

		this._alert = new ClayAlert(
			{
				closeable: true,
				destroyOnHide: true,
				message,
				spritemap,
				style,
				title,
				visible: true
			},
			document.querySelector(
				`#p_p_id${portletNamespace} .lfr-alert-wrapper`
			)
		);

		this._hideTimeout = setTimeout(() => this.closeAlert(), hideDelay);
	}

	static showError(message) {
		this.showAlert(message, Liferay.Language.get('error'), 'danger');
	}
}

export default Notifications;
export {Notifications};
