/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTabs from '@clayui/tabs';
import React, {useContext, useState} from 'react';

import {DefinitionBuilderContext} from '../../../../DefinitionBuilderContext';
import {DetailsTab} from './DetailsTab';
import {VersionRow} from './VersionRow';

const RevisionHistory = ({version}) => {
	const otherVersions = [];

	for (let i = version - 1; i > 0; i--) {
		otherVersions.push({versionNumber: i});
	}

	return (
		<>
			<div className="info-group">
				<label>
					{Liferay.Language.get('current-version')}: {version}
				</label>

				<div className="sheet-subtitle" />
			</div>

			{otherVersions.map(({versionNumber}, index) => (
				<VersionRow key={index} versionNumber={versionNumber} />
			))}
		</>
	);
};

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

	const {definitionInfo, version} = useContext(DefinitionBuilderContext);

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
					<ClayTabs.TabPane key={index}>
						<Component
							definitionInfo={definitionInfo}
							version={version}
						/>
					</ClayTabs.TabPane>
				))}
			</ClayTabs.Content>
		</>
	);
}
