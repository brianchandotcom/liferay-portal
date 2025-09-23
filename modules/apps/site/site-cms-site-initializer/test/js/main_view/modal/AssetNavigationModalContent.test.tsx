/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';

// eslint-disable-next-line
import {checkAccessibility} from '@liferay/layout-js-components-web/test/__lib__/index';
import {fireEvent, render, screen} from '@testing-library/react';
import React from 'react';

import AssetNavigationModalContent from '../../../../src/main/resources/META-INF/resources/js/main_view/modal/asset_navigation_view/AssetNavigationModalContent';

const item1 = {
	embedded: {
		content: 'asd',
		creator: {
			contentType: 'UserAccount',
			externalReferenceCode: '38e08113-4166-162b-5d2c-9ec3a68aabb2',
			id: 20132,
			name: 'Test Test',
		},
		externalReferenceCode: 'cd9c3564-80ad-8d3f-300f-76659955e96d',
		file: {
			externalReferenceCode: '7468ba44-4db5-760b-cac4-1c56b9041a86',
			id: 36530,
			link: {
				href: '/documents/36523/36525/liferay+icon.png/7468ba44-4db5-760b-cac4-1c56b9041a86?version=1.0&t=1757930508059&download=true&groupExternalReferenceCode=5e059871-285a-5a19-46a7-27127417ad9f&objectDefinitionExternalReferenceCode=L_BASIC_DOCUMENT&objectEntryExternalReferenceCode=cd9c3564-80ad-8d3f-300f-76659955e96d',
				label: 'liferay icon.png',
			},
			mimeType: 'image/png',
			name: 'liferay icon.png',
			thumbnailURL:
				'/documents/36523/36525/liferay+icon.png/7468ba44-4db5-760b-cac4-1c56b9041a86?version=1.0&t=1757930508059&imageThumbnail=1',
		},
		id: 36535,
		objectEntryFolderExternalReferenceCode: 'L_FILES',
		objectEntryFolderId: 35400,
		scopeId: 35393,
		scopeKey: 'Default',
		title: 'liferay icon.png',
	},
	entryClassName: 'com.liferay.object.model.ObjectDefinition#Z7P5',
	id: 36535,
	title: 'liferay icon.png',
};
const item2 = {
	embedded: {
		content: '<p>asd</p>',
		creator: {
			contentType: 'UserAccount',
			id: 20132,
			name: 'Test Test',
		},
		externalReferenceCode: 'aa35cc56-67ec-44f4-23bf-2f2ea3799b21',
		id: 36599,
		objectEntryFolderExternalReferenceCode:
			'2fefc7c4-f2e1-ffd2-f255-7a6e8fa496f1',
		objectEntryFolderId: 36592,
		scopeId: 35393,
		scopeKey: 'Default',
		title: 'KB Article',
	},
	entryClassName: 'com.liferay.object.model.ObjectDefinition#H6H2',
	id: 36599,
	title: 'KB Article',
};
const item3 = {
	embedded: {
		content: '<p>Third content</p>',
		creator: {
			contentType: 'UserAccount',
			id: 20132,
			name: 'Test Test',
		},
		externalReferenceCode: 'aa35cc56-67ec-44f4-23bf-2f2ea3799b21',
		id: 36590,
		objectEntryFolderExternalReferenceCode:
			'2fefc7c4-f2e1-ffd2-f255-7a6e8fa496f1',
		objectEntryFolderId: 36592,
		scopeId: 35393,
		scopeKey: 'Default',
		title: 'First Blog',
	},
	entryClassName: 'com.liferay.object.model.ObjectDefinition#H6H2',
	id: 36590,
	title: 'First Blog',
};

const DEFAULT_PROPS = {
	additionalProps: {cmsGroupId: 123},
	contentViewURL: '/my-random-content-view',
	currentIndex: 0,
	items: [item1, item2, item3],
};

const renderComponent = (props = DEFAULT_PROPS) => {
	return render(<AssetNavigationModalContent {...props} />);
};

describe('AssetNavigationModalContent', () => {
	it('checks the accessibility of the item navigation component', async () => {
		const {container} = renderComponent();

		await checkAccessibility({bestPractices: true, context: container});
	});

	it('renders with the first item with the download link', () => {
		renderComponent();

		expect(screen.getByText('liferay icon.png')).toBeInTheDocument();
		expect(
			screen.getByRole('link', {name: 'download'})
		).toBeInTheDocument();
		expect(screen.getByRole('img')).toBeInTheDocument();
	});

	it('navigates to next item when right arrow key is pressed', () => {
		const {getByLabelText} = renderComponent();

		fireEvent.click(getByLabelText('next'));

		expect(screen.getByText('KB Article')).toBeInTheDocument();
	});

	it('wraps around to last item when pressing previous on first item', () => {
		const {getByLabelText} = renderComponent();

		fireEvent.click(getByLabelText('previous'));

		expect(screen.getByText('First Blog')).toBeInTheDocument();
	});

	it('arrows are hidden if there is only one element', () => {
		renderComponent({
			...DEFAULT_PROPS,
			items: [item1],
		});

		expect(
			screen.queryByRole('button', {name: /previous/i})
		).not.toBeInTheDocument();
		expect(
			screen.queryByRole('button', {name: /next/i})
		).not.toBeInTheDocument();
	});
});
