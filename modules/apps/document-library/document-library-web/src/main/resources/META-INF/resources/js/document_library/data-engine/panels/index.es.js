/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import InfoPanel from './InfoPanel.es';

/**
 * Entry-point for sidebar pane functionality.
 */
export default class {
	constructor({panel}) {
		this.title = panel.label;
		this.url = panel.url;
	}

	renderSidebar() {
		return <InfoPanel title={this.title} url={this.url} />;
	}
}
