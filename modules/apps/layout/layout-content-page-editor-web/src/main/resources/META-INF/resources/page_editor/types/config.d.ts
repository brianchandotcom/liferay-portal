/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import type {LayoutType} from '../app/config/constants/layoutTypes';
import type {SidebarPanel} from './SidebarPanel';

export interface Config {
	actionableInfoItemSelectorURL: string;
	addFragmentCompositionURL: string;
	addFragmentEntryLinkCommentURL: string;
	addFragmentEntryLinkURL: string;
	addFragmentEntryLinksURL: string;
	addItemURL: string;
	addPortletURL: string;
	addRuleURL: string;
	addSegmentsExperienceURL: string;

	assetCategoryTreeNodeItemSelectorURL: string;

	autoExtendSessionEnabled: boolean;

	availableLanguages: {
		[key: string]: {
			languageIcon: string;
			languageLabel: string;
		};
	};

	availableSegmentsEntries: {
		[key: string]: {
			name: string;
			segmentsEntryId: string;
		};
	};

	availableViewportSizes: {
		[key: string]: {
			icon: string;
			label: string;
			maxWidth: string;
			minWidth: string;
			sizeId: string;
		};
	};

	changeMasterLayoutURL: string;
	changeStyleBookEntryURL: string;
	collectionSelectorURL: string;

	commonStyles: Array<{
		label: string;
		styles: Array<{
			cssTemplate: string;
			dataType: string;
			defaultValue: string | object;
			dependencies: Array<{
				styleName: string;
				value: string | object;
			}>;
			label: string;
			name: string;
			responsive: boolean;
			type: string;
			validValues: Array<{
				label: string;
				value: string | object;
			}>;
		}>;
	}>;

	commonStylesFields: Record<
		string,
		{
			cssTemplate: string;
			defaultValue: string | object;
		}
	>;

	contentPagePersonalizationLearnURL: string;
	createLayoutPageTemplateEntryURL: string;

	defaultEditorConfigurations: Record<
		'comment' | 'rich-text' | 'text',
		{
			editorConfig: object;
			editorOptions: object;
		}
	>;

	defaultLanguageId: Liferay.Language.Locale;
	defaultSegmentsEntryId: string;
	defaultSegmentsExperienceId: string;
	defaultStyleBookEntryImagePreviewURL: string;
	defaultStyleBookEntryName: string;
	deleteFragmentEntryLinkCommentURL: string;
	deleteRuleURL: string;
	deleteSegmentsExperienceURL: string;
	discardDraftURL: string;
	duplicateItemURL: string;
	duplicateSegmentsExperienceURL: string;
	editFragmentEntryLinkCommentURL: string;
	editFragmentEntryLinkURL: string;
	editSegmentsEntryURL: string;
	frontendTokens: {
		[key: string]:
			| {
					cssVariable: string;
					editorType: string;
					label: string;
					name: string;
					value: string;
			  }
			| string;
	};
	getAvailableImageConfigurationsURL: string;
	getAvailableListItemRenderersURL: string;
	getAvailableListRenderersURL: string;
	getAvailableTemplatesURL: string;
	getCollectionConfigurationURL: string;
	getCollectionFieldURL: string;
	getCollectionFiltersURL: string;
	getCollectionItemCountURL: string;
	getCollectionMappingFieldsURL: string;
	getCollectionSupportedFiltersURL: string;
	getCollectionWarningMessageURL: string;
	getExperienceDataURL: string;
	getFormConfigURL: string;
	getIframeContentCssURL: string;
	getIframeContentURL: string;
	getInfoItemActionErrorMessageURL: string;
	getInfoItemFieldValueURL: string;
	getLayoutFriendlyURL: string;
	getLayoutPageTemplateCollectionsURL: string;
	getPageContentsURL: string;
	getPortletsURL: string;
	getRolesURL: string;
	getUsersURL: string;
	imageSelectorURL: string;
	infoItemPreviewSelectorURL: string;
	infoItemSelectorURL: string;
	isConversionDraft: boolean;
	isPrivateLayoutsEnabled: boolean;
	layoutConversionWarningMessages: string[] | null;
	layoutItemSelectorURL: String;
	layoutType: LayoutType;
	lookAndFeelURL: string;
	mappingFieldsURL: string;
	markItemForDeletionURL: string;
	masterLayouts: Array<{
		imagePreviewURL: string;
		masterLayoutPlid: string;
		name: string;
	}>;
	masterUsed: boolean;
	moveItemURL: string;
	paddingOptions: Array<{
		label: string;
		value: string;
	}>;
	panels: string[][];
	pending: boolean;
	plid: string;
	pluginsRootPath: string;
	portletNamespace: string;
	publishURL: string;
	redirectURL: string;
	renderFragmentEntryURL: string;
	restoreCollectionDisplayConfigURL: string;
	searchContainerPageMaxDelta: number;

	selectedMappingTypes?: {
		subtype: {
			id: string;
			label: string;
		};
		type: {
			id: string;
			label: string;
		};
	};

	selectedSegmentsEntryId: string;

	sidebarPanels: SidebarPanel[] | Record<string, SidebarPanel>;

	singleSegmentsExperienceMode: boolean;
	siteNavigationMenuItemSelectorURL: string;
	styleBookEnabled: boolean;
	styleBooks: Array<{
		imagePreviewURL: string;
		name: string;
		styleBookEntryId: string;
	}>;
	stylebookEntryId: string;
	themeColorCssClasses: string[];
	toolbarId: string;

	toolbarPlugins: Array<{
		loadingPlaceholder: string;
		pluginEntryPoint: string;
		toolbarPluginId: string;
	}>;

	unmarkItemsForDeletionURL: string;
	updateCollectionDisplayConfigURL: string;
	updateConfigurationValuesURL: string;
	updateFormItemConfigURL: string;
	updateFragmentPortletSetsSortURL: string;
	updateItemConfigURL: string;
	updateLayoutPageTemplateDataURL: string;
	updateRowColumnsURL: string;
	updateRuleURL: string;
	updateSegmentsExperiencePriorityURL: string;
	updateSegmentsExperienceURL: string;
	videoItemSelectorURL: string;
	workflowEnabled: boolean;
}
