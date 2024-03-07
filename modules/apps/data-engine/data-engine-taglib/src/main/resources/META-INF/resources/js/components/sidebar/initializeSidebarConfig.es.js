/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const DEFAULT_CONFIG = {
	sidebarVariant: 'light',
	toolbarId: 'dataEngineToolbar',
};

export const config = null;

/**
 * Extracts the immutable parts from the server data.
 *
 * Unlike data in the store, this config does not change over the lifetime of
 * the app, so we can safely store is as a variable.
 */
export default function initializeSidebarConfig(backendInfo) {
	const toolbarId = `${backendInfo.portletNamespace}${DEFAULT_CONFIG.toolbarId}`;

	// Special items requiring augmentation, creation, or transformation.

	const augmentedPanels = augmentPanelData(
		Object.values(backendInfo.sidebarPanels)
	);

	const syntheticItems = {
		panels: generatePanels(augmentedPanels),
		sidebarPanels: partitionPanels(augmentedPanels),
		toolbarId,
	};

	return {
		...DEFAULT_CONFIG,
		...syntheticItems,
	};
}

/**
 * In general, we expect the sidebarPanelId to correspond with the name
 * of a plugin. Here we deal with the exceptions by mapping IDs to
 * plugin names.
 */
const SIDEBAR_PANEL_IDS_TO_PLUGINS = {
	elements: 'fragments',
};

function augmentPanelData(sidebarPanels) {
	return sidebarPanels.map((panel) => {
		if (isSeparator(panel) || panel.isLink) {
			return panel;
		}

		const mapping = SIDEBAR_PANEL_IDS_TO_PLUGINS[panel.sidebarPanelId];

		const sidebarPanelId = mapping || panel.sidebarPanelId;

		return {
			...panel,
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
