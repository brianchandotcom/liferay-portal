/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayTabs from '@clayui/tabs';
import {ExperienceSelector} from '@liferay/layout-js-components-web';
import {fetch} from 'frontend-js-web';
import React, {cloneElement, useContext, useEffect, useState} from 'react';

import {ConstantsContext} from '../context/ConstantsContext';
import {StoreStateContext} from '../context/StoreContext';
import LayoutReports from './layout_reports/LayoutReports';
import RenderTimes from './render_times/RenderTimes';

import './PageAudit.scss';

export default function PageAudit({panelIsOpen}) {
	const [data, setData] = useState({});
	const {layoutReportsTabsURL} = useContext(ConstantsContext);

	useEffect(() => {
		if (panelIsOpen && layoutReportsTabsURL) {
			fetch(layoutReportsTabsURL, {method: 'GET'})
				.then((response) => response.json())
				.then((data) => setData(data))
				.catch((error) => console.error(error));
		}
	}, [layoutReportsTabsURL, panelIsOpen]);

	if (!data.tabsData || !data.segmentExperienceSelectorData) {
		return null;
	}

	return (
		<PageAuditBody
			segments={data.segmentExperienceSelectorData}
			tabs={data.tabsData}
		>
			<LayoutReports />
		</PageAuditBody>
	);
}

export function PageAuditBody({children, segments, tabs}) {
	const [activeTab, setActiveTab] = useState(0);
	const {selectedIssue} = useContext(StoreStateContext);

	if (selectedIssue) {
		return <div className="c-p-3">{children}</div>;
	}

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

			{tabs.length ? (
				<>
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
						{tabs.map((tab) => (
							<ClayTabs.TabPane
								aria-labelledby={`tab-${tab.id}`}
								className="p-3"
								key={tab.id}
							>
								{tab.id === 'render-times' ? (
									<RenderTimes url={tab.url} />
								) : (
									cloneElement(children, {url: tab.url})
								)}
							</ClayTabs.TabPane>
						))}
					</ClayTabs.Content>
				</>
			) : (
				<ClayLoadingIndicator displayType="secondary" size="sm" />
			)}
		</>
	);
}
