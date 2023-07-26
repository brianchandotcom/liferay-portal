/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import ClayModal, {useModal} from '@clayui/modal';
import ClaySticker from '@clayui/sticker';
import ClayTabs from '@clayui/tabs';
import {ReactDOMServer, useEventListener} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import {useId} from 'frontend-js-components-web';
import {fetch, navigate, openSelectionModal} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useMemo, useRef, useState} from 'react';

import '../css/ApplicationsMenu.scss';

const getOpenMenuTooltip = (keyLabel) => (
	<>
		<div>{Liferay.Language.get('open-applications-menu')}</div>
		<kbd className="c-kbd c-kbd-dark mt-1">
			<kbd className="c-kbd">Ctrl</kbd>

			<span className="c-kbd-separator">+</span>

			<kbd className="c-kbd">{keyLabel}</kbd>

			<span className="c-kbd-separator">+</span>

			<kbd className="c-kbd">A</kbd>
		</kbd>
	</>
);

const SitesPanel = ({portletNamespace, sites, virtualInstance}) => {
	return (
		<div className="applications-menu-sites c-p-3 c-px-md-4">
			<h2 className="applications-menu-sites-label c-mt-2 c-mt-md-0 mb-0 text-uppercase">
				{Liferay.Language.get('sites')}
			</h2>

			<div className="c-mt-2">
				{virtualInstance && (
					<a
						className="applications-menu-nav-link applications-menu-virtual-instance"
						href={virtualInstance.url}
					>
						<ClayLayout.ContentRow verticalAlign="center">
							<ClayLayout.ContentCol>
								<ClaySticker>
									<img
										alt=""
										height="32px"
										src={virtualInstance.logoURL}
									/>
								</ClaySticker>
							</ClayLayout.ContentCol>

							<ClayLayout.ContentCol className="applications-menu-shrink c-ml-2">
								<span className="text-truncate">
									{virtualInstance.label}
								</span>
							</ClayLayout.ContentCol>
						</ClayLayout.ContentRow>
					</a>
				)}
			</div>

			<div className="applications-menu-nav-divider c-my-3"></div>

			<div className="applications-menu-sites c-my-2">
				<ul
					aria-label={Liferay.Language.get('sites')}
					className="list-unstyled"
				>
					{sites && (
						<Sites
							mySites={sites.mySites}
							portletNamespace={portletNamespace}
							recentSites={sites.recentSites}
							viewAllURL={sites.viewAllURL}
						/>
					)}
				</ul>
			</div>
		</div>
	);
};

const Site = ({current, label, logoURL, url}) => {
	return (
		<li className="c-mt-3">
			<a className="applications-menu-nav-link" href={url}>
				<ClayLayout.ContentRow verticalAlign="center">
					<ClayLayout.ContentCol>
						<ClaySticker size="sm">
							{logoURL ? (
								<img alt="" height="20px" src={logoURL} />
							) : (
								<ClayIcon symbol="sites" />
							)}
						</ClaySticker>
					</ClayLayout.ContentCol>

					<ClayLayout.ContentCol className="applications-menu-shrink c-ml-2">
						<span className="text-truncate">{label}</span>
					</ClayLayout.ContentCol>

					{current && (
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

const Sites = ({mySites, portletNamespace, recentSites, viewAllURL}) => {
	return (
		<>
			{recentSites?.length > 0 &&
				recentSites.map(({current, key, label, logoURL, url}) => (
					<Site
						current={current}
						key={key}
						label={label}
						logoURL={logoURL}
						url={url}
					/>
				))}

			{recentSites?.length > 0 && mySites?.length > 0 && (
				<li
					className="applications-menu-nav-divider c-mt-3"
					role="presentation"
				></li>
			)}

			{mySites?.length > 0 &&
				mySites.map(({current, key, label, logoURL, url}) => (
					<Site
						current={current}
						key={key}
						label={label}
						logoURL={logoURL}
						url={url}
					/>
				))}

			{viewAllURL && (
				<li className="c-mt-3">
					<ClayButton
						className="applications-menu-btn btn-unstyled c-mb-0 c-mt-3"
						displayType="link"
						onClick={() => {
							openSelectionModal({
								id: `${portletNamespace}selectSite`,
								onSelect: (selectedItem) => {
									navigate(selectedItem.url);
								},
								selectEventName: `${portletNamespace}selectSite`,
								title: Liferay.Language.get('select-site'),
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
	handleCloseButtonClick = () => {},
	liferayLogoURL,
	liferayName,
	portletNamespace,
	selectedPortletId,
	sites,
	virtualInstance,
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
									<ClayTabs>
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
										aria-label={Liferay.Language.get(
											'close'
										)}
										displayType="unstyled"
										onClick={handleCloseButtonClick}
										size="sm"
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
													<NavigationSection
														id={`nav_${key}`}
														key={key}
														label={label}
														panelApps={panelApps}
														selectedPortletId={
															selectedPortletId
														}
													/>
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
							<SitesPanel
								portletNamespace={portletNamespace}
								sites={sites}
								virtualInstance={virtualInstance}
							/>
						</ClayLayout.Col>
					</ClayLayout.Row>
				</ClayLayout.ContainerFluid>
			</div>

			<div className="applications-menu-bg applications-menu-footer">
				<ClayLayout.ContainerFluid>
					<ClayLayout.Row>
						<ClayLayout.Col lg="9" md="8">
							<ClayLayout.ContentRow
								className="applications-menu-border-top bg-white c-py-3"
								verticalAlign="center"
							>
								<ClayLayout.ContentCol expand>
									<ClayLayout.ContentRow verticalAlign="center">
										<ClayLayout.ContentCol>
											<ClaySticker>
												<img
													alt=""
													height="32px"
													src={liferayLogoURL}
												/>
											</ClaySticker>
										</ClayLayout.ContentCol>

										<ClayLayout.ContentCol className="c-ml-2">
											<div className="applications-menu-company c-mb-0">
												{liferayName}
											</div>
										</ClayLayout.ContentCol>
									</ClayLayout.ContentRow>
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

const NavigationSection = ({id, label, panelApps, selectedPortletId}) => {
	return (
		<ClayLayout.Col md>
			<nav aria-labelledby={id}>
				<h2 className="applications-menu-nav-header c-my-3" id={id}>
					{label}
				</h2>

				<ul className="list-unstyled">
					{panelApps.map(({label, portletId, url}) => (
						<li className="c-mt-2" key={portletId}>
							<a
								className={classNames(
									'component-link applications-menu-nav-link',
									{
										active: portletId === selectedPortletId,
									}
								)}
								href={url}
							>
								<span className="c-inner" tabIndex="-1">
									{label}
								</span>
							</a>
						</li>
					))}
				</ul>
			</nav>
		</ClayLayout.Col>
	);
};

const ApplicationsMenu = ({
	liferayLogoURL,
	liferayName,
	panelAppsURL,
	selectedPortletId,
	virtualInstance,
}) => {
	const [appsPanelData, setAppsPanelData] = useState({});
	const buttonRef = useRef();
	const buttonTitleId = useId();
	const [visible, setVisible] = useState(false);

	const {observer, onClose} = useModal({
		onClose: () => {
			setVisible(false);
			buttonRef.current.focus();
		},
	});

	const buttonTitle = useMemo(() => {
		const keyLabel = Liferay.Browser.isMac() ? '⌥' : 'Alt';

		return getOpenMenuTooltip(keyLabel);
	}, []);

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

	useEventListener(
		'keydown',
		(event) => {
			const AKey = Liferay.Browser.isMac() ? 'å' : 'a';

			if (
				event.ctrlKey &&
				event.altKey &&
				event.key.toLowerCase() === AKey
			) {
				event.preventDefault();

				if (visible) {
					onClose();
				}
				else {
					handleTriggerButtonClick();
				}
			}
		},
		true,
		window
	);

	useEffect(() => {
		const closeEventBusHandler = Liferay.on(
			'closeApplicationsMenu',
			onClose
		);

		return () => {
			closeEventBusHandler.detach();
		};
	}, [onClose]);

	return (
		<>
			{visible && (
				<ClayModal
					className="applications-menu-modal"
					containerProps={{className: 'cadmin'}}
					observer={observer}
					status="info"
				>
					<ClayModal.Header className="sr-only" withTitle={false}>
						<ClayModal.Title>
							{Liferay.Language.get('applications-menu')}
						</ClayModal.Title>
					</ClayModal.Header>

					<ClayModal.Body className="p-0">
						<AppsPanel
							handleCloseButtonClick={onClose}
							liferayLogoURL={liferayLogoURL}
							liferayName={liferayName}
							virtualInstance={virtualInstance}
							{...appsPanelData}
						/>
					</ClayModal.Body>
				</ClayModal>
			)}

			<ClayButtonWithIcon
				aria-haspopup="dialog"
				aria-labelledby={buttonTitleId}
				className="dropdown-toggle lfr-portal-tooltip"
				data-qa-id="applicationsMenu"
				data-title={ReactDOMServer.renderToString(buttonTitle)}
				data-title-set-as-html
				data-tooltip-align="bottom-left"
				displayType="unstyled"
				onClick={handleTriggerButtonClick}
				onFocus={fetchCategories}
				onMouseOver={fetchCategories}
				ref={buttonRef}
				size="sm"
				symbol="grid"
			/>

			<div className="sr-only" id={buttonTitleId}>
				{buttonTitle}
			</div>
		</>
	);
};

ApplicationsMenu.propTypes = {
	liferayLogoURL: PropTypes.string,
	liferayName: PropTypes.string,
	panelAppsURL: PropTypes.string,
	selectedPortletId: PropTypes.string,
	virtualInstance: PropTypes.object,
};

export default ApplicationsMenu;
