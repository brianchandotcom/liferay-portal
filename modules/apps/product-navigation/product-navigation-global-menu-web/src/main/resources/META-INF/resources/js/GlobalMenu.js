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
import ClayModal, {useModal} from '@clayui/modal';
import ClayTabs from '@clayui/tabs';
import {fetch} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useRef, useState} from 'react';

const Column = ({panelApps}) => {
	return (
		<ul className="list-unstyled mt-4" role="list">
			{panelApps.map(({label, portletId, url}) => (
				<li className="mb-2" key={portletId}>
					<a className="text-secondary" href={url}>
						{label}
					</a>
				</li>
			))}
		</ul>
	);
};

const Content = ({
	childCategories,
	portletNamespace,
	recentSites,
	viewAllURL,
}) => {
	return (
		<div className="row">
			{childCategories.map(({key, label, panelApps}) => (
				<div className="col p-4" key={key}>
					<h5 className="text-secondary text-uppercase">{label}</h5>

					<Column panelApps={panelApps} />
				</div>
			))}

			<div className="bg-lighter col p-4 rounded-sm" key="sites">
				<h5 className="mb-5 text-secondary text-uppercase">
					{Liferay.Language.get('sites')}
				</h5>

				<h6 className="text-secondary text-uppercase">
					{Liferay.Language.get('recently-visited')}
				</h6>

				<ul className="list-unstyled mt-3" role="list">
					{recentSites.map(({key, label, logoURL, url}) => (
						<li className="mb-2" key={key}>
							<div className="autofit-row autofit-row-center">
								<div className="autofit-col mr-2">
									<div className="sticker sticker-secondary">
										<img
											className="sticker-img"
											src={logoURL}
										/>
									</div>
								</div>

								<div className="autofit-col autofit-col-expand">
									<a className="text-secondary" href={url}>
										{label}
									</a>
								</div>
							</div>
						</li>
					))}
				</ul>

				<ClayButton
					displayType="link"
					onClick={() => {
						Liferay.Util.selectEntity(
							{
								dialog: {
									constrain: true,
									destroyOnHide: true,
									modal: true,
								},
								eventName: `${portletNamespace}selectSite`,
								id: `${portletNamespace}selectSite`,
								title: Liferay.Language.get(
									'select-site-or-asset-library'
								),
								uri: viewAllURL,
							},
							(event) => {
								location.href = event.url;
							}
						);
					}}
				>
					{Liferay.Language.get('view-all')}
				</ClayButton>
			</div>
		</div>
	);
};

function GlobalMenu({panelAppsURL}) {
	const [visible, setVisible] = useState(false);
	const {observer} = useModal({
		onClose: () => setVisible(false),
	});

	const [activeTab, setActiveTab] = useState(0);
	const [context, setContext] = useState({});

	const preloadPromise = useRef();

	const {items = [], portletNamespace, recentSites, viewAllURL} = context;

	function preloadItems() {
		if (!preloadPromise.current) {
			preloadPromise.current = fetch(panelAppsURL)
				.then((response) => response.json())
				.then(({items, portletNamespace, recentSites, viewAllURL}) => {
					setContext({
						items,
						portletNamespace,
						recentSites,
						viewAllURL,
					});
				});
		}
	}

	return (
		<>
			{visible && (
				<ClayModal observer={observer} size="full-screen" status="info">
					<ClayModal.Body>
						<ClayTabs modern>
							{items.map(({key, label}, index) => (
								<ClayTabs.Item
									active={activeTab === index}
									key={key}
									onClick={() => setActiveTab(index)}
								>
									{label}
								</ClayTabs.Item>
							))}
						</ClayTabs>

						<div className="mt-4">
							<ClayTabs.Content activeIndex={activeTab}>
								{items.map(({childCategories}, index) => (
									<ClayTabs.TabPane
										aria-labelledby={`tab-${index}`}
										key={`tabPane-${index}`}
									>
										<Content
											childCategories={childCategories}
											portletNamespace={portletNamespace}
											recentSites={recentSites}
											viewAllURL={viewAllURL}
										/>
									</ClayTabs.TabPane>
								))}
							</ClayTabs.Content>
						</div>
					</ClayModal.Body>
				</ClayModal>
			)}

			<ClayButtonWithIcon
				className={'lfr-portal-tooltip'}
				displayType="unstyled"
				onClick={() => {
					preloadItems();

					setVisible(true);
				}}
				onFocus={preloadItems}
				symbol="grid"
				title={Liferay.Language.get('global-menu')}
			/>
		</>
	);
}

GlobalMenu.propTypes = {
	panelAppsURL: PropTypes.string,
};

export default GlobalMenu;
