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
	containerConfiguration: {
		icon: 'cog',
		id: 'container_configuration',
		panelId: 'container_configuration',
		title: Liferay.Language.get('container-configuration'),
		type: 'panel'
	},

	duplicateItem: {
		icon: 'paste',
		id: 'duplicate_item',
		panelId: '',
		title: Liferay.Language.get('duplicate'),
		type: 'panel'
	},

	fragmentBackgroundImage: {
		icon: 'pencil',
		id: 'fragment_background_image',
		panelId: 'fragment_background_image',
		title: Liferay.Language.get('fragment-background-image'),
		type: 'panel'
	},

	fragmentConfiguration: {
		icon: 'cog',
		id: 'fragment_configuration',
		panelId: 'fragment_configuration',
		title: Liferay.Language.get('fragment-configuration'),
		type: 'panel'
	},

	rowConfiguration: {
		icon: 'cog',
		id: 'row_configuration',
		panelId: 'row_configuration',
		title: Liferay.Language.get('row-configuration'),
		type: 'panel'
	},

	saveComposition: {
		icon: 'download',
		id: 'save_composition',
		panelId: 'save_composition',
		title: Liferay.Language.get('save-as-fragment'),
		type: 'panel'
	}
};
