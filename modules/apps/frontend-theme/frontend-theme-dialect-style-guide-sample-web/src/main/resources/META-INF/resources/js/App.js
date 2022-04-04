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
