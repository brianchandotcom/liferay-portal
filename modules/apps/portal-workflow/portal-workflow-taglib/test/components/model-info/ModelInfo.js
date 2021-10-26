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

import '@testing-library/jest-dom/extend-expect';
import {cleanup, render} from '@testing-library/react';
import React from 'react';

import ModelInfo from '../../../src/main/resources/META-INF/resources/workflow_status/js/components/model-info/ModelInfo';

describe('The ModelInfo component should', () => {
	let label, value;

	afterEach(cleanup);

	it('render with ID label', () => {
		label = 'ID';
		value = 'Valid ID';

		const {debug, queryByText} = render(
			<ModelInfo label={label} value={value} />
		);

		debug();

		const id = queryByText('ID:');
		const idValue = queryByText('Valid ID');

		expect(id).toBeTruthy();
		expect(idValue).toBeTruthy();
	});

	it('render with Version label', () => {
		label = 'Version';
		value = 'Valid Version';

		const {queryByText} = render(<ModelInfo label={label} value={value} />);

		const version = queryByText(`${label}:`);
		const versionValue = queryByText('Valid Version');

		expect(version).toBeTruthy();
		expect(versionValue).toBeTruthy();
	});
});
