/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayTabs from '@clayui/tabs';
import React, {useState} from 'react';

import TokenGroup from '../components/TokenGroup';
import TokenItem from '../components/TokenItem';

const TERTIARY_TAB = (
	<div className="align-items-center d-flex p-2">
		<span className="inline-item inline-item-before">
			<ClayIcon symbol="hashtag" />
		</span>

		<div className="mr-2 text-paragraph">Tertiary</div>

		<div>
			<div>
				<h5 className="d-inline">k</h5>

				<h1 className="d-inline">999.9</h1>

				<h5 className="d-inline">k</h5>
			</div>

			<div className="font-weight-bold text-paragraph-sm text-right">
				12.5%
			</div>
		</div>
	</div>
);

const TABS_ACTIVE_BAR_COMPONENTS = [
	{
		className: 'nav-tabs nav-active-bar-top',
		type: 'underline',
	},
	{
		className: 'nav-secondary nav-active-bar-top',
		type: 'secondary',
	},
	{
		child: TERTIARY_TAB,
		className: 'nav-tertiary nav-active-bar-top',
		type: 'tertiary',
	},
];

const TABS_DISABLED_COMPONENTS = [
	{
		className: 'nav-tabs',
		disabled: true,
	},
	{
		className: 'nav-secondary',
		disabled: true,
	},
	{
		child: TERTIARY_TAB,
		className: 'nav-tertiary',
		disabled: true,
	},
	{
		child: 'segment',
		className: 'nav-segment ',
		disabled: true,
	},
];

const TABS_COMPONENTS = [
	{className: 'nav-tabs'},
	{className: 'nav-secondary'},
	{
		child: TERTIARY_TAB,
		className: 'nav-tertiary',
	},
	{className: 'nav-segment'},
];

const TABS_ICONS_COMPONENTS = [
	{className: 'nav-tabs', icon: 'after'},
	{className: 'nav-tabs', icon: 'before'},
	{className: 'nav-secondary', icon: 'after'},
	{className: 'nav-secondary', icon: 'before'},
	{className: 'nav-segment', icon: 'after'},
	{className: 'nav-segment', icon: 'before'},
];

const TABS_VERTICAL_COMPONENTS = [
	{className: 'nav-vertical nav-tabs'},
	{className: 'nav-vertical nav-secondary'},
	{
		child: TERTIARY_TAB,
		className: 'nav-vertical nav-tertiary',
	},
];

const TABS_TYPES = [
	{components: TABS_COMPONENTS, title: Liferay.Language.get('tabs')},
	{
		className: 'd-flex',
		components: TABS_VERTICAL_COMPONENTS,
		title: Liferay.Language.get('tabs-vertical'),
	},
	{
		components: TABS_ICONS_COMPONENTS,
		title: Liferay.Language.get('tabs-icons'),
	},
	{
		components: TABS_ACTIVE_BAR_COMPONENTS,
		title: Liferay.Language.get('tabs-active-bar-position'),
	},
	{
		components: TABS_DISABLED_COMPONENTS,
		title: Liferay.Language.get('tabs-disabled'),
	},
];

const Tabs = (props) => {
	const {child, className, disabled, icon} = props;

	const [activeTabKeyValue, setActiveTabKeyValue] = useState(0);
	const TABS_NAME = ['Tab 1', 'Tab 2', 'Tab 3'];

	return (
		<>
			<ClayTabs className={className} displayType="custom">
				{TABS_NAME.map((name, i) => (
					<ClayTabs.Item
						active={activeTabKeyValue === i}
						disabled={disabled}
						innerProps={{
							'aria-controls': 'tabpanel-1',
						}}
						key={i}
						onClick={() => setActiveTabKeyValue(i)}
					>
						{icon === 'before' && (
							<span className="inline-item inline-item-before">
								<ClayIcon symbol="hashtag" />
							</span>
						)}

						{child ? child : name}

						{icon === 'after' && (
							<span className="inline-item inline-item-after">
								<ClayIcon symbol="hashtag" />
							</span>
						)}
					</ClayTabs.Item>
				))}
			</ClayTabs>

			<ClayTabs.Content
				activeIndex={activeTabKeyValue}
				className="m-3"
				fade
			>
				<ClayTabs.TabPane aria-labelledby="tab-1">
					1. Proin efficitur imperdiet dolor, a iaculis orci lacinia
					eu.
				</ClayTabs.TabPane>

				<ClayTabs.TabPane aria-labelledby="tab-2">
					2. Proin efficitur imperdiet dolor, a iaculis orci lacinia
					eu.
				</ClayTabs.TabPane>

				<ClayTabs.TabPane aria-labelledby="tab-3">
					3. Proin efficitur imperdiet dolor, a iaculis orci lacinia
					eu.
				</ClayTabs.TabPane>
			</ClayTabs.Content>
		</>
	);
};

const TabsGuide = () => {
	return (
		<>
			{TABS_TYPES.map((tabType, tabTypeIndex) => (
				<TokenGroup
					group="tabs"
					key={tabTypeIndex}
					title={tabType.title}
				>
					{tabType.components.map((tabProps, i) => (
						<TokenItem
							className={`my-5 ${tabType.className}`}
							key={i}
							label={tabProps.className}
							size="large"
						>
							{Tabs(tabProps)}
						</TokenItem>
					))}
				</TokenGroup>
			))}
		</>
	);
};

export default TabsGuide;
