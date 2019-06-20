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

import ClaySpinner from 'components/shared/ClaySpinner.es';
import React from 'react';
import {cleanup, render} from 'react-testing-library';

const SPINNER_TESTID = 'spinner';

describe('ClaySpinner', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(<ClaySpinner loading />);

		expect(container.firstChild).toMatchSnapshot();
	});

	it('should render a small spinner', () => {
		const {getByTestId} = render(<ClaySpinner loading size='sm' />);

		const spinnerElement = getByTestId(SPINNER_TESTID);

		const classes = spinnerElement.classList;

		expect(classes.contains('loading-animation-sm')).toBeTruthy();
	});

	it('should not render if the spinner is not loading', () => {
		const {queryByTestId} = render(<ClaySpinner loading={false} />);

		const spinnerElement = queryByTestId(SPINNER_TESTID);

		expect(spinnerElement).toBeNull();
	});
});
