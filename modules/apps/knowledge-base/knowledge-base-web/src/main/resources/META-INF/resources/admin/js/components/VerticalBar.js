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
import React, {useEffect, useState} from 'react';

import NavigationPanel from './NavigationPanel';

const CSS_EXPANDED = 'expanded';

const VerticalNavigationBar = ({items, parentContainerId}) => {
	const parentContainer = document.getElementById(parentContainerId);

	const currentItem = items.find((item) => item.active);

	const [productMenuOpen, setProductMenuOpen] = useState(false);

	useEffect(() => {
		const productMenu = Liferay.SideNavigation.instance(
			document.querySelector('.product-menu-toggle')
		);

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
	}, []);

	useEffect(() => {
		if (productMenuOpen) {
			parentContainer.classList.remove(CSS_EXPANDED);
		}
		else {
			parentContainer.classList.add(CSS_EXPANDED);
		}
	}, [productMenuOpen, parentContainer]);

	const onIconClick = (event, item) => {
		if (item.key !== currentItem.key) {
			event.preventDefault();

			navigate(item.href);
		}
		else {
			parentContainer.classList.toggle(CSS_EXPANDED);
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
			active={productMenuOpen ? undefined : currentItem.key}
			position="left"
		>
			<VerticalBar.Bar displayType="light" items={items}>
				{(item) => (
					<VerticalBar.Item key={item.key}>
						<ClayButtonWithIcon
							data-tooltip-align="right"
							displayType="unstyled"
							onClick={(event) => {
								onIconClick(event, item);
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

export default VerticalNavigationBar;
