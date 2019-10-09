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
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {ClayTooltipProvider} from '@clayui/tooltip';
import classNames from 'classnames';
import {useIsMounted} from 'frontend-js-react-web';
import React from 'react';

import {ConfigContext} from '../config/index';
import usePlugins from '../hooks/usePlugins';
import usePreload from '../hooks/usePreload';
import useStateSafe from '../hooks/useStateSafe';
import {StoreContext} from '../store/index';

const {Suspense, lazy, useContext} = React;

/**
 * Failure to preload is a non-critical failure, so we'll use this to swallow
 * rejected promises silently.
 */
const swallow = [value => value, _error => undefined];

// TODO: add react error boundary
export default function Sidebar() {
	const config = useContext(ConfigContext);
	const store = useContext(StoreContext);

	// TODO: default to open and eagerly load first plugin
	const [open, setOpen] = useStateSafe(false);
	const [activePluginId, setActivePluginId] = useStateSafe(null);

	const {sidebarPanels} = store;

	const isMounted = useIsMounted();

	const preload = usePreload();

	const {getInstance, register} = usePlugins();

	// TODO: useEffect to call deactivate before unmounting

	const togglePanel = panel => {
		const {rendersSidebarContent, sidebarPanelId} = panel;

		const shouldActivate =
			!rendersSidebarContent || sidebarPanelId !== activePluginId;

		const wantOpen = rendersSidebarContent && shouldActivate;

		if (open !== wantOpen) {
			setOpen(wantOpen);
		}

		getInstance(activePluginId).then(activePlugin => {
			if (activePlugin && typeof activePlugin.deactivate === 'function') {
				activePlugin.deactivate();
			}
		});

		if (shouldActivate) {
			const promise = preload(sidebarPanelId, panel.pluginEntryPoint);

			register(sidebarPanelId, promise, {config, panel, store}).then(
				plugin => {
					if (
						plugin &&
						typeof plugin.activate === 'function' &&
						isMounted()
					) {
						plugin.activate();
					}

				}
			);

			setActivePluginId(sidebarPanelId);
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
								preload(sidebarPanelId, pluginEntryPoint).then(
									...swallow
								);

							// TODO: also handle keydown
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
					<ErrorBoundary>
						<Suspense fallback={<ClayLoadingIndicator />}>
							<SidebarPanel
								plugin={getInstance(activePluginId)}
							/>
						</Suspense>
					</ErrorBoundary>
				</div>
			</div>
		</ClayTooltipProvider>
	);
}

const SidebarPanel = ({plugin}) => {
	const Component = lazy(() =>
		plugin.then(instance => {
			return {
				default: () => {
					if (
						instance &&
						typeof instance.renderSidebar === 'function'
					) {
						return instance.renderSidebar();
					} else {
						return null;
					}
				}
			};
		})
	);

	return <Component />;
};

class ErrorBoundary extends React.Component {
	static getDerivedStateFromError(_error) {
		// TODO: specifically look for loading errors here
		return {hasError: true};
	}

	constructor(props) {
		super(props);

		this.state = {hasError: false};
	}

	componentDidCatch() {
		// TODO: log here in dev
	}

	render() {
		if (this.state.hasError) {
			return (
				<span>
					got an error, could
					<a onClick={() => this.setState({hasError: false})}>
						retry
					</a>
				</span>
			);
		} else {
			return this.props.children;
		}
	}
}
