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

import ClayAlert from '@clayui/alert';
import ClayCard from '@clayui/card';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import ClaySticker from '@clayui/sticker';
import ClayTabs from '@clayui/tabs';
import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import React, {useCallback, useEffect, useMemo, useState} from 'react';

import {LAYOUT_TYPES} from '../../../app/config/constants/layoutTypes';
import {config} from '../../../app/config/index';
import {useDispatch, useSelector} from '../../../app/contexts/StoreContext';
import LayoutService from '../../../app/services/LayoutService';
import changeMasterLayout from '../../../app/thunks/changeMasterLayout';
import useTabsKeyboardNavigation from '../../../app/utils/useTabsKeyboardNavigation';
import SidebarPanelHeader from '../../../common/components/SidebarPanelHeader';
import {useId} from '../../../core/hooks/useId';
import {useSetStyleBook, useStyleBook} from '../hooks/useStyleBook';

const OPTIONS_TYPES = {
	master: 'master',
	styleBook: 'styleBook',
};

export default function PageDesignOptionsSidebar() {
	const dispatch = useDispatch();
	const selectedStyleBook = useStyleBook();
	const setSelectedStyleBook = useSetStyleBook();

	const [defaultStyleBook, setDefaultStyleBook] = useState({
		imagePreviewURL: config.defaultStyleBookEntryImagePreviewURL,
		name: config.defaultStyleBookEntryName,
	});

	const masterLayoutPlid = useSelector(
		(state) => state.masterLayout?.masterLayoutPlid
	);

	const onSelectMasterLayout = useCallback(
		(masterLayout) => {
			dispatch(
				changeMasterLayout({
					masterLayoutPlid: masterLayout.masterLayoutPlid,
				})
			).then(({styleBook}) => {
				const {
					defaultStyleBookEntryImagePreviewURL,
					defaultStyleBookEntryName,
					styleBookEntryId,
					tokenValues,
				} = styleBook;

				setDefaultStyleBook({
					imagePreviewURL: defaultStyleBookEntryImagePreviewURL,
					name: defaultStyleBookEntryName,
				});

				// Changing the master layout should only affect the selected stylebook
				// only if styleBookEntryId is equal to 0 which means that the stylebook is
				// inherited

				if (styleBookEntryId === '0') {
					setSelectedStyleBook({styleBookEntryId, tokenValues});
				}
			});
		},
		[dispatch, setSelectedStyleBook]
	);

	const onSelectStyleBook = useCallback(
		(styleBookEntryId) => {
			LayoutService.changeStyleBookEntry({
				onNetworkStatus: () => {},
				styleBookEntryId,
			}).then(({tokenValues}) => {
				setSelectedStyleBook({styleBookEntryId, tokenValues});
			});
		},
		[setSelectedStyleBook]
	);

	useEffect(() => {
		if (selectedStyleBook && document.documentElement) {
			Object.values(selectedStyleBook.tokenValues).forEach((token) => {
				document.documentElement.style.setProperty(
					`--${token.cssVariable}`,
					token.value
				);
			});
		}
	}, [selectedStyleBook]);

	const tabs = useMemo(
		() =>
			getTabs(
				masterLayoutPlid,
				selectedStyleBook,
				defaultStyleBook,
				onSelectMasterLayout,
				onSelectStyleBook
			),
		[
			defaultStyleBook,
			masterLayoutPlid,
			onSelectMasterLayout,
			onSelectStyleBook,
			selectedStyleBook,
		]
	);

	const {onTabItemKeyDown, tabItemRef} = useTabsKeyboardNavigation({
		numberOfPanels: tabs.length,
		selectPanel: (panelIndex) => {
			setActiveTabId(panelIndex);
		},
	});

	const [activeTabId, setActiveTabId] = useState(0);
	const tabIdNamespace = useId();

	const getTabId = (tabId) => `${tabIdNamespace}tab${tabId}`;
	const getTabPanelId = (tabId) => `${tabIdNamespace}tabPanel${tabId}`;

	return (
		<>
			<SidebarPanelHeader
				iconRight={
					<ClayLink
						displayType="secondary"
						href={config.lookAndFeelURL}
						monospaced
						title={Liferay.Language.get('more-page-design-options')}
					>
						<ClayIcon symbol="cog" />
					</ClayLink>
				}
			>
				{Liferay.Language.get('page-design-options')}
			</SidebarPanelHeader>

			<ClayTabs className="flex-shrink-0 page-editor__sidebar__page-design-options__tabs px-3">
				{tabs.map((tab, index) => (
					<ClayTabs.Item
						active={activeTabId === index}
						innerProps={{
							'aria-controls': getTabPanelId(index),
							'id': getTabId(index),
						}}
						key={index}
						onClick={() => setActiveTabId(index)}
						onKeyDown={(event) => onTabItemKeyDown(event, index)}
						ref={(ref) => tabItemRef(ref, index)}
					>
						{tab.label}
					</ClayTabs.Item>
				))}
			</ClayTabs>

			<ClayTabs.Content
				activeIndex={activeTabId}
				className="overflow-auto px-3"
				fade
			>
				{tabs.map(({icon, label, options, type}, index) => (
					<ClayTabs.TabPane
						aria-label={sub(
							Liferay.Language.get('select-x'),
							label
						)}
						id={getTabPanelId(index)}
						key={index}
					>
						<OptionList icon={icon} options={options} type={type} />
					</ClayTabs.TabPane>
				))}
			</ClayTabs.Content>
		</>
	);
}

const OptionList = ({options = [], icon, type}) => {
	if (type === OPTIONS_TYPES.styleBook && !config.styleBookEnabled) {
		return (
			<ClayAlert className="mt-3" displayType="info">
				{config.isPrivateLayoutsEnabled
					? Liferay.Language.get(
							'this-page-is-using-a-different-theme-than-the-one-set-for-public-pages'
					  )
					: Liferay.Language.get(
							'this-page-is-using-a-different-theme-than-the-one-set-for-pages'
					  )}
			</ClayAlert>
		);
	}

	return (
		<ul className="list-unstyled mt-4">
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
									onClick();
								}
							}}
							onKeyDown={(event) => {
								if (event.key === 'Enter' && !isActive) {
									onClick();
								}
							}}
							role="button"
							selectable
							tabindex="0"
						>
							<ClayCard.AspectRatio
								className="card-item-first"
								containerAspectRatio="16/9"
							>
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

								{isActive && (
									<ClaySticker
										displayType="primary"
										position="bottom-left"
									>
										<ClayIcon symbol="check-circle" />
									</ClaySticker>
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

function getDefaultStyleBookLabel(defaultStyleBook, masterLayoutPlid) {
	const inheritingFromMaster =
		masterLayoutPlid !== '0' && config.layoutType !== LAYOUT_TYPES.master;
	const usingThemeStylebook = !defaultStyleBook.name;

	if (usingThemeStylebook) {
		return Liferay.Language.get('styles-from-theme');
	}

	if (inheritingFromMaster) {
		return Liferay.Language.get('styles-from-master');
	}

	return Liferay.Language.get('styles-by-default');
}

function getTabs(
	masterLayoutPlid,
	selectedStyleBook,
	defaultStyleBook,
	onSelectMasterLayout,
	onSelectStyleBook
) {
	const styleBooks = [
		{
			imagePreviewURL: defaultStyleBook.imagePreviewURL,
			name: getDefaultStyleBookLabel(defaultStyleBook, masterLayoutPlid),
			styleBookEntryId: '0',
			subtitle: defaultStyleBook.name,
		},
		...config.styleBooks,
	];

	const tabs = [];

	if (config.layoutType !== LAYOUT_TYPES.master) {
		tabs.push({
			icon: 'page',
			label: Liferay.Language.get('master'),
			options: config.masterLayouts.map((masterLayout) => ({
				...masterLayout,
				isActive: masterLayoutPlid === masterLayout.masterLayoutPlid,
				onClick: () => onSelectMasterLayout(masterLayout),
			})),
			type: OPTIONS_TYPES.master,
		});
	}

	tabs.push({
		icon: 'magic',
		label: Liferay.Language.get('style-book'),
		options: styleBooks.map((styleBook) => ({
			...styleBook,
			isActive:
				selectedStyleBook.styleBookEntryId ===
				styleBook.styleBookEntryId,
			onClick: () => onSelectStyleBook(styleBook.styleBookEntryId),
		})),
		type: OPTIONS_TYPES.styleBook,
	});

	return tabs;
}
