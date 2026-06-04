/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import {FeatureIndicator} from 'frontend-js-components-web';
import React from 'react';

const TYPES = [
	'beta',
	'deprecated',
	'enterprise',
	'maintenance',
	'private-beta',
];

export default function FeatureIndicatorSamples({learnResourceContext}) {
	const renderVariants = (type, dark) => {
		const interactiveProps =
			type === 'enterprise'
				? {dark, interactive: true}
				: {dark, interactive: true, learnResourceContext};

		return (
			<>
				<ClayLayout.Col>
					<FeatureIndicator {...interactiveProps} type={type} />
				</ClayLayout.Col>

				<ClayLayout.Col>
					<FeatureIndicator dark={dark} type={type} />
				</ClayLayout.Col>

				<ClayLayout.Col>
					<FeatureIndicator dark={dark} iconOnly type={type} />
				</ClayLayout.Col>

				<ClayLayout.Col>
					<FeatureIndicator
						{...interactiveProps}
						iconOnly
						type={type}
					/>
				</ClayLayout.Col>
			</>
		);
	};

	return (
		<div className="p-3">
			<ClayLayout.Row>
				<ClayLayout.Col size={2}>
					<h4>Type</h4>
				</ClayLayout.Col>

				<ClayLayout.Col>
					<h4>Interactive</h4>
				</ClayLayout.Col>

				<ClayLayout.Col>
					<h4>Default</h4>
				</ClayLayout.Col>

				<ClayLayout.Col>
					<h4>Icon Only</h4>
				</ClayLayout.Col>

				<ClayLayout.Col>
					<h4>Interactive Icon Only</h4>
				</ClayLayout.Col>
			</ClayLayout.Row>

			{TYPES.map((type) => (
				<React.Fragment key={type}>
					<ClayLayout.Row className="align-items-center mb-2">
						<ClayLayout.Col size={2}>
							<strong className="text-uppercase">{type}</strong>
						</ClayLayout.Col>

						{renderVariants(type, false)}
					</ClayLayout.Row>

					<ClayLayout.Row className="align-items-center bg-dark mb-3 p-2 text-white">
						<ClayLayout.Col size={2}>
							<strong className="text-uppercase">
								{type} (dark)
							</strong>
						</ClayLayout.Col>

						{renderVariants(type, true)}
					</ClayLayout.Row>
				</React.Fragment>
			))}
		</div>
	);
}
