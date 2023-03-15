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

import {ClayTooltipProvider} from '@clayui/tooltip';
import classNames from 'classnames';
import {useRef, useState} from 'react';
import {Link, useLocation} from 'react-router-dom';
import {STORAGE_KEYS} from '~/core/Storage';
import {CONSENT_TYPE} from '~/util/enum';

import useStorage from '../../hooks/useStorage';
import i18n from '../../i18n';
import {TestrayIcon, TestrayIconBrand} from '../../images';
import CompareRunsPopover from '../CompareRunsPopover';
import TestrayIcons from '../Icons/TestrayIcon';
import Tooltip from '../Tooltip';
import SidebarFooter from './SidebarFooter';
import SidebarItem from './SidebarItem';
import TaskSidebar from './TasksSidebar';

const Sidebar = () => {
	const {pathname} = useLocation();
	const [expanded, setExpanded] = useStorage(STORAGE_KEYS.SIDEBAR, {
		consentType: CONSENT_TYPE.PERSONALIZATION,
		initialValue: true,
		storageType: 'persisted',
	});
	const [visible, setVisible] = useState(false);

	const CompareRunsContent = (
		<div
			className={classNames(
				'tr-sidebar__content__list__item tr-sidebar__content__list__item__compare-runs-options'
			)}
		>
			<TestrayIcons
				className="tr-sidebar__content__list__item__icon"
				fill="#8b8db2"
				size={35}
				symbol="drop"
			/>

			<span
				className={classNames('tr-sidebar__content__list__item__text', {
					'tr-sidebar__content__list__item__text--expanded': expanded,
				})}
			>
				{i18n.sub('compare-x', 'runs')}
			</span>
		</div>
	);

	const ref = useRef<HTMLDivElement>(null);

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
			element: (
				<div onClick={() => setVisible((show) => !show)} ref={ref}>
					<Tooltip
						position="right"
						title={
							expanded
								? undefined
								: i18n.translate('compare-runs')
						}
					>
						{CompareRunsContent}
					</Tooltip>
				</div>
			),
		},
	];

	return (
		<ClayTooltipProvider>
			<div
				className={classNames('tr-sidebar', {
					'tr-sidebar--expanded': expanded,
				})}
			>
				<div className="tr-sidebar__content">
					<div className="mb-4">
						<Link className="tr-sidebar__content__title" to="/">
							<TestrayIcon />

							<TestrayIconBrand
								className={classNames(
									'tr-sidebar__content__title__brand',
									{
										'tr-sidebar__content__title__brand--expanded': expanded,
									}
								)}
							/>
						</Link>

						<div className="tr-sidebar__content__list">
							{sidebarItems.map(
								({element, icon, label, path}, index) => {
									const [, ...items] = sidebarItems;

									if (path) {
										const someItemIsActive = items.some(
											(item) =>
												item.path
													? pathname.includes(
															item.path
													  )
													: false
										);

										return (
											<SidebarItem
												active={
													index === 0
														? !someItemIsActive
														: pathname.includes(
																path
														  )
												}
												expanded={expanded}
												icon={icon}
												key={index}
												label={label}
												path={path}
											/>
										);
									}

									return (
										<div
											className="tr-sidebar__content_list__item"
											key={index}
										>
											{element}
										</div>
									);
								}
							)}
						</div>

						<CompareRunsPopover
							expanded={expanded}
							setVisible={setVisible}
							triggedRef={ref}
							visible={visible}
						/>

						<div className="tr-sidebar__content__divider" />
					</div>

					<TaskSidebar expanded={expanded} />

					<div className="pb-1">
						<SidebarFooter
							expanded={expanded}
							onClick={() => setExpanded(!expanded)}
						/>
					</div>
				</div>
			</div>
		</ClayTooltipProvider>
	);
};

export default Sidebar;
