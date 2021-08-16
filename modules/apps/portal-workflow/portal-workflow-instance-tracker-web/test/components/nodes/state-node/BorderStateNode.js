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
import {render} from '@testing-library/react';
import React from 'react';

import BorderStateNode from '../../../../src/main/resources/META-INF/resources/js/components/nodes/state-node/BorderStateNode';

describe('The BorderStateNode component should', () => {
	it('Be rendered with End title as default', () => {
		const {getByText} = render(<BorderStateNode />);

		expect(getByText('end')).toBeTruthy();
	});

	it('Be rendered with Start title when it receives the start prop', () => {
		const {getByText} = render(<BorderStateNode start />);

		expect(getByText('start')).toBeTruthy();
	});
});
