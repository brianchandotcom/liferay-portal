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
import ClayLink from '@clayui/link';
import ClayTabs from '@clayui/tabs';
import classNames from 'classnames';
import React, {useMemo, useState} from 'react';

import {LAYOUT_TYPES} from '../../../app/config/constants/layoutTypes';
import {config} from '../../../app/config/index';
import LayoutService from '../../../app/services/LayoutService';
import {useDispatch, useSelector} from '../../../app/store/index';
import changeMasterLayout from '../../../app/thunks/changeMasterLayout';
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

	const tabs = useMemo(() => getTabs(masterLayoutPlid), [masterLayoutPlid]);
	const [activeTabId, setActiveTabId] = useState(0);
	const tabIdNamespace = useId();

	const getTabId = (tabId) => `${tabIdNamespace}tab${tabId}`;
	const getTabPanelId = (tabId) => `${tabIdNamespace}tabPanel${tabId}`;

	return (
		<>
			<SidebarPanelHeader className="justify-content-between">
				{Liferay.Language.get('page-design-options')}

				<ClayLink
					className="font-weight-normal"
					href={config.lookAndFeelURL}
				>
					{Liferay.Language.get('more')}
				</ClayLink>
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
	const dispatch = useDispatch();

	return (
		<ul className="list-unstyled mt-3">
			{options.map(
				(
					{imagePreviewURL, isActive, name, onClick, subtitle},
					index
				) => (
					<li key={index}>
						<ClayCard
							aria-label={name}
							className={classNames({
								'page-editor__sidebar__design-options__tab-card--active': isActive,
							})}
							displayType="file"
							onClick={() => {
								if (!isActive) {
									onClick(dispatch);
								}
							}}
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
											</ClayCard.Description>
											{subtitle && (
												<ClayCard.Description displayType="subtitle">
													{subtitle}
												</ClayCard.Description>
											)}
										</section>
									</div>
								</ClayCard.Row>
							</ClayCard.Body>
						</ClayCard>
					</li>
				)
			)}
		</ul>
	);
};

function getTabs(masterLayoutPlid) {
	const styleBooks = [
		{
			name:
				config.layoutType === LAYOUT_TYPES.master
					? Liferay.Language.get('default-style-book')
					: Liferay.Language.get('inherited-from-master'),
			styleBookEntryId: '0',
			subtitle:
				config.defaultStyleBookEntryName ||
				Liferay.Language.get('provided-by-theme'),
		},
		...config.styleBooks,
	];

	const tabs = [
		{
			icon: 'magic',
			label: Liferay.Language.get('style-book'),
			options: styleBooks.map((styleBook) => ({
				...styleBook,
				isActive:
					config.styleBookEntryId === styleBook.styleBookEntryId,
				onClick: () => {
					LayoutService.changeStyleBookEntry({
						onNetworkStatus: () => {},
						styleBookEntryId: styleBook.styleBookEntryId,
					}).then(() => {
						Liferay.Util.navigate(window.location.href);
					});
				},
			})),
			type: OPTIONS_TYPES.styleBook,
		},
	];

	if (config.layoutType !== LAYOUT_TYPES.master) {
		tabs.splice(0, 0, {
			disabled: config.layoutType === LAYOUT_TYPES.master,
			icon: 'page',
			label: Liferay.Language.get('master'),
			options: config.masterLayouts.map((masterLayout) => ({
				...masterLayout,
				isActive: masterLayoutPlid === masterLayout.masterLayoutPlid,
				onClick: (dispatch) =>
					dispatch(
						changeMasterLayout({
							masterLayoutPlid: masterLayout.masterLayoutPlid,
						})
					),
			})),
			type: OPTIONS_TYPES.master,
		});
	}

	return tabs;
}
