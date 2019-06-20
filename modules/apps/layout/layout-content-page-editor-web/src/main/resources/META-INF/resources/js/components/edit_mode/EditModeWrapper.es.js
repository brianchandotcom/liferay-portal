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

/**
 * @type string
 */
const WRAPPER_CLASS = 'fragment-entry-link-list-wrapper';

/**
 * @type string
 */
const WRAPPER_PADDED_CLASS = 'fragment-entry-link-list-wrapper--padded';

/**
 * EditModeWrapper
 * @review
 */
class EditModeWrapper extends Component {
	/**
	 * @inheritdoc
	 */
	created() {
		this._handleSelectedSidebarPanelIdChanged = this._handleSelectedSidebarPanelIdChanged.bind(
			this
		);

		this.on(
			'selectedSidebarPanelIdChanged',
			this._handleSelectedSidebarPanelIdChanged
		);

		this._handleSelectedSidebarPanelIdChanged();
	}

	/**
	 * Callback called when the sidebar visibily changes
	 */
	_handleSelectedSidebarPanelIdChanged() {
		const wrapper = document.getElementById('wrapper');

		if (wrapper) {
			wrapper.classList.add(WRAPPER_CLASS);

			if (this.selectedSidebarPanelId) {
				wrapper.classList.add(WRAPPER_PADDED_CLASS);
			} else {
				wrapper.classList.remove(WRAPPER_PADDED_CLASS);
			}
		}
	}
}

const ConnectedEditModeWrapper = getConnectedComponent(EditModeWrapper, [
	'selectedSidebarPanelId'
]);

export {ConnectedEditModeWrapper, EditModeWrapper};
export default ConnectedEditModeWrapper;
