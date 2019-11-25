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

const DEFAULT_CONFIG = {
	toolbarId: 'pageEditorToolbar'
};

let computedConfig = DEFAULT_CONFIG;

/**
 * Returns existing computed config, which defaults to DEFAULT_CONFIG
 * if no data has been given through setConfig function.
 * @return {object}
 */
export function getConfig() {
	return computedConfig;
}

/**
 * Extracts the immutable parts from the server data.
 *
 * Unlike data in the store, this config does not change over the lifetime of
 * the app, so we can safely store is as a variable.
 */
export function setConfig(data) {
	const {
		availableLanguages,
		defaultLanguageId,
		discardDraftURL,
		lookAndFeelURL,
		portletNamespace,
		publishURL,
		sidebarPanels,
		singleSegmentsExperienceMode
	} = data;

	// Items copied over directly without modification.
	const copiedItems = {
		availableLanguages,
		defaultLanguageId,
		discardDraftURL,
		lookAndFeelURL,
		portletNamespace,
		publishURL,
		singleSegmentsExperienceMode
	};

	// Special items requiring augmentation, creation, or transformation.
	const syntheticItems = {
		sidebarPanels: partitionPanels(augmentPanelData(sidebarPanels)),
		toolbarId: portletNamespace + DEFAULT_CONFIG.toolbarId,
		toolbarPlugins: getToolbarPlugins(data)
	};

	computedConfig = {
		...DEFAULT_CONFIG,
		...copiedItems,
		...syntheticItems
	};
}

/**
 * In general, we expect the sidebarPanelId to correspond with the name
 * of a plugin. Here we deal with the exceptions by mapping IDs to
 * plugin names.
 */
const SIDEBAR_PANEL_IDS_TO_PLUGINS = {
	elements: 'fragments',

	lookAndFeel: 'look-and-feel'
};

/**
 * Until we decompose the layout-content-page-editor module into a
 * set of smaller OSGi plugins, we "fake" it here to show how we would
 * lazily-load components from elsewhere by injecting an additional
 * "pluginEntryPoint" property.
 *
 * Note that in the final version we'll be using NPMResolver to obtain these
 * paths dynamically.
 */
const PLUGIN_ROOT = 'layout-content-page-editor-web@2.0.0/page_editor/plugins';

function augmentPanelData(sidebarPanels) {
	return sidebarPanels.map(panel => {
		if (isSeparator(panel)) {
			return panel;
		}

		const mapping = SIDEBAR_PANEL_IDS_TO_PLUGINS[panel.sidebarPanelId];

		const sidebarPanelId = mapping || panel.sidebarPanelId;

		return {
			...panel,

			// https://github.com/liferay/liferay-js-toolkit/issues/324
			pluginEntryPoint: `${PLUGIN_ROOT}/${sidebarPanelId}/index`,

			rendersSidebarContent: rendersSidebarContent(sidebarPanelId),

			sidebarPanelId
		};
	});
}

/**
 * Currently we have segments experience data sprinkled throughout the
 * server data. In the future we may choose to encapsulate it better and
 * deal with it inside the plugin.
 */
function getToolbarPlugins({toolbarId}) {
	const toolbarPluginId = 'experience';
	const selectId = `${toolbarId}_${toolbarPluginId}`;

	return [
		{
			loadingPlaceholder: `
				<div class="align-items-center d-flex mr-2">
					<label class="mr-2" for="${selectId}">
						Experience
					</label>
					<select class="form-control" disabled id="${selectId}">
						<option value="1">Default</option>
					</select>
				</div>
			`,
			pluginEntryPoint: `${PLUGIN_ROOT}/experience/index`,
			toolbarPluginId: 'experience'
		}
	];
}

function isSeparator(panel) {
	return panel.sidebarPanelId === 'separator';
}

/**
 * Instead of using fake panels with an ID of `separator`, partition the panels
 * array into an array of arrays; we'll draw a separator between each group.
 */
function partitionPanels(panels) {
	return panels.reduce(
		(groups, panel) => {
			if (isSeparator(panel)) {
				groups.push([]);
			} else {
				groups[groups.length - 1].push(panel);
			}

			return groups;
		},
		[[]]
	);
}

function rendersSidebarContent(sidebarPanelId) {
	return sidebarPanelId !== 'look-and-feel';
}
