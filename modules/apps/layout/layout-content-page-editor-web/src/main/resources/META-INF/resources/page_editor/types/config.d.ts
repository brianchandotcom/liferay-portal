import {LayoutTypes} from './LayoutTypes';

export interface Config {
	addFragmentCompositionURL: string;
	addFragmentEntryLinkCommentURL: string;
	addFragmentEntryLinkURL: string;
	addFragmentEntryLinksURL: string;
	addItemURL: string;
	addPortletURL: string;
	addSegmentsExperienceURL: string;

	autoExtendSessionEnabled: boolean;

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

	changeMasterLayoutURL: string;
	changeStyleBookEntryURL: string;
	collectionSelectorURL: string;

	commonStyles: Array<{
		label: string;
		styles: Array<{
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
		}>
	}>;

	containerItemFlexEnabled: boolean;

	defaultEditorConfigurations: {
		[key: 'comment' | 'rich-text' | 'text']: {
			editorConfig: object;
			editorOptions: object;
		};
	};

	defaultLanguageId: string;
	defaultStyleBookEntryName: string;
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
	frontendTokens: {
		[key: string]: {
			cssVariable: string;
			editorType: string;
			label: string;
			name: string;
			value: string;
		};
	}
	getAvailableListRenderersURL: string;
	getAvailableListItemRenderersURL: string;
	getAvailableTemplatesURL: string;
	getCollectionFieldURL: string;
	getCollectionMappingFieldsURL: string;
	getExperienceUsedPortletsURL: string;
	getIframeContentCssURL: string;
	getIframeContentURL: string;
	getInfoItemFieldValueURL: string;
	getInfoItemMappingFieldsURL: string;
	getPageContentsURL: string;
	imageSelectorURL: string;
	infoItemSelectorURL: string;

	languageDirection: {
		[key: string]: 'ltr' | 'rtl';
	};

	layoutConversionWarningMessages: string[] | null;
	layoutType: LayoutTypes[keyof LayoutTypes];
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
	panels: string[][]
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
	stylebookEntryId: string;
	styleBooks: Array<{
		imagePreviewURL: string;
		name: string;
		styleBookEntryId: string;
	}>;
	themeColorCssClasses: string[];
	toolbarId: string;

	toolbarPlugins: Array<{
		loadingPlaceholder: string;
		pluginEntryPoint: string;
		toolbarPluginId: string;
	}>;

	unmarkItemForDeletionURL: string;
	updateConfigurationValuesURL: string;
	updateItemConfigURL: string;
	updateLayoutPageTemplateDataURL: string;
	updateRowColumnsURL: string;
	updateSegmentsExperiencePriorityURL: string;
	updateSegmentsExperienceURL: string;
	workflowEnabled: boolean;
}
