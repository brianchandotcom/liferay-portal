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

import Sidebar from '../../../src/main/resources/META-INF/resources/js/components/Sidebar';
import SidebarPanelInfoView from '../../../src/main/resources/META-INF/resources/js/components/SidebarPanelInfoView';

import '@testing-library/jest-dom/extend-expect';

const mockCategories = [
	'Crime',
	'Romantic',
	'Classics',
	'Fantasy',
	'Science Fiction',
	'Advanced',
	'Education',
	'Decision',
];

const mockClassPK = 38070;

const mockCreateDate = '2020-07-27T10:50:55.19';

const mockData = {
	'display-date': {
		title: 'Display Date',
		value: '2020-07-27T10:53:00',
	},
	'expiration-date': {
		title: 'Expiration Date',
		value: '2020-07-28T10:00:00',
	},
	'review-date': {
		title: 'Review Date',
		value: '2020-07-27T14:14:30',
	},
};

const mockModifiedDate = '2020-07-27T10:56:56.027';

const mockSubType = 'Basic Web Content';

const mockTags = ['tag1', 'tag2'];

const mockTitle = 'Basic Web Content Title';

const mockUserName = 'Kate Williams';

const mockVersions = [
	{
		statusLabel: 'Approved',
		statusStyle: 'success',
		version: 1.6,
	},
	{
		statusLabel: 'Draft',
		statusStyle: 'secondary',
		version: 1.7,
	},
];

const mockViewURLs = [
	{
		default: false,
		languageId: 'es-ES',
		viewURL:
			'http://localhost:8080/es-ES/web/guest/-/basic-web-content-title',
	},
	{
		default: false,
		languageId: 'fr-FR',
		viewURL:
			'http://localhost:8080/fr-FR/web/guest/-/basic-web-content-title',
	},
	{
		default: true,
		languageId: 'en-US',
		viewURL:
			'http://localhost:8080/en-US/web/guest/-/basic-web-content-title',
	},
	{
		default: false,
		languageId: 'pt-BR',
		viewURL:
			'http://localhost:8080/pt-BR/web/guest/-/basic-web-content-title',
	},
];

describe('SidebarPanelInfoView', () => {
	afterEach(() => {
		jest.clearAllMocks();
		cleanup();
	});

	it('renders', () => {
		const {asFragment} = render(
			<Sidebar>
				<SidebarPanelInfoView
					categories={mockCategories}
					classPK={mockClassPK}
					createDate={mockCreateDate}
					data={mockData}
					languageTag={'en'}
					modifiedDate={mockModifiedDate}
					subType={mockSubType}
					tags={mockTags}
					title={mockTitle}
					userId={'20126'}
					userName={mockUserName}
					userPortraitURL={''}
					versions={mockVersions}
					viewURLs={mockViewURLs}
				/>
			</Sidebar>
		);

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders sidebar panel with info according to the API', () => {
		const {getByText} = render(
			<Sidebar>
				<SidebarPanelInfoView
					categories={mockCategories}
					classPK={mockClassPK}
					createDate={mockCreateDate}
					data={mockData}
					languageTag={'en'}
					modifiedDate={mockModifiedDate}
					subType={mockSubType}
					tags={mockTags}
					title={mockTitle}
					userId={'20126'}
					userName={mockUserName}
					userPortraitURL={''}
					versions={mockVersions}
					viewURLs={mockViewURLs}
				/>
			</Sidebar>
		);

		expect(getByText('content-info')).toBeInTheDocument();

		expect(getByText('Basic Web Content Title')).toBeInTheDocument();
		expect(getByText('Basic Web Content')).toBeInTheDocument();
		expect(getByText('version 1.6')).toBeInTheDocument();
		expect(getByText('Approved')).toBeInTheDocument();
		expect(getByText('version 1.7')).toBeInTheDocument();
		expect(getByText('Draft')).toBeInTheDocument();

		expect(getByText('details')).toBeInTheDocument();

		expect(getByText('Kate Williams')).toBeInTheDocument();

		expect(getByText('languages-translated-into')).toBeInTheDocument();
		expect(getByText('en-US')).toBeInTheDocument();
		expect(getByText('default')).toBeInTheDocument();
		expect(getByText('es-ES')).toBeInTheDocument();
		expect(getByText('fr-FR')).toBeInTheDocument();
		expect(getByText('pt-BR')).toBeInTheDocument();

		expect(getByText('tags')).toBeInTheDocument();
		expect(getByText('tag1')).toBeInTheDocument();
		expect(getByText('tag2')).toBeInTheDocument();

		expect(getByText('categories')).toBeInTheDocument();
		expect(getByText('Crime')).toBeInTheDocument();
		expect(getByText('Romantic')).toBeInTheDocument();
		expect(getByText('Classics')).toBeInTheDocument();
		expect(getByText('Fantasy')).toBeInTheDocument();
		expect(getByText('Science Fiction')).toBeInTheDocument();
		expect(getByText('Advanced')).toBeInTheDocument();
		expect(getByText('Education')).toBeInTheDocument();
		expect(getByText('Decision')).toBeInTheDocument();

		expect(getByText('display-date')).toBeInTheDocument();
		expect(getByText('Jul 27, 2020, 10:53 AM')).toBeInTheDocument();

		expect(getByText('creation-date')).toBeInTheDocument();
		expect(getByText('Jul 27, 2020, 10:50 AM')).toBeInTheDocument();

		expect(getByText('modified-date')).toBeInTheDocument();
		expect(getByText('Jul 27, 2020, 10:50 AM')).toBeInTheDocument();

		expect(getByText('expiration-date')).toBeInTheDocument();
		expect(getByText('Jul 28, 2020, 10:00 AM')).toBeInTheDocument();

		expect(getByText('review-date')).toBeInTheDocument();
		expect(getByText('Jul 27, 2020, 2:14 PM')).toBeInTheDocument();

		expect(getByText('id')).toBeInTheDocument();
		expect(getByText('38070')).toBeInTheDocument();
	});
});
