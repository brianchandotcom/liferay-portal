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

import Legend from '../../../../../../src/main/resources/META-INF/resources/js/custom/form-report/components/chart/Legend';

const data = [
	{count: 1, label: 'label1'},
	{count: 2, label: 'label2'},
	{count: 3, label: 'label3'},
	{count: 4, label: 'label4'},
	{count: 5, label: 'label5'},
	{count: 6, label: 'label6'},
	{count: 7, label: 'label7'},
	{count: 8, label: 'label8'},
	{count: 9, label: 'label9'},
	{count: 10, label: 'label10'},
	{count: 11, label: 'label11'},
];

const props = {
	activeIndex: null,
	data: [
		{count: 1, label: 'label1'},
		{count: 2, label: 'label2'},
	],
};

describe('Legend', () => {
	afterEach(cleanup);

	it('dims all labels except one that is being hovered', () => {
		const {container} = render(<Legend {...props} activeIndex={0} />);

		expect(container.querySelectorAll('li')[0].className).toBe('');
		expect(container.querySelectorAll('li')[1].className).toBe('dim');
	});

	it('displays showAll button when there are more than 10 items', () => {
		const {queryByText} = render(<Legend {...props} data={data} />);

		expect(queryByText('show-all')).toBeTruthy();
	});

	it('displays showLess button when showAll button is clicked', () => {
		const {queryByText} = render(<Legend {...props} data={data} />);

		fireEvent.click(queryByText('show-all'));

		expect(queryByText('show-less')).toBeTruthy();
	});
});
