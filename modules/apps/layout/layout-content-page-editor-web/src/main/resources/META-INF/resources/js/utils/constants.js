/**
 * Available values for buttonType config key
 */
export const BUTTON_TYPES = [
	{
		buttonTypeId: 'link',
		label: Liferay.Language.get('link')
	},
	{
		buttonTypeId: 'primary',
		label: Liferay.Language.get('primary-button')
	},
	{
		buttonTypeId: 'secondary',
		label: Liferay.Language.get('secondary-button')
	}
];

/**
 * List of editable types and their compatibilities
 * with the corresponding mappeable types
 * @review
 * @see DDMStructureClassType.java for compatible types
 * @type {!object}
 */

export const COMPATIBLE_TYPES = {
	'html': [
		'ddm-date',
		'ddm-decimal',
		'ddm-integer',
		'ddm-number',
		'ddm-text-html',
		'text',
		'textarea',
		'url'
	],

	'image': [
		'ddm-image',
		'image'
	],

	'rich-text': [
		'ddm-date',
		'ddm-decimal',
		'ddm-integer',
		'ddm-number',
		'ddm-text-html',
		'text',
		'textarea',
		'url'
	],

	'text': [
		'ddm-date',
		'ddm-decimal',
		'ddm-integer',
		'ddm-number',
		'text',
		'textarea',
		'url'
	]
};

/**
 * Available editable field config keys
 */
export const EDITABLE_FIELD_CONFIG_KEYS = {
	imageLink: 'imageLink',
	imageSource: 'imageSource',
	imageTarget: 'imageTarget',
	textAlignment: 'textAlignment',
	textColor: 'textColor',
	textStyle: 'textStyle'
};

/**
 * FloatingToolbar panels
 */
export const FLOATING_TOOLBAR_BUTTONS = {
	backgroundColor: {
		icon: 'color-picker',
		id: 'background_color',
		panelId: 'background_color',
		title: Liferay.Language.get('background-color'),
		type: 'panel'
	},

	backgroundImage: {
		icon: 'picture',
		id: 'background_image',
		panelId: 'background_image',
		title: Liferay.Language.get('background-image'),
		type: 'panel'
	},

	edit: {
		icon: 'pencil',
		id: 'edit',
		title: Liferay.Language.get('edit'),
		type: 'editor'
	},

	imageLink: {
		icon: 'link',
		id: 'image_properties',
		panelId: 'image_properties',
		title: Liferay.Language.get('image-properties'),
		type: 'panel'
	},

	imageProperties: {
		icon: 'pencil',
		id: 'image_properties',
		panelId: 'image_properties',
		title: Liferay.Language.get('image-properties'),
		type: 'panel'
	},

	link: {
		icon: 'link',
		id: 'link',
		panelId: 'link',
		title: Liferay.Language.get('link'),
		type: 'panel'
	},

	map: {
		icon: 'bolt',
		id: 'mapping',
		panelId: 'mapping',
		title: Liferay.Language.get('map'),
		type: 'panel'
	},

	spacing: {
		icon: 'table',
		id: 'spacing',
		panelId: 'spacing',
		title: Liferay.Language.get('spacing'),
		type: 'panel'
	},

	textProperties: {
		icon: 'pencil',
		id: 'text_properties',
		panelId: 'text_properties',
		title: Liferay.Language.get('text-properties'),
		type: 'panel'
	}
};

/**
 * Fragment Entry Link types
 * @review
 * @type {!object}
 */
export const FRAGMENT_ENTRY_LINK_TYPES = {
	component: 'fragment-entry-link-component',
	section: 'fragment-entry-link-section'
};

/**
 * Fragments Editor dragging class
 * @review
 * @type {string}
 */
export const FRAGMENTS_EDITOR_DRAGGING_CLASS = 'dragging';

/**
 * Fragments Editor item borders
 * @review
 * @type {!object}
 */
export const FRAGMENTS_EDITOR_ITEM_BORDERS = {
	bottom: 'fragments-editor-border-bottom',
	top: 'fragments-editor-border-top'
};

/**
 * Fragments Editor item types
 * @review
 * @type {!object}
 */
export const FRAGMENTS_EDITOR_ITEM_TYPES = {
	column: 'fragments-editor-column',
	editable: 'fragments-editor-editable-field',
	fragment: 'fragments-editor-fragment',
	fragmentList: 'fragments-editor-fragment-list',
	row: 'fragments-editor-row'
};

/**
 * Fragments Editor row types
 * @review
 * @type {!object}
 */
export const FRAGMENTS_EDITOR_ROW_TYPES = {
	componentRow: 'fragments-editor-component-row',
	sectionRow: 'fragments-editor-section-row'
};

/**
 * Available attributes for target config key
 */
export const TARGET_TYPES = [
	{
		label: Liferay.Language.get('self'),
		targetTypeId: '_self'
	},
	{
		label: Liferay.Language.get('blank'),
		targetTypeId: '_blank'
	},
	{
		label: Liferay.Language.get('parent'),
		targetTypeId: '_parent'
	},
	{
		label: Liferay.Language.get('top'),
		targetTypeId: '_top'
	}
];

/**
 * Available values for textAlignmentOptions config key
 */
export const TEXT_ALIGNMENT_OPTIONS = [
	{
		label: Liferay.Language.get('left'),
		textAlignmentId: 'left'
	},
	{
		label: Liferay.Language.get('center'),
		textAlignmentId: 'center'
	},
	{
		label: Liferay.Language.get('justify'),
		textAlignmentId: 'justify'
	},
	{
		label: Liferay.Language.get('right'),
		textAlignmentId: 'right'
	}
];

/**
 * Available values for textStyle config key
 */
export const TEXT_STYLES = [
	{
		label: Liferay.Language.get('normal'),
		textStyleId: ''
	},
	{
		label: Liferay.Language.get('small'),
		textStyleId: 'small'
	},
	{
		label: Liferay.Language.get('lead'),
		textStyleId: 'lead'
	},
	{
		label: Liferay.Util.sub(
			Liferay.Language.get('heading-x'),
			'1'
		),
		textStyleId: 'h1'
	},
	{
		label: Liferay.Util.sub(
			Liferay.Language.get('heading-x'),
			'2'
		),
		textStyleId: 'h2'
	},
	{
		label: Liferay.Util.sub(
			Liferay.Language.get('heading-x'),
			'3'
		),
		textStyleId: 'h3'
	},
	{
		label: Liferay.Util.sub(
			Liferay.Language.get('heading-x'),
			'4'
		),
		textStyleId: 'h4'
	}
];