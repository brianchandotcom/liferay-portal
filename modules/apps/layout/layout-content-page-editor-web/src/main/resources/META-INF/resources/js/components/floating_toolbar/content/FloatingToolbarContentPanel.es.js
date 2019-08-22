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

import 'clay-checkbox';
import Component from 'metal-component';
import Soy from 'metal-soy';

import './FloatingToolbarContentPanelDelegateTemplate.soy';
import getConnectedComponent from '../../../store/ConnectedComponent.es';
import templates from './FloatingToolbarContentPanel.soy';
import {openCreateContentDialog} from '../../../utils/FragmentsEditorDialogUtils';

/**
 * FloatingToolbarContentPanel
 */
class FloatingToolbarContentPanel extends Component {
	/**
	 * Opens content creation dialog
	 * @private
	 * @review
	 */
	_handleCreateContentClick() {
		openCreateContentDialog(this.store, () => {}, () => {}, () => {});
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
FloatingToolbarContentPanel.STATE = {};

const ConnectedFloatingToolbarContentPanel = getConnectedComponent(
	FloatingToolbarContentPanel,
	['selectedItems']
);

Soy.register(ConnectedFloatingToolbarContentPanel, templates);

export {FloatingToolbarContentPanel};
export default FloatingToolbarContentPanel;
