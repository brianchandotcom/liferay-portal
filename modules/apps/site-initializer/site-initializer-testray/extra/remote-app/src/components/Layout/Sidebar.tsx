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

import {Align} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {useLocation} from 'react-router-dom';

import TestrayLogo from '../../images/testray-logo';
import {Liferay} from '../../services/liferay/liferay';
import {MANAGE_DROPDOWN, USER_DROPDOWN} from '../../util/constants';
import {Avatar} from '../Avatar';
import DropDown from '../DropDown';
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
	const {pathname} = useLocation();

	return (
		<div className="testray-sidebar">
			<div className="testray-sidebar-content">
				<a
					className="d-flex flex-center mb-5 testray-logo"
					href="https://testray.liferay.com/web/guest"
				>
					<TestrayLogo />
				</a>

				{sidebarItems.map(({className, icon, label, path}, index) => {
					const [, ...items] = sidebarItems;

					const someItemIsActive = items.some((item) =>
						pathname.includes(item.path)
					);

					return (
						<SidebarItem
							active={
								index === 0
									? !someItemIsActive
									: pathname.includes(path)
							}
							className={className}
							icon={icon}
							key={index}
							label={label}
							path={path}
						/>
					);
				})}
			</div>

			<div className="testray-sidebar-footer">
				<div className="divider divider-full" />

				<DropDown
					items={MANAGE_DROPDOWN}
					position={Align.RightBottom}
					trigger={
						<div className="align-items-center d-flex testray-sidebar-item">
							<ClayIcon fontSize={16} symbol="cog" />

							<span className="ml-1 testray-sidebar-text">
								Manage
							</span>
						</div>
					}
				></DropDown>

				<DropDown
					items={USER_DROPDOWN}
					position={Align.RightBottom}
					trigger={
						<div className="testray-sidebar-item">
							<Avatar
								displayName
								name={Liferay.ThemeDisplay.getUserName()}
								url="https://clayui.com/images/long_user_image.png"
							/>
						</div>
					}
				></DropDown>
			</div>
		</div>
	);
};

export default Sidebar;
