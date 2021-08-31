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

export const mockedProps = {
	categories: [
		'Crime',
		'Romantic',
		'Classics',
		'Fantasy',
		'Science Fiction',
		'Advanced',
		'Education',
		'Decision',
	],
	classPK: 38070,
	createDate: '2020-07-27T10:50:55.19',
	data: {
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
	},
	languageTag: 'en',
	modifiedDate: '2020-07-27T10:56:56.027',
	subType: 'Basic Web Content',
	tags: ['tag1', 'tag2'],
	title: 'Basic Web Content Title',
	type: 'Web Content Article',
	user: {
		name: 'Kate Williams',
		url: '',
		userId: 20126,
	},
	versions: [
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
	],
	viewURLs: [
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
	],
};

export const mockedNoTaxonomies = {
	categories: [],
	tags: [],
};

export const mockedUser = {
	user: {
		name: 'Kate Williams',
		url: 'my/avatar/image/user.jpg',
		userId: 20126,
	},
};

export const mockedImageDocumentProps = {
	specificFields: {
		description: 'Mocked description',
		downloadURL: 'mocked/download/url/demo.jpg&download=true',
		extension: 'jpg',
		fileName: 'demo.jpg',
		previewImageURL: 'mocked/preview/url/demo.jpg',
		previewURL: 'mocked/vuew/url/',
		size: '200 KB',
		viewURL: 'mocked/view/url/in/portal',
	},
	subType: 'Basic Document',
	type: 'Document',
};

export const mockedVideoShortcutDocumentProps = {
	specificFields: {
		description: 'Mocked description',
		downloadURL: 'mocked/download/url/demo.jpg&download=true',
		extension: '',
		fileName: 'Mocked filename',
		previewImageURL: 'mocked/preview/url/demo.jpg',
		size: '0 B',
		viewURL: 'mocked/view/url/in/portal',
	},
	subType: 'External Video Shortcut',
	type: 'Document',
};

export const mockedFileDocumentProps = {
	specificFields: {
		description: 'Mocked description',
		downloadURL: 'mocked/download/url/demo.jpg&download=true',
		extension: 'sh',
		fileName: 'script.sh',
		previewImageURL: '',
		size: '9 KB',
		viewURL: 'mocked/view/url/in/portal',
	},
	subType: 'Basic Document',
	type: 'Document',
};
