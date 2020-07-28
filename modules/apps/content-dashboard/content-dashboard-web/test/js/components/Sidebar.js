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

const ControlledSidebar = ({open}) => {
	const [isOpen, setIsOpen] = useState(open);

	return (
		<Sidebar onClose={() => setIsOpen(false)} open={isOpen}>
			<Sidebar.Header subtitle={'Subtitle'} title={'Title'} />
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

	it('renders sidebar open', () => {
		render(<Sidebar />);

		act(() => {
			jest.runAllTimers();
		});

		expect(document.body).toHaveAttribute('class', 'sidebar-open');
	});

	it('renders sidebar close', () => {
		render(<Sidebar open={false} />);

		act(() => {
			jest.runAllTimers();
		});

		expect(document.body).not.toHaveAttribute('class', 'sidebar-open');
	});

	it('renders sidebar closing', () => {
		const {getByRole, getByText} = render(
			<ControlledSidebar open={true} />
		);

		act(() => {
			jest.runAllTimers();
		});

		expect(document.body).toHaveAttribute('class', 'sidebar-open');

		expect(getByText('Subtitle')).toBeInTheDocument();
		expect(getByText('Title')).toBeInTheDocument();

		const closeIcon = getByRole('presentation');

		expect(closeIcon).toBeInTheDocument();

		userEvent.click(closeIcon);

		act(() => {
			jest.runAllTimers();
		});

		expect(document.body).not.toHaveAttribute('class', 'sidebar-open');
	});
});
