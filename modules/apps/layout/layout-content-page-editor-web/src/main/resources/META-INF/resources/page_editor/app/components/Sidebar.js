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
import {getConfig} from '../config/index';
import {DispatchContext} from '../reducers/index';
import {StoreContext} from '../store/index';

const {Suspense, useCallback, useContext, useEffect} = React;

/**
 * Failure to preload is a non-critical failure, so we'll use this to swallow
 * rejected promises silently.
 */
const swallow = [value => value, _error => undefined];

export default function Sidebar() {
	const config = getConfig;
	const dispatch = useContext(DispatchContext);
	const store = useContext(StoreContext);

	const [hasError, setHasError] = useStateSafe(false);
	const [open, setOpen] = useStateSafe(false);
	const [activePluginId, setActivePluginId] = useStateSafe(null);

	const {sidebarPanels} = config;

	const isMounted = useIsMounted();

	const load = useLoad();

	const {getInstance, register} = usePlugins();

	useEffect(
		() => {
			// Open first panel on mount.
			const panel = sidebarPanels[0] && sidebarPanels[0][0];

			if (panel) {
				togglePanel(panel);
			} else {
				adjustWrapperPadding({sidebarOpen: false});
			}
		},
		// We really only want to do this once, on first mount.
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[]
	);

	const SidebarPanel = useLazy(
		useCallback(({instance}) => {
			if (typeof instance.renderSidebar === 'function') {
				return instance.renderSidebar();
			} else {
				return null;
			}
		}, [])
	);

	const togglePanel = panel => {
		if (hasError) {
			setHasError(false);
		}

		const {rendersSidebarContent, sidebarPanelId} = panel;

		const shouldActivate =
			!rendersSidebarContent || sidebarPanelId !== activePluginId;

		const wantOpen = rendersSidebarContent && shouldActivate;

		if (open !== wantOpen) {
			setOpen(wantOpen);

			adjustWrapperPadding({sidebarOpen: wantOpen});
		}

		getInstance(activePluginId).then(activePlugin => {
			if (activePlugin && typeof activePlugin.deactivate === 'function') {
				activePlugin.deactivate();
			}
		});

		if (shouldActivate) {
			setActivePluginId(sidebarPanelId);

			const promise = load(sidebarPanelId, panel.pluginEntryPoint);

			const app = {
				Actions,
				StoreContext,
				config,
				dispatch,
				store
			};

			register(sidebarPanelId, promise, {app, panel}).then(plugin => {
				if (
					plugin &&
					typeof plugin.activate === 'function' &&
					isMounted()
				) {
					plugin.activate();
				} else if (!plugin) {
					setHasError(true);
				}
			});
		} else {
			setActivePluginId(null);
		}
	};

	return (
		<ClayTooltipProvider>
			<div className="page-editor-sidebar">
				<div className="page-editor-sidebar-buttons">
					{sidebarPanels.reduce((elements, group, groupIndex) => {
						const buttons = group.map(panel => {
							const {
								icon,
								label,
								pluginEntryPoint,
								sidebarPanelId
							} = panel;

							const prefetch = () =>
								load(sidebarPanelId, pluginEntryPoint).then(
									...swallow
								);

							return (
								<ClayButtonWithIcon
									data-tooltip-align="left"
									displayType="unstyled"
									key={sidebarPanelId}
									onClick={() => togglePanel(panel)}
									onFocus={prefetch}
									onMouseEnter={prefetch}
									symbol={icon}
									title={label}
								/>
							);
						});

						// Add separator between groups.
						if (groupIndex === sidebarPanels.length - 1) {
							return elements.concat(buttons);
						} else {
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
						'page-editor-sidebar-content-open': open
					})}
				>
					{hasError ? (
						<div>
							<ClayButton
								block
								displayType="secondary"
								onClick={() => {
									setActivePluginId(null);
									setHasError(false);
									setOpen(false);
								}}
								small
							>
								{Liferay.Language.get('refresh')}
							</ClayButton>
						</div>
					) : (
						<ErrorBoundary handleError={() => setHasError(true)}>
							<Suspense fallback={<ClayLoadingIndicator />}>
								<SidebarPanel
									plugin={getInstance(activePluginId)}
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
		} else {
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
		} else {
			classList.add('page-editor-sidebar-padding');
			classList.remove('page-editor-sidebar-padding-open');
		}
	}
}
