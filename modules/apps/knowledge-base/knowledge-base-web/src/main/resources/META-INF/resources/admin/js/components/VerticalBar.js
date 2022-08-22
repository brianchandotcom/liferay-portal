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
import SuggestionsPanel from './SuggestionsPanel';
import TemplatesPanel from './TemplatesPanel';

const CSS_EXPANDED = 'expanded';

const VerticalNavigationBar = ({
	items,
	parentContainerId,
	productMenuOpen: initialProductMenuOpen,
}) => {
	const parentContainer = document.getElementById(parentContainerId);

	const [productMenuOpen, setProductMenuOpen] = useState(
		initialProductMenuOpen
	);
	const [verticalBarOpen, setVerticalBarOpen] = useState(
		!initialProductMenuOpen
	);
	const [activePanel, setActivePanel] = useState(
		items.find(({active}) => active)?.key
	);

	const productMenu = Liferay.SideNavigation.instance(
		document.querySelector('.product-menu-toggle')
	);

	useEffect(() => {
		if (productMenu) {
			const productMenuOpenListener = productMenu.on(
				'openStart.lexicon.sidenav',
				() => {
					setProductMenuOpen(true);
					setVerticalBarOpen(false);
				}
			);

			const productMenuCloseListener = productMenu.on(
				'closedStart.lexicon.sidenav',
				() => {
					setProductMenuOpen(false);
					setVerticalBarOpen(true);
				}
			);

			return () => {
				productMenuOpenListener.removeListener();
				productMenuCloseListener.removeListener();
			};
		}
	}, [productMenu]);

	useEffect(() => {
		parentContainer.classList.toggle(
			CSS_EXPANDED,
			Boolean(verticalBarOpen && activePanel)
		);
	}, [activePanel, parentContainer, verticalBarOpen]);

	const onActiveChange = (currentActivePanelKey) => {
		setVerticalBarOpen(
			(currenVerticalBarOpen) =>
				Boolean(currentActivePanelKey) &&
				!(
					currentActivePanelKey === activePanel &&
					currenVerticalBarOpen
				)
		);

		if (currentActivePanelKey) {
			if (currentActivePanelKey !== activePanel) {
				setActivePanel(currentActivePanelKey);

				const href = items.find(
					({key}) => key === currentActivePanelKey
				)?.href;

				if (productMenuOpen) {
					const productMenuOpenListener = productMenu.on(
						'closed.lexicon.sidenav',
						() => {
							productMenuOpenListener.removeListener();
							navigate(href);
						}
					);
				}
				else {
					navigate(href);
				}
			}

			if (productMenuOpen) {
				productMenu.hide();
			}
		}
	};

	const VerticalBarPanels = {
		article: NavigationPanel,
		suggestion: SuggestionsPanel,
		template: TemplatesPanel,
	};

	return (
		<VerticalBar
			absolute
			active={verticalBarOpen && activePanel ? activePanel : null}
			onActiveChange={onActiveChange}
			position="left"
		>
			<VerticalBar.Bar displayType="light" items={items}>
				{(item) => (
					<VerticalBar.Item key={item.key}>
						<ClayButtonWithIcon
							data-tooltip-align="right"
							displayType="unstyled"
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
							<div className="sidebar-header">
								<div className="component-title">
									{item.title}
								</div>
							</div>

							<div className="sidebar-body">
								<PanelComponent
									items={item.navigationItems}
									selectedItemId={item.selectedItemId}
								/>
							</div>
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
	productMenuOpen: PropTypes.bool,
};

export default VerticalNavigationBar;
