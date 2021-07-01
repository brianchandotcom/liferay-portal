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

import type {ImpactValue} from 'axe-core';

import type {Violations} from '../hooks/useA11y';

interface ICodeBlock extends React.HTMLAttributes<HTMLDivElement> {
	children: React.ReactNode;
}

function CodeBlock({children, ...otherProps}: ICodeBlock) {
	return (
		<div
			className="a11y-panel__sidebar--occurrence-panel-code-block"
			role="textbox"
			{...otherProps}
		>
			<div className="a11y-panel__sidebar--occurrence-panel-code-text p-2">
				{children}
			</div>
		</div>
	);
}

type Params = {
	name: string;
	ruleId: string;
	target: string;
};

type OccurrenceProps = {
	params?: Params;
	previous?: (state: Omit<Params, 'target'>) => void;
	violations: Violations;
};

function Occurrence({params, previous, violations}: OccurrenceProps) {
	if (!params) {
		return null;
	}

	const {name, ruleId, target} = params;

	const {helpUrl, tags} = violations.rules[ruleId];

	const {html, impact} = violations.nodes[target][ruleId];

	return (
		<>
			<PanelNavigator
				helpUrl={helpUrl}
				impact={impact as ImpactValue}
				onBack={() => {
					if (previous) {
						previous({name, ruleId});
					}
				}}
				tags={tags}
				title={name}
			/>
			<div className="a11y-panel__sidebar--occurrence-description-wrapper">
				<p className="text-secondary">
					{Liferay.Language.get(
						'open-developer-tools-in-the-browser-to-see-the-selected-occurrence'
					)}
				</p>
				<div className="my-3">
					<strong>{Liferay.Language.get('target')}</strong>
				</div>
				<CodeBlock aria-label={Liferay.Language.get('target-selector')}>
					{target}
				</CodeBlock>
				<div className="my-3">
					<strong>{Liferay.Language.get('code')}</strong>
				</div>
				<CodeBlock
					aria-label={Liferay.Language.get('code-of-the-element')}
				>
					{html}
				</CodeBlock>
			</div>
		</>
	);
}

export default Occurrence;
