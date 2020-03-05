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
import {Align} from 'metal-position';
import React from 'react';

import Popover from '../../../../src/main/resources/META-INF/resources/js/components/popover/Popover.es';

const getTitle = () => <h1>Title</h1>;
const getContent = () => <p>Content</p>;
const getFooter = () => <span>Footer</span>;

describe('Popover', () => {
	afterEach(() => {
		cleanup();
		jest.restoreAllMocks();
	});

	it('renders popover on top', () => {
		jest.spyOn(Align, 'align').mockImplementation(() => 0);

		const {container, queryByText} = render(
			<Popover
				ref={React.createRef()}
				showArrow
				title={getTitle}
				visible
			/>
		);

		expect(queryByText('Title')).not.toBeNull();

		expect(container.querySelector('div.arrow')).not.toBeNull();
		expect(container.querySelector('.clay-popover-top')).not.toBeNull();
		expect(container.querySelector('.no-content')).not.toBeNull();
		expect(container.querySelector('.popover-body')).not.toBeNull();
	});

	it('renders popover on the right with children', () => {
		jest.spyOn(Align, 'align').mockImplementation(() => 2);

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

		expect(queryByText('Title')).not.toBeNull();
		expect(queryByText('Content')).not.toBeNull();
		expect(queryByText('Footer')).not.toBeNull();

		expect(container.querySelector('div.arrow')).not.toBeNull();
		expect(container.querySelector('.clay-popover-right')).not.toBeNull();
		expect(container.querySelector('.no-content')).toBeNull();
		expect(container.querySelector('.popover-header')).not.toBeNull();
		expect(container.querySelector('.popover-body')).not.toBeNull();
		expect(container.querySelector('.popover-footer')).not.toBeNull();
	});

	it('renders popover with no ref', () => {
		jest.spyOn(Align, 'align').mockImplementation(() => 0);

		const {container, queryByText} = render(
			<Popover title={getTitle} visible />
		);

		expect(queryByText('Title')).not.toBeNull();

		expect(container.querySelector('div.arrow')).not.toBeNull();
		expect(container.querySelector('.no-content')).not.toBeNull();
		expect(container.querySelector('.hide')).toBeNull();
	});
});
