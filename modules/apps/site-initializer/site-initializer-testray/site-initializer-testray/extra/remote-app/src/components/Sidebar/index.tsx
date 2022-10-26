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
import {ClayTooltipProvider} from '@clayui/tooltip';
import classNames from 'classnames';
import {Fragment, useRef} from 'react';
import {Link, useLocation} from 'react-router-dom';

import useStorage from '../../hooks/useStorage';
import i18n from '../../i18n';
import {TestrayIcon, TestrayIconBrand} from '../../images';
import TestrayIcons from '../Icons/TestrayIcon';
import Tooltip from '../Tooltip';
import CompareRun from './CompareRuns';
import SidebarFooter from './SidebarFooter';
import SidebarItem from './SidebarItem';

const Sidebar = () => {
	const {pathname} = useLocation();
	const [expanded, setExpanded] = useStorage('sidebar', true);
	const tooltipRef = useRef(null);

	const TooltipProviderWrapper = expanded ? Fragment : ClayTooltipProvider;

	const CompareRunsContent = (
		<div className={classNames('cursor-pointer testray-sidebar-item')}>
			<TestrayIcons
				className="testray-icon"
				fill="#8b8db2"
				size={35}
				symbol="drop"
			/>

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
					disableScroll
					header={i18n.translate('compare-runs')}
					size="lg"
					trigger={
						<div>
							{expanded ? (
								<Tooltip
									position="right"
									ref={tooltipRef}
									title={i18n.translate('compare-runs')}
								>
									{CompareRunsContent}
								</Tooltip>
							) : (
								CompareRunsContent
							)}
						</div>
					}
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
			<TooltipProviderWrapper>
				<>
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
							(
								{className, element, icon, label, path},
								index
							) => {
								const [, ...items] = sidebarItems;

								if (path) {
									const someItemIsActive = items.some(
										(item) =>
											item.path
												? pathname.includes(item.path)
												: false
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
				</>
			</TooltipProviderWrapper>
		</div>
	);
};

export default Sidebar;
