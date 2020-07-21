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

import compareCheckboxLauncher from '../../../src/main/resources/META-INF/resources/components/compare_checkbox/entry';
import miniCompareLauncher from '../../../src/main/resources/META-INF/resources/components/mini_compare/entry';

import '../../../src/main/resources/META-INF/resources/styles/main.scss';

miniCompareLauncher('miniCompare', 'mini-compare-root', {
	compareProductsURL: '#',
	editCompareProductActionURL: '#',
	itemsLimit: 4,
	portletNamespace: 'portletNamespace',
	spritemap: './assets/icons.svg'
});

compareCheckboxLauncher('miniCompare1', 'compare-checkbox-root-1', {
	itemId: '001',
	label: 'Compare',
	pictureUrl: 'https://via.placeholder.com/150'
});

compareCheckboxLauncher('miniCompare2', 'compare-checkbox-root-2', {
	itemId: '002',
	label: 'Compare',
	pictureUrl: 'https://via.placeholder.com/150'
});

compareCheckboxLauncher('miniCompare3', 'compare-checkbox-root-3', {
	itemId: '003',
	label: 'Compare',
	pictureUrl: 'https://via.placeholder.com/150'
});

compareCheckboxLauncher('miniCompare4', 'compare-checkbox-root-4', {
	itemId: '004',
	label: 'Compare',
	pictureUrl: 'https://via.placeholder.com/150'
});

compareCheckboxLauncher('miniCompare5', 'compare-checkbox-root-5', {
	itemId: '005',
	label: 'Compare',
	pictureUrl: 'https://via.placeholder.com/150'
});

compareCheckboxLauncher('miniCompare6', 'compare-checkbox-root-6', {
	itemId: '006',
	label: 'Compare',
	pictureUrl: 'https://via.placeholder.com/150'
});
