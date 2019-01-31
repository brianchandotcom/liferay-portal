import {Config} from 'metal-state';

import {FRAGMENTS_EDITOR_ITEM_BORDERS} from '../utils/constants';

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
	activeItemId: Config
		.string()
		.value(''),

	/**
	 * Type of the active item
	 * @default ''
	 * @review
	 * @type {string}
	 */
	activeItemType: Config
		.string()
		.value(''),

	/**
	 * URL for associating fragment entries to the underlying model.
	 * @default ''
	 * @review
	 * @type {string}
	 */
	addFragmentEntryLinkURL: Config
		.string()
		.value(''),

	/**
	 * URL for associating portlets to the underlying model.
	 * @default ''
	 * @review
	 * @type {string}
	 */
	addPortletURL: Config
		.string()
		.value(''),

	/**
	 * Object of available languages.
	 * @default {}
	 * @review
	 * @type {object}
	 */
	availableLanguages: Config
		.objectOf(
			Config.shapeOf(
				{
					languageId: Config.string(),
					languageLabel: Config.string()
				}
			)
		)
		.value({}),

	/**
	 * List of available segments
	 * @default []
	 * @review
	 * @type {object[]}
	 */
	availableSegments: Config
		.objectOf(
			Config.shapeOf(
				{
					segmentId: Config.string().required(),
					segmentLabel: Config.string().required()
				}
			)
		)
		.value({}),


	/**
	 * Class name id used for storing changes.
	 * @default ''
	 * @review
	 * @type {string}
	 */
	classNameId: Config
		.string()
		.value(''),

	/**
	 * Class primary key used for storing changes.
	 * @default ''
	 * @review
	 * @type {string}
	 */
	classPK: Config
		.string()
		.value(''),

	/**
	 * Default language id.
	 * @default ''
	 * @review
	 * @type {string}
	 */
	defaultLanguageId: Config
		.string()
		.value(''),

	/**
	 * URL for removing fragment entries of the underlying model.
	 * @default ''
	 * @review
	 * @type {string}
	 */
	deleteFragmentEntryLinkURL: Config
		.string()
		.value(''),

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
	 *     name: !string
	 *   }>,
	 *   name: !string
	 * }>}
	 */
	elements: Config
		.arrayOf(
			Config.shapeOf(
				{
					fragmentCollectionId: Config.string().required(),
					fragmentEntries: Config.arrayOf(
						Config.shapeOf(
							{
								fragmentEntryKey: Config.string().required(),
								imagePreviewURL: Config.string(),
								name: Config.string().required()
							}
						)
					).required(),
					name: Config.string().required()
				}
			)
		)
		.value([]),

	/**
	 * List of fragment instances being used.
	 * @default {}
	 * @review
	 * @type {object}
	 */
	fragmentEntryLinks: Config
		.objectOf(
			Config.shapeOf(
				{
					config: Config.object().value({}),
					content: Config.any().value(''),
					editableValues: Config.object().value({}),
					fragmentEntryKey: Config.string().required(),
					fragmentEntryLinkId: Config.string().required(),
					name: Config.string().required()
				}
			)
		)
		.value({}),

	/**
	 * Allow opening/closing fragments editor sidebar
	 * @default true
	 * @review
	 * @type {boolean}
	 */
	fragmentsEditorSidebarVisible: Config
		.bool()
		.value(true),

	/**
	 * If true, editable values should be highlighted.
	 * @default false
	 * @review
	 * @type {boolean}
	 */
	highlightMapping: Config
		.bool()
		.value(false),

	/**
	 * Border of the target item where another item is being dragged to
	 * @default null
	 * @review
	 * @type {string}
	 */
	dropTargetBorder: Config
		.oneOf(Object.values(FRAGMENTS_EDITOR_ITEM_BORDERS))
		.value(null),

	/**
	 * Id of the element where a fragment is being dragged over
	 * @default ''
	 * @review
	 * @type {string}
	 */
	dropTargetItemId: Config
		.string()
		.value(''),

	/**
	 * Type of the element where a fragment is being dragged over
	 * @default ''
	 * @review
	 * @type {string}
	 */
	dropTargetItemType: Config
		.string()
		.value(''),

	/**
	 * Id of the last element that was hovered
	 * @default ''
	 * @review
	 * @type {string}
	 */
	hoveredItemId: Config
		.string()
		.value(''),

	/**
	 * Type of the last element that was hovered
	 * @default ''
	 * @review
	 * @type {string}
	 */
	hoveredItemType: Config
		.string()
		.value(''),

	/**
	 * Image selector url
	 * @default undefined
	 * @review
	 * @type {string}
	 */
	imageSelectorURL: Config
		.string()
		.value(''),

	/**
	 * Currently selected language id.
	 * @default ''
	 * @review
	 * @type {string}
	 */
	languageId: Config
		.string()
		.value(''),

	/**
	 * Last date when the autosave has been executed.
	 * @default ''
	 * @review
	 * @type {string}
	 */
	lastSaveDate: Config
		.string()
		.value(''),

	/**
	 * Data associated to the layout
	 * @default {structure: []}
	 * @review
	 * @type {{structure: Array}}
	 */
	layoutData: Config
		.shapeOf(
			{
				nextColumnId: Config.number(),
				nextRowId: Config.number(),
				structure: Config.arrayOf(
					Config.shapeOf(
						{
							columns: Config.arrayOf(
								Config.shapeOf(
									{
										columnId: Config.string(),
										fragmentEntryLinkIds: Config.arrayOf(
											Config.string()
										),
										size: Config.string().value('')
									}
								)
							),
							rowId: Config.string()
						}
					)
				)
			}
		)
		.value(
			{
				nextColumnId: 0,
				nextRowId: 0,
				structure: []
			}
		),

	/**
	 * Portlet namespace needed for prefixing form inputs
	 * @default ''
	 * @review
	 * @type {string}
	 */
	portletNamespace: Config
		.string()
		.value(''),

	/**
	 * @default ''
	 * @review
	 * @type {!string}
	 */
	publishLayoutPageTemplateEntryURL: Config
		.string()
		.value(''),

	/**
	 * @default ''
	 * @review
	 * @type {!string}
	 */
	redirectURL: Config
		.string()
		.value(''),

	/**
	 * URL for getting a fragment content.
	 * @default ''
	 * @review
	 * @type {string}
	 */
	renderFragmentEntryURL: Config
		.string()
		.value(''),

	/**
	 * When true, it indicates that are changes pending to save.
	 * @default false
	 * @review
	 * @type {boolean}
	 */
	savingChanges: Config
		.bool()
		.value(false),

	/**
	 * Currently selected segment id.
	 * @default ''
	 * @review
	 * @type {string}
	 */
	segmentId: Config
		.string()
		.value(''),

	/**
	 * Editable type of the field that is being mapped
	 * @default ''
	 * @review
	 * @type {string}
	 */
	selectMappingDialogEditableType: Config
		.string()
		.value(''),

	/**
	 * EditableId of the field that is being mapped
	 * @default ''
	 * @review
	 * @type {string}
	 */
	selectMappingDialogEditableId: Config
		.string()
		.value(''),

	/**
	 * FragmentEntryLinkId of the field that is being mapped
	 * @default ''
	 * @review
	 * @type {string}
	 */
	selectMappingDialogFragmentEntryLinkId: Config
		.string()
		.value(''),

	/**
	 * Mapped field ID of the field that is being mapped
	 * @default ''
	 * @review
	 * @type {string}
	 */
	selectMappingDialogMappedFieldId: Config
		.string()
		.value(''),

	/**
	 * Flag indicating if the SelectMappingDialog should be shown
	 * @default false
	 * @review
	 * @type {boolean}
	 */
	selectMappingDialogVisible: Config
		.bool()
		.value(false),

	/**
	 * Flag indicating if the SelectMappingTypeDialog should be shown
	 * @default false
	 * @review
	 * @type {boolean}
	 */
	selectMappingTypeDialogVisible: Config
		.bool()
		.value(false),

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
	selectedMappingTypes: Config
		.shapeOf(
			{
				subtype: Config.shapeOf(
					{
						id: Config.string().required(),
						label: Config.string().required()
					}
				),
				type: Config.shapeOf(
					{
						id: Config.string().required(),
						label: Config.string().required()
					}
				)
			}
		)
		.value({}),

	/**
	 * Path of the available icons.
	 * @default ''
	 * @review
	 * @type {string}
	 */
	spritemap: Config
		.string()
		.value(''),

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
	 *     name: !string
	 *   }>,
	 *   name: !string
	 * }>}
	 */
	sections: Config
		.arrayOf(
			Config.shapeOf(
				{
					fragmentCollectionId: Config.string().required(),
					fragmentEntries: Config.arrayOf(
						Config.shapeOf(
							{
								fragmentEntryKey: Config.string().required(),
								imagePreviewURL: Config.string(),
								name: Config.string().required()
							}
						).required()
					).required(),
					name: Config.string().required()
				}
			)
		)
		.value([]),

	/**
	 * List of sidebar panels
	 * @default []
	 * @review
	 * @type {object[]}
	 */
	sidebarPanels: Config
		.arrayOf(
			Config.shapeOf(
				{
					icon: Config.string(),
					label: Config.string(),
					sidebarPanelId: Config.string()
				}
			)
		)
		.value([]),

	/**
	 * @default ''
	 * @review
	 * @type {!string}
	 */
	status: Config
		.string()
		.value(''),

	/**
	 * @default []
	 * @review
	 * @type {Array}
	 */
	themeColorsCssClasses: Config
		.arrayOf(
			Config.string()
		)
		.value([]),

	/**
	 * Translation status
	 * @default {languageValues: [], translationKeys: []}
	 * @review
	 * @type {{languageValues: object[], translationKeys: string[]}}
	 */
	translationStatus: Config
		.shapeOf(
			{
				languageValues: Config.arrayOf(
					Config.shapeOf(
						{
							languageId: Config.string(),
							values: Config.arrayOf(
								Config.string()
							)
						}
					)
				),
				translationKeys: Config.arrayOf(
					Config.string()
				)
			}
		)
		.value(
			{
				languageValues: [],
				translationKeys: []
			}
		),

	/**
	 * URL for updating layout data.
	 * @default ''
	 * @review
	 * @type {string}
	 */
	updateLayoutPageTemplateDataURL: Config
		.string()
		.value(''),

	/**
	 * URL for updating the asset type associated to a template.
	 * @default ''
	 * @review
	 * @type {string}
	 */
	updateLayoutPageTemplateEntryAssetTypeURL: Config
		.string()
		.value(''),

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
	widgets: Config
		.arrayOf(
			Config.shapeOf(
				{
					categories: Config.array(),
					path: Config.string().required(),
					portlets: Config.arrayOf(
						Config.shapeOf(
							{
								instanceable: Config.bool().required(),
								portletId: Config.string().required(),
								title: Config.string().required(),
								used: Config.bool().required()
							}
						).required()
					),
					title: Config.string().required()
				}
			)
		)
		.value([])

};

/**
 * Initial default state
 * @review
 * @type {object}
 */
const DEFAULT_INITIAL_STATE = Object.keys(INITIAL_STATE)
	.reduce(
		(accumulatedState, key) => {
			let newAccumulatgedState = accumulatedState;
			if (INITIAL_STATE[key].config) {
				newAccumulatgedState = Object.assign(
					accumulatedState,
					{
						[key]: INITIAL_STATE[key].config.value
					}
				);
			}
			return newAccumulatgedState;
		},
		{}
	);

export {INITIAL_STATE, DEFAULT_INITIAL_STATE};
export default INITIAL_STATE;