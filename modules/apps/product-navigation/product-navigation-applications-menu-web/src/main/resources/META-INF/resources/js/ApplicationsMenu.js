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
import classNames from 'classnames';

import '../css/ApplicationsMenu.scss';

import ClaySticker from '@clayui/sticker';
import ClayTabs from '@clayui/tabs';
import {fetch} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useRef, useState} from 'react';

const Sites = ({label, sites}) => {
	return (
		<>
			<li className="applications-menu-nav-subheader">{label}</li>

			{sites.map(({key, label, logoURL, url}) => (
				<li className="applications-menu-nav-item" key={key}>
					<a className="applications-menu-nav-link" href={url}>
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
	companyName,
	handleCloseButtonClick = () => {},
	logoURL,
	portletNamespace,
	selectedPortletId,
	sites,
}) => {
	let index = categories.findIndex((category) =>
		category.childCategories.some((childCategory) =>
			childCategory.panelApps.some(
				(panelApp) => panelApp.portletId === selectedPortletId
			)
		)
	);

	if (index === -1) {
		index = 0;
	}

	const [activeTab, setActiveTab] = useState(index);

	return (
		<>
			<div className="border-bottom applications-menu-header">
				<ClayLayout.ContainerFluid>
					<ClayLayout.ContentRow verticalAlign="center">
						<ClayLayout.ContentCol>
							<ClaySticker>
								<img alt="" height="32px" src={logoURL} />
							</ClaySticker>
						</ClayLayout.ContentCol>
						<ClayLayout.ContentCol className="c-mr-3 c-pl-1 c-pr-3 control-menu-level-1-heading">
							{companyName}
						</ClayLayout.ContentCol>
						<ClayLayout.ContentCol
							className="applications-menu-tabs"
							expand
						>
							<ClayTabs modern>
								{categories.map(({key, label}, index) => (
									<ClayTabs.Item
										active={activeTab === index}
										id={`${portletNamespace}tab_${index}`}
										key={key}
										onClick={() => setActiveTab(index)}
									>
										<span className="c-inner" tabIndex="-1">
											{label}
										</span>
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
			</div>

			<ClayTabs.Content activeIndex={activeTab}>
				{categories.map(({childCategories}, index) => (
					<ClayTabs.TabPane
						aria-labelledby={`${portletNamespace}tab_${index}`}
						key={`tabPane-${index}`}
					>
						<ClayLayout.Row className="applications-menu-nav">
							<ClayLayout.Col className="c-p-0" md>
								<div className="applications-menu-nav-columns">
									{childCategories.map(
										({key, label, panelApps}) => (
											<ClayLayout.Col key={key} md>
												<ul className="list-unstyled">
													<li className="applications-menu-nav-header">
														{label}
													</li>

													{panelApps.map(
														({
															label,
															portletId,
															url,
														}) => (
															<li
																className="applications-menu-nav-item"
																key={portletId}
															>
																<a
																	className={classNames(
																		'component-link applications-menu-nav-link',
																		{
																			active:
																				portletId ===
																				selectedPortletId,
																		}
																	)}
																	href={url}
																>
																	<span
																		className="c-inner"
																		tabIndex="-1"
																	>
																		{label}
																	</span>
																</a>
															</li>
														)
													)}
												</ul>
											</ClayLayout.Col>
										)
									)}
								</div>
							</ClayLayout.Col>

							<div className="applications-menu-sites">
								<ul className="bg-light list-unstyled rounded">
									<li className="applications-menu-nav-header">
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
												className="c-pl-0"
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
							</div>
						</ClayLayout.Row>
					</ClayTabs.TabPane>
				))}
			</ClayTabs.Content>
		</>
	);
};

const ApplicationsMenu = ({
	companyName,
	logoURL,
	panelAppsURL,
	selectedPortletId,
}) => {
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
						selectedPortletId,
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
					className="applications-menu-modal"
					observer={observer}
					size="full"
					status="info"
				>
					<ClayModal.Body>
						<AppsPanel
							companyName={companyName}
							handleCloseButtonClick={onClose}
							logoURL={logoURL}
							{...appsPanelData}
						/>
					</ClayModal.Body>
				</ClayModal>
			)}

			<ClayButtonWithIcon
				className="dropdown-toggle lfr-portal-tooltip"
				data-qa-id="applicationsMenu"
				displayType="unstyled"
				onClick={handleTriggerButtonClick}
				onFocus={fetchCategories}
				onMouseOver={fetchCategories}
				small
				symbol="grid"
				title={Liferay.Language.get('applications-menu')}
			/>
		</>
	);
};

ApplicationsMenu.propTypes = {
	companyName: PropTypes.string,
	logoURL: PropTypes.string,
	panelAppsURL: PropTypes.string,
	selectedPortletId: PropTypes.string,
};

export default ApplicationsMenu;
