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

import {ClayButtonWithIcon} from '@clayui/button';
import {VerticalBar} from '@clayui/core';
import {navigate} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import NavigationPanel from './NavigationPanel';

const CSS_EXPANDED = 'expanded';
const BLANK = '';

const VerticalNavigationBar = ({items, parentContainerId}) => {
	const parentContainer = document.getElementById(parentContainerId);

	const currentItem = items.find((item) => item.active);

	const [productMenuOpen, setProductMenuOpen] = useState(false);
	const [activePanel, setActivePanel] = useState();

	const productMenu = Liferay.SideNavigation.instance(
		document.querySelector('.product-menu-toggle')
	);

	useEffect(() => {
		if (productMenu) {
			setProductMenuOpen(productMenu.visible());

			const productMenuOpenListener = productMenu.on(
				'openStart.lexicon.sidenav',
				() => {
					setProductMenuOpen(true);
				}
			);

			const productMenuCloseListener = productMenu.on(
				'closedStart.lexicon.sidenav',
				() => {
					setProductMenuOpen(false);
				}
			);

			return () => {
				productMenuOpenListener.removeListener();
				productMenuCloseListener.removeListener();
			};
		}
	}, [productMenu]);

	useEffect(() => {
		setActivePanel(productMenuOpen ? BLANK : currentItem.key);
	}, [productMenuOpen, parentContainer, currentItem]);

	useEffect(() => {
		if (activePanel) {
			parentContainer.classList.add(CSS_EXPANDED);
		}
		else {
			parentContainer.classList.remove(CSS_EXPANDED);
		}
	}, [activePanel, parentContainer]);

	const onActiveChange = () => {
		setActivePanel(activePanel ? BLANK : currentItem.key);

		if (productMenuOpen) {
			productMenu.hide();
		}
	};

	const VerticalBarPanels = {
		article: NavigationPanel,
		suggestion: NavigationPanel,
		template: NavigationPanel,
	};

	return (
		<VerticalBar
			absolute
			active={activePanel}
			onActiveChange={onActiveChange}
			position="left"
		>
			<VerticalBar.Bar displayType="light" items={items}>
				{(item) => (
					<VerticalBar.Item key={item.key}>
						<ClayButtonWithIcon
							data-tooltip-align="right"
							displayType="unstyled"
							onClick={(event) => {
								if (item.key !== currentItem.key) {
									event.preventDefault();

									navigate(item.href);
								}
							}}
							symbol={item.icon}
							title={Liferay.Language.get(item.title)}
						/>
					</VerticalBar.Item>
				)}
			</VerticalBar.Bar>

			<VerticalBar.Content items={items}>
				{(item) => {
					const PanelComponent = VerticalBarPanels[item.key];

					return (
						<VerticalBar.Panel key={item.key}>
							<>
								<div className="sidebar-header">
									<div className="component-title">
										{item.title}
									</div>
								</div>
								<div className="sidebar-body">
									<PanelComponent
										items={item.navigationItems}
									/>
								</div>
							</>
						</VerticalBar.Panel>
					);
				}}
			</VerticalBar.Content>
		</VerticalBar>
	);
};

const itemShape = {
	active: PropTypes.bool,
	href: PropTypes.string.isRequired,
	icon: PropTypes.string.isRequired,
	key: PropTypes.string.isRequired,
	title: PropTypes.string.isRequired,
};

VerticalNavigationBar.propTypes = {
	items: PropTypes.arrayOf(PropTypes.shape(itemShape)),
	parentContainerId: PropTypes.string.isRequired,
};

export default VerticalNavigationBar;
