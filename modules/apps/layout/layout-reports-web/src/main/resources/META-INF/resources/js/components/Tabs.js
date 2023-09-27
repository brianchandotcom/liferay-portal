/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTabs from '@clayui/tabs';
import {ExperienceSelector} from '@liferay/layout-js-components-web';
import React, {useState} from 'react';

import LayoutReports from './layout_reports/LayoutReports';
import RenderTimes from './render_times/RenderTimes';

const TAB_COMPONENTS = {
	'page-speed-insights': LayoutReports,
	'performance': RenderTimes,
};

export default function Tabs({segments, tabs}) {
	const [activeTab, setActiveTab] = useState(0);

	return (
		<>
			{segments.segmentsExperiences.length > 1 ? (
				<ExperienceSelector
					className="c-px-3 c-py-1 page-audit__experience-selector"
					segmentsExperiences={segments.segmentsExperiences}
					selectedSegmentsExperience={
						segments.selectedSegmentsExperience
					}
				/>
			) : null}

			<ClayTabs
				active={activeTab}
				className="px-2"
				onActiveChange={setActiveTab}
			>
				{tabs.map((tab, index) => (
					<ClayTabs.Item
						id={`tab-${tab.id}`}
						innerProps={{
							'aria-controls': `tabpanel-${index}`,
						}}
						key={tab.id}
					>
						{Liferay.Language.get(tab.name)}
					</ClayTabs.Item>
				))}
			</ClayTabs>

			<ClayTabs.Content activeIndex={activeTab} fade>
				{tabs.map((tab, index) => {
					const Component = TAB_COMPONENTS[tab.id];

					return (
						<ClayTabs.TabPane
							aria-labelledby={`tab-${tab.id}`}
							className="p-3"
							id={`tabpanel-${index}`}
							key={tab.id}
						>
							<Component url={tab.url} />
						</ClayTabs.TabPane>
					);
				})}
			</ClayTabs.Content>
		</>
	);
}
