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

import VerticalNavigationBar from '../../../../src/main/resources/META-INF/resources/admin/js/components/VerticalBar';

const FOLDERS_AND_ARTICLES_TITLE = 'Folders and articles';
const TEMPLATES_TITLE = 'Templates';
const SUGGESTIONS_TITLE = 'Suggestions';

const mockNavigate = jest.fn();

jest.mock('frontend-js-web', () => ({
	navigate: () => mockNavigate,
}));

const defaultProps = {
	items: [
		{
			active: true,
			href: 'article_url',
			icon: 'pages-tree',
			key: 'article',
			navigationItems: [],
			title: FOLDERS_AND_ARTICLES_TITLE,
		},
		{
			active: false,
			href: 'template_url',
			icon: 'page-template',
			key: 'template',
			title: TEMPLATES_TITLE,
		},
		{
			active: false,
			href: 'suggestions_url',
			icon: 'message',
			key: 'suggestion',
			title: SUGGESTIONS_TITLE,
		},
	],
	parentContainerId: 'parentContainerId',
	productMenuOpen: true,
};

const renderComponent = (props = defaultProps) =>
	render(<VerticalNavigationBar {...props} />);

describe('VerticalBar', () => {
	afterEach(() => {
		jest.clearAllMocks();
	});

	beforeEach(() => {
		cleanup();

		const productMenuOn = {
			removeListener: jest.fn(),
		};

		const productMenu = {
			hide: jest.fn(),
			on: () => productMenuOn,
		};

		global.Liferay.SideNavigation = {instance: () => productMenu};

		global.document.body.innerHTML = `<div id="parentContainerId"></div>`;
	});

	it('renders three navigation items', () => {
		const {getAllByRole, getByTitle} = renderComponent();

		expect(getAllByRole('button').length).toBe(3);

		expect(getByTitle(FOLDERS_AND_ARTICLES_TITLE)).toBeInTheDocument();
		expect(getByTitle(TEMPLATES_TITLE)).toBeInTheDocument();
		expect(getByTitle(SUGGESTIONS_TITLE)).toBeInTheDocument();
	});

	it('does not render the panel if the product menu is open', () => {
		const {container} = renderComponent();

		expect(container.querySelector('.sidebar')).not.toBeInTheDocument();
	});

	it('renders the panel if the product menu is closed', () => {
		const {container} = renderComponent({
			...defaultProps,
			productMenuOpen: false,
		});

		expect(container.querySelector('.sidebar')).toBeInTheDocument();
	});

	it('does not navigate if user clicks on the current panel icon', () => {
		const {getAllByRole} = renderComponent();

		const foldersAndArticlesButton = getAllByRole('button')[0];

		fireEvent.click(foldersAndArticlesButton);

		expect(mockNavigate).toHaveBeenCalledTimes(0);
	});

	it('opens the panel if user clicks on the current panel icon', () => {
		const {container, getAllByRole} = renderComponent();

		const foldersAndArticlesButton = getAllByRole('button')[0];

		fireEvent.click(foldersAndArticlesButton);

		expect(container.querySelector('.sidebar')).toBeInTheDocument();
	});
});
