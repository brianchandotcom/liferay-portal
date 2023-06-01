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
