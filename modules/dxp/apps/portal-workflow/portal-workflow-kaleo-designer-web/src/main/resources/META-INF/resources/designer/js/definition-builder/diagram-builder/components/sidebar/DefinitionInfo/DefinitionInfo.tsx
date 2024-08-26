/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTabs from '@clayui/tabs';
import React, {useContext, useState} from 'react';

import {DefinitionBuilderContext} from '../../../../DefinitionBuilderContext';
import {DetailsTab} from './DetailsTab';
import {RevisionHistory} from './RevisionHistory';

import './DefinitionInfo.scss';

const TABS = [
	{
		Component: DetailsTab,
		label: Liferay.Language.get('details'),
	},
	{
		Component: RevisionHistory,
		label: Liferay.Language.get('revision-history'),
	},
];

export function DefinitionInfo() {
	const [activeIndex, setActiveIndex] = useState(0);

	const {
		definitionInfo,
		setWorkflowDefinitionVersions,
		timeZoneId,
		workflowDefinitionVersions,
	} = useContext(DefinitionBuilderContext);

	return (
		<>
			<ClayTabs>
				{TABS.map(({label}, index) => (
					<ClayTabs.Item
						active={activeIndex === index}
						key={index}
						onClick={() => setActiveIndex(index)}
					>
						{label}
					</ClayTabs.Item>
				))}
			</ClayTabs>

			<ClayTabs.Content activeIndex={activeIndex} fade>
				{TABS.map(({Component}, index) => (
					<ClayTabs.TabPane
						className="lfr-workflow__definition-info-tabs"
						key={index}
					>
						<Component
							definitionInfo={definitionInfo}
							setWorkflowDefinitionVersions={
								setWorkflowDefinitionVersions
							}
							timeZoneId={timeZoneId}
							workflowDefinitionVersions={
								workflowDefinitionVersions
							}
						/>
					</ClayTabs.TabPane>
				))}
			</ClayTabs.Content>
		</>
	);
}
