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

import './Violation.scss';

import ClayList from '@clayui/list';
import ClayPanel from '@clayui/panel';
import React from 'react';

import PanelNavigator from './PanelNavigator';
import Rule from './Rule';

import type {Result} from 'axe-core';

type PanelSectionProps = {
	children: React.ReactNode;
	title: React.ReactNode;
};

function PanelSection({children, title}: PanelSectionProps) {
	return (
		<ClayPanel
			displayTitle={title}
			displayType="unstyled"
			role="tab"
			showCollapseIcon={false}
		>
			<ClayPanel.Body>{children}</ClayPanel.Body>
		</ClayPanel>
	);
}

type TViolationNext = {
	occurrenceIndex: number;
	occurrenceName: string;
	violationIndex: number;
};

type ViolationProps = {
	navigationState?: {violationIndex: number};
	next?: (payload: TViolationNext) => void;
	previous?: () => void;
	violations: Array<Result>;
};

function Violation({
	navigationState,
	next,
	previous,
	violations,
}: ViolationProps) {
	if (!navigationState) {
		return null;
	}

	const {violationIndex} = navigationState;

	const {description, helpUrl, id, impact, nodes, tags} = violations[
		violationIndex
	];

	return (
		<>
			<PanelNavigator
				helpUrl={helpUrl}
				impact={impact}
				onBack={() => {
					if (previous) {
						previous();
					}
				}}
				tags={tags}
				title={id}
			/>
			<div className="a11y-panel__sidebar--violation-panel-wrapper">
				<ClayPanel.Group flush>
					<PanelSection title={Liferay.Language.get('details')}>
						{description}
					</PanelSection>
					<PanelSection title={Liferay.Language.get('occurrences')}>
						<ClayList className="list-group-flush">
							{nodes.map((_occurrence, index) => {
								const occurrenceName = `${Liferay.Language.get(
									'occurrence'
								)} ${String(index + 1)}`;

								return (
									<Rule
										aria-label={occurrenceName}
										key={index}
										onClick={() => {
											if (next) {
												next({
													occurrenceIndex: index,
													occurrenceName,
													violationIndex,
												});
											}
										}}
										ruleText={occurrenceName}
									/>
								);
							})}
						</ClayList>
					</PanelSection>
				</ClayPanel.Group>
			</div>
		</>
	);
}

export default Violation;
