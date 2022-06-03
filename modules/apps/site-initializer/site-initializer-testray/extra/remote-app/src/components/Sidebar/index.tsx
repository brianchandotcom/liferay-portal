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

import ClayIcon from '@clayui/icon';
import ClayPopover from '@clayui/popover';
import classNames from 'classnames';
import {Link, useLocation} from 'react-router-dom';

import useLocalStorage from '../../hooks/useLocalStorage';
import i18n from '../../i18n';
import TestrayIcon from '../../images/testray-icon';
import TestrayIconBrand from '../../images/testray-icon-brand';
import CompareRun from './CompareRuns';
import SidebarFooter from './SidebarFooter';
import SidebarItem from './SidebarItem';

const Sidebar = () => {
	const {pathname} = useLocation();
	const [expanded, setExpanded] = useLocalStorage('sidebar', true);

	const popoverItem = (
		<div className={classNames('cursor-pointer testray-sidebar-item')}>
			<ClayIcon fontSize={20} symbol="drop" />

			<span
				className={classNames('ml-1 testray-sidebar-text', {
					'testray-sidebar-text-expanded': expanded,
				})}
			>
				{i18n.translate('compare-runs')}
			</span>
		</div>
	);

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
					trigger={popoverItem}
				>
					<CompareRun />
				</ClayPopover>
			),
		},
	];

	return (
		<div
			className={classNames('testray-sidebar', {
				'testray-sidebar-expanded': expanded,
			})}
		>
			<div className="testray-sidebar-content">
				<Link className="d-flex flex-center mb-5 w-100" to="/">
					<TestrayIcon className="testray-logo" />

					<TestrayIconBrand
						className={classNames('testray-brand-logo', {
							'testray-brand-logo-expand': expanded,
						})}
					/>
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
									expanded={expanded}
									icon={icon}
									key={index}
									label={label}
									path={path}
								/>
							);
						}

						return (
							<div className={className} key={index}>
								{element}
							</div>
						);
					}
				)}
			</div>

			<SidebarFooter
				expanded={expanded}
				onClick={() => setExpanded(!expanded)}
			/>
		</div>
	);
};

export default Sidebar;
