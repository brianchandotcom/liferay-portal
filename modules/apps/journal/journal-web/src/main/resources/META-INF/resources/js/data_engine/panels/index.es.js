/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import BasicInfoPanel from './BasicInfoPanel.es';

/**
 * Entry-point for "Properties" (sidebar pane) functionality.
 */
export default class {
	constructor({panel}) {
		this.title = panel.label;
		this.url = panel.url;
	}

	renderSidebar() {
		return <BasicInfoPanel url={this.url} />;
	}
}
