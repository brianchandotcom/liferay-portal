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

import './Occurrence.scss';

import React from 'react';

import PanelNavigator from './PanelNavigator';

function CodeBlock({ariaLabel, children}) {
	return (
		<div
			aria-label={ariaLabel}
			className="page-accessibility-tool__sidebar--occurrence-panel-code-block"
			role="textbox"
		>
			<div className="p-2 page-accessibility-tool__sidebar--occurrence-panel-code-text">
				{children}
			</div>
		</div>
	);
}

function Occurrence({navigationState, previous, violations}) {
	const {occurrenceIndex, occurrenceName, violationIndex} = navigationState;

	const {helpUrl, html, impact, target} = violations[violationIndex].nodes[
		occurrenceIndex
	];

	return (
		<>
			<PanelNavigator
				helpUrl={helpUrl}
				impact={impact}
				onBack={() => previous()}
				title={occurrenceName}
			/>
			<div className="page-accessibility-tool__sidebar--occurrence-description-wrapper">
				<p className="text-secondary">
					{Liferay.Language.get(
						'please-open-the-devTools-in-the-browser-to-see-selected-occurrence'
					)}
				</p>
				<div className="my-3">
					<strong>{Liferay.Language.get('target')}</strong>
				</div>
				<CodeBlock ariaLabel={Liferay.Language.get('target-selector')}>
					{target}
				</CodeBlock>
				<div className="my-3">
					<strong>{Liferay.Language.get('code')}</strong>
				</div>
				<CodeBlock
					ariaLabel={Liferay.Language.get('code-of-the-element')}
				>
					{html}
				</CodeBlock>
			</div>
		</>
	);
}

export default Occurrence;
