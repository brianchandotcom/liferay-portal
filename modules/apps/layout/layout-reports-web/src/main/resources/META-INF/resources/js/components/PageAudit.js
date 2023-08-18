/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
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

export const TAB_IDS = {
	pageSpeedInsights: 'page-speed-insights',
	renderTimes: 'render-times',
};

export default function PageAudit({panelIsOpen}) {
	const [data, setData] = useState(null);
	const [loading, setLoading] = useState(true);

	const {layoutReportsDataURL} = useContext(ConstantsContext);

	useEffect(() => {
		if (panelIsOpen && layoutReportsDataURL) {
			fetch(layoutReportsDataURL)
				.then((response) => response.json())
				.then((data) => setData(data))
				.catch((error) => console.error(error))
				.finally(() => setLoading(false));
		}
	}, [layoutReportsDataURL, panelIsOpen]);

	if (loading) {
		return <ClayLoadingIndicator displayType="secondary" size="sm" />;
	}

	if (!data) {
		return (
			<ClayAlert
				displayType="danger"
				title={Liferay.Language.get('error')}
			>
				{Liferay.Language.get('an-unexpected-error-occurred')}
			</ClayAlert>
		);
	}

	const pageSpeedData = data.tabsData.find(
		({id}) => id === TAB_IDS.pageSpeedInsights
	);

	return (
		<PageAuditBody
			segments={data.segmentsExperienceSelectorData}
			tabs={data.tabsData}
		>
			<LayoutReports url={pageSpeedData.url} />
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
						{tab.id === TAB_IDS.renderTimes ? (
							<RenderTimes url={tab.url} />
						) : (
							cloneElement(children, {url: tab.url})
						)}
					</ClayTabs.TabPane>
				))}
			</ClayTabs.Content>
		</>
	);
}
