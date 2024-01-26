/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
