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

import '@testing-library/jest-dom/extend-expect';
import {render, screen} from '@testing-library/react';
import React from 'react';

import BasePage from '../BasePage';

const ChildComponent = () => <p>I am a child component</p>;

const FooterChildComponent = () => <p>I am a footer child component</p>;

describe('BasePage', () => {
	it('renders BasePage component without crashing', () => {
		render(
			<BasePage description="test description" title="test title">
				<ChildComponent />
			</BasePage>
		);

		expect(screen.getByText(/test title/i)).toBeInTheDocument();

		expect(screen.getByText(/test description/i)).toBeInTheDocument();

		expect(screen.getByText(/I am a child component/i)).toBeInTheDocument();
	});

	it('renders BasePageFooter component without crashing', () => {
		render(
			<BasePage.Footer>
				<FooterChildComponent />
			</BasePage.Footer>
		);

		expect(
			screen.getByText(/I am a footer child component/i)
		).toBeInTheDocument();
	});
});
