/**
 * Available element config keys
 */
export const ITEM_CONFIG_KEYS = {
	containerType: 'containerType'
};

/**
 * Available values for containerType config key
 */
export const CONTAINER_TYPES = {
	fixed: {
		containerTypeId: 'fixed',
		label: Liferay.Language.get('fixed-width')
	},

	fluid: {
		containerTypeId: 'fluid',
		label: Liferay.Language.get('fluid')
	}
};