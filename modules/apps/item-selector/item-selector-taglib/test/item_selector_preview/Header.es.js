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
	handleClickClose: jest.fn(),
	headerTitle,
	showInfoIcon: true
};

describe('Header', () => {
	afterEach(cleanup);

	it('renders the Header component as a navigation bar', () => {
		const {container} = render(<Header {...headerProps} />);

		expect(container.firstChild.classList[0]).toEqual('navbar');
		expect(container.firstChild.classList).toContain(
			'navigation-bar-light'
		);
	});

	it('renders two navigation bars inside the first one', () => {
		const {container} = render(<Header {...headerProps} />);

		expect(container.firstChild.querySelectorAll('nav').length).toBe(2);
	});

	it('renders the back button with "angle-left" icon and the header title on the first nav item', () => {
		const {container} = render(<Header {...headerProps} />);

		const firstNavElement = container.querySelector('nav');

		const iconElement = firstNavElement.querySelector('.lexicon-icon');

		expect([...iconElement.classList]).toContain('lexicon-icon-angle-left');

		const titleElement = firstNavElement.getElementsByTagName('strong')[0];

		expect(titleElement.innerHTML).toEqual(headerTitle);
	});

	it('calls to handleClickClose when click on back button', () => {
		const {container} = render(<Header {...headerProps} />);

		const iconBack = container.querySelector('.lexicon-icon-angle-left');

		fireEvent.click(iconBack.parentElement);

		expect(headerProps.handleClickClose).toHaveBeenCalled();
	});

	it('renders the "Add" button on the second nav item with class "btn-primary"', () => {
		const {container} = render(<Header {...headerProps} />);

		const secondNavElement = container.querySelectorAll('nav')[1];

		const buttonElement = secondNavElement.querySelector('.btn-primary');

		expect(buttonElement).not.toBeNull();
		expect(buttonElement.innerHTML).toEqual('add');
	});

	it('calls to handleClickAdd when click on the Add button', () => {
		const {getByText} = render(<Header {...headerProps} />);

		getByText('add').click();

		expect(headerProps.handleClickClose).toHaveBeenCalled();
	});

	it('renders the "info-panel-open" icon when "showInfoIcon" prop is set to true', () => {
		const {container} = render(<Header {...headerProps} />);

		const infoIcon = container.querySelector(
			'.lexicon-icon-info-panel-open'
		);

		expect(infoIcon).not.toBeNull();

		const infoIconClasses = [...infoIcon.parentElement.classList];

		expect(infoIconClasses).toContain('btn-outline-secondary');
		expect(infoIconClasses).toContain('btn-outline-borderless');
	});

	it('does not render the "info-panel-open" icon when "showInfoIcon" prop is set to false', () => {
		const props2 = {...headerProps, showInfoIcon: false};

		const {container} = render(<Header {...props2} />);

		const infoIconClasses = container.querySelector(
			'.lexicon-icon-info-panel-open'
		);

		expect(infoIconClasses).toBeNull();
	});

	it('does not render the "icon-pencil" icon when "showEditIcon" prop is set to false', () => {
		const {container} = render(<Header {...headerProps} />);

		const editIcon = container.querySelector('.lexicon-icon-pencil');

		expect(editIcon).toBeNull();
	});

	it('renders the "icon-pencil" icon when "showEditIcon" prop is set to true', () => {
		const props2 = {...headerProps, showEditIcon: true};

		const {container} = render(<Header {...props2} />);

		const editIcon = container.querySelector('.lexicon-icon-pencil');

		expect(editIcon).not.toBeNull();
	});

	it('calls to handleClickEdit when click on edit icon', () => {
		const props2 = {
			...headerProps,
			handleClickEdit: jest.fn(),
			showEditIcon: true
		};

		const {container} = render(<Header {...props2} />);

		container.querySelector('.lexicon-icon-pencil').parentElement.click();

		expect(props2.handleClickEdit).toHaveBeenCalled();
	});
});
