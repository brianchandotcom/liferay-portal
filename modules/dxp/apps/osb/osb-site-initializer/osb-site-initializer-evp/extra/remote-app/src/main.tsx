/* eslint-disable no-console */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Root, createRoot} from 'react-dom/client';

import ClayIconProvider from './common/provider/ClayIconProvider';
import GenerateReport from './routes/reports/generateReport';

import './style/index.css';
import GenerateFinancialReport from './routes/reports/generateFinancialReport';

const NoRouteSelected = () => <div className="evp-app">No route selected</div>;

export type EVPComponentType = {
	[key: string]: JSX.Element;
};

const EVPComponent: EVPComponentType = {
	'generate-financial-report': <GenerateFinancialReport />,
	'generate-report': <GenerateReport />,
	'no-route-selected': <NoRouteSelected />,
};

class EVPRemoteAppComponent extends HTMLElement {
	public root: Root | undefined;

	connectedCallback() {
		type propertyType = {
			route: string;
		};

		const properties: propertyType = {
			route: this.getAttribute('route') || 'no-route-selected',
		};

		if (!this.root) {
			this.root = createRoot(this);

			this.root.render(
				<ClayIconProvider>
					{EVPComponent[properties.route]}
				</ClayIconProvider>
			);
		}
	}
}

const ELEMENT_NAME = 'liferay-remote-app-evp';

if (!customElements.get(ELEMENT_NAME)) {
	customElements.define(ELEMENT_NAME, EVPRemoteAppComponent);
}
