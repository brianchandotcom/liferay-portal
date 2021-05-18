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

import ClayList from '@clayui/list';
import React from 'react';

import PanelNavigator from './PanelNavigator';
import Rule from './Rule';

function Description({helpUrl, html, impact, onBack, target, title}) {
	return (
		<>
			<PanelNavigator
				helpUrl={helpUrl}
				impact={impact}
				onBack={onBack}
				title={title}
			/>
			<div className="page-accessibility-tool__sidebar--occurrences-description-wrapper">
				<p className="text-secondary">
					{Liferay.Language.get(
						'please-open-the-devTools-in-the-browser-to-see-selected-occurrence'
					)}
				</p>
				<div className="my-3">
					<strong>{Liferay.Language.get('target')}</strong>
				</div>
				<CodeBlock>{target}</CodeBlock>
				<div className="my-3">
					<strong>{Liferay.Language.get('code')}</strong>
				</div>
				<CodeBlock>{html}</CodeBlock>
			</div>
		</>
	);
}

function List({nodes, onOccurrenceClicked}) {
	return (
		<ClayList className="list-group-flush">
			{nodes.map((occurrence, index) => (
				<Rule
					key={index}
					onListItemClick={onOccurrenceClicked}
					text={`${Liferay.Language.get('occurrence')} ${String(
						index
					)}`}
					{...occurrence}
				/>
			))}
		</ClayList>
	);
}

function CodeBlock({children}) {
	return (
		<div className="page-accessibility-tool__sidebar--occurrences-panel-code-block">
			<div className="p-2 page-accessibility-tool__sidebar--occurrences-panel-code-text">
				{children}
			</div>
		</div>
	);
}

const Occurrences = {
	Description,
	List,
};

export default Occurrences;
