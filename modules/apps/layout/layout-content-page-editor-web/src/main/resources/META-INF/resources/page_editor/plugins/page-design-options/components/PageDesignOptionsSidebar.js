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

import ClayCard from '@clayui/card';
import ClayIcon from '@clayui/icon';
import ClayTabs from '@clayui/tabs';
import classNames from 'classnames';
import React, {useMemo, useState} from 'react';

import {CHANGE_MASTER_LAYOUT} from '../../../app/actions/types';
import {config} from '../../../app/config/index';
import LayoutService from '../../../app/services/LayoutService';
import {useDispatch, useSelector} from '../../../app/store/index';
import {useId} from '../../../app/utils/useId';
import SidebarPanelContent from '../../../common/components/SidebarPanelContent';
import SidebarPanelHeader from '../../../common/components/SidebarPanelHeader';

const OPTIONS_TYPES = {
	master: 'master',
	styleBook: 'styleBook',
};

export default function PageDesignOptionsSidebar() {
	const masterLayoutPlid = useSelector(
		(state) => state.masterLayout?.masterLayoutPlid
	);

	const tabs = useMemo(
		() => [
			{
				icon: 'page',
				label: Liferay.Language.get('master'),
				options: config.masterLayouts.map((masterLayout) => ({
					...masterLayout,
					isActive:
						masterLayoutPlid === masterLayout.masterLayoutPlid,
				})),
				type: OPTIONS_TYPES.master,
			},
			{
				icon: 'magic',
				label: Liferay.Language.get('style-book'),
				options: config.styleBooks.map((styleBook) => ({
					...styleBook,
					isActive:
						config.styleBookEntryId === styleBook.styleBookEntryId,
				})),
				type: OPTIONS_TYPES.styleBook,
			},
		],
		[masterLayoutPlid]
	);
	const [activeTabId, setActiveTabId] = useState(0);
	const tabIdNamespace = useId();

	const getTabId = (tabId) => `${tabIdNamespace}tab${tabId}`;
	const getTabPanelId = (tabId) => `${tabIdNamespace}tabPanel${tabId}`;

	return (
		<>
			<SidebarPanelHeader>
				{Liferay.Language.get('page-design-options')}
			</SidebarPanelHeader>

			<SidebarPanelContent>
				<ClayTabs
					className="page-editor__sidebar__page-design-options__tabs"
					modern
				>
					{tabs.map((tab, index) => (
						<ClayTabs.Item
							active={activeTabId === index}
							innerProps={{
								'aria-controls': getTabPanelId(index),
								id: getTabId(index),
							}}
							key={index}
							onClick={() => setActiveTabId(index)}
						>
							{tab.label}
						</ClayTabs.Item>
					))}
				</ClayTabs>

				<ClayTabs.Content activeIndex={activeTabId} fade>
					{tabs.map(({icon, options, type}, index) => (
						<ClayTabs.TabPane
							aria-labelledby={getTabId(index)}
							id={getTabPanelId(index)}
							key={index}
						>
							<OptionList
								icon={icon}
								options={options}
								type={type}
							/>
						</ClayTabs.TabPane>
					))}
				</ClayTabs.Content>
			</SidebarPanelContent>
		</>
	);
}

const OptionList = ({options = [], icon}) => {
	return (
		<ul className="list-unstyled mt-3">
			{options.map(({imagePreviewURL, isActive, name}, index) => (
				<li key={index}>
					<ClayCard
						className={classNames({
							'page-editor__sidebar__design-options__tab-card--active': isActive,
						})}
						displayType="file"
						selectable
					>
						<ClayCard.AspectRatio className="card-item-first">
							{imagePreviewURL ? (
								<img
									alt="thumbnail"
									className="aspect-ratio-item aspect-ratio-item-center-middle aspect-ratio-item-fluid"
									src={imagePreviewURL}
								/>
							) : (
								<div className="aspect-ratio-item aspect-ratio-item-center-middle aspect-ratio-item-fluid card-type-asset-icon">
									<ClayIcon symbol={icon} />
								</div>
							)}
						</ClayCard.AspectRatio>
						<ClayCard.Body>
							<ClayCard.Row>
								<div className="autofit-col autofit-col-expand">
									<section className="autofit-section">
										<ClayCard.Description displayType="title">
											{name}
											{isActive && (
												<ClayIcon
													className="ml-2 text-primary"
													symbol={'check-circle'}
												/>
											)}
										</ClayCard.Description>
									</section>
								</div>
							</ClayCard.Row>
						</ClayCard.Body>
					</ClayCard>
				</li>
			))}
		</ul>
	);
};
