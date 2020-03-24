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

import {cleanup, render} from '@testing-library/react';

import {ClayColorPickerWithState} from '../../../src/main/resources/META-INF/resources/ColorPicker/ReactColorPickerAdapter.es';

import '@testing-library/jest-dom/extend-expect';
import React from 'react';

const name = 'ReactColorPickerAdapter';
const spritemap = 'icons.svg';

describe('Field Color Picker', () => {
	afterEach(cleanup);

	it('renders with basic color', async () => {
		const color = '#FF67AA';

		const {container} = render(
			<ClayColorPickerWithState
				{...{
					inputValue: color,
					name,
					readOnly: true,
					spritemap,
				}}
			></ClayColorPickerWithState>
		);

		expect(container.querySelector('input').value).toBe(color);
	});
});
