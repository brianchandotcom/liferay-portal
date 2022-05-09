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

import ClayPopover from '@clayui/popover';
import {Link, useLocation} from 'react-router-dom';

import i18n from '../../i18n';
import TestrayLogo from '../../images/testray-logo';
import CompareRun from './CompareRuns';
import SidebarFooter from './SidebarFooter';
import SidebarItem from './SidebarItem';

const Sidebar = () => {
	const {pathname} = useLocation();

	const sidebarItems = [
		{
			icon: 'polls',
			label: i18n.translate('results'),
			path: '/',
		},
		{
			icon: 'merge',
			label: i18n.translate('testflow'),
			path: '/testflow',
		},
		{
			className: 'mt-3',
			element: (
				<ClayPopover
					alignPosition="right"
					closeOnClickOutside
					disableScroll={true}
					header="Compare Runs"
					size="lg"
					trigger={
						<div>
							<SidebarItem
								icon="drop"
								label={i18n.translate('compare-runs')}
							/>
						</div>
					}
				>
					<CompareRun />
				</ClayPopover>
			),
		},
	];

	return (
		<div className="testray-sidebar">
			<div className="testray-sidebar-content">
				<Link className="d-flex flex-center mb-5 testray-logo" to="/">
					<TestrayLogo />
				</Link>

				{sidebarItems.map(
					({className, element, icon, label, path}, index) => {
						const [, ...items] = sidebarItems;

						if (path) {
							const someItemIsActive = items.some((item) =>
								item.path ? pathname.includes(item.path) : false
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
						}

						return <div key={index}>{element}</div>;
					}
				)}
			</div>

			<SidebarFooter />
		</div>
	);
};

export default Sidebar;
