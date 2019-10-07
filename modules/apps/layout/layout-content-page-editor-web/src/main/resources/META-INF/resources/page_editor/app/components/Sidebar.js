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
import {ClayTooltipProvider} from '@clayui/tooltip';
import classNames from 'classnames';
import {useIsMounted} from 'frontend-js-react-web';
import React from 'react';

import {ConfigContext} from '../config/index';
import {StoreContext} from '../store/index';

const {useContext, useRef, useState} = React;

const SEPARATOR = 'separator';

/**
 * Failure to preload is a non-critical failure, so we use this swallow
 * rejected promises silently.
 */
const swallow = [value => value, _error => undefined];

// TODO: possibly extract this into frontend-js-react-web, or at least into
// hooks directory
function useStateSafe(initialValue) {
	const isMounted = useIsMounted();

	const [state, setState] = useState(initialValue);

	return [
		state,
		function setStateSafe(newValue) {
			if (isMounted()) {
				setState(newValue);
			}
		}
	];
}

// TODO: add react error boundary
export default function Sidebar() {
	const config = useContext(ConfigContext);
	const store = useContext(StoreContext);

	// TODO: default to open and eagerly load first plugin
	const [open, setOpen] = useStateSafe(false);
	const [activePlugin, setActivePlugin] = useStateSafe(null);

	const {sidebarPanels} = store;

	const isMounted = useIsMounted();

	// We use refs here to avoid state-related races.
	const preloaded = useRef(new Map());
	const pluginInstances = useRef(new Map());

	const preload = ({pluginEntryPoint, sidebarPanelId}) => {
		if (!preloaded.current.get(sidebarPanelId)) {
			preloaded.current.set(
				sidebarPanelId,
				new Promise((resolve, reject) => {
					Liferay.Loader.require(
						[pluginEntryPoint],
						Plugin => {
							if (isMounted()) {
								resolve(Plugin.default);
							}
						},
						error => {
							if (isMounted()) {
								// Reset so that we retry next time.
								preloaded.current.set(sidebarPanelId, null);
								reject(error);
							}
						}
					);
				})
			);
		}

		return preloaded.current.get(sidebarPanelId);
	};

	// TODO: useEffect to call deactivate before unmounting

	const togglePanel = sidebarPanel => {
		const {rendersSidebarContent, sidebarPanelId} = sidebarPanel;

		function toggle() {
			const pluginInstance = pluginInstances.current.get(sidebarPanelId);

			if (isMounted()) {
				const shouldActivate =
					!rendersSidebarContent || pluginInstance !== activePlugin;

				const wantOpen = rendersSidebarContent && shouldActivate;

				if (typeof activePlugin.deactivate === 'function') {
					activePlugin.deactivate();
				}

				if (
					shouldActivate &&
					typeof pluginInstance.activate === 'function'
				) {
					pluginInstance.activate();
				}

				setActivePlugin(shouldActivate ? pluginInstance : null);

				if (open !== wantOpen) {
					setOpen(wantOpen);
				}
			}
		}

		if (pluginInstances.current.has(sidebarPanelId)) {
			toggle();
		} else {
			const promise =
				preloaded.current.get(sidebarPanelId) || preload(sidebarPanel);

			promise
				.then(Plugin => {
					const pluginInstance = new Plugin(store, config);
					pluginInstances.current.set(sidebarPanelId, pluginInstance);
					toggle();
				})
				.catch(error => {
					if (process.env.NODE_ENV === 'development') {
						console.error(error);
					}
					// TODO: show a toast or something
				});
		}
	};

	return (
		<ClayTooltipProvider>
			<div className="page-editor-sidebar">
				<div className="page-editor-sidebar-buttons">
					{sidebarPanels.map((sidebarPanel, index) => {
						const {icon, label, sidebarPanelId} = sidebarPanel;

						if (sidebarPanelId === SEPARATOR) {
							return <hr key={`separator-${index}`} />;
						} else {
							return (
								// TODO: also handle keydown
								<ClayButtonWithIcon
									data-tooltip-align="left"
									displayType="unstyled"
									key={sidebarPanelId}
									onClick={() => togglePanel(sidebarPanel)}
									onFocus={() =>
										preload(sidebarPanel).then(...swallow)
									}
									onMouseEnter={() =>
										preload(sidebarPanel).then(...swallow)
									}
									symbol={icon}
									title={label}
								/>
							);
						}
					})}
				</div>
				<div
					className={classNames({
						'page-editor-sidebar-content': true,
						'page-editor-sidebar-content-open': open
					})}
				>
					sidebar contents
				</div>
			</div>
		</ClayTooltipProvider>
	);
}
