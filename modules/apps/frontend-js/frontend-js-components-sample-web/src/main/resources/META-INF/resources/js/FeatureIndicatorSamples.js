/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import {FeatureIndicator} from 'frontend-js-components-web';
import React from 'react';

export default function FeatureIndicatorSamples({learnResourceContext}) {
	return (
		<>
			<ClayLayout.Row className="p-3">
				<ClayLayout.Col>
					<h3>Beta Interactive</h3>

					<FeatureIndicator
						interactive
						learnResourceContext={learnResourceContext}
						type="beta"
					/>
				</ClayLayout.Col>

				<ClayLayout.Col>
					<h3>Beta</h3>

					<FeatureIndicator type="beta" />
				</ClayLayout.Col>

				<ClayLayout.Col>
					<h3>Deprecated Interactive</h3>

					<FeatureIndicator
						interactive
						learnResourceContext={learnResourceContext}
						type="deprecated"
					/>
				</ClayLayout.Col>

				<ClayLayout.Col>
					<h3>Deprecated</h3>

					<FeatureIndicator type="deprecated" />
				</ClayLayout.Col>
			</ClayLayout.Row>

			<ClayLayout.Row className="bg-dark clay-dark mb-3 p-3 text-white">
				<ClayLayout.Col>
					<h3>Dark Beta Interactive</h3>

					<FeatureIndicator
						interactive
						learnResourceContext={learnResourceContext}
						type="beta"
					/>
				</ClayLayout.Col>

				<ClayLayout.Col>
					<h3>Dark Beta</h3>

					<FeatureIndicator type="beta" />
				</ClayLayout.Col>

				<ClayLayout.Col>
					<h3>Dark Deprecated Interactive</h3>

					<FeatureIndicator
						interactive
						learnResourceContext={learnResourceContext}
						type="deprecated"
					/>
				</ClayLayout.Col>

				<ClayLayout.Col>
					<h3>Dark Deprecated</h3>

					<FeatureIndicator type="deprecated" />
				</ClayLayout.Col>
			</ClayLayout.Row>
		</>
	);
}
