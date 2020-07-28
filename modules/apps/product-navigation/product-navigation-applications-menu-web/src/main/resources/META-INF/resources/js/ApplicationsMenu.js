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

import ClayLabel from '@clayui/label';
import ClaySticker from '@clayui/sticker';
import ClayTabs from '@clayui/tabs';
import {fetch} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useRef, useState} from 'react';

const Site = ({label, logoURL, url}) => {
	return (
		<li className="c-mt-3">
			<a className="applications-menu-nav-link" href={url}>
				<ClayLayout.ContentRow verticalAlign="center">
					<ClayLayout.ContentCol>
						<ClaySticker size="sm">
							<img alt="" height="20px" src={logoURL} />
						</ClaySticker>
					</ClayLayout.ContentCol>

					<ClayLayout.ContentCol className="applications-menu-shrink c-ml-2">
						<span className="text-truncate">{label}</span>
					</ClayLayout.ContentCol>

					{!url && (
						<ClayLayout.ContentCol className="c-ml-2">
							<ClayLabel displayType="info">
								{Liferay.Language.get('current')}
							</ClayLabel>
						</ClayLayout.ContentCol>
					)}
				</ClayLayout.ContentRow>
			</a>
		</li>
	);
};

const SitesPanel = ({mySites, portletNamespace, recentSites, viewAllURL}) => {
	return (
		<>
			{recentSites?.length > 0 &&
				recentSites.map(({key, label, logoURL, url}) => (
					<Site key={key} label={label} logoURL={logoURL} url={url} />
				))}

			{recentSites?.length > 0 && mySites?.length > 0 && (
				<li className="applications-menu-nav-divider c-mt-3"></li>
			)}

			{mySites?.length > 0 &&
				mySites.map(({key, label, logoURL, url}) => (
					<Site key={key} label={label} logoURL={logoURL} url={url} />
				))}

			{viewAllURL && (
				<li className="c-mt-3">
					<ClayButton
						className="applications-menu-btn btn-unstyled c-mb-0 c-mt-3"
						displayType="link"
						onClick={() => {
							Liferay.Util.openModal({
								id: `${portletNamespace}selectSite`,
								onSelect: (selectedItem) => {
									Liferay.Util.navigate(selectedItem.url);
								},
								selectEventName: `${portletNamespace}selectSite`,
								title: Liferay.Language.get(
									'select-site-or-asset-library'
								),
								url: viewAllURL,
							});
						}}
					>
						{Liferay.Language.get('view-all')}
					</ClayButton>
				</li>
			)}
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
		<div className="applications-menu-wrapper">
			<div className="applications-menu-header">
				<ClayLayout.ContainerFluid>
					<ClayLayout.Row>
						<ClayLayout.Col>
							<ClayLayout.ContentRow verticalAlign="center">
								<ClayLayout.ContentCol expand>
									<ClayTabs modern>
										{categories.map(
											({key, label}, index) => (
												<ClayTabs.Item
													active={activeTab === index}
													id={`${portletNamespace}tab_${index}`}
													key={key}
													onClick={() =>
														setActiveTab(index)
													}
												>
													<span
														className="c-inner"
														tabIndex="-1"
													>
														{label}
													</span>
												</ClayTabs.Item>
											)
										)}
									</ClayTabs>
								</ClayLayout.ContentCol>

								<ClayLayout.ContentCol>
									<ClayButtonWithIcon
										displayType="unstyled"
										onClick={handleCloseButtonClick}
										small
										symbol="times"
										title={Liferay.Language.get('close')}
									/>
								</ClayLayout.ContentCol>
							</ClayLayout.ContentRow>
						</ClayLayout.Col>
					</ClayLayout.Row>
				</ClayLayout.ContainerFluid>
			</div>

			<div className="applications-menu-bg applications-menu-border-top applications-menu-content">
				<ClayLayout.ContainerFluid>
					<ClayLayout.Row>
						<ClayLayout.Col lg="9" md="8">
							<ClayTabs.Content activeIndex={activeTab}>
								{categories.map(({childCategories}, index) => (
									<ClayTabs.TabPane
										aria-labelledby={`${portletNamespace}tab_${index}`}
										key={`tabPane-${index}`}
									>
										<div className="applications-menu-nav-columns c-mt-md-3 c-my-2">
											{childCategories.map(
												({key, label, panelApps}) => (
													<ClayLayout.Col
														key={key}
														md
													>
														<ul className="list-unstyled">
															<li className="c-my-3">
																<h3 className="applications-menu-nav-header">
																	{label}
																</h3>
															</li>

															{panelApps.map(
																({
																	label,
																	portletId,
																	url,
																}) => (
																	<li
																		className="c-mt-2"
																		key={
																			portletId
																		}
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
																			href={
																				url
																			}
																		>
																			<span
																				className="c-inner"
																				tabIndex="-1"
																			>
																				{
																					label
																				}
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
									</ClayTabs.TabPane>
								))}
							</ClayTabs.Content>
						</ClayLayout.Col>

						<ClayLayout.Col
							className="c-pl-md-2 c-px-0"
							lg="3"
							md="4"
						>
							<div className="applications-menu-sites c-p-3 c-px-md-4">
								<h2 className="applications-menu-sites-label c-mt-2 c-mt-md-0">
									{Liferay.Language.get('sites')}
								</h2>

								<ul className="c-mb-2 list-unstyled">
									{sites && (
										<SitesPanel
											mySites={sites.mySites}
											portletNamespace={portletNamespace}
											recentSites={sites.recentSites}
											viewAllURL={sites.viewAllURL}
										/>
									)}
								</ul>
							</div>
						</ClayLayout.Col>
					</ClayLayout.Row>
				</ClayLayout.ContainerFluid>
			</div>

			<div className="applications-menu-bg applications-menu-footer">
				<ClayLayout.ContainerFluid>
					<ClayLayout.Row>
						<ClayLayout.Col lg="9" md="8">
							<ClayLayout.ContentRow
								className="applications-menu-border-top c-py-3"
								verticalAlign="center"
							>
								<ClayLayout.ContentCol expand>
									<ClayLayout.ContentRow verticalAlign="center">
										<ClayLayout.ContentCol>
											<ClaySticker>
												<img
													alt=""
													height="32px"
													src={logoURL}
												/>
											</ClaySticker>
										</ClayLayout.ContentCol>

										<ClayLayout.ContentCol className="c-ml-2">
											<h1 className="applications-menu-company c-mb-0">
												{companyName}
											</h1>
										</ClayLayout.ContentCol>
									</ClayLayout.ContentRow>
								</ClayLayout.ContentCol>

								<ClayLayout.ContentCol expand>
									<p className="applications-menu-powered c-mb-0">
										Powered by
									</p>

									<p className="applications-menu-copyright c-mb-0 c-mt-n1">
										Liferay DXP
									</p>
								</ClayLayout.ContentCol>
							</ClayLayout.ContentRow>
						</ClayLayout.Col>

						<ClayLayout.Col
							className="c-pl-md-2 c-px-0 d-md-block d-none"
							lg="3"
							md="4"
						>
							<div className="applications-menu-sites"></div>
						</ClayLayout.Col>
					</ClayLayout.Row>
				</ClayLayout.ContainerFluid>
			</div>
		</div>
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
