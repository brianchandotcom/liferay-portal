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

import ClayButton from 'components/shared/ClayButton.es';
import React from 'react';
import {cleanup, render} from 'react-testing-library';

describe('ClayButton', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {asFragment} = render(<ClayButton />);

		expect(asFragment()).toMatchSnapshot();
	});

	it('should render with a label', () => {
		const {container} = render(<ClayButton label='test' />);

		const button = container.querySelector('button');

		expect(button.textContent).toEqual('test');
	});
});
