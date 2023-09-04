/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {useIsMounted, useStateSafe} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import {sub} from 'frontend-js-web';
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
	const tabListRef = useRef();

	const [activePanel, setActivePanel] = useState('fields');

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

	const getMessage = (label) => {
		return Liferay.Language.get('panel') + label;
	};

	const handlePanelClick = ({sidebarPanelId}) => {
		const builder = document.querySelector('.ddm-form-builder');
		const sidebar = document.querySelector('.multi-panel-sidebar-content');
		const closeButtonPressed =
			builder.classList.contains('ddm-form-builder--sidebar-open') !==
			open;

		if (closeButtonPressed) {
			builder.classList.toggle('ddm-form-builder--sidebar-open');
			sidebar.classList.toggle('multi-panel-sidebar-content-open');
		}

		const newOpen = closeButtonPressed ? open : !open;

		if (sidebarPanelId !== currentPanelId) {
			setActivePanel(sidebarPanelId);
		}

		onChange({
			sidebarOpen: sidebarPanelId !== currentPanelId || newOpen,
			sidebarPanelId,
		});
	};

	const handleTabPanelKeyDown = (event) => {
		if (event.key === 'ArrowUp' || event.key === 'ArrowDown') {
			const tabs = Array.from(
				tabListRef.current.querySelectorAll('button')
			);

			const activeTabIndex = tabs.indexOf(document.activeElement);

			const activeTab =
				tabs[
					event.key === 'ArrowUp'
						? activeTabIndex - 1
						: activeTabIndex + 1
				];

			if (activeTab) {
				activeTab.focus();
			}
		}
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
				<div
					aria-orientation="vertical"
					className={classNames(
						'multi-panel-sidebar-buttons',
						'tbar',
						'tbar-stacked',
						variant === 'dark'
							? `tbar-${variant}-d1`
							: `tbar-${variant}`
					)}
					onKeyDown={handleTabPanelKeyDown}
					ref={tabListRef}
					role="tablist"
				>
					{panels.reduce((elements, group, groupIndex) => {
						const buttons = group.map((panelId, index) => {
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
								<>
									{isLink ? (
										<a className={btnClasses} href={url}>
											<ClayIcon symbol={icon} />
										</a>
									) : (
										<ClayButtonWithIcon
											aria-label={getMessage(label)}
											aria-selected={active}
											className={btnClasses}
											data-panel-id={label}
											data-tooltip-align="left"
											displayType="unstyled"
											id={panel.sidebarPanelId}
											onClick={() =>
												handlePanelClick(panel)
											}
											onFocus={prefetch}
											onMouseEnter={prefetch}
											role="tab"
											symbol={icon}
											tabIndex={index === 0 ? '0' : '-1'}
											title={label}
										/>
									)}
								</>
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
				</div>

				<div
					aria-label={sub(
						Liferay.Language.get('x-panel'),
						sidebarPanels[activePanel].label
					)}
					className={classNames('multi-panel-sidebar-content', {
						'multi-panel-sidebar-content-open': open,
					})}
					data-sidebar-content={activePanel}
					role="tabpanel"
					tabIndex="-1"
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
							{!panelComponents.length && (
								<ClayLoadingIndicator />
							)}

							{panelComponents.map((panel) => (
								<div
									className={classNames({
										'd-none':
											panel.sidebarPanelId !==
											currentPanelId,
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
