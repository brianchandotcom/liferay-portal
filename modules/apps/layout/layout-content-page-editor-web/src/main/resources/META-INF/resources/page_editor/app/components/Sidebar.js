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
import {useIsMounted} from 'frontend-js-react-web';
import React from 'react';

import {ConfigContext} from '../config/index';
import {StoreContext} from '../store/index';

const {useContext, useRef} = React;

const SEPARATOR = 'separator';

/**
 * Failure to preload is a non-critical failure, so we use this swallow
 * rejected promises silently.
 */
const swallow = [value => value, _error => undefined];

// TODO: add react error boundary
export default function Sidebar() {
	const config = useContext(ConfigContext);
	const state = useContext(StoreContext);

	const {sidebarPanels} = state;

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

	const togglePanel = sidebarPanel => {
		const {sidebarPanelId} = sidebarPanel;

		let pluginInstance = pluginInstances.current.get(sidebarPanelId);

		if (pluginInstance) {
			if (typeof pluginInstance.activate === 'function') {
				pluginInstance.activate();
			}
		} else {
			const promise =
				preloaded.current.get(sidebarPanelId) || preload(sidebarPanel);

			promise
				.then(Plugin => {
					pluginInstance = new Plugin(state, config);
					pluginInstances.current.set(sidebarPanelId, pluginInstance);

					if (typeof pluginInstance.activate === 'function') {
						pluginInstance.activate();
					}
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
			</div>
		</ClayTooltipProvider>
	);
}
