import {PageTypes} from './PageTypes';

export interface Config {
	addFragmentEntryLinkCommentURL: string;
	addFragmentEntryLinkURL: string;
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

	collections: Array<{
		fragmentCollectionId: number;
		name: string;
	}>;

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
	duplicateItemURL: string;
	editFragmentEntryLinkCommentURL: string;
	editFragmentEntryLinkURL: string;
	editSegmentsEntryURL: string;

	fragments: Array<{
		fragmentEntries: Array<{
			groupId: string;
			name: string;
			type: 0 | 1;
			fragmentEntryKey: string;
			imagePreviewURL: string;
		}>;
		fragmentCollectionId: string;
		name: string;
	}>;

	getAssetFieldValueURL: string;
	getAssetMappingFieldsURL: string;
	getAvailableTemplatesURL: string;
	getExperienceUsedPortletsURL: string;
	getPageContentsURL: string;
	imageSelectorURL: string;
	infoItemSelectorURL: string;

	languageDirection: {
		[key: string]: 'ltr' | 'rtl';
	};

	layoutConversionWarningMessages: string[] | null;
	masterUsed: boolean;
	moveItemURL: string;
	pageType: PageTypes[keyof PageTypes];
	panels: string[][];
	pending: boolean;
	plid: string;
	pluginsRootPath: string;
	portletNamespace: string;
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

	updateItemConfigURL: string;
	updateLayoutPageTemplateDataURL: string;
	updateRowColumnsURL: string;
	updateSegmentsExperiencePriorityURL: string;
	updateSegmentsExperienceURL: string;
	workflowEnabled: boolean;
}
