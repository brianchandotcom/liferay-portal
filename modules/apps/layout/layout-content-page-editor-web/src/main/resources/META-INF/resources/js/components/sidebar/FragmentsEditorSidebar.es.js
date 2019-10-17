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
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import './FragmentsEditorSidebarContent.es';
import {UPDATE_SELECTED_SIDEBAR_PANEL_ID} from '../../actions/actions.es';
import {getConnectedComponent} from '../../store/ConnectedComponent.es';
import templates from './FragmentsEditorSidebar.soy';

/**
 * FragmentsEditorSidebar
 * @review
 */
class FragmentsEditorSidebar extends Component {
	/**
	 * @inheritDoc
	 * @review
	 */
	created() {
		const productMenuToggle = document.querySelector(
			'.product-menu-toggle'
		);

		if (productMenuToggle) {
			this._productMenuToggle = productMenuToggle;

			this._handleHide = this._handleHide.bind(this);

			const sidenav = Liferay.SideNavigation.instance(
				this._productMenuToggle
			);

			this._toggleHandle = sidenav.on(
				'openStart.lexicon.sidenav',
				this._handleHide
			);
		}
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	disposed() {
		this._toggleHandle.removeListener(
			'openStart.lexicon.sidenav',
			this._handleHide
		);
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	rendered() {
		const selectedSidebarPanel = this.sidebarPanels.find(
			panel => panel.sidebarPanelId === this.selectedSidebarPanelId
		);

		if (selectedSidebarPanel) {
			Liferay.SideNavigation.hide(this._productMenuToggle);
		}
	}

	/**
	 * @private
	 * @review
	 */
	_handleHide() {
		this.store.dispatch({
			type: UPDATE_SELECTED_SIDEBAR_PANEL_ID,
			value: ''
		});
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
FragmentsEditorSidebar.STATE = {
	/**
	 * Synced ProductMenu toggle button.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentsEditorSidebar
	 * @review
	 * @type {object}
	 */
	_productMenuToggle: Config.internal(),

	/**
	 * Internal property for subscribing to sidenav events.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentsEditorSidebar
	 * @review
	 * @type {EventHandle}
	 */
	_toggleHandle: Config.internal()
};

const ConnectedFragmentsEditorSidebar = getConnectedComponent(
	FragmentsEditorSidebar,
	['selectedSidebarPanelId', 'sidebarPanels']
);

Soy.register(ConnectedFragmentsEditorSidebar, templates);

export {ConnectedFragmentsEditorSidebar, FragmentsEditorSidebar};
export default ConnectedFragmentsEditorSidebar;
