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

import {openToast} from 'frontend-js-web';

export default function showSuccessMessage(portletNamespace) {
	const openToastSuccessProps = {
		message: Liferay.Language.get('your-request-completed-successfully'),
		type: 'success',
	};

	const reloadButtonLabel = Liferay.Language.get('reload');
	const reloadButtonClassName = 'knowledge-base-reload-button';

	openToastSuccessProps.message =
		openToastSuccessProps.message +
		`<div class="alert-footer">
				<div class="btn-group" role="group">
					<button class="btn btn-sm btn-primary alert-btn ${reloadButtonClassName}">${reloadButtonLabel}</button>
				</div>
		</div>`;

	openToastSuccessProps.onClick = ({event, onClose: closeToast}) => {
		if (event.target.classList.contains(reloadButtonClassName)) {
			Liferay.Portlet.refresh(`#p_p_id${portletNamespace}`);
			closeToast();
		}
	};

	openToast(openToastSuccessProps);
}
