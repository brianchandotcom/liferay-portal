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

import {ClayButtonWithIcon, default as ClayButton} from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {ClayTooltipProvider} from '@clayui/tooltip';
import classNames from 'classnames';
import {useIsMounted, useStateSafe} from 'frontend-js-react-web';
import React from 'react';
import {createPortal} from 'react-dom';

import useLazy from '../../core/hooks/useLazy';
import useLoad from '../../core/hooks/useLoad';
import usePlugins from '../../core/hooks/usePlugins';
import * as Actions from '../actions/index';
import {config} from '../config/index';
import selectAvailablePanels from '../selectors/selectAvailablePanels';
import selectAvailableSidebarPanels from '../selectors/selectAvailableSidebarPanels';
import {useDispatch, useSelector} from '../store/index';
import {useDropClear} from '../utils/dragAndDrop/useDragAndDrop';
import {useId} from '../utils/useId';
import {useSelectItem} from './Controls';

const {Suspense, useCallback, useEffect} = React;

/**
 * Failure to preload is a non-critical failure, so we'll use this to swallow
 * rejected promises silently.
 */
const swallow = [(value) => value, (_error) => undefined];

/**
 * Load the first available panel if the selected sidebar panel ID is not found.
 * This may happen because the list of panels is modified depending on the user permissions.
 *
 * @param {string} panelId
 * @param {Array} panels
 * @param {object} sidebarPanels
 */
const getActivePanelData = ({panelId, panels, sidebarPanels}) => {
	let sidebarPanelId = panelId;

	let panel = sidebarPanels[sidebarPanelId];

	if (!panel) {
		sidebarPanelId = panels[0][0];
		panel = sidebarPanels[sidebarPanelId];
	}

	return {panel, sidebarPanelId};
};

export default function Sidebar() {
	const dropClearRef = useDropClear();
	const [hasError, setHasError] = useStateSafe(false);
	const {getInstance, register} = usePlugins();
	const dispatch = useDispatch();
	const isMounted = useIsMounted();
	const load = useLoad();
	const selectItem = useSelectItem();
	const sidebarId = useId();
	const store = useSelector((state) => state);

	const panels = useSelector(selectAvailablePanels(config.panels));
	const sidebarPanels = useSelector(
		selectAvailableSidebarPanels(config.sidebarPanels)
	);
	const sidebarOpen = store.sidebar.open;
	const {panel, sidebarPanelId} = getActivePanelData({
		panelId: store.sidebar.panelId,
		panels,
		sidebarPanels,
	});

	const promise = panel
		? load(sidebarPanelId, panel.pluginEntryPoint)
		: Promise.resolve();

	const app = {
		Actions,
		config,
		dispatch,
		store,
	};

	let registerPanel;

	if (sidebarPanelId && panel) {
		registerPanel = register(sidebarPanelId, promise, {app, panel});
	}

	const togglePlugin = () => {
		if (hasError) {
			setHasError(false);
		}

		if (registerPanel) {
			registerPanel.then((plugin) => {
				if (
					plugin &&
					typeof plugin.activate === 'function' &&
					isMounted()
				) {
					plugin.activate();
				}
				else if (!plugin) {
					setHasError(true);
				}
			});
		}
	};

	useEffect(
		() => {
			if (panel) {
				togglePlugin(panel);
			}
			else if (sidebarPanelId) {
				dispatch(
					Actions.switchSidebarPanel({
						sidebarOpen: false,
						sidebarPanelId: null,
					})
				);
			}
		},
		/* eslint-disable react-hooks/exhaustive-deps */
		[panel, sidebarOpen, sidebarPanelId]
	);

	useEffect(() => {
		const sideNavigation = Liferay.SideNavigation.instance(
			document.querySelector('.product-menu-toggle')
		);

		if (sideNavigation) {
			const onHandleSidebar = (open) => {
				dispatch(
					Actions.switchSidebarPanel({
						sidebarOpen: open,
					})
				);
			};

			if (!sideNavigation.visible()) {
				onHandleSidebar(true);
			}

			const sideNavigationListener = sideNavigation.on(
				'openStart.lexicon.sidenav',
				() => onHandleSidebar(false)
			);

			return () => {
				sideNavigationListener.removeListener();
			};
		}
	}, []);

	const SidebarPanel = useLazy(
		useCallback(({instance}) => {
			if (typeof instance.renderSidebar === 'function') {
				return instance.renderSidebar();
			}
			else {
				return null;
			}
		}, [])
	);

	const deselectItem = (event) => {
		if (event.target === event.currentTarget) {
			selectItem(null);
		}
	};

	const handleClick = (panel) => {
		const open =
			panel.sidebarPanelId === sidebarPanelId ? !sidebarOpen : true;
		const productMenuToggle = document.querySelector(
			'.product-menu-toggle'
		);

		if (productMenuToggle && !sidebarOpen) {
			Liferay.SideNavigation.hide(productMenuToggle);
		}

		dispatch(
			Actions.switchSidebarPanel({
				sidebarOpen: open,
				sidebarPanelId: panel.sidebarPanelId,
			})
		);
	};

	return createPortal(
		<ClayTooltipProvider>
			<div
				className="page-editor__sidebar page-editor__theme-adapter-forms"
				ref={dropClearRef}
			>
				<div
					className={classNames('page-editor__sidebar__buttons', {
						light: true,
					})}
					onClick={deselectItem}
				>
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

							if (isLink) {
								return (
									<a
										className={classNames({active})}
										data-tooltip-align="left"
										href={url}
										key={panel.sidebarPanelId}
										title={label}
									>
										<ClayIcon symbol={icon} />
									</a>
								);
							}

							const prefetch = () =>
								load(
									panel.sidebarPanelId,
									pluginEntryPoint
								).then(...swallow);

							return (
								<ClayButtonWithIcon
									aria-pressed={active}
									className={classNames({active})}
									data-tooltip-align="left"
									displayType="unstyled"
									id={`${sidebarId}${panel.sidebarPanelId}`}
									key={panel.sidebarPanelId}
									onClick={() => handleClick(panel)}
									onFocus={prefetch}
									onMouseEnter={prefetch}
									small={true}
									symbol={icon}
									title={label}
								/>
							);
						});

						// Add separator between groups.

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
					className={classNames({
						'page-editor__sidebar__content': true,
						'page-editor__sidebar__content--open': sidebarOpen,
						rtl:
							config.languageDirection[
								themeDisplay?.getLanguageId()
							] === 'rtl',
					})}
					onClick={deselectItem}
				>
					{hasError ? (
						<div>
							<ClayButton
								block
								displayType="secondary"
								onClick={() => {
									dispatch(
										Actions.switchSidebarPanel({
											sidebarOpen: false,
											sidebarPanelId:
												panels[0] && panels[0][0],
										})
									);
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
							<Suspense fallback={<ClayLoadingIndicator />}>
								<SidebarPanel
									getInstance={getInstance}
									pluginId={sidebarPanelId}
								/>
							</Suspense>
						</ErrorBoundary>
					)}
				</div>
			</div>
		</ClayTooltipProvider>,
		document.body
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
