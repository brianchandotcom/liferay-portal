/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FDS_EVENT} from '@liferay/frontend-data-set-web';
import {getOpener, getTop} from 'frontend-js-web';

class SidePanelListenersInitializer {
	constructor() {
		Liferay.on(FDS_EVENT.OPEN_MODAL, this.handleOpenModalFromSidePanel);

		document.body.classList.remove('open');

		document
			.querySelectorAll('.side-panel-iframe-close, .btn-cancel')
			.forEach((trigger) => {
				trigger.addEventListener('click', (event) => {
					event.preventDefault();

					const parentWindow = getOpener();

					parentWindow.Liferay.fire(FDS_EVENT.CLOSE_SIDE_PANEL);
				});
			});
	}

	dispose() {
		Liferay.detach(FDS_EVENT.OPEN_MODAL, this.handleOpenModalFromSidePanel);
	}

	handleOpenModalFromSidePanel(payload) {
		const topWindow = getTop();

		topWindow.Liferay.fire(FDS_EVENT.OPEN_MODAL_FROM_IFRAME, payload);
	}
}

export default SidePanelListenersInitializer;
