/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {Sidebar} from 'data-engine-taglib';
import React, {useState} from 'react';

import DataAndViewsTab from './DataAndViewsTab.es';

export default () => {
	const [currentTab, setCurrentTab] = useState();

	const tabs = [
		{
			content: <DataAndViewsTab />,
			title: Liferay.Language.get('data-and-views'),
		},
	];

	return (
		<Sidebar className="app-builder-workflow-app__sidebar">
			<Sidebar.Header>
				{!currentTab ? (
					<h3 className="title">
						{Liferay.Language.get('configuration')}
					</h3>
				) : (
					<div className="tab-title">
						<ClayButton
							data-testid="back-button"
							displayType="secondary"
							onClick={() => setCurrentTab()}
							small
						>
							<span className="icon-monospaced">
								<ClayIcon symbol="angle-left" />
							</span>
						</ClayButton>

						<h3>{currentTab.title}</h3>
					</div>
				)}
			</Sidebar.Header>

			<Sidebar.Body>
				{!currentTab
					? tabs.map(({title}, index) => (
							<ClayButton
								className="tab-button"
								displayType="secondary"
								key={index}
								onClick={() => setCurrentTab(tabs[index])}
							>
								<span className="float-left">{title}</span>

								<ClayIcon
									className="dropdown-button-asset float-right"
									symbol="angle-right"
								/>
							</ClayButton>
					  ))
					: currentTab.content}
			</Sidebar.Body>
		</Sidebar>
	);
};
