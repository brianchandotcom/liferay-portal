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

import ClayCard from '@clayui/card';
import ClayTabs from '@clayui/tabs';
import React, {useMemo, useState} from 'react';

import {config} from '../../../app/config/index';
import {useId} from '../../../app/utils/useId';
import SidebarPanelContent from '../../../common/components/SidebarPanelContent';
import SidebarPanelHeader from '../../../common/components/SidebarPanelHeader';

export default function PageDesignOptionsSidebar() {
	const tabs = useMemo(
		() => [
			{
				label: Liferay.Language.get('master'),
				options: config.masterLayouts,
			},
			{
				label: Liferay.Language.get('style-book'),
				options: config.styleBooks,
			},
		],
		[]
	);
	const [activeTabId, setActiveTabId] = useState(0);
	const tabIdNamespace = useId();

	const getTabId = (tabId) => `${tabIdNamespace}tab${tabId}`;
	const getTabPanelId = (tabId) => `${tabIdNamespace}tabPanel${tabId}`;

	return (
		<>
			<SidebarPanelHeader>
				{Liferay.Language.get('page-design-options')}
			</SidebarPanelHeader>

			<SidebarPanelContent>
				<ClayTabs
					className="page-editor__sidebar__page-design-options__tabs"
					modern
				>
					{tabs.map((tab, index) => (
						<ClayTabs.Item
							active={activeTabId === index}
							innerProps={{
								'aria-controls': getTabPanelId(index),
								id: getTabId(index),
							}}
							key={index}
							onClick={() => setActiveTabId(index)}
						>
							{tab.label}
						</ClayTabs.Item>
					))}
				</ClayTabs>

				<ClayTabs.Content activeIndex={activeTabId} fade>
					{tabs.map(({options}, index) => (
						<ClayTabs.TabPane
							aria-labelledby={getTabId(index)}
							id={getTabPanelId(index)}
							key={index}
						>
							<OptionList options={options} />
						</ClayTabs.TabPane>
					))}
				</ClayTabs.Content>
			</SidebarPanelContent>
		</>
	);
}

const OptionList = ({options = []}) => {
	return (
		<ul className="list-unstyled mt-3">
			{options.map(({imagePreviewURL, name}, index) => (
				<li key={index}>
					<ClayCard
						className="page-editor__sidebar__design-options__tab-card"
						displayType="image"
					>
						<ClayCard.AspectRatio className="card-item-first">
							{imagePreviewURL && (
								<img
									alt="thumbnail"
									className="aspect-ratio-item aspect-ratio-item-center-middle aspect-ratio-item-fluid"
									src={imagePreviewURL}
								/>
							)}
						</ClayCard.AspectRatio>
						<ClayCard.Body>
							<ClayCard.Row>
								<div className="autofit-col autofit-col-expand">
									<section className="autofit-section">
										<ClayCard.Description displayType="title">
											{name}
										</ClayCard.Description>
									</section>
								</div>
							</ClayCard.Row>
						</ClayCard.Body>
					</ClayCard>
				</li>
			))}
		</ul>
	);
};
