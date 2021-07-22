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

import {
	CLOSE_SIDE_PANEL,
	OPEN_MODAL,
	OPEN_MODAL_FROM_IFRAME,
} from 'frontend-taglib-clay/data_set_display/utils/eventsDefinitions';

class SidePanelListenersInitializer {
	constructor() {
		Liferay.on(OPEN_MODAL, this.handleOpenModalFromSidePanel);

		document.body.classList.remove('open');

		document
			.querySelectorAll('.side-panel-iframe-close, .btn-cancel')
			.forEach((trigger) => {
				trigger.addEventListener('click', (event) => {
					event.preventDefault();

					const parentWindow = Liferay.Util.getOpener();

					parentWindow.Liferay.fire(CLOSE_SIDE_PANEL);
				});
			});
	}

	dispose() {
		Liferay.detach(OPEN_MODAL, this.handleOpenModalFromSidePanel);
	}

	handleOpenModalFromSidePanel(payload) {
		const topWindow = Liferay.Util.getTop();

		topWindow.Liferay.fire(OPEN_MODAL_FROM_IFRAME, payload);
	}
}

export default SidePanelListenersInitializer;
