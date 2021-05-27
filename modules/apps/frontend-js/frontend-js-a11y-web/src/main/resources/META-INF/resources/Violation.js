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

function PanelSection({children, title}) {
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

function Violation({navigationState, next, previous, violations}) {
	const {violationIndex} = navigationState;

	const {description, helpUrl, id, impact, nodes} = violations[
		violationIndex
	];

	return (
		<>
			<PanelNavigator
				helpUrl={helpUrl}
				impact={impact}
				onBack={() => previous()}
				title={id}
			/>
			<div className="page-accessibility-tool__sidebar--violation-panel-wrapper">
				<ClayPanel.Group flush>
					<PanelSection title={Liferay.Language.get('details')}>
						{description}
					</PanelSection>
					<PanelSection title={Liferay.Language.get('occurrences')}>
						<ClayList className="list-group-flush">
							{nodes.map((occurrence, index) => {
								const occurrenceName = `${Liferay.Language.get(
									'occurrence'
								)} ${String(index)}`;

								return (
									<Rule
										aria-label={occurrenceName}
										key={index}
										onClick={() =>
											next({
												occurrenceIndex: index,
												occurrenceName,
												violationIndex,
											})
										}
										text={occurrenceName}
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
