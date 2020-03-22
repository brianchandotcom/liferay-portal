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

import {
	cleanup,
	fireEvent,
	render,
	waitForElement,
} from '@testing-library/react';
import React from 'react';
import {act} from 'react-dom/test-utils';

import DestinationURLInput from '../src/main/resources/META-INF/resources/js/DestinationURLInput.es';

const defaultProps = {
	namespace: '_portlet_namespace_',
};

const renderComponent = (props = defaultProps) =>
	render(<DestinationURLInput {...props} />);

describe('DestinationURLInput', () => {
	afterEach(cleanup);

	it('renders an input element', () => {
		const {getByLabelText} = renderComponent();

		expect(getByLabelText('destination-url'));
	});

	it('does not fetchs for external urls', async () => {
		const {getByLabelText} = renderComponent();

		fetch.mockResponse({});

		await act(async () => {
			const inputElement = await waitForElement(() =>
				getByLabelText('destination-url')
			);
			inputElement.value = 'http://www.test.com';

			fireEvent.blur(inputElement);
		});

		expect(fetch).toHaveBeenCalledTimes(0);
	});

	it('fetchs only for internal urls', async () => {
		const {getByLabelText, getByText} = renderComponent();

		fetch.mockResponse({});

		await act(async () => {
			const inputElement = await waitForElement(() =>
				getByLabelText('destination-url')
			);
			inputElement.value = window.location.hostname + '/url';

			fireEvent.blur(inputElement);
		});

		expect(fetch).toHaveBeenCalledTimes(1);
		expect(getByText('working-url'));
	});
});
