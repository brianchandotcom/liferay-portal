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
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {ClayTooltipProvider} from '@clayui/tooltip';
import classNames from 'classnames';
import {useIsMounted} from 'frontend-js-react-web';
import React from 'react';

import useLazy from '../../core/hooks/useLazy';
import useLoad from '../../core/hooks/useLoad';
import usePlugins from '../../core/hooks/usePlugins';
import useStateSafe from '../../core/hooks/useStateSafe';
import * as Actions from '../actions/index';
import {ConfigContext} from '../config/index';
import selectAvailablePanels from '../selectors/selectAvailablePanels';
import selectAvailableSidebarPanels from '../selectors/selectAvailableSidebarPanels';
import {useDispatch, useSelector} from '../store/index';
import {useSelectItem} from './Controls';

const {Suspense, useCallback, useContext, useEffect} = React;

/**
 * Failure to preload is a non-critical failure, so we'll use this to swallow
 * rejected promises silently.
 */
const swallow = [value => value, _error => undefined];

export default function Sidebar() {
	const config = useContext(ConfigContext);
	const dispatch = useDispatch();
	const store = useSelector(state => state);
	const [hasError, setHasError] = useStateSafe(false);
	const isMounted = useIsMounted();
	const selectItem = useSelectItem();
	const load = useLoad();
	const {getInstance, register} = usePlugins();

	const panels = useSelector(selectAvailablePanels(config.panels));
	const sidebarPanels = useSelector(
		selectAvailableSidebarPanels(config.sidebarPanels)
	);
	const {sidebarOpen, sidebarPanelId} = store;

	const panel = sidebarPanels[sidebarPanelId];
	const promise = panel
		? load(sidebarPanelId, panel.pluginEntryPoint)
		: Promise.resolve();

	const app = {
		Actions,
		config,
		dispatch,
		store
	};

	const registerPanel = register(sidebarPanelId, promise, {app, panel});

	useEffect(
		() => {
			if (panel) {
				togglePlugin(panel);
			}
			else if (sidebarPanelId) {
				dispatch(
					Actions.switchSidebarPanel({
						sidebarOpen: false,
						sidebarPanelId: null
					})
				);
			}
			else {
				adjustWrapperPadding({sidebarOpen: false});
			}
		},
		/* eslint-disable react-hooks/exhaustive-deps */
		[panel, sidebarOpen, sidebarPanelId]
	);

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

	const deselectItem = event => {
		if (event.target === event.currentTarget) {
			selectItem(null, {multiSelect: event.shiftKey});
		}
	};

	const handleClick = panel => {
		const open =
			panel.sidebarPanelId === sidebarPanelId ? !sidebarOpen : true;

		dispatch(
			Actions.switchSidebarPanel({
				sidebarOpen: open,
				sidebarPanelId: panel.sidebarPanelId
			})
		);
	};

	const togglePlugin = () => {
		if (hasError) {
			setHasError(false);
		}

		getInstance(sidebarPanelId);

		registerPanel.then(plugin => {
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
	};

	return (
		<ClayTooltipProvider>
			<div className="page-editor-sidebar">
				<div
					className="page-editor-sidebar-buttons"
					onClick={deselectItem}
				>
					{panels.reduce((elements, group, groupIndex) => {
						const buttons = group.map(panelId => {
							const panel = sidebarPanels[panelId];

							const active =
								sidebarOpen && sidebarPanelId === panelId;
							const {icon, label, pluginEntryPoint} = panel;

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
									id={panel.sidebarPanelId}
									key={panel.sidebarPanelId}
									onClick={() => handleClick(panel)}
									onFocus={prefetch}
									onMouseEnter={prefetch}
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
								<hr key={`separator-${groupIndex}`} />
							]);
						}
					}, [])}
				</div>
				<div
					className={classNames({
						'page-editor-sidebar-content': true,
						'page-editor-sidebar-content-open': sidebarOpen
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
												panels[0] && panels[0][0]
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

function adjustWrapperPadding({sidebarOpen}) {
	const wrapper = document.getElementById('wrapper');

	if (wrapper) {
		const classList = wrapper.classList;

		if (sidebarOpen) {
			classList.add('page-editor-sidebar-padding-open');
			classList.remove('page-editor-sidebar-padding');
		}
		else {
			classList.add('page-editor-sidebar-padding');
			classList.remove('page-editor-sidebar-padding-open');
		}
	}
}
