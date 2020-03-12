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

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';
import {act} from 'react-dom/test-utils';

import RatingsThumbs from '../../src/main/resources/META-INF/resources/js/components/RatingsThumbs.es';

const requiredProps = {
	className: 'className',
	classPK: 'classPK',
	signedIn: true,
	url: 'http://url',
	enabled: true,
};

themeDisplay.getPlid = themeDisplay.getPlid || (() => 'plis');

const renderComponent = props => render(<RatingsThumbs {...props} />);

describe('RatingsThumbs', () => {
	afterEach(cleanup);

	it('renders with the component defaults props', () => {
		const {getAllByRole} = renderComponent(requiredProps);

		const [thumbUpButton, thumbDownButton] = getAllByRole('button');

		expect(thumbUpButton.value).toBe('0');
		expect(thumbDownButton.value).toBe('0');
	});

	it('thumbUp works', async () => {
		fetch.mockResponseOnce(
			JSON.stringify({totalEntries: 1, totalScore: 1})
		);

		const result = renderComponent({
			...requiredProps,
			initialPositiveVotes: 0,
		});
		const {getAllByRole} = result;
		const [thumbUpButton] = getAllByRole('button');

		await act(async () => {
			fireEvent.click(thumbUpButton);
		});


		expect(thumbUpButton.value).toBe('1');
	});
});
