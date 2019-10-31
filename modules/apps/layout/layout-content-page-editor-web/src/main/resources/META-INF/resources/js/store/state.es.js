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

import {Config} from 'metal-state';

import {setIn} from '../utils/FragmentsEditorUpdateUtils.es';
import {getEmptyLayoutData} from '../utils/LayoutDataList.es';
import {
	EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
	FRAGMENTS_EDITOR_ITEM_BORDERS,
	FRAGMENTS_EDITOR_ROW_TYPES,
	PAGE_TYPES
} from '../utils/constants';

const LayoutDataShape = Config.shapeOf({
	nextColumnId: Config.number(),
	nextRowId: Config.number(),
	structure: Config.arrayOf(
		Config.shapeOf({
			columns: Config.arrayOf(
				Config.shapeOf({
					columnId: Config.string(),
					fragmentEntryLinkIds: Config.arrayOf(Config.string()),
					size: Config.string().value('')
				})
			),
			rowId: Config.string(),
			type: Config.oneOf(Object.values(FRAGMENTS_EDITOR_ROW_TYPES)).value(
				FRAGMENTS_EDITOR_ROW_TYPES.componentRow
			)
		})
	)
});

/**
 * Initial state
 * @review
 * @type {object}
 */

const INITIAL_STATE = {
	/**
	 * Id of the active item
	 * @default ''
	 * @review
	 * @type {string}
	 */
	activeItemId: Config.string().value(''),

	/**
	 * Type of the active item
	 * @default ''
	 * @review
	 * @type {string}
	 */
	activeItemType: Config.string().value(''),

	/**
	 * URL for adding a comment to a FragmentEntryLink
	 * @default ''
	 * @review
	 * @type {string}
	 */
	addFragmentEntryLinkCommentURL: Config.string().value(''),

	/**
	 * URL for associating fragment entries to the underlying model.
	 * @default ''
	 * @review
	 * @type {string}
	 */
	addFragmentEntryLinkURL: Config.string().value(''),

	/**
	 * URL for associating portlets to the underlying model.
	 * @default ''
	 * @review
	 * @type {string}
	 */
	addPortletURL: Config.string().value(''),

	/**
	 * URL for adding structured content
	 * @default ''
	 * @review
	 * @type {string}
	 */
	addStructuredContentURL: Config.string().value(''),

	/**
	 * Object of available languages.
	 * @default {}
	 * @review
	 * @type {object}
	 */
	availableLanguages: Config.objectOf(
		Config.shapeOf({
			languageId: Config.string(),
			languageLabel: Config.string()
		})
	).value({}),

	/**
	 * List of available segments
	 * @default []
	 * @review
	 * @type {object[]}
	 */
	availableSegmentsEntries: Config.objectOf(
		Config.shapeOf({
			name: Config.string().required(),
			segmentsEntryId: Config.string().required()
		})
	).value({}),

	/**
	 * List of available segments
	 * @default []
	 * @review
	 * @type {object[]}
	 */
	availableSegmentsExperiences: Config.objectOf(
		Config.shapeOf({
			active: Config.bool(),
			hasLockedSegmentsExperiment: Config.bool(),
			name: Config.string(),
			priority: Config.number(),
			segmentsEntryId: Config.string(),
			segmentsExperienceId: Config.string(),
			segmentsExperimentStatus: Config.oneOfType([
				Config.objectOf(
					Config.shapeOf({
						label: Config.string(),
						value: Config.number()
					})
				),
				Config.object()
			]),
			segmentsExperimentURL: Config.string()
		})
	).value({}),

	/**
	 * Class name id used for storing changes.
	 * @default ''
	 * @review
	 * @type {string}
	 */
	classNameId: Config.string().value(''),

	/**
	 * Class primary key used for storing changes.
	 * @default ''
	 * @review
	 * @type {string}
	 */
	classPK: Config.string().value(''),

	/**
	 * Flag indicating if the content creation is enabled
	 * @default false
	 * @review
	 * @type {boolean}
	 */
	contentCreationEnabled: Config.bool().value(false),

	/**
	 * Flag indicating if the Create Content dialog should be shown
	 * @default false
	 * @review
	 * @type {boolean}
	 */
	createContentDialogVisible: Config.bool().value(false),

	/**
	 * Default configurations for AlloyEditor instances.
	 * @default {}
	 * @instance
	 * @review
	 * @type {object}
	 */
	defaultEditorConfigurations: Config.object().value({}),

	/**
	 * Default language id.
	 * @default ''
	 * @review
	 * @type {string}
	 */
	defaultLanguageId: Config.string().value(''),

	/**
	 * Default segment id.
	 * @default ''
	 * @review
	 * @type {string}
	 */
	defaultSegmentsEntryId: Config.string().value(''),

	/**
	 * Default experience id.
	 * @default undefined
	 * @review
	 * @type {string}
	 */
	defaultSegmentsExperienceId: Config.string().value(undefined),

	/**
	 * URL for removing fragment entries of the underlying model.
	 * @default ''
	 * @review
	 * @type {string}
	 */
	deleteFragmentEntryLinkURL: Config.string().value(''),

	/**
	 * URL for removing a segmentsExperience and it's associated
	 * fragmentEntryLinks.
	 * @default ''
	 * @review
	 * @type {string}
	 */
	deleteSegmentsExperienceURL: Config.string().value(''),

	/**
	 * URL to redirect after discard draft action.
	 * @default ''
	 * @review
	 * @type {!string}
	 */
	discardDraftRedirectURL: Config.string().value(''),

	/**
	 * URL to discard draft changes and return to the latest published version.
	 * @default ''
	 * @review
	 * @type {!string}
	 */
	discardDraftURL: Config.string().value(''),

	/**
	 * Border of the target item where another item is being dragged to
	 * @default null
	 * @review
	 * @type {string}
	 */
	dropTargetBorder: Config.oneOf(
		Object.values(FRAGMENTS_EDITOR_ITEM_BORDERS)
	).value(null),

	/**
	 * Id of the element where a fragment is being dragged over
	 * @default ''
	 * @review
	 * @type {string}
	 */
	dropTargetItemId: Config.string().value(''),

	/**
	 * Type of the element where a fragment is being dragged over
	 * @default ''
	 * @review
	 * @type {string}
	 */
	dropTargetItemType: Config.string().value(''),

	/**
	 * URL for duplicating a FragmentEntryLink
	 * @default ''
	 * @review
	 * @type {string}
	 */
	duplicateFragmentEntryLinkURL: Config.string().value(''),

	/**
	 * URL for editing a comment to a FragmentEntryLink
	 * @default ''
	 * @review
	 * @type {string}
	 */
	editFragmentEntryLinkCommentURL: Config.string().value(''),

	/**
	 * URL for updating a distinct fragment entries of the editor.
	 * @default ''
	 * @instance
	 * @review
	 * @type {string}
	 */
	editFragmentEntryLinkURL: Config.string().value(''),

	/**
	 * Available elements that can be dragged inside the existing Page Template,
	 * organized by fragment categories.
	 * @default []
	 * @review
	 * @type {Array<{
	 *   fragmentCollectionId: !string,
	 *   fragmentEntries: Array<{
	 *     fragmentEntryKey: !string,
	 *     imagePreviewURL: string,
	 *     groupId: string,
	 *     name: !string
	 *   }>,
	 *   name: !string
	 * }>}
	 */
	elements: Config.arrayOf(
		Config.shapeOf({
			fragmentCollectionId: Config.string().required(),
			fragmentEntries: Config.arrayOf(
				Config.shapeOf({
					fragmentEntryKey: Config.string().required(),
					groupId: Config.string().value(''),
					imagePreviewURL: Config.string(),
					name: Config.string().required()
				})
			).required(),
			name: Config.string().required()
		})
	).value([]),

	/**
	 * List of fragment instances being used.
	 * @default {}
	 * @review
	 * @type {object}
	 */
	fragmentEntryLinks: Config.objectOf(
		Config.shapeOf({
			config: Config.object().value({}),
			configuration: Config.object().value({}),
			content: Config.any().value(''),
			editableValues: Config.shapeOf({
				[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: Config.objectOf(
					Config.shapeOf({
						classNameId: Config.string().value(''),
						classPK: Config.string().value(''),
						defaultValue: Config.string().required(),
						fieldId: Config.string().value(''),
						mappedField: Config.string().value('')
					})
				).value({})
			}).value({}),
			fragmentEntryId: Config.oneOfType([
				Config.string(),
				Config.number()
			]).value(''),
			fragmentEntryKey: Config.string().required(),
			fragmentEntryLinkId: Config.string().required(),
			name: Config.string().required(),
			portletId: Config.string().value('')
		})
	).value({}),

	/**
	 * URL for getting an asset field value
	 * @default ''
	 * @review
	 * @type {string}
	 */
	getAssetFieldValueURL: Config.string().value(''),

	/**
	 * Get asset mapping fields url
	 * @default undefined
	 * @review
	 * @type {string}
	 */
	getAssetMappingFieldsURL: Config.string().value(''),

	/**
	 * Get available templates url
	 * @default undefined
	 * @review
	 * @type {string}
	 */
	getAvailableTemplatesURL: Config.string().value(''),

	/**
	 * URL for obtaining the content structure mapping fields
	 * created.
	 * @default '''
	 * @review
	 * @type {string}
	 */
	getContentStructureMappingFieldsURL: Config.string().value(''),

	/**
	 * URL for obtaining the content structures
	 * created.
	 * @default '''
	 * @review
	 * @type {string}
	 */
	getContentStructuresURL: Config.string().value(''),

	/**
	 * Get portlets used in a particular experience
	 * @default undefined
	 * @review
	 * @type {string}
	 */
	getExperienceUsedPortletsURL: Config.string().value(''),

	/**
	 * URL for obtaining the asset types for which info display pages can be
	 * created.
	 * @default '''
	 * @review
	 * @type {string}
	 */
	getInfoClassTypesURL: Config.string().value(''),

	/**
	 * URL for obtaining the asset types for which info display pages can be
	 * created.
	 * @default '''
	 * @review
	 * @type {string}
	 */
	getInfoDisplayContributorsURL: Config.string().value(''),

	/**
	 * Get page content url
	 * @default undefined
	 * @review
	 * @type {string}
	 */
	getPageContentsURL: Config.string().value(''),

	/**
	 * @default true
	 * @review
	 * @type {bool}
	 */
	hasUpdatePermissions: Config.bool().value(true),

	/**
	 * Id of the last element that was hovered
	 * @default ''
	 * @review
	 * @type {string}
	 */
	hoveredItemId: Config.string().value(''),

	/**
	 * Type of the last element that was hovered
	 * @default ''
	 * @review
	 * @type {string}
	 */
	hoveredItemType: Config.string().value(''),

	/**
	 * Image selector url
	 * @default undefined
	 * @review
	 * @type {string}
	 */
	imageSelectorURL: Config.string().value(''),

	/**
	 * URL for selecting info items
	 * @default []
	 * @review
	 * @type {object[]}
	 */
	infoItemSelectorURL: Config.string().value(''),

	/**
	 * Currently selected language id.
	 * @default ''
	 * @review
	 * @type {string}
	 */
	languageId: Config.string().value(''),

	/**
	 * Last date when the autosave has been executed.
	 * @default ''
	 * @review
	 * @type {string}
	 */
	lastSaveDate: Config.string().value(''),

	/**
	 * Data associated to the layout
	 * @default {structure: []}
	 * @review
	 * @type {{structure: Array}}
	 */
	layoutData: LayoutDataShape.value(getEmptyLayoutData()),

	/**
	 * List of layoutData related to segmentsExperiences
	 * @default ''
	 * @review
	 * @type {!Array}
	 */
	layoutDataList: Config.arrayOf(
		Config.shapeOf({
			layoutData: LayoutDataShape.required(),
			segmentsExperienceId: Config.string().required()
		})
	).value([]),

	/**
	 * Current layout look&feel url
	 * @default undefined
	 * @review
	 * @type {string}
	 */
	lookAndFeelURL: Config.string().value(''),

	/**
	 * @default []
	 * @review
	 * @type {object[]}
	 */
	mappedInfoItems: Config.array().value([]),

	/**
	 * URL for getting the list of mapping fields
	 * @default ''
	 * @review
	 * @type {string}
	 */
	mappingFieldsURL: Config.string().value(''),

	/**
	 * Master page layout data
	 * @review
	 * @type {{structure: Array}}
	 */
	masterPageLayoutData: LayoutDataShape,

	/**
	 * @default []
	 * @review
	 * @type {Array<{name: string, status: { label: string, style: string }, title: string, usagesCount: number}>}
	 */
	pageContents: Config.arrayOf(
		Config.shapeOf({
			name: Config.string(),
			status: Config.shapeOf({
				label: Config.string(),
				style: Config.string()
			}),
			title: Config.string(),
			usagesCount: Config.number()
		})
	).value([]),

	/**
	 * @review
	 * @type {number}
	 */
	pageType: Config.oneOf(Object.values(PAGE_TYPES)),

	/**
	 * Portlet namespace needed for prefixing form inputs
	 * @default ''
	 * @review
	 * @type {string}
	 */
	portletNamespace: Config.string().value(''),

	/**
	 * @default ''
	 * @review
	 * @type {!string}
	 */
	publishURL: Config.string().value(''),

	/**
	 * @default ''
	 * @review
	 * @type {!string}
	 */
	redirectURL: Config.string().value(''),

	/**
	 * URL for getting a fragment content.
	 * @default ''
	 * @review
	 * @type {string}
	 */
	renderFragmentEntryURL: Config.string().value(''),

	/**
	 * When true, it indicates that are changes pending to save.
	 * @default false
	 * @review
	 * @type {boolean}
	 */
	savingChanges: Config.bool().value(false),

	/**
	 * Available sections that can be dragged inside the existing Page Template,
	 * organized by fragment categories.
	 * @default []
	 * @review
	 * @type {Array<{
	 *   fragmentCollectionId: !string,
	 *   fragmentEntries: Array<{
	 *     fragmentEntryKey: !string,
	 *     imagePreviewURL: string,
	 *     groupId: string,
	 *     name: !string
	 *   }>,
	 *   name: !string
	 * }>}
	 */
	sections: Config.arrayOf(
		Config.shapeOf({
			fragmentCollectionId: Config.string().required(),
			fragmentEntries: Config.arrayOf(
				Config.shapeOf({
					fragmentEntryKey: Config.string().required(),
					groupId: Config.string().value(''),
					imagePreviewURL: Config.string(),
					name: Config.string().required()
				}).required()
			).required(),
			name: Config.string().required()
		})
	).value([]),

	/**
	 * The active segmentsExperience
	 * @default ''
	 * @review
	 * @type {string}
	 */
	segmentsExperienceId: Config.string().value(),

	/**
	 * The selected Experience test status
	 * This is meant to be swapped every time an Experience is selected
	 * @default null
	 * @review
	 * @type {object}
	 */
	segmentsExperimentStatus: Config.oneOfType([
		Config.objectOf(
			Config.shapeOf({
				label: Config.string(),
				value: Config.number()
			})
		),
		Config.object()
	]).value(),

	/**
	 * Selected items
	 * @default []
	 * @review
	 * @type {Array<string>}
	 */
	selectedItems: Config.arrayOf(
		Config.shapeOf({
			itemId: Config.string(),
			itemType: Config.string()
		})
	).value([]),

	/**
	 * Selected mapping type label
	 * @default {}
	 * @review
	 * @type {{
	 *   subtype: {
	 *   	id: !string,
	 *   	label: !string
	 *   },
	 *   type: {
	 *   	id: !string,
	 *   	label: !string
	 *   }
	 * }}
	 */
	selectedMappingTypes: Config.shapeOf({
		subtype: Config.shapeOf({
			id: Config.string().required(),
			label: Config.string().required()
		}),
		type: Config.shapeOf({
			id: Config.string().required(),
			label: Config.string().required()
		})
	}).value({}),

	/**
	 * Selected sidebar panel ID to be shown (null or empty)
	 * when sidebar is hidden.
	 * @default 'sections'
	 * @review
	 * @type {string}
	 */
	selectedSidebarPanelId: Config.string().value('elements'),

	/**
	 * Flag indicating if resolved comments should be shown
	 * @default false
	 * @review
	 * @type {boolean}
	 */
	showResolvedComments: Config.bool().value(false),

	/**
	 * List of sidebar panels
	 * @default []
	 * @review
	 * @type {object[]}
	 */
	sidebarPanels: Config.arrayOf(
		Config.shapeOf({
			icon: Config.string(),
			label: Config.string(),
			sidebarPanelId: Config.string()
		})
	).value([]),

	/**
	 * Path of the available icons.
	 * @default ''
	 * @review
	 * @type {string}
	 */
	spritemap: Config.string().value(''),

	/**
	 * @default ''
	 * @review
	 * @type {!string}
	 */
	status: Config.string().value(''),

	/**
	 * @default []
	 * @review
	 * @type {Array}
	 */
	themeColorsCssClasses: Config.arrayOf(Config.string()).value([]),

	/**
	 * URL for updating layout data.
	 * @default ''
	 * @review
	 * @type {string}
	 */
	updateLayoutPageTemplateDataURL: Config.string().value(''),

	/**
	 * Available widgets that can be dragged inside the existing Page Template,
	 * organized by categories.
	 * @default []
	 * @review
	 * @type {Array<{
	 *   categories: Array,
	 *   path: !string,
	 *   portlets: Array<{
	 *     instanceable: !boolean,
	 *     portletId: string,
	 *     title: !string,
	 *     used: !boolean
	 *   }>,
	 * 	 title: !string
	 * }>}
	 */
	widgets: Config.arrayOf(
		Config.shapeOf({
			categories: Config.array(),
			path: Config.string().required(),
			portlets: Config.arrayOf(
				Config.shapeOf({
					instanceable: Config.bool().required(),
					portletId: Config.string().required(),
					title: Config.string().required(),
					used: Config.bool().required()
				}).required()
			),
			title: Config.string().required()
		})
	).value([])
};

/**
 * Initial default state
 * @review
 * @type {object}
 */
const DEFAULT_INITIAL_STATE = Object.keys(INITIAL_STATE).reduce(
	(defaultInitialState, key) =>
		setIn(defaultInitialState, [key], INITIAL_STATE[key].config.value),
	{}
);

export {INITIAL_STATE, DEFAULT_INITIAL_STATE};
export default INITIAL_STATE;
