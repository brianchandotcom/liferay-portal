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

import ClayTabs from '@clayui/tabs';

const tabs = [
	{
		active: true,
		title: 'Overview',
	},
	{
		active: true,
		title: 'Routines',
	},
	{
		active: true,
		title: 'Suites',
	},
	{
		active: true,
		title: 'Cases',
	},
	{
		active: true,
		title: 'Requirements',
	},
];

const Header = () => (
	<div className="header-container">
		<span className="d-flex flex-column">
			<small className="font-weight-bold text-secondary">PROJECT</small>

			<h1 className="font-weight-500">Project Directory</h1>
		</span>

		<div>
			<ClayTabs className="header-container-tabs" modern>
				{tabs.map((tab, index) => (
					<ClayTabs.Item
						active={false}
						innerProps={{
							'aria-controls': 'tabpanel-1',
						}}
						key={index}
						onClick={() => index}
					>
						{tab.title}
					</ClayTabs.Item>
				))}
			</ClayTabs>
		</div>
	</div>
);

export default Header;
