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
import {useIsMounted, useStateSafe} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import React, {useEffect, useRef, useState} from 'react';

import useLoad from '../../hooks/useLoad.es';

import './MultiPanelSidebar.scss';

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
	currentPanelId,
	onChange,
	open,
	panels,
	sidebarPanels,
	variant = 'dark',
}) {
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

	useEffect(() => {
		const productMenu = Liferay.SideNavigation.instance(
			document.querySelector('.product-menu-toggle')
		);

		if (productMenu) {

			// Close product menu whenever sidebarOpen becomes true

			if (open) {
				productMenu.hide();
			}

			// Add listener on product menu to turn sidebarOpen false if opened

			const sideNavigationListener = productMenu.on(
				'openStart.lexicon.sidenav',
				() => onChange({sidebarOpen: false})
			);

			return () => sideNavigationListener.removeListener();
		}
	}, [onChange, open]);

	const handlePanelClick = ({sidebarPanelId}) =>
		onChange({
			sidebarOpen: sidebarPanelId !== currentPanelId || !open,
			sidebarPanelId,
		});

	return (
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

							const active = open && currentPanelId === panelId;
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
										<a className={btnClasses} href={url}>
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
												handlePanelClick(panel)
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
					'multi-panel-sidebar-content-open': open,
				})}
			>
				{hasError ? (
					<div>
						<ClayButton
							block
							displayType="secondary"
							onClick={() => {
								onChange({sidebarOpen: false});
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
										panel.sidebarPanelId !== currentPanelId,
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
