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

import launcher from '../../../src/main/resources/META-INF/resources/components/step_tracker/entry';

import '../../../src/main/resources/META-INF/resources/styles/main.scss';

launcher('step_tracker', 'step-tracker', {
	spritemap: './assets/clay/icons.svg',
	steps: [
		{
			id: 'received',
			label: 'Received asd asd',
			state: 'completed',
		},
		{
			id: 'confirmed',
			label: 'Confirmed',
			state: 'active',
		},
		{
			id: 'trasmitted',
			label: 'Trasmitted',
			state: 'inactive',
		},
		{
			id: 'shipped',
			label: 'Shipped',
			state: 'inactive',
		},
		{
			id: 'completed',
			label: 'Completed',
			state: 'inactive',
		},
	],
});
