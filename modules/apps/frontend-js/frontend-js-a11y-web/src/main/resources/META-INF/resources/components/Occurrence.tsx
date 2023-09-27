/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import './Occurrence.scss';

import ClayPanel from '@clayui/panel';
import React from 'react';

import PanelNavigator from './PanelNavigator';

import type {CheckResult, ImpactValue} from 'axe-core';

import type {Violations} from '../hooks/useA11y';

interface ICodeBlock extends React.HTMLAttributes<HTMLDivElement> {
	children: React.ReactNode;
}

const CodeBlock = ({children, ...otherProps}: ICodeBlock) => (
	<div className="a11y-panel--code-block" role="textbox" {...otherProps}>
		<div className="a11y-panel--code-text p-2">{children}</div>
	</div>
);

type CheckProps = {
	data: Array<CheckResult>;
	title: string;
};

const Check = ({data, title}: CheckProps) => (
	<>
		<p className="text-secondary">{title}</p>
		{data.map(({id, message}) => (
			<div key={id}>{`- ${message}`}</div>
		))}
	</>
);

type Params = {
	name: string;
	ruleId: string;
	target: string;
};

type OccurrenceProps = {
	params?: Params;
	previous?: (state: Omit<Params, 'target'>) => void;
	violations: Omit<Violations, 'iframes'>;
};

function Occurrence({params, previous, violations}: OccurrenceProps) {
	if (!params) {
		return null;
	}

	const {name, ruleId, target} = params;

	const {helpUrl, tags} = violations.rules[ruleId];

	const {all, any, html, impact} = violations.nodes[target][ruleId];

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
			<div className="a11y-panel--body">
				<p className="mt-2 text-secondary">
					{Liferay.Language.get(
						'open-developer-tools-in-the-browser-to-see-the-selected-occurrence'
					)}
				</p>

				<div className="my-3">
					<span>{Liferay.Language.get('target')}</span>
				</div>

				<CodeBlock aria-label={Liferay.Language.get('target-selector')}>
					{target}
				</CodeBlock>

				<div className="my-3">
					<span>{Liferay.Language.get('code')}</span>
				</div>

				<CodeBlock
					aria-label={Liferay.Language.get('code-of-the-element')}
				>
					{html}
				</CodeBlock>

				<ClayPanel
					className="mt-4"
					displayTitle={Liferay.Language.get('how-to-fix')}
					displayType="unstyled"
					role="tab"
					showCollapseIcon={false}
				>
					<ClayPanel.Body>
						{Boolean(any.length) && (
							<Check
								data={any}
								title={Liferay.Language.get(
									'fix-at-least-one-of-the-following'
								)}
							/>
						)}

						{Boolean(all.length) && (
							<Check
								data={all}
								title={Liferay.Language.get(
									'fix-all-of-the-following-checks'
								)}
							/>
						)}
					</ClayPanel.Body>
				</ClayPanel>
			</div>
		</>
	);
}

export default Occurrence;
