/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {FeatureIndicator} from 'frontend-js-components-web';
import React from 'react';

import '../css/main.scss';

export default function App() {
	return (
		<div>
			<ClayAlert title="Info">
				This widget is used to test out Clay components. Simply add
				whatever JS you want to App.js and redeploy.
			</ClayAlert>

			<div className="p-3">
				<div className="h1">Feature Indicator (JS)</div>

				<FeatureIndicator interactive type="beta" />

				<FeatureIndicator type="beta" />

				<FeatureIndicator interactive type="deprecated" />

				<FeatureIndicator type="deprecated" />
			</div>

			<div className="bg-dark clay-dark p-3">
				<FeatureIndicator interactive type="beta" />

				<FeatureIndicator type="beta" />

				<FeatureIndicator interactive type="deprecated" />

				<FeatureIndicator type="deprecated" />
			</div>
		</div>
	);
}
