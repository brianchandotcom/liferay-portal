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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayLayout from '@clayui/layout';
import ClayModal, {useModal} from '@clayui/modal';

import 'product-navigation-global-apps/css/GlobalMenu.scss';
import ClaySticker from '@clayui/sticker';
import ClayTabs from '@clayui/tabs';
import {fetch} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useRef, useState} from 'react';

const Sites = ({label, sites}) => {
	return (
		<>
			<li className="dropdown-subheader">{label}</li>

			{sites.map(({key, label, logoURL, url}) => (
				<li key={key}>
					<a className="dropdown-item" href={url}>
						<ClaySticker
							className="inline-item-before"
							inline={true}
							size="sm"
						>
							<img className="sticker-img" src={logoURL} />
						</ClaySticker>

						{label}
					</a>
				</li>
			))}
		</>
	);
};

const AppsPanel = ({
	categories = [],
	handleCloseButtonClick = () => {},
	portletNamespace,
	sites,
}) => {
	const [activeTab, setActiveTab] = useState(0);

	return (
		<>
			<ClayLayout.ContainerFluid>
				<ClayLayout.ContentRow>
					<ClayLayout.ContentCol expand>
						<ClayTabs modern>
							{categories.map(({key, label}, index) => (
								<ClayTabs.Item
									active={activeTab === index}
									id={`${portletNamespace}tab_${index}`}
									key={key}
									onClick={() => setActiveTab(index)}
								>
									{label}
								</ClayTabs.Item>
							))}
						</ClayTabs>
					</ClayLayout.ContentCol>

					<ClayLayout.ContentCol>
						<ClayButtonWithIcon
							className="text-secondary"
							displayType="unstyled"
							onClick={handleCloseButtonClick}
							small
							symbol="times"
							title={Liferay.Language.get('close')}
						/>
					</ClayLayout.ContentCol>
				</ClayLayout.ContentRow>
			</ClayLayout.ContainerFluid>

			<ClayTabs.Content activeIndex={activeTab}>
				{categories.map(({childCategories}, index) => (
					<ClayTabs.TabPane
						aria-labelledby={`${portletNamespace}tab_${index}`}
						key={`tabPane-${index}`}
					>
						<ClayLayout.Row className="c-p-md-3">
							{childCategories.map(({key, label, panelApps}) => (
								<ClayLayout.Col key={key} md>
									<ul className="list-unstyled">
										<li className="dropdown-subheader">
											{label}
										</li>

										{panelApps.map(
											({label, portletId, url}) => (
												<li key={portletId}>
													<a
														className="dropdown-item"
														href={url}
													>
														{label}
													</a>
												</li>
											)
										)}
									</ul>
								</ClayLayout.Col>
							))}

							<ClayLayout.Col md>
								<ul className="bg-light list-unstyled rounded">
									<li className="dropdown-subheader">
										{Liferay.Language.get('sites')}
									</li>

									{sites.recentSites &&
										sites.recentSites.length > 0 && (
											<Sites
												label={Liferay.Language.get(
													'recently-visited'
												)}
												sites={sites.recentSites}
											/>
										)}

									{sites.mySites &&
										sites.mySites.length > 0 && (
											<Sites
												label={Liferay.Language.get(
													'my-sites'
												)}
												sites={sites.mySites}
											/>
										)}

									{sites.viewAllURL && (
										<li>
											<ClayButton
												displayType="link"
												onClick={() => {
													Liferay.Util.openModal({
														id: `${portletNamespace}selectSite`,
														onSelect: (
															selectedItem
														) => {
															Liferay.Util.navigate(
																selectedItem.url
															);
														},
														selectEventName: `${portletNamespace}selectSite`,
														title: Liferay.Language.get(
															'select-site-or-asset-library'
														),
														url: sites.viewAllURL,
													});
												}}
												small
											>
												{Liferay.Language.get(
													'view-all'
												)}
											</ClayButton>
										</li>
									)}
								</ul>
							</ClayLayout.Col>
						</ClayLayout.Row>
					</ClayTabs.TabPane>
				))}
			</ClayTabs.Content>
		</>
	);
};

const GlobalMenu = ({panelAppsURL}) => {
	const [appsPanelData, setAppsPanelData] = useState({});
	const [visible, setVisible] = useState(false);

	const {observer, onClose} = useModal({
		onClose: () => setVisible(false),
	});

	const fetchCategoriesPromiseRef = useRef();

	const fetchCategories = () => {
		if (!fetchCategoriesPromiseRef.current) {
			fetchCategoriesPromiseRef.current = fetch(panelAppsURL)
				.then((response) => response.json())
				.then(({items, portletNamespace, sites}) => {
					setAppsPanelData({
						categories: items,
						portletNamespace,
						sites,
					});
				})
				.catch(() => {
					fetchCategoriesPromiseRef.current = null;
				});
		}
	};

	const handleTriggerButtonClick = () => {
		fetchCategories();
		setVisible(true);
	};

	return (
		<>
			{visible && (
				<ClayModal
					className="global-apps-menu-modal"
					observer={observer}
					size="full"
					status="info"
				>
					<ClayModal.Body>
						<AppsPanel
							handleCloseButtonClick={onClose}
							{...appsPanelData}
						/>
					</ClayModal.Body>
				</ClayModal>
			)}

			<ClayButtonWithIcon
				className="dropdown-toggle lfr-portal-tooltip"
				data-qa-id="globalMenu"
				displayType="unstyled"
				onClick={handleTriggerButtonClick}
				onFocus={fetchCategories}
				onMouseOver={fetchCategories}
				small
				symbol="grid"
				title={Liferay.Language.get('global-menu')}
			/>
		</>
	);
};

GlobalMenu.propTypes = {
	panelAppsURL: PropTypes.string,
};

export default GlobalMenu;
