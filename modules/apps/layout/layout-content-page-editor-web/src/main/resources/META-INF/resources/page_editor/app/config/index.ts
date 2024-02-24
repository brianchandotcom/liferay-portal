/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import Browser from '../../plugins/browser/index';

// @ts-ignore

import Comments from '../../plugins/comments/index';

// @ts-ignore

import Experience from '../../plugins/experience/index';

// @ts-ignore

import FragmentsAndWidgets from '../../plugins/fragments_and_widgets/index';

// @ts-ignore

import Mapping from '../../plugins/mapping/index';

// @ts-ignore

import PageContent from '../../plugins/page_content/index';

// @ts-ignore

import PageDesignOptions from '../../plugins/page_design_options/index';

// @ts-ignore

import PageRules from '../../plugins/page_rules/index';
import {SidebarPanel} from '../../types/SidebarPanel';
import {Config} from '../../types/config';
import {LAYOUT_TYPES, LayoutType} from './constants/layoutTypes';

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

	const {
		commonStyles,
		layoutType,
		portletNamespace,
		sidebarPanels,
	} = backendConfig;

	const toolbarId = `${portletNamespace}${DEFAULT_CONFIG.toolbarId}`;

	// Special items requiring augmentation, creation, or transformation.

	const augmentedPanels = augmentPanelData(sidebarPanels as SidebarPanel[]);

	const syntheticItems: Partial<Config> = {
		commonStyles: getCommonStyles(commonStyles),
		commonStylesFields: getCommonStylesFields(commonStyles),
		panels: generatePanels(augmentedPanels),
		sidebarPanels: partitionPanels(augmentedPanels),
		toolbarId,
		toolbarPlugins: getToolbarPlugins(layoutType, toolbarId),
	};

	config = {
		...DEFAULT_CONFIG,
		...backendConfig,
		...syntheticItems,
	};

	return config;
}

const PLUGIN_CLASS_MAP = {
	browser: Browser,
	comments: Comments,
	experience: Experience,
	fragments_and_widgets: FragmentsAndWidgets,
	mapping: Mapping,
	page_content: PageContent,
	page_design_options: PageDesignOptions,
	page_rules: PageRules,
};

/**
 * In general, we expect the sidebarPanelId to correspond with the name
 * of a plugin. Here we deal with the exceptions by mapping IDs to
 * plugin names.
 */
const SIDEBAR_PANEL_IDS_TO_PLUGINS: Record<string, string> = {};

function augmentPanelData(sidebarPanels: SidebarPanel[]) {
	return sidebarPanels.map((panel) => {
		if (isSeparator(panel) || panel.isLink) {
			return panel;
		}

		const mapping = SIDEBAR_PANEL_IDS_TO_PLUGINS[panel.sidebarPanelId];

		const sidebarPanelId = mapping || panel.sidebarPanelId;

		return {
			...panel,

			// https://github.com/liferay/liferay-js-toolkit/issues/324

			// @ts-ignore

			pluginClass: PLUGIN_CLASS_MAP[sidebarPanelId],

			sidebarPanelId,
		};
	});
}

function generatePanels(sidebarPanels: SidebarPanel[]): Config['panels'] {
	return sidebarPanels.reduce<SidebarPanel['sidebarPanelId'][][]>(
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

/**
 * Currently we have segments experience data sprinkled throughout the
 * server data. In the future we may choose to encapsulate it better and
 * deal with it inside the plugin.
 */
function getToolbarPlugins(layoutType: LayoutType, toolbarId: string) {
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
					pluginClass: Experience,
					toolbarPluginId: 'experience',
				},
		  ]
		: [];
}

function isSeparator(panel: SidebarPanel) {
	return panel.sidebarPanelId === 'separator';
}

/**
 * Instead of using fake panels with an ID of `separator`, partition the panels
 * array into an array of arrays; we'll draw a separator between each group.
 */
type PartitionedSidebarPanels = Record<
	SidebarPanel['sidebarPanelId'],
	SidebarPanel
>;

function partitionPanels(panels: SidebarPanel[]): PartitionedSidebarPanels {
	return panels.reduce<PartitionedSidebarPanels>((map, panel) => {
		const {sidebarPanelId} = panel;
		if (!isSeparator(panel)) {
			map[sidebarPanelId] = panel;
		}

		return map;
	}, {});
}
