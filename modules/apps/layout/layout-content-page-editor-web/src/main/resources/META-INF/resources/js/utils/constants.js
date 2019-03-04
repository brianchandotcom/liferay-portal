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
 * Available editable field config keys
 */
export const EDITABLE_FIELD_CONFIG_KEYS = {
	textAlignment: 'textAlignment',
	textColor: 'textColor',
	textStyle: 'textStyle'
};

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