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
	classPK: '38070',
	createDate: '2020-07-27T10:50:55.19',
	languageTag: 'en',
	modifiedDate: '2020-07-27T10:56:56.027',
	specificFields: {
		'display-date': {
			title: 'Display Date',
			type: 'Date',
			value: '2020-07-27T10:53:00',
		},
		'expiration-date': {
			title: 'Expiration Date',
			type: 'Date',
			value: '2020-07-28T10:00:00',
		},
		'review-date': {
			title: 'Review Date',
			type: 'Date',
			value: '2020-07-27T14:14:30',
		},
	},
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
	vocabularies: {
		1000: {
			categories: [
				'Travel 1',
				'Travel 2',
				'Travel 3',
				'Travel 4',
				'Travel 5',
				'Travel 6',
				'Travel 7',
			],
			groupName: 'Demo Site',
			isPublic: true,
			vocabularyName: 'Travel',
		},
		2000: {
			categories: [
				'Topic 1',
				'Topic 2',
				'Topic 3',
				'Topic 4',
				'Topic 5',
				'Topic 6',
				'Topic 7',
			],
			groupName: 'Global',
			isPublic: true,
			vocabularyName: 'Topic',
		},
		3000: {
			categories: [
				'QA',
				'Frontend',
				'Backend',
				'Data Scientist',
				'Devops',
				'Design',
			],
			groupName: 'Liferay',
			isPublic: true,
			vocabularyName: 'Developers',
		},
		4000: {
			categories: [
				'Internal 1',
				'Internal 2',
				'Internal 3',
				'Internal 4',
				'Internal 5',
				'Internal 6',
			],
			groupName: 'Liferay',
			isPublic: false,
			vocabularyName: 'Internal categorization',
		},
		5000: {
			categories: [
				'Italian',
				'Mexican',
				'Chinese',
				'Spanish',
				'Thai',
				'Indian',
			],
			groupName: 'Global',
			isPublic: true,
			vocabularyName: 'Foods',
		},
		6000: {
			categories: [
				'Fake 1',
				'Fake 2',
				'Fake 3',
				'Fake 4',
				'Fake 5',
				'Fake 6',
			],
			groupName: 'Liferay',
			isPublic: true,
			vocabularyName: 'ZZ Fake vocabulary',
		},
		7000: {
			categories: [
				'Another Fake 1',
				'Another Fake 2',
				'Another Fake 3',
				'Another Fake 4',
				'Another Fake 5',
				'Another Fake 6',
			],
			groupName: 'Liferay',
			isPublic: true,
			vocabularyName: 'AA Another Fake vocabulary',
		},
		8000: {
			categories: [
				'Global Random 1',
				'Global Random 2',
				'Global Random 3',
				'Global Random 4',
				'Global Random 5',
				'Global Random 6',
			],
			groupName: 'Global',
			isPublic: true,
			vocabularyName: 'ZZ Global Random Vocabulary',
		},
		9000: {
			categories: [
				'Another Global Random 1',
				'Another Global Random 2',
				'Another Global Random 3',
				'Another Global Random 4',
				'Another Global Random 5',
				'Another Global Random 6',
			],
			groupName: 'Global',
			isPublic: true,
			vocabularyName: 'AA Another Global Random Vocabulary',
		},
		10000: {
			categories: [
				'Clothe 1',
				'Clothe 2',
				'Clothe 3',
				'Clothe 4',
				'Clothe 5',
				'Clothe 6',
				'Clothe 7',
			],
			groupName: 'Demo Site',
			isPublic: true,
			vocabularyName: 'Clothes',
		},
		11000: {
			categories: [
				'Private Stuff 1',
				'Private Stuff 2',
				'Private Stuff 3',
				'Private Stuff 4',
				'Private Stuff 5',
				'Private Stuff 6',
				'Private Stuff 7',
			],
			groupName: 'Demo Site',
			isPublic: false,
			vocabularyName: 'Private Stuff',
		},
	},
};

export const mockedNoTaxonomies = {
	tags: [],
	vocabularies: {},
};

export const mockedUser = {
	user: {
		name: 'Kate Williams',
		url: 'my/avatar/image/user.jpg',
		userId: 20126,
	},
};

export const mockedImageDocumentProps = {
	className: 'com.liferay.portal.kernel.repository.model.FileEntry',
	clipboard: {
		name: 'demo.jpg',
		url: 'mocked/view/url/in/portal',
	},
	description: 'Mocked description',
	downloadURL: 'mocked/download/url/demo.jpg&download=true',
	preview: {
		imageURL: 'mocked/preview/url/demo.jpg',
		url: 'mocked/vuew/url/',
	},
	specificFields: {
		extension: {
			title: 'Extension',
			type: 'string',
			value: 'jpg',
		},
		size: {
			title: 'Size',
			type: 'string',
			value: '200 KB',
		},
	},
	subType: 'Basic Document',
	type: 'Document',
	viewURLs: [],
};

export const mockedVideoShortcutDocumentProps = {
	className: 'com.liferay.portal.kernel.repository.model.FileEntry',
	clipboard: {
		name: 'Mocked filename',
		url: 'mocked/view/url/in/portal',
	},
	description: 'Mocked description',
	preview: {
		imageURL: 'mocked/preview/url/demo.jpg',
		url: 'mocked/vuew/url/',
	},
	specificFields: {
		extension: {
			title: 'Extension',
			type: 'string',
			value: '',
		},
		size: {
			title: 'Size',
			type: 'string',
			value: '0 B',
		},
	},
	subType: 'External Video Shortcut',
	type: 'Document',
	viewURLs: [],
};

export const mockedFileDocumentProps = {
	className: 'com.liferay.portal.kernel.repository.model.FileEntry',
	clipboard: {
		name: 'script.sh',
		url: 'mocked/view/url/in/portal',
	},
	description: 'Mocked description',
	downloadURL: 'mocked/download/url/demo.jpg&download=true',
	preview: {
		imageURL: '',
		url: 'mocked/vuew/url/',
	},
	specificFields: {
		extension: {
			title: 'Extension',
			type: 'string',
			value: 'sh',
		},
		size: {
			title: 'Size',
			type: 'string',
			value: '9 KB',
		},
	},
	subType: 'Basic Document',
	type: 'Document',
	viewURLs: [],
};
