/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {VerticalBar} from '@clayui/core';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {navigate} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {Suspense, useEffect, useState} from 'react';

const NavigationPanel = React.lazy(() => import('./NavigationPanel'));
const SuggestionsPanel = React.lazy(() => import('./SuggestionsPanel'));
const TemplatesPanel = React.lazy(() => import('./TemplatesPanel'));

const CSS_EXPANDED = 'expanded';

const DELAY_ANIMATION = 300;

const SUGGESTION_KEY = 'suggestion';

const VerticalNavigationBar = ({
	items,
	moveKBObjectURL,
	parentContainerId,
	portletNamespace,
	productMenuOpen: initialProductMenuOpen,
}) => {
	const parentContainer = document.getElementById(parentContainerId);

	const [productMenuOpen, setProductMenuOpen] = useState(
		initialProductMenuOpen
	);

	const [activePanel, setActivePanel] = useState(
		items.find(({active}) => active)?.key
	);

	const [verticalBarOpen, setVerticalBarOpen] = useState(
		!initialProductMenuOpen && activePanel !== SUGGESTION_KEY
	);

	const productMenu = Liferay.SideNavigation.instance(
		document.querySelector('.product-menu-toggle')
	);

	useEffect(() => {
		const onProductMenuChange = ({open}) => {
			setProductMenuOpen(open);

			if (open) {
				setVerticalBarOpen(false);
			}
			else {
				if (activePanel !== SUGGESTION_KEY) {
					setTimeout(() => {
						setVerticalBarOpen(true);
					}, DELAY_ANIMATION);
				}
			}
		};

		const closedProductMenuListener = productMenu?.on(
			'closed.lexicon.sidenav',
			() => onProductMenuChange({open: false})
		);

		const openProductMenuListener = productMenu?.on(
			'openStart.lexicon.sidenav',
			() => onProductMenuChange({open: true})
		);

		if (initialProductMenuOpen) {
			setTimeout(() => {
				productMenu.hide();
			}, DELAY_ANIMATION);
		}

		return () => {
			closedProductMenuListener?.removeListener();
			openProductMenuListener?.removeListener();

			productMenu?.destroy();
		};
	}, [activePanel, initialProductMenuOpen, productMenu]);

	useEffect(() => {
		parentContainer.classList.toggle(
			CSS_EXPANDED,
			Boolean(
				activePanel !== SUGGESTION_KEY && verticalBarOpen && activePanel
			)
		);

		if (activePanel === SUGGESTION_KEY) {
			parentContainer.classList.add('not-expandable');
		}
	}, [activePanel, parentContainer, verticalBarOpen]);

	const onActiveChange = (currentActivePanelKey) => {
		if (currentActivePanelKey === SUGGESTION_KEY) {
			setVerticalBarOpen(false);
		}
		else {
			setVerticalBarOpen(
				(currenVerticalBarOpen) =>
					Boolean(currentActivePanelKey) &&
					!(
						currentActivePanelKey === activePanel &&
						currenVerticalBarOpen
					)
			);
		}

		if (currentActivePanelKey) {
			if (currentActivePanelKey !== activePanel) {
				setActivePanel(currentActivePanelKey);

				const href = items.find(
					({key}) => key === currentActivePanelKey
				)?.href;

				if (
					productMenuOpen &&
					currentActivePanelKey !== SUGGESTION_KEY
				) {
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

			if (productMenuOpen && currentActivePanelKey !== SUGGESTION_KEY) {
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
							aria-label={Liferay.Language.get(item.title)}
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
								<Suspense fallback={<ClayLoadingIndicator />}>
									<PanelComponent
										items={item.navigationItems}
										moveKBObjectURL={moveKBObjectURL}
										portletNamespace={portletNamespace}
										selectedItemId={item.selectedItemId}
									/>
								</Suspense>
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
	moveKBObjectURL: PropTypes.string,
	parentContainerId: PropTypes.string.isRequired,
	portletNamespace: PropTypes.string.isRequired,
	productMenuOpen: PropTypes.bool,
};

export default VerticalNavigationBar;
