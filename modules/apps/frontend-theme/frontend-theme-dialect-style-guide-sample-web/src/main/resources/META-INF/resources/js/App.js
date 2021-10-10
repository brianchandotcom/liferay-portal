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
import ColorGuide from './guides/ColorGuide';
import FormGuide from './guides/FormGuide';
import GeneralGuide from './guides/GeneralGuide';
import TypographyGuide from './guides/TypographyGuide';

const TABS = [
	{
		content: <ColorGuide />,
		label: Liferay.Language.get('colors'),
	},
	{
		content: <TypographyGuide />,
		label: Liferay.Language.get('typography'),
	},
	{
		content: <GeneralGuide />,
		label: Liferay.Language.get('general'),
	},
	{
		content: <ButtonGuide />,
		label: Liferay.Language.get('buttons'),
	},
	{
		content: <FormGuide />,
		label: Liferay.Language.get('forms'),
	},
];

export default function App() {
	const [activeTabKeyValue, setActiveTabKeyValue] = useState(0);

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

				<ClayTabs modern>
					{TABS.map((tab, i) => (
						<ClayTabs.Item
							active={activeTabKeyValue === i}
							id={`tab-${i}`}
							innerProps={{
								'aria-controls': `tabpanel-${i}`,
							}}
							key={tab.label}
							onClick={() => setActiveTabKeyValue(i)}
						>
							{tab.label}
						</ClayTabs.Item>
					))}
				</ClayTabs>

				<ClayTabs.Content activeIndex={activeTabKeyValue} fade>
					{TABS.map((tab, i) => (
						<ClayTabs.TabPane
							aria-labelledby={`tab-${i}`}
							id={`tabpanel-${i}`}
							key={tab.label}
						>
							{tab.content}
						</ClayTabs.TabPane>
					))}
				</ClayTabs.Content>
			</ClayLayout.ContainerFluid>
		</div>
	);
}
