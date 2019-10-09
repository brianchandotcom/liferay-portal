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

import React from 'react';

import PageStructureSidebar from './components/PageStructureSidebar';

/**
 * Entry-point for "Page Structure" (sidebar pane) functionality.
 */
export default class PageStructure {
	static name = 'PageStructure';

	// TODO: make other app contexts available here as well
	constructor({ActionTypes, dispatch, panel}) {
		this.ActionTypes = ActionTypes;
		this.dispatch = dispatch;
		this.title = panel.label;
	}

	activate({loadReducer}) {
		const reducer = (state, action) => {
			const nextState = state;

			switch (action.type) {
				default:
					break;
			}

			return nextState;
		};

		this.dispatch(loadReducer(reducer, PageStructure.name));
	}

	deactivate({unloadReducer}) {
		this.dispatch(unloadReducer(PageStructure.name));
	}

	renderSidebar() {
		return (
			<PageStructureSidebar dispatch={this.dispatch} title={this.title} />
		);
	}
}
