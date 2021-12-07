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

const labels = [
	'label1',
	'label2',
	'label3',
	'label4',
	'label5',
	'label6',
	'label7',
	'label8',
	'label9',
	'label10',
	'label11',
];

const props = {
	activeIndex: null,
	labels: ['label1', 'label2'],
};

describe('Legend', () => {
	afterEach(cleanup);

	it('dims all labels except one that is being hovered', () => {
		const {container} = render(<Legend {...props} activeIndex={0} />);

		expect(container.querySelectorAll('li')[0].className).toBe('');
		expect(container.querySelectorAll('li')[1].className).toBe('dim');
	});

	it('displays showAll button when there are more than 10 items', () => {
		const {queryByText} = render(<Legend {...props} labels={labels} />);

		expect(queryByText('show-all')).toBeTruthy();
	});

	it('displays showLess button when showAll button is clicked', () => {
		const {queryByText} = render(<Legend {...props} labels={labels} />);

		fireEvent.click(queryByText('show-all'));

		expect(queryByText('show-less')).toBeTruthy();
	});
});
