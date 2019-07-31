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

import Component from 'metal-component';
import getConnectedComponent from '../../store/ConnectedComponent.es';

const WRAPPER_CLASSES = {
	default: 'fragment-entry-link-list-wrapper',
	padded: 'fragment-entry-link-list-wrapper--padded'
};

/**
 * EditModeWrapper
 * @review
 */
class EditModeWrapper extends Component {
	/**
	 * @param {string} selectedSidebarPanelId
	 * @review
	 */
	syncSelectedSidebarPanelId(selectedSidebarPanelId) {
		const wrapper = document.getElementById('wrapper');

		if (wrapper) {
			wrapper.classList.add(WRAPPER_CLASSES.default);

			if (selectedSidebarPanelId) {
				wrapper.classList.add(WRAPPER_CLASSES.padded);
			} else {
				wrapper.classList.remove(WRAPPER_CLASSES.padded);
			}
		}
	}
}

const ConnectedEditModeWrapper = getConnectedComponent(EditModeWrapper, [
	'selectedSidebarPanelId'
]);

export {ConnectedEditModeWrapper, EditModeWrapper};
export default ConnectedEditModeWrapper;
