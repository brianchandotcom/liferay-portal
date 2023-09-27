/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LAYOUT_TYPES} from './constants/layoutTypes';

const DEFAULT_CONFIG = {
	toolbarId: 'pageEditorToolbar',
};

/** @type {import('../../types/config').Config} */
export let config = DEFAULT_CONFIG;

/**
 * Extracts the immutable parts from the server data.
 *
 * Unlike data in the store, this config does not change over the lifetime of
 * the app, so we can safely store is as a variable.
 */
export function initializeConfig(backendConfig) {
	if (!backendConfig.layoutType) {
		config = {
			...backendConfig,
		};

		return config;
	}

	const {
		commonStyles,
		layoutType,
		pluginsRootPath,
		portletNamespace,
		sidebarPanels,
	} = backendConfig;
	const toolbarId = `${portletNamespace}${DEFAULT_CONFIG.toolbarId}`;

	// Special items requiring augmentation, creation, or transformation.

	const augmentedPanels = augmentPanelData(pluginsRootPath, sidebarPanels);

	const syntheticItems = {
		commonStyles: getCommonStyles(commonStyles),
		commonStylesFields: getCommonStylesFields(commonStyles),
		panels: generatePanels(augmentedPanels),
		sidebarPanels: partitionPanels(augmentedPanels),
		toolbarId,
		toolbarPlugins: getToolbarPlugins(
			layoutType,
			pluginsRootPath,
			toolbarId
		),
	};

	config = {
		...DEFAULT_CONFIG,
		...backendConfig,
		...syntheticItems,
	};

	return config;
}

/**
 * In general, we expect the sidebarPanelId to correspond with the name
 * of a plugin. Here we deal with the exceptions by mapping IDs to
 * plugin names.
 */
const SIDEBAR_PANEL_IDS_TO_PLUGINS = {};

function augmentPanelData(pluginsRootPath, sidebarPanels) {
	return sidebarPanels.map((panel) => {
		if (isSeparator(panel) || panel.isLink) {
			return panel;
		}

		const mapping = SIDEBAR_PANEL_IDS_TO_PLUGINS[panel.sidebarPanelId];

		const sidebarPanelId = mapping || panel.sidebarPanelId;

		return {
			...panel,

			// https://github.com/liferay/liferay-js-toolkit/issues/324

			pluginEntryPoint: `${pluginsRootPath}/${sidebarPanelId}/index`,

			sidebarPanelId,
		};
	});
}

function generatePanels(sidebarPanels) {
	return sidebarPanels.reduce(
		(groups, panel) => {
			if (isSeparator(panel)) {
				groups.push([]);
			}
			else {
				groups[groups.length - 1].push(panel.sidebarPanelId);
			}

			return groups;
		},
		[[]]
	);
}

function getCommonStyles(commonStyles) {
	return commonStyles.map((fieldSet) => {
		return {
			...fieldSet,
			styles: fieldSet.styles.map((style) => ({
				...style,
				responsive: true,
			})),
		};
	});
}

function getCommonStylesFields(commonStyles) {
	const commonStylesFields = {};

	const fieldSets = Object.values(commonStyles);

	fieldSets.forEach((fieldSet) => {
		fieldSet.styles.forEach((field) => {
			commonStylesFields[field.name] = {
				cssTemplate: field.cssTemplate,
				defaultValue: field.defaultValue,
			};
		});
	});

	return commonStylesFields;
}

/**
 * Currently we have segments experience data sprinkled throughout the
 * server data. In the future we may choose to encapsulate it better and
 * deal with it inside the plugin.
 */
function getToolbarPlugins(layoutType, pluginsRootPath, toolbarId) {
	const toolbarPluginId = 'experience';
	const selectId = `${toolbarId}_${toolbarPluginId}`;

	return layoutType === LAYOUT_TYPES.content
		? [
				{
					loadingPlaceholder: `
			<div class="page-editor__toolbar-experience">
				<label class="d-lg-block d-none mr-2" for="${selectId}">
					Experience
				</label>
				<button class="form-control-select pr-4 text-left text-truncate btn btn-sm btn-secondary"
					type="button" 
					id="${selectId}"
					disabled>
					<div class="autofit-row autofit-row-center">
						<div class="autofit-col autofit-col-expand">
							<span class="text-truncate">Default</span>
						</div>
						<div class="autofit-col"></div>
					</div>
				</button>
			</div>
		`,
					pluginEntryPoint: `${pluginsRootPath}/experience/index`,
					toolbarPluginId: 'experience',
				},
		  ]
		: [];
}

function isSeparator(panel) {
	return panel.sidebarPanelId === 'separator';
}

/**
 * Instead of using fake panels with an ID of `separator`, partition the panels
 * array into an array of arrays; we'll draw a separator between each group.
 */
function partitionPanels(panels) {
	return panels.reduce((map, panel) => {
		const {sidebarPanelId} = panel;
		if (!isSeparator(panel)) {
			map[sidebarPanelId] = panel;
		}

		return map;
	}, {});
}
