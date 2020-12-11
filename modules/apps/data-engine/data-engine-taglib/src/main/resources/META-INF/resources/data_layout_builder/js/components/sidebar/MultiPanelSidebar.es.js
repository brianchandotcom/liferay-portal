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
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {ClayTooltipProvider} from '@clayui/tooltip';
import classNames from 'classnames';
import {useIsMounted, useStateSafe} from 'frontend-js-react-web';
import React, {useRef, useState} from 'react';

import useLoad from '../../hooks/useLoad.es';

const {useEffect} = React;

const CLASSNAME_INDICATORS = [
	'.change-tracking-indicator',
	'.staging-indicator',
];

/**
 * Failure to preload is a non-critical failure, so we'll use this to swallow
 * rejected promises silently.
 */
const swallow = [(value) => value, (_error) => undefined];

export default function MultiPanelSidebar({
	createPlugin,
	panels,
	sidebarPanels,
	variant = 'dark',
}) {
	const [{sidebarOpen, sidebarPanelId}, setSidebarState] = useState({
		sidebarOpen: true,
		sidebarPanelId: 'fields',
	});
	const [hasError, setHasError] = useStateSafe(false);
	const isMounted = useIsMounted();
	const load = useLoad();
	const sidebarPanelsRef = useRef(sidebarPanels);

	const [panelComponents, setPanelComponents] = useState([]);

	useEffect(() => {
		const panelPromises = Object.values(sidebarPanelsRef.current).map(
			(sidebarPanel) =>
				load(sidebarPanel.sidebarPanelId, sidebarPanel.pluginEntryPoint)
					.then((Plugin) => {
						const instance = new Plugin(
							createPlugin({
								panel: sidebarPanel,
								sidebarOpen: true,
								sidebarPanelId: sidebarPanel.sidebarPanelId,
							}),
							sidebarPanel
						);

						return {
							Component: () => instance.renderSidebar(),
							sidebarPanelId: sidebarPanel.sidebarPanelId,
						};
					})
					.catch((error) => console.error(error))
		);

		setPanelComponents([]);

		Promise.all(panelPromises).then((result) => {
			if (isMounted()) {
				setPanelComponents(result);
			}
		});
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [isMounted, load]);

	const changeAlertClassName = (styleName) => {
		const formBuilderMessage = document.querySelector(
			'.data-engine-form-builder-messages'
		);
		const className = formBuilderMessage.className;

		formBuilderMessage.className = className.replace(
			formBuilderMessage.className,
			styleName
		);
	};

	useEffect(() => {
		const sideNavigation = Liferay.SideNavigation.instance(
			document.querySelector('.product-menu-toggle')
		);

		if (sideNavigation) {
			const onCloseSidebar = () => {
				if (sidebarOpen) {
					changeAlertClassName('data-engine-form-builder-messages');
				}

				setSidebarState({
					sidebarOpen: false,
					sidebarPanelId: null,
				});
			};

			const sideNavigationListener = sideNavigation.on(
				'openStart.lexicon.sidenav',
				onCloseSidebar
			);

			return () => {
				sideNavigationListener.removeListener();
			};
		}
	}, [sidebarOpen]);

	const handleClick = (panel) => {
		const open =
			panel.sidebarPanelId === sidebarPanelId ? !sidebarOpen : true;
		const productMenuToggle = document.querySelector(
			'.product-menu-toggle'
		);

		if (productMenuToggle && !sidebarOpen) {
			Liferay.SideNavigation.hide(productMenuToggle);
		}

		if (open) {
			changeAlertClassName(
				'data-engine-form-builder-messages data-engine-form-builder-messages--collapsed'
			);
		}
		else {
			changeAlertClassName('data-engine-form-builder-messages');
		}

		setSidebarState({
			sidebarOpen: open,
			sidebarPanelId: panel.sidebarPanelId,
		});
	};

	return (
		<ClayTooltipProvider>
			<div
				className={classNames(
					'multi-panel-sidebar',
					`multi-panel-sidebar-${variant}`,
					{
						'menu-indicator-enabled': document.querySelector(
							CLASSNAME_INDICATORS.join(',')
						),
					}
				)}
			>
				<nav
					className={classNames(
						'multi-panel-sidebar-buttons',
						'tbar',
						'tbar-stacked',
						variant === 'dark'
							? `tbar-${variant}-d1`
							: `tbar-${variant}`
					)}
				>
					<ul className="tbar-nav">
						{panels.reduce((elements, group, groupIndex) => {
							const buttons = group.map((panelId) => {
								const panel = sidebarPanels[panelId];

								const active =
									sidebarOpen && sidebarPanelId === panelId;
								const {
									icon,
									isLink,
									label,
									pluginEntryPoint,
									url,
								} = panel;

								const prefetch = () =>
									load(
										panel.sidebarPanelId,
										pluginEntryPoint
									).then(...swallow);

								const btnClasses = classNames(
									'tbar-btn tbar-btn-monospaced',
									{active}
								);

								return (
									<li
										className={classNames(
											'tbar-item',
											`tbar-item--${panel.sidebarPanelId}`
										)}
										key={panel.sidebarPanelId}
									>
										{isLink ? (
											<a
												className={btnClasses}
												href={url}
											>
												<ClayIcon symbol={icon} />
											</a>
										) : (
											<ClayButtonWithIcon
												aria-pressed={active}
												className={btnClasses}
												data-tooltip-align="left"
												displayType="unstyled"
												id={panel.sidebarPanelId}
												onClick={() =>
													handleClick(panel)
												}
												onFocus={prefetch}
												onMouseEnter={prefetch}
												symbol={icon}
												title={label}
											/>
										)}
									</li>
								);
							});

							if (groupIndex === panels.length - 1) {
								return elements.concat(buttons);
							}
							else {
								return elements.concat([
									...buttons,
									<hr key={`separator-${groupIndex}`} />,
								]);
							}
						}, [])}
					</ul>
				</nav>
				<div
					className={classNames('multi-panel-sidebar-content', {
						'multi-panel-sidebar-content-open': sidebarOpen,
					})}
				>
					{hasError ? (
						<div>
							<ClayButton
								block
								displayType="secondary"
								onClick={() => {
									setSidebarState({
										sidebarOpen: false,
										sidebarPanelId:
											panels[0] && panels[0][0],
									});
									setHasError(false);
								}}
								small
							>
								{Liferay.Language.get('refresh')}
							</ClayButton>
						</div>
					) : (
						<ErrorBoundary
							handleError={() => {
								setHasError(true);
							}}
						>
							{panelComponents.length === 0 && (
								<ClayLoadingIndicator />
							)}

							{panelComponents.map((panel) => (
								<div
									className={classNames({
										'd-none':
											panel.sidebarPanelId !==
											sidebarPanelId,
									})}
									key={panel.sidebarPanelId}
								>
									<panel.Component />
								</div>
							))}
						</ErrorBoundary>
					)}
				</div>
			</div>
		</ClayTooltipProvider>
	);
}

class ErrorBoundary extends React.Component {
	static getDerivedStateFromError(_error) {
		return {hasError: true};
	}

	constructor(props) {
		super(props);

		this.state = {hasError: false};
	}

	componentDidCatch(error) {
		if (this.props.handleError) {
			this.props.handleError(error);
		}
	}

	render() {
		if (this.state.hasError) {
			return null;
		}
		else {
			return this.props.children;
		}
	}
}
