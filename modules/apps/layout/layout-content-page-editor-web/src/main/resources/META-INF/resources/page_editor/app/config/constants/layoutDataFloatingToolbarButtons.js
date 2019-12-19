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
	backgroundColor: {
		icon: 'color-picker',
		id: 'background_color',
		panelId: 'background_color',
		title: Liferay.Language.get('background-color'),
		type: 'panel'
	},

	duplicateFragment: {
		icon: 'paste',
		id: 'duplicate_fragment',
		panelId: '',
		title: Liferay.Language.get('duplicate-fragment'),
		type: 'panel'
	},

	edit: {
		icon: 'pencil',
		id: 'edit',
		title: Liferay.Language.get('edit'),
		type: 'editor'
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

	imageProperties: {
		icon: 'pencil',
		id: 'image_properties',
		panelId: 'image_properties',
		title: Liferay.Language.get('image-properties'),
		type: 'panel'
	},

	layoutBackgroundImage: {
		icon: 'picture',
		id: 'layout_background_image',
		panelId: 'layout_background_image',
		title: Liferay.Language.get('layout-background-image'),
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
	}
};
