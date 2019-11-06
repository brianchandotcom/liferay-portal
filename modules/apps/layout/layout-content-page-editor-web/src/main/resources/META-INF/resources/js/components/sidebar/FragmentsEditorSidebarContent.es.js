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

import './comments/SidebarCommentsPanel.es';

import './fragments/SidebarElementsPanel.es';

import './mapping/SidebarMappingPanel.es';

import './page_content/SidebarPageContentsPanel.es';

import './page_structure/SidebarPageStructurePanel.es';

import './widgets/SidebarWidgetsPanel.es';
import {UPDATE_SELECTED_SIDEBAR_PANEL_ID} from '../../actions/actions.es';
import {getConnectedComponent} from '../../store/ConnectedComponent.es';
import templates from './FragmentsEditorSidebarContent.soy';

/**
 * FragmentsEditorSidebarContent
 * @review
 */
class FragmentsEditorSidebarContent extends Component {
	/**
	 * Opens look and feel configuration window
	 * @private
	 * @review
	 */
	_handleLookAndFeeldButtonClick() {
		Liferay.Util.navigate(this.lookAndFeelURL);
	}

	/**
	 * Updates active panel
	 * @param {!MouseEvent} event
	 * @private
	 * @review
	 */
	_handlePanelButtonClick(event) {
		let {sidebarPanelId} = event.delegateTarget.dataset;

		if (
			this.selectedSidebarPanel &&
			this.selectedSidebarPanel.sidebarPanelId === sidebarPanelId
		) {
			sidebarPanelId = '';
		}

		this.store.dispatch({
			type: UPDATE_SELECTED_SIDEBAR_PANEL_ID,
			value: sidebarPanelId
		});
	}

	/**
	 * Hides sidebar
	 * @private
	 * @review
	 */
	_hideSidebar() {
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
FragmentsEditorSidebarContent.STATE = {
	/**
	 * @default undefined
	 * @instance
	 * @memberOf FragmentsEditorSidebarContent
	 * @review
	 * @type {object}
	 */
	selectedSidebarPanel: Config.object().value(undefined)
};

const ConnectedFragmentsEditorSidebarContent = getConnectedComponent(
	FragmentsEditorSidebarContent,
	['lockedSegmentsExperience', 'lookAndFeelURL', 'sidebarPanels', 'spritemap']
);

Soy.register(ConnectedFragmentsEditorSidebarContent, templates);

export {ConnectedFragmentsEditorSidebarContent, FragmentsEditorSidebarContent};
export default ConnectedFragmentsEditorSidebarContent;
