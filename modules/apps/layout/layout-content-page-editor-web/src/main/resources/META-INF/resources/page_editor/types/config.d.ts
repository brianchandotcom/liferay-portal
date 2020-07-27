import {LayoutTypes} from './LayoutTypes';

export interface Config {
	addFragmentEntryLinkCommentURL: string;
	addFragmentEntryLinkURL: string;
	addFragmentEntryLinksURL: string;
	addItemURL: string;
	addPortletURL: string;
	addSegmentsExperienceURL: string;

	availableLanguages: {
		[key: string]: {
			languageIcon: string;
			languageLabel: string;
		};
	};

	availableSegmentsEntries: {
		[key: string]: {
			segmentsEntryId: string;
			name: string;
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

	containerItemFlexEnabled: boolean;

	defaultEditorConfigurations: {
		[key: 'comment' | 'rich-text' | 'text']: {
			editorConfig: object;
			editorOptions: object;
		};
	};

	defaultLanguageId: string;
	defaultSegmentsEntryId: string;
	defaultSegmentsExperienceId: string;
	deleteFragmentEntryLinkCommentURL: string;
	deleteItemURL: string;
	deleteSegmentsExperienceURL: string;
	discardDraftRedirectURL: string;
	discardDraftURL: string;
	draft: boolean;
	duplicateItemURL: string;
	editFragmentEntryLinkCommentURL: string;
	editFragmentEntryLinkURL: string;
	editSegmentsEntryURL: string;
	getAssetFieldValueURL: string;
	getAssetMappingFieldsURL: string;
	getAvailableListRenderersURL: string;
	getAvailableListItemRenderersURL: string;
	getAvailableTemplatesURL: string;
	getCollectionFieldURL: string;
	getCollectionMappingFieldsURL: string;
	getExperienceUsedPortletsURL: string;
	getIframeContentCssURL: string;
	getIframeContentURL: string;
	getPageContentsURL: string;
	imageSelectorURL: string;
	infoItemSelectorURL: string;

	languageDirection: {
		[key: string]: 'ltr' | 'rtl';
	};

	layoutConversionWarningMessages: string[] | null;
	layoutType: LayoutTypes[keyof LayoutTypes];
	masterUsed: boolean;
	moveItemURL: string;
	panels: string[][];
	pending: boolean;
	plid: string;
	pluginsRootPath: string;
	portletNamespace: string;
	previewPageURL: string;
	publishURL: string;
	redirectURL: string;
	renderFragmentEntryURL: string;
	selectedSegmentsEntryId: string;

	sidebarPanels: {
		[key: string]: {
			icon: string;
			isLink: boolean;
			label: string;
			pluginEntryPoint?: string;
			sidebarPanelId: string;
			url?: string | null;
		};
	};

	singleSegmentsExperienceMode: boolean;
	themeColorCssClasses: string[];
	toolbarId: string;

	toolbarPlugins: Array<{
		loadingPlaceholder: string;
		pluginEntryPoint: string;
		toolbarPluginId: string;
	}>;

	updateConfigurationValuesURL: string;
	updateItemConfigURL: string;
	updateLayoutPageTemplateDataURL: string;
	updateRowColumnsURL: string;
	updateSegmentsExperiencePriorityURL: string;
	updateSegmentsExperienceURL: string;
	workflowEnabled: boolean;
}
