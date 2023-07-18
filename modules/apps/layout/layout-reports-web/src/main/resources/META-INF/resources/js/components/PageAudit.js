/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTabs from '@clayui/tabs';
import {fetch} from 'frontend-js-web';
import React, {useContext, useEffect, useState} from 'react';

import {ConstantsContext} from '../context/ConstantsContext';
import LayoutReports from './layout_reports/LayoutReports';
import RenderTimes from './render_times/RenderTimes';

import './PageAudit.scss';

export default function PageAudit({layoutReportsEventTriggered, panelIsOpen}) {
	const [activeTab, setActiveTab] = useState(0);
	const [tabs, setTabs] = useState([]);
	const {layoutReportsTabsURL} = useContext(ConstantsContext);

	useEffect(() => {
		if (panelIsOpen && layoutReportsTabsURL) {
			fetch(layoutReportsTabsURL, {method: 'GET'})
				.then((response) => response.json())
				.then((tabs) => setTabs(tabs))
				.catch((error) => console.error(error));
		}
	}, [layoutReportsTabsURL, panelIsOpen]);

	return tabs.length ? (
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
							<LayoutReports
								eventTriggered={layoutReportsEventTriggered}
								url={tab.url}
							/>
						)}
					</ClayTabs.TabPane>
				))}
			</ClayTabs.Content>
		</>
	) : null;
}
