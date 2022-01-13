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

import '@testing-library/jest-dom/extend-expect';

import CategoriesPopover from '../../../src/main/resources/META-INF/resources/js/components/CategoriesPopover';

const mockProps = {
	categories: ['Teenagers', 'Young adults', 'Adults', 'Children'],
	vocabulary: 'Public',
};

describe('CategoriesPopover', () => {
	beforeEach(() => {
		cleanup();
	});

	it('renders only the button with a "+" and the numbers of categories it will show in a Popover, but not Popover is shown yet', () => {
		const {getByText, queryByText} = render(
			<CategoriesPopover {...mockProps} />
		);

		expect(getByText('+ 4')).toBeInTheDocument();
		expect(queryByText('4 Public categories')).not.toBeInTheDocument();
		expect(queryByText('Teenagers')).not.toBeInTheDocument();
	});

	it('renders the Popover with a title and all the categories when clicking the button', () => {
		const {container, getByText} = render(
			<CategoriesPopover {...mockProps} />
		);

		fireEvent.click(container.querySelector('.category-label-see-more'));
		expect(getByText('+ 4')).toBeInTheDocument();
		expect(getByText('4 Public categories')).toBeInTheDocument();
		expect(getByText('Teenagers')).toBeInTheDocument();
		expect(getByText('Young adults')).toBeInTheDocument();
		expect(getByText('Adults')).toBeInTheDocument();
		expect(getByText('Children')).toBeInTheDocument();
	});

	it('hides the Popover when clicking in the button again', () => {
		const {container, getByText, queryByText} = render(
			<CategoriesPopover {...mockProps} />
		);

		fireEvent.click(container.querySelector('.category-label-see-more'));
		expect(getByText('4 Public categories')).toBeInTheDocument();
		expect(getByText('Teenagers')).toBeInTheDocument();

		fireEvent.click(container.querySelector('.category-label-see-more'));
		expect(queryByText('4 Public categories')).not.toBeInTheDocument();
		expect(queryByText('Teenagers')).not.toBeInTheDocument();
	});

	it('hides the Popover when clicking outside', () => {
		const {container, getByText, queryByText} = render(
			<>
				<CategoriesPopover {...mockProps} />
				<button>Other place to click</button>
			</>
		);

		fireEvent.click(container.querySelector('.category-label-see-more'));
		expect(getByText('4 Public categories')).toBeInTheDocument();
		expect(getByText('Teenagers')).toBeInTheDocument();

		fireEvent.mouseDown(
			container.querySelector(
				':not(.categories-popover):not(.category-label-see-more)'
			)
		);

		expect(queryByText('4 Public categories')).not.toBeInTheDocument();

		expect(queryByText('Teenagers')).not.toBeInTheDocument();
	});
});
