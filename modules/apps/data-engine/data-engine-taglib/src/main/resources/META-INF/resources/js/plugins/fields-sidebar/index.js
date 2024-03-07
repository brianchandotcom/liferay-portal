/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {Component} from '../PluginContext.es';
import {FieldsSidebar} from './components/FieldsSidebar';

/**
 * Entry-point for "FieldsSidebar" (sidebar panel) functionality.
 */
export default class FieldsSidebarPluginEntryPoint {
	constructor({app, panel}) {
		this.Component = Component(app);
		this.title = panel.label;
	}

	renderSidebar() {
		const {Component} = this;

		return (
			<Component>
				<FieldsSidebar title={this.title} />
			</Component>
		);
	}
}
