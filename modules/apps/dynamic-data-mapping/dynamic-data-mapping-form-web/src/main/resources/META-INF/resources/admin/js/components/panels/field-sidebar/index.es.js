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

import {PluginComponent} from 'data-engine-taglib';
import React from 'react';

import {FormsFieldSidebar} from './components/FormsFieldSidebar.es';

/**
 * Entry-point for "FormsFieldSidebar" (sidebar panel) functionality.
 */
export default class {
	constructor({app, panel}) {
		this.Component = PluginComponent(app);
		this.title = panel.label;
	}

	renderSidebar() {
		const {Component} = this;

		return (
			<Component>
				<FormsFieldSidebar title={this.title} />
			</Component>
		);
	}
}
