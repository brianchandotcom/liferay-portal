/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SidebarPanel} from '../../types/SidebarPanel';
import {Config} from '../../types/config';

const DEFAULT_CONFIG: Partial<Config> = {
	toolbarId: 'pageEditorToolbar',
};

export let config = DEFAULT_CONFIG as Config;

/**
 * Extracts the immutable parts from the server data.
 *
 * Unlike data in the store, this config does not change over the lifetime of
 * the app, so we can safely store is as a variable.
 */
export function initializeConfig(backendConfig: Config) {
	if (!backendConfig.layoutType) {
		config = {
			...backendConfig,
		};

		return config;
	}

	const {commonStyles, portletNamespace, sidebarPanels} = backendConfig;

	const toolbarId = `${portletNamespace}${DEFAULT_CONFIG.toolbarId}`;

	const syntheticItems: Partial<Config> = {
		commonStyles: getCommonStyles(commonStyles),
		commonStylesFields: getCommonStylesFields(commonStyles),
		sidebarPanelsMap: generatePanels(sidebarPanels as SidebarPanel[]),
		toolbarId,
	};

	config = {
		...DEFAULT_CONFIG,
		...backendConfig,
		...syntheticItems,
	};

	return config;
}

function getCommonStyles(commonStyles: Config['commonStyles']) {
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

function getCommonStylesFields(
	commonStyles: Config['commonStyles']
): Config['commonStylesFields'] {
	const commonStylesFields: Config['commonStylesFields'] = {};

	commonStyles.forEach((fieldSet) => {
		fieldSet.styles.forEach((field) => {
			commonStylesFields[field.name] = {
				cssTemplate: field.cssTemplate,
				defaultValue: field.defaultValue,
			};
		});
	});

	return commonStylesFields;
}

function generatePanels(sidebarPanels: SidebarPanel[]) {
	const map: Record<string, SidebarPanel> = {};

	sidebarPanels.forEach((panel) => {
		map[panel.sidebarPanelId] = panel;
	});

	return map;
}
