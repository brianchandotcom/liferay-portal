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

import {render} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom/extend-expect';

import Sidebar from '../../../src/main/resources/META-INF/resources/js/components/Sidebar';
import SidebarPanelInfoView from '../../../src/main/resources/META-INF/resources/js/components/SidebarPanelInfoView';
import {
	mockedFileDocumentProps,
	mockedImageDocumentProps,
	mockedNoTaxonomies,
	mockedProps,
	mockedUser,
	mockedVideoShortcutDocumentProps,
} from '../mocks/props';

const _getSidebarComponent = (props) => {
	return (
		<Sidebar>
			<SidebarPanelInfoView {...props} />
		</Sidebar>
	);
};

describe('SidebarPanelInfoView', () => {
	afterEach(() => {
		jest.clearAllMocks();
	});

	it('renders', () => {
		const {asFragment} = render(_getSidebarComponent(mockedProps));

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders sidebar panel with proper info for a basic web content', () => {
		const {container, getByText} = render(
			_getSidebarComponent(mockedProps)
		);

		expect(getByText('Basic Web Content Title')).toBeInTheDocument();
		expect(getByText('Basic Web Content')).toBeInTheDocument();
		expect(getByText('version 1.6')).toBeInTheDocument();
		expect(getByText('Approved')).toBeInTheDocument();
		expect(getByText('version 1.7')).toBeInTheDocument();
		expect(getByText('Draft')).toBeInTheDocument();

		expect(getByText('Kate Williams')).toBeInTheDocument();
		const avatar = container.querySelector('.lexicon-icon-user');
		expect(avatar).toBeTruthy();

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

		expect(getByText('categorization')).toBeInTheDocument();
		expect(getByText('details')).toBeInTheDocument();
	});

	it('renders sidebar panel with proper info for a basic web content', () => {
		const {queryByText} = render(
			_getSidebarComponent({...mockedProps, ...mockedNoTaxonomies})
		);

		expect(queryByText('categorization')).not.toBeInTheDocument();
	});

	it('renders sidebar panel with proper info for an image document', () => {
		const {container, getByText, queryByText} = render(
			_getSidebarComponent({
				...mockedProps,
				...mockedImageDocumentProps,
			})
		);
		const previewFigureTag = container.querySelector('figure');

		expect(
			previewFigureTag.classList.contains('document-preview-figure')
		).toBe(true);

		expect(getByText('Basic Document')).toBeInTheDocument();
		expect(getByText('Mocked description')).toBeInTheDocument();
		expect(getByText('download')).toBeInTheDocument();
		expect(getByText('size')).toBeInTheDocument();

		expect(
			queryByText('languages-translated-into')
		).not.toBeInTheDocument();
	});

	it('renders sidebar panel with proper info for a video shortcut document', () => {
		const {container, getByText, queryByText} = render(
			_getSidebarComponent({
				...mockedProps,
				...mockedVideoShortcutDocumentProps,
			})
		);
		const previewFigureTag = container.querySelector('figure');

		expect(
			previewFigureTag.classList.contains('document-preview-figure')
		).toBe(true);

		expect(getByText('Mocked description')).toBeInTheDocument();
		expect(getByText('External Video Shortcut')).toBeInTheDocument();

		expect(queryByText('download')).not.toBeInTheDocument();
		expect(queryByText('size')).not.toBeInTheDocument();
		expect(queryByText('filename')).not.toBeInTheDocument();
		expect(queryByText('url')).not.toBeInTheDocument();
		expect(
			queryByText('languages-translated-into')
		).not.toBeInTheDocument();
	});

	it('renders sidebar panel with proper info for a file', () => {
		const {container, getByText, queryByText} = render(
			_getSidebarComponent({
				...mockedProps,
				...mockedFileDocumentProps,
			})
		);
		const previewFigureTag = container.querySelector('figure');

		expect(previewFigureTag).toBe(null);

		expect(
			container.getElementsByClassName('lexicon-icon-copy').length
		).toBe(1);

		expect(getByText('Basic Document')).toBeInTheDocument();
		expect(getByText('download')).toBeInTheDocument();

		expect(
			queryByText('languages-translated-into')
		).not.toBeInTheDocument();
	});

	it('renders sidebar panel with proper info if author has avatar', () => {
		const {container} = render(
			_getSidebarComponent({
				...mockedProps,
				...mockedUser,
			})
		);

		const avatar = container.querySelector('.lexicon-icon-user');
		expect(avatar).toBeFalsy();

		const image = container.querySelector('.sticker-img');
		expect(image).toBeTruthy();
	});
});
