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
	bottom: 'border-bottom',
	top: 'border-top'
};

/**
 * Fragments Editor item types
 * @review
 * @type {!object}
 */
export const FRAGMENTS_EDITOR_ITEM_TYPES = {
	column: 'fragments-editor-column',
	fragment: 'fragments-editor-fragment',
	fragmentList: 'fragments-editor-fragment-list',
	section: 'fragments-editor-section'
};