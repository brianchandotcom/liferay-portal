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
 * Available values for containerType config key
 */
export const NUMBER_OF_COLUMNS_OPTIONS = [
	{
		label: '1',
		numberOfColumnId: '1'
	},
	{
		label: '2',
		numberOfColumnId: '2'
	},
	{
		label: '3',
		numberOfColumnId: '3'
	},
	{
		label: '4',
		numberOfColumnId: '4'
	},
	{
		label: '5',
		numberOfColumnId: '5'
	},
	{
		label: '6',
		numberOfColumnId: '6'
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
		'textarea'
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
		'textarea'
	],

	'text': [
		'ddm-date',
		'ddm-decimal',
		'ddm-integer',
		'ddm-number',
		'text',
		'textarea'
	]
};

/**
 * Available values for containerType config key
 */
export const CONTAINER_TYPES = [
	{
		containerTypeId: 'fluid',
		label: Liferay.Language.get('fluid')
	},

	{
		containerTypeId: 'fixed',
		label: Liferay.Language.get('fixed-width')
	}
];

/**
 * Available editable field config keys
 */
export const EDITABLE_FIELD_CONFIG_KEYS = {
	imageLink: 'imageLink',
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
		icon: 'format',
		id: 'text_properties',
		panelId: 'text_properties',
		title: Liferay.Language.get('text-properties'),
		type: 'panel'
	}
};

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
	section: 'fragments-editor-section'
};

/**
 * Available element config keys
 */
export const ITEM_CONFIG_KEYS = {
	backgroundColorCssClass: 'backgroundColorCssClass',
	columnSpacing: 'columnSpacing',
	containerType: 'containerType',
	padding: 'padding'
};

/**
 * Max section columns
 */
export const MAX_SECTION_COLUMNS = 12;

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
		label: Liferay.Language.get('regular'),
		textStyleId: ''
	},
	{
		label: Liferay.Language.get('small'),
		textStyleId: 'small'
	},
	{
		label: Liferay.Language.get('Large'),
		textStyleId: 'lead'
	}
];