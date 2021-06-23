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

import launcher from '../../../src/main/resources/META-INF/resources/components/dropdown/entry';

import '../../../src/main/resources/META-INF/resources/styles/main.scss';

const props = {
	items: [
		{
			href: '/side-panel/delete',
			icon: 'plus',
			label: 'Delete',
		},
		{
			href: '/side-panel/email',
			icon: 'plus',
			label: 'Add',
			order: 20,
			target: 'modal',
		},
		{
			href: '/test',
			icon: 'view',
			label: 'View',
			order: 1,
		},
	],
	spritemap: './assets/clay/icons.svg',
};

launcher('dropdownId', 'dropdown-root-id', props);
