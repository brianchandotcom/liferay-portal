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
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import './FloatingToolbarFragmentConfigurationPanelDelegateTemplate.soy';
import {getConnectedComponent} from '../../../store/ConnectedComponent.es';
import templates from './FloatingToolbarFragmentConfigurationPanel.soy';

/**
 * FloatingToolbarFragmentConfigurationPanel
 */
class FloatingToolbarFragmentConfigurationPanel extends Component {
	/**
	 * Handles Restore button click
	 * @private
	 * @review
	 */
	_handleRestoreButtonClick() {}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
FloatingToolbarFragmentConfigurationPanel.STATE = {
	/**
	 * @default undefined
	 * @memberof FloatingToolbarLinkPanel
	 * @review
	 * @type {object}
	 */
	item: Config.object().value(null),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarFragmentConfigurationPanel
	 * @review
	 * @type {!string}
	 */
	itemId: Config.string().required()
};

const ConnectedFloatingToolbarFragmentConfigurationPanel = getConnectedComponent(
	FloatingToolbarFragmentConfigurationPanel,
	['spritemap']
);

Soy.register(ConnectedFloatingToolbarFragmentConfigurationPanel, templates);

export {
	ConnectedFloatingToolbarFragmentConfigurationPanel,
	FloatingToolbarFragmentConfigurationPanel
};
export default ConnectedFloatingToolbarFragmentConfigurationPanel;