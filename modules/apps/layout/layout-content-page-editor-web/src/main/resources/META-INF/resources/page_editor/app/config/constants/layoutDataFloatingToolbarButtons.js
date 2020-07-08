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

/**
 * FloatingToolbar panels
 */
export const LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS = {
	collectionConfiguration: {
		icon: 'cog',
		id: 'collection_configuration',
		panelId: 'collection_configuration',
		title: Liferay.Language.get('collection-display-configuration'),
		type: 'panel',
	},

	containerLink: {
		icon: 'link',
		id: 'container_link',
		panelId: 'container_link',
		title: Liferay.Language.get('link'),
		type: 'panel',
	},

	containerStyles: {
		icon: 'format',
		id: 'container_configuration',
		panelId: 'container_configuration',
		title: Liferay.Language.get('container-styles'),
		type: 'panel',
	},

	fragmentBackgroundImage: {
		icon: 'pencil',
		id: 'fragment_background_image',
		panelId: 'fragment_background_image',
		title: Liferay.Language.get('fragment-background-image'),
		type: 'panel',
	},

	fragmentConfiguration: {
		icon: 'cog',
		id: 'fragment_configuration',
		panelId: 'fragment_configuration',
		title: Liferay.Language.get('fragment-configuration'),
		type: 'panel',
	},

	fragmentStyles: {
		icon: 'format',
		id: 'fragment_styles',
		panelId: 'fragment_styles',
		title: Liferay.Language.get('fragment-styles'),
		type: 'panel',
	},

	rowConfiguration: {
		icon: 'cog',
		id: 'row_configuration',
		panelId: 'row_configuration',
		title: Liferay.Language.get('grid-configuration'),
		type: 'panel',
	},

	rowStyles: {
		icon: 'format',
		id: 'row_styles',
		panelId: 'row_styles',
		title: Liferay.Language.get('grid-styles'),
		type: 'panel',
	},
};
