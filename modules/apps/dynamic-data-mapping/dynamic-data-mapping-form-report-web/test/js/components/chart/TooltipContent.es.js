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
import React from 'react';

import TooltipContent from '../../../../src/main/resources/META-INF/resources/js/components/chart/TooltipContent.es';

describe('Tooltip', () => {
	afterEach(cleanup);

	it('renders label, number of entries and percentage', () => {
		const {container} = render(
			<TooltipContent
				active={true}
				activeIndex={0}
				payload={[
					{
						name: 'count',
						payload: {count: 2, label: 'label1'},
						value: 2,
					},
				]}
				showHeader={false}
				totalEntries={2}
			/>
		);

		const tooltipLabel = container.querySelector('#tooltip-label')
			.innerHTML;

		expect(tooltipLabel).toBe('label1: 2 entries <b>(100%)</b>');
	});
});
