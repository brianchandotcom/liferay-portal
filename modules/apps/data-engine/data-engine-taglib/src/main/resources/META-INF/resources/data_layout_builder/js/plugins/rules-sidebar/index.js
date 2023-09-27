/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {Component} from '../PluginContext.es';
import RulesSidebar from './components/RulesSidebar';

/**
 * Entry-point for "RulesSidebar" (sidebar panel) functionality.
 */
export default class RulesSidebarPluginEntryPoint {
	constructor({app, panel}) {
		this.Component = Component(app);
		this.title = panel.label;
	}

	renderSidebar() {
		const {Component} = this;

		return (
			<Component>
				<RulesSidebar title={this.title} />
			</Component>
		);
	}
}
