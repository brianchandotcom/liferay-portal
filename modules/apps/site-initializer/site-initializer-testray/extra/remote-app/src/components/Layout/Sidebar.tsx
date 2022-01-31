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

import ClaySticker from '@clayui/sticker';

import TestrayLogo from '../../images/testray-logo';
import {Liferay} from '../../services/liferay/liferay';
import SidebarItem from './SidebarItem';

const sidebarItems = [
	{
		icon: 'polls',
		label: 'Results',
		path: '/',
	},
	{
		icon: 'merge',
		label: 'TestFlow',
		path: '/testflow',
	},
	{
		className: 'mt-3',
		icon: 'drop',
		label: 'Compare Runs',
		path: '/compare-runs',
	},
];

const Sidebar = () => {
	return (
		<div className="testray-sidebar">
			<div className="testray-sidebar-content">
				<a
					className="d-flex flex-center mb-5 testray-logo"
					href="https://testray.liferay.com/web/guest"
				>
					<TestrayLogo />
				</a>

				{sidebarItems.map((item, index) => (
					<SidebarItem
						className={item.className}
						icon={item.icon}
						key={index}
						label={item.label}
						path={item.path}
					/>
				))}
			</div>

			<div className="testray-sidebar-footer">
				<SidebarItem icon="cog" label="Manage" path="/manage" />

				<div className="divider divider-full" />

				<div className="testray-sidebar-item">
					<ClaySticker size="lg">
						<ClaySticker.Image
							alt="placeholder"
							src="https://clayui.com/images/long_user_image.png"
						/>
					</ClaySticker>

					<span className="ml-2">
						{Liferay.ThemeDisplay.getUserName()}
					</span>
				</div>
			</div>
		</div>
	);
};

export default Sidebar;
