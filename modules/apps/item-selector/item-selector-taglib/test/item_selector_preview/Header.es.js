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

import Header from '../../src/main/resources/META-INF/resources/item_selector_preview/js/Header.es';

const headerTitle = 'Images';

const headerProps = {
	handleClickAdd: jest.fn(),
	handleClickBack: jest.fn(),
	headerTitle,
	showInfoIcon: true,
	showNavbar: true,
};

describe('Header', () => {
	afterEach(cleanup);

	it('renders the Header component as a navigation bar', () => {
		const {container} = render(<Header {...headerProps} />);

		expect(container.firstChild.classList).toContain('navbar');
	});

	it('renders the Header component as a light navigation bar', () => {
		const {container} = render(<Header {...headerProps} />);

		expect(container.firstChild.classList).toContain(
			'navigation-bar-light'
		);
	});

	it('renders two navigation bars inside the first one', () => {
		const {container} = render(<Header {...headerProps} />);

		expect(container.firstChild.querySelectorAll('nav').length).toBe(2);
	});

	it('renders the back button with "angle-left" icon on the first nav item', () => {
		const {container} = render(<Header {...headerProps} />);

		const firstNavElement = container.querySelector('nav');

		const iconElement = firstNavElement.querySelector('.lexicon-icon');

		expect([...iconElement.classList]).toContain('lexicon-icon-angle-left');
	});

	it('renders the header title', () => {
		const {getByText} = render(<Header {...headerProps} />);

		expect(getByText(headerTitle));
	});

	it('calls to handleClickBack when click on back button', () => {
		const {container} = render(<Header {...headerProps} />);

		const iconBack = container.querySelector('.lexicon-icon-angle-left');

		fireEvent.click(iconBack.parentElement);

		expect(headerProps.handleClickBack).toHaveBeenCalled();
	});

	it('renders the "Add" button on the second nav item with class "btn-primary"', () => {
		const {container} = render(<Header {...headerProps} />);

		const secondNavElement = container.querySelectorAll('nav')[1];

		const buttonElement = secondNavElement.querySelector('.btn-primary');

		expect(buttonElement).not.toBeNull();
	});

	it('calls to handleClickAdd when click on the Add button', () => {
		const {getByText} = render(<Header {...headerProps} />);

		getByText('add').click();

		expect(headerProps.handleClickAdd).toHaveBeenCalled();
	});

	it('renders the "info-panel-open" icon when "showInfoIcon" prop is set to true', () => {
		const {container} = render(<Header {...headerProps} />);

		const infoIcon = container.querySelector(
			'.lexicon-icon-info-panel-open'
		);

		expect(infoIcon).not.toBeNull();
	});

	it('does not render the "info-panel-open" icon when "showInfoIcon" prop is set to false', () => {
		const props = {...headerProps, showInfoIcon: false};

		const {container} = render(<Header {...props} />);

		const infoIcon = container.querySelector(
			'.lexicon-icon-info-panel-open'
		);

		expect(infoIcon).toBeNull();
	});
});
