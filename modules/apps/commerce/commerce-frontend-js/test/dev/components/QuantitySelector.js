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

import launcher from '../../../src/main/resources/META-INF/resources/components/quantity_selector/entry';

import '../../../src/main/resources/META-INF/resources/styles/main.scss';

launcher('quantity-selector', 'quantity-selector', {
	onUpdate: (quantity) =>
		new Promise((resolve) => {
			setTimeout(() => {
				resolve(quantity);
			}, 0);
		}),
	spritemap: './assets/icons.svg',
});
