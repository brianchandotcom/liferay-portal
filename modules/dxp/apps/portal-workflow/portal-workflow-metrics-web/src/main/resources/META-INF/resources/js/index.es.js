/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import 'string.prototype.startswith';
import AppComponent from './components/App.es';
import React from 'react';
import ReactDOM from 'react-dom';

export default function(defaultDelta, deltas, isAmPm, maxPages, namespace) {
	const container = document.getElementById(`${namespace}root`);

	const buildContainer = () => {
		ReactDOM.render(
			<AppComponent
				companyId={Liferay.ThemeDisplay.getCompanyId()}
				defaultDelta={defaultDelta}
				deltas={deltas}
				isAmPm={isAmPm}
				maxPages={maxPages}
				namespace={namespace}
			/>,
			container
		);
		container.setAttribute('data-rendered', true);
	};

	if (!container.getAttribute('data-rendered')) {
		buildContainer();
	}

	Liferay.once('destroyPortlet', () => {
		ReactDOM.unmountComponentAtNode(container);
	});
}
