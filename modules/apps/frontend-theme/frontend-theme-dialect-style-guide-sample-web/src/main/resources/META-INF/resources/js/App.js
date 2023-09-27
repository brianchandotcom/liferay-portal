/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import ClayTabs from '@clayui/tabs';
import React, {useState} from 'react';

import '../css/main.scss';
import ButtonGuide from './guides/ButtonGuide';
import CardGuide from './guides/CardGuide';
import ColorGuide from './guides/ColorGuide';
import FormGuide from './guides/FormGuide';
import GeneralGuide from './guides/GeneralGuide';
import LabelGuide from './guides/LabelGuide';
import TableGuide from './guides/TableGuide';
import TabsGuide from './guides/TabsGuide';
import TypographyGuide from './guides/TypographyGuide';

const TABS = [
	{
		content: <ColorGuide />,
		hash: '#colors',
		label: Liferay.Language.get('colors'),
	},
	{
		content: <TypographyGuide />,
		hash: '#typography',
		label: Liferay.Language.get('typography'),
	},
	{
		content: <GeneralGuide />,
		hash: '#general',
		label: Liferay.Language.get('general'),
	},
	{
		content: <ButtonGuide />,
		hash: '#buttons',
		label: Liferay.Language.get('buttons'),
	},
	{
		content: <CardGuide />,
		hash: '#cards',
		label: Liferay.Language.get('cards'),
	},
	{
		content: <FormGuide />,
		hash: '#forms',
		label: Liferay.Language.get('forms'),
	},
	{
		content: <LabelGuide />,
		hash: '#labels',
		label: Liferay.Language.get('labels'),
	},
	{
		content: <TableGuide />,
		hash: '#tables',
		label: Liferay.Language.get('tables'),
	},
	{
		content: <TabsGuide />,
		hash: '#tabs',
		label: Liferay.Language.get('tabs'),
	},
];

export default function App() {
	const [activeTabKeyValue, setActiveTabKeyValue] = useState(
		TABS.findIndex((tab) => tab.hash === location.hash) >= 0
			? location.hash
			: TABS[0].hash
	);

	return (
		<div className="dialect-style-guide">
			<ClayLayout.ContainerFluid>
				<ClayLayout.Row>
					<ClayLayout.Col>
						<h1>
							{Liferay.Language.get('dialect-style-guide-sample')}
						</h1>
					</ClayLayout.Col>
				</ClayLayout.Row>

				<ClayTabs>
					{TABS.map((tab, i) => (
						<ClayTabs.Item
							active={activeTabKeyValue === tab.hash}
							href={tab.hash}
							id={`tab-${i}`}
							innerProps={{
								'aria-controls': `tabpanel-${tab.hash}`,
							}}
							key={tab.label}
							onClick={() => setActiveTabKeyValue(tab.hash)}
						>
							{tab.label}
						</ClayTabs.Item>
					))}
				</ClayTabs>

				<ClayTabs.Content
					activeIndex={TABS.findIndex(
						(tab) => tab.hash === activeTabKeyValue
					)}
					fade
				>
					{TABS.map((tab) => (
						<ClayTabs.TabPane
							aria-labelledby={`tab-${tab.hash}`}
							id={`tabpanel-${tab.hash}`}
							key={tab.hash}
						>
							{tab.content}
						</ClayTabs.TabPane>
					))}
				</ClayTabs.Content>
			</ClayLayout.ContainerFluid>
		</div>
	);
}
