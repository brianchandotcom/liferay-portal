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

import {act, cleanup, render} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React, {useState} from 'react';

import Sidebar from '../../../src/main/resources/META-INF/resources/js/components/Sidebar';

import '@testing-library/jest-dom/extend-expect';

const ControlledSidebar = ({open, title = ''}) => {
	const [isOpen, setIsOpen] = useState(open);

	return (
		<Sidebar onClose={() => setIsOpen(false)} open={isOpen}>
			<Sidebar.Header title={title} />
		</Sidebar>
	);
};

describe('Sidebar', () => {
	beforeEach(() => {
		jest.useFakeTimers();
	});

	afterEach(() => {
		jest.clearAllTimers();
		cleanup();
	});

	it('renders an open sidebar', () => {
		const {container} = render(<Sidebar />);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
		expect(document.body).toHaveAttribute('class', 'sidebar-open');
	});

	it('renders a closed sidebar', () => {
		const {container} = render(<Sidebar open={false} />);

		act(() => {
			jest.runAllTimers();
		});

		expect(container).toMatchSnapshot();
		expect(document.body).not.toHaveAttribute('class', 'sidebar-open');
	});

	it('renders a sidebar with a header with title and subtitle', () => {
		const {container, getByText} = render(
			<Sidebar>
				<Sidebar.Header title="Title" />
			</Sidebar>
		);

		expect(container).toMatchSnapshot();

		expect(getByText('Title')).toBeInTheDocument();
	});

	it('renders a sidebar with body with custom content', () => {
		const {container, getByText} = render(
			<Sidebar>
				<Sidebar.Body>
					<div>Custom content text</div>
				</Sidebar.Body>
			</Sidebar>
		);

		const customChildren = getByText('Custom content text');

		expect(container).toMatchSnapshot();
		expect(customChildren).toBeInTheDocument();
		expect(customChildren.parentNode).toHaveAttribute(
			'class',
			'sidebar-body'
		);
	});

	it('renders an open sidebar with header and close it on close button click', () => {
		const {getByRole} = render(<ControlledSidebar open={true} />);

		act(() => {
			jest.runAllTimers();
		});

		expect(document.body).toHaveAttribute('class', 'sidebar-open');

		const closeIcon = getByRole('presentation');

		expect(closeIcon).toBeInTheDocument();

		userEvent.click(closeIcon);

		act(() => {
			jest.runAllTimers();
		});

		expect(document.body).not.toHaveAttribute('class', 'sidebar-open');
	});
});
