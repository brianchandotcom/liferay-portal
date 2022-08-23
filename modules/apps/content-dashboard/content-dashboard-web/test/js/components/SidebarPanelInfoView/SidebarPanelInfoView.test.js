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

import {fireEvent, render, waitFor} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom/extend-expect';
import {fetch, openToast} from 'frontend-js-web';

import Sidebar from '../../../../src/main/resources/META-INF/resources/js/components/Sidebar';
import SidebarPanelInfoView from '../../../../src/main/resources/META-INF/resources/js/components/SidebarPanelInfoView/SidebarPanelInfoView';
import {
	mockedContentWithPreview,
	mockedContentWithPreviewWithoutLink,
	mockedFileDocumentProps,
	mockedImageDocumentProps,
	mockedNoTaxonomies,
	mockedProps,
	mockedUser,
	mockedVideoShortcutDocumentProps,
} from '../../mocks/props';

jest.mock('frontend-js-web', () => ({
	fetch: jest.fn().mockReturnValue({
		ok: true,
	}),

	openToast: jest.fn(),
}));

const _getSidebarComponent = (props) => {
	return (
		<Sidebar fetchData={() => jest.fn()}>
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
		expect(
			getByText('Web Content Article - Basic Web Content')
		).toBeInTheDocument();
		expect(getByText('version 1.6')).toBeInTheDocument();
		expect(getByText('Approved')).toBeInTheDocument();
		expect(getByText('version 1.7')).toBeInTheDocument();
		expect(getByText('Draft')).toBeInTheDocument();

		expect(getByText('Kate Williams')).toBeInTheDocument();
		const avatar = container.querySelector('.lexicon-icon-user');
		expect(avatar).toBeTruthy();

		expect(getByText('id')).toBeInTheDocument();
		expect(getByText('38070')).toBeInTheDocument();

		expect(getByText('categorization')).toBeInTheDocument();
		expect(getByText('details')).toBeInTheDocument();
	});

	it('renders sidebar panel with proper dates for a basic web content', () => {
		const {getByText} = render(_getSidebarComponent(mockedProps));

		expect(getByText('Display Date')).toBeInTheDocument();
		expect(getByText('Jul 27, 2020, 10:53 AM')).toBeInTheDocument();

		expect(getByText('creation-date')).toBeInTheDocument();
		expect(getByText('Jul 27, 2020, 10:50 AM')).toBeInTheDocument();

		expect(getByText('modified-date')).toBeInTheDocument();
		expect(getByText('Jul 27, 2020, 10:50 AM')).toBeInTheDocument();

		expect(getByText('Expiration Date')).toBeInTheDocument();
		expect(getByText('Jul 28, 2020, 10:00 AM')).toBeInTheDocument();

		expect(getByText('Review Date')).toBeInTheDocument();
		expect(getByText('Jul 27, 2020, 2:14 PM')).toBeInTheDocument();
	});

	it('renders sidebar panel with proper languages for a basic web content', () => {
		const {getByText} = render(_getSidebarComponent(mockedProps));

		expect(getByText('languages-translated-into')).toBeInTheDocument();
		expect(getByText('en-US')).toBeInTheDocument();
		expect(getByText('default')).toBeInTheDocument();
		expect(getByText('es-ES')).toBeInTheDocument();
		expect(getByText('fr-FR')).toBeInTheDocument();
		expect(getByText('pt-BR')).toBeInTheDocument();
	});

	it('renders sidebar panel with proper tags for a basic web content', () => {
		const {getByText} = render(_getSidebarComponent(mockedProps));

		expect(getByText('tags')).toBeInTheDocument();
		expect(getByText('tag1')).toBeInTheDocument();
		expect(getByText('tag2')).toBeInTheDocument();
	});

	it('renders sidebar panel with proper vocabularies and categories for a basic web content', () => {
		const {getByText} = render(_getSidebarComponent(mockedProps));

		expect(getByText('public-categories')).toBeInTheDocument();
		expect(getByText('internal-categories')).toBeInTheDocument();

		expect(getByText('Topic (Global)')).toBeInTheDocument();
		expect(getByText('Topic 1')).toBeInTheDocument();
		expect(getByText('Topic 7')).toBeInTheDocument();

		expect(getByText('Developers (Liferay)')).toBeInTheDocument();
		expect(getByText('QA')).toBeInTheDocument();
		expect(getByText('Design')).toBeInTheDocument();

		expect(
			getByText('Internal categorization (Liferay)')
		).toBeInTheDocument();
		expect(getByText('Internal 1')).toBeInTheDocument();
		expect(getByText('Internal 6')).toBeInTheDocument();

		expect(getByText('Foods (Global)')).toBeInTheDocument();
		expect(getByText('Italian')).toBeInTheDocument();
		expect(getByText('Mexican')).toBeInTheDocument();

		expect(getByText('ZZ Fake vocabulary (Liferay)')).toBeInTheDocument();
		expect(getByText('Fake 1')).toBeInTheDocument();
		expect(getByText('Fake 2')).toBeInTheDocument();

		expect(
			getByText('AA Another Fake vocabulary (Liferay)')
		).toBeInTheDocument();
		expect(getByText('Another Fake 1')).toBeInTheDocument();
		expect(getByText('Another Fake 2')).toBeInTheDocument();

		expect(
			getByText('ZZ Global Random Vocabulary (Global)')
		).toBeInTheDocument();
		expect(getByText('Global Random 1')).toBeInTheDocument();
		expect(getByText('Global Random 2')).toBeInTheDocument();

		expect(
			getByText('AA Another Global Random Vocabulary (Global)')
		).toBeInTheDocument();
		expect(getByText('Another Global Random 1')).toBeInTheDocument();
		expect(getByText('Another Global Random 2')).toBeInTheDocument();

		expect(getByText('Travel (Demo Site)')).toBeInTheDocument();
		expect(getByText('Travel 1')).toBeInTheDocument();
		expect(getByText('Travel 2')).toBeInTheDocument();

		expect(getByText('Clothes (Demo Site)')).toBeInTheDocument();
		expect(getByText('Clothe 1')).toBeInTheDocument();
		expect(getByText('Clothe 2')).toBeInTheDocument();
	});

	it('renders sidebar panel with vocabularies subtitles in the proper order for a basic web content', () => {
		const {container} = render(_getSidebarComponent(mockedProps));

		const subtitles = container.querySelectorAll(
			'.item-vocabularies h6.sidebar-section-subtitle-sm'
		);
		expect(subtitles.length).toBe(2);
		expect(subtitles[0].textContent).toBe('public-categories');
		expect(subtitles[1].textContent).toBe('internal-categories');
	});

	it('renders sidebar panel with vocabularies names grouped and sorted for a basic web content', () => {
		const {container} = render(_getSidebarComponent(mockedProps));

		const vocabularies = container.querySelectorAll(
			'.item-vocabularies h5.font-weight-semi-bold'
		);
		expect(vocabularies.length).toBe(
			Object.keys(mockedProps.vocabularies).length
		);

		expect(vocabularies[0].textContent).toBe(
			'AA Another Fake vocabulary (Liferay)'
		);
		expect(vocabularies[1].textContent).toBe('Clothes (Demo Site)');
		expect(vocabularies[2].textContent).toBe('Developers (Liferay)');
		expect(vocabularies[3].textContent).toBe('Travel (Demo Site)');
		expect(vocabularies[4].textContent).toBe(
			'ZZ Fake vocabulary (Liferay)'
		);

		expect(vocabularies[5].textContent).toBe(
			'AA Another Global Random Vocabulary (Global)'
		);
		expect(vocabularies[6].textContent).toBe('Foods (Global)');
		expect(vocabularies[7].textContent).toBe('Topic (Global)');
		expect(vocabularies[8].textContent).toBe(
			'ZZ Global Random Vocabulary (Global)'
		);

		expect(vocabularies[9].textContent).toBe(
			'Internal categorization (Liferay)'
		);
		expect(vocabularies[10].textContent).toBe('Private Stuff (Demo Site)');
	});

	it('renders sidebar panel without the categorization for a basic web content', () => {
		const {queryByText} = render(
			_getSidebarComponent({...mockedProps, ...mockedNoTaxonomies})
		);

		expect(queryByText('categorization')).not.toBeInTheDocument();
	});

	it('renders sidebar panel with proper info for an image document', () => {
		const {getByText, queryByText} = render(
			_getSidebarComponent({
				...mockedProps,
				...mockedImageDocumentProps,
			})
		);

		expect(getByText('Document - Basic Document')).toBeInTheDocument();
		expect(getByText('Mocked description')).toBeInTheDocument();
		expect(getByText('download')).toBeInTheDocument();
		expect(getByText('Size')).toBeInTheDocument();

		expect(
			queryByText('languages-translated-into')
		).not.toBeInTheDocument();
	});

	it('renders sidebar panel with proper info for a video shortcut document', () => {
		const {getByText, queryByText} = render(
			_getSidebarComponent({
				...mockedProps,
				...mockedVideoShortcutDocumentProps,
			})
		);

		expect(getByText('Mocked description')).toBeInTheDocument();
		expect(
			getByText('Document - External Video Shortcut')
		).toBeInTheDocument();

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

		expect(
			container.getElementsByClassName('lexicon-icon-copy').length
		).toBe(2);

		expect(getByText('Document - Basic Document')).toBeInTheDocument();
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

	it('renders preview of the content with a link if proceeds', () => {
		const {container, rerender} = render(
			_getSidebarComponent({
				...mockedProps,
				...mockedContentWithPreview,
			})
		);
		const previewFigureTag = container.querySelector('figure');
		const previewLink = container.querySelectorAll('figure a');

		expect(
			previewFigureTag.classList.contains('document-preview-figure')
		).toBe(true);
		expect(previewLink.length).toBe(1);
		expect(previewLink[0].getAttribute('href')).toBe(
			mockedContentWithPreview.preview.url
		);

		const copyMockedContentWithPreview = JSON.parse(
			JSON.stringify(mockedContentWithPreview)
		);
		copyMockedContentWithPreview.preview = null;

		rerender(
			_getSidebarComponent({
				...mockedProps,
				...copyMockedContentWithPreview,
			})
		);

		const newPreviewFigureTag = container.querySelector('figure');

		expect(newPreviewFigureTag).toBe(null);
	});

	it('renders preview of the content without a link if no url is provided', () => {
		const {container} = render(
			_getSidebarComponent({
				...mockedProps,
				...mockedContentWithPreviewWithoutLink,
			})
		);
		const previewFigureTag = container.querySelector('figure');
		const previewLink = container.querySelectorAll('figure a');

		expect(
			previewFigureTag.classList.contains('document-preview-figure')
		).toBe(true);
		expect(previewLink.length).toBe(0);
	});

	it('renders sidebar panel with/without subscribe button if proceeds', () => {
		const {getByTitle, queryByTitle, rerender} = render(
			_getSidebarComponent({
				...mockedProps,
			})
		);

		expect(getByTitle('Subscribe')).toBeInTheDocument();

		const copyMockedProps = JSON.parse(JSON.stringify(mockedProps));
		copyMockedProps.subscribe = null;

		rerender(
			_getSidebarComponent({
				...copyMockedProps,
			})
		);

		expect(queryByTitle('Subscribe')).not.toBeInTheDocument();
	});

	it('renders sidebar panel with subscribe button and it calls the API', async () => {
		const {getByTitle} = render(
			_getSidebarComponent({
				...mockedProps,
			})
		);

		const button = getByTitle('Subscribe');

		fireEvent(
			button,
			new MouseEvent('click', {
				bubbles: true,
				cancelable: true,
			})
		);

		await waitFor(() =>
			expect(fetch).toHaveBeenCalledWith(mockedProps.subscribe.url)
		);

		await waitFor(() =>
			expect(openToast).toHaveBeenCalledWith({
				message: Liferay.Language.get(
					'your-request-completed-successfully'
				),
				type: 'success',
			})
		);
	});

	it('renders sidebar panel with subscribe button and it handles the API error', async () => {
		fetch.mockImplementation(() => {
			return {
				ok: false,
			};
		});

		const {getByTitle} = render(
			_getSidebarComponent({
				...mockedProps,
			})
		);

		const button = getByTitle('Subscribe');

		fireEvent(
			button,
			new MouseEvent('click', {
				bubbles: true,
				cancelable: true,
			})
		);

		expect(fetch).toHaveBeenCalledWith(mockedProps.subscribe.url);
		await waitFor(() =>
			expect(openToast).toHaveBeenCalledWith({
				message: Liferay.Language.get('an-unexpected-error-occurred'),
				type: 'danger',
			})
		);
	});
});
