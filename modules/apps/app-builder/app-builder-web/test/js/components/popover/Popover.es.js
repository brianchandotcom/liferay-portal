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
import {align} from 'frontend-js-web';
import React from 'react';

import Popover from '../../../../src/main/resources/META-INF/resources/js/components/popover/Popover.es';

const getTitle = () => <h1>Title</h1>;
const getContent = () => <p>Content</p>;
const getFooter = () => <span>Footer</span>;

const alignMock = {
	align,
};

describe('Popover', () => {
	afterEach(() => {
		cleanup();
		jest.restoreAllMocks();
	});

	it('renders popover on top', () => {
		jest.spyOn(alignMock, 'align').mockImplementation(() => 0);

		const {container, queryByText} = render(
			<Popover
				ref={React.createRef()}
				showArrow
				title={getTitle}
				visible
			/>
		);

		expect(queryByText('Title')).toBeTruthy();

		expect(container.querySelector('div.arrow')).toBeTruthy();
		expect(container.querySelector('.clay-popover-top')).toBeTruthy();
		expect(container.querySelector('.no-content')).toBeTruthy();
		expect(container.querySelector('.popover-body')).toBeTruthy();
	});

	it('renders popover on the right with children', () => {
		jest.spyOn(alignMock, 'align').mockImplementation(() => 2);

		const {container, queryByText} = render(
			<Popover
				content={getContent}
				footer={getFooter}
				ref={React.createRef()}
				showArrow
				suggestedPosition="right"
				title={getTitle}
				visible
			/>
		);

		expect(queryByText('Title')).toBeTruthy();
		expect(queryByText('Content')).toBeTruthy();
		expect(queryByText('Footer')).toBeTruthy();

		expect(container.querySelector('div.arrow')).toBeTruthy();
		expect(container.querySelector('.clay-popover-right')).toBeTruthy();
		expect(container.querySelector('.no-content')).toBeFalsy();
		expect(container.querySelector('.popover-header')).toBeTruthy();
		expect(container.querySelector('.popover-body')).toBeTruthy();
		expect(container.querySelector('.popover-footer')).toBeTruthy();
	});

	it('renders popover with no ref', () => {
		jest.spyOn(alignMock, 'align').mockImplementation(() => 0);

		const {container, queryByText} = render(
			<Popover title={getTitle} visible />
		);

		expect(queryByText('Title')).toBeTruthy();

		expect(container.querySelector('div.arrow')).toBeTruthy();
		expect(container.querySelector('.no-content')).toBeTruthy();
		expect(container.querySelector('.hide')).toBeFalsy();
	});
});
