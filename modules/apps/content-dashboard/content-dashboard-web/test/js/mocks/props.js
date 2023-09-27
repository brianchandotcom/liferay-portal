/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const mockedAudioDocumentProps = {
	subType: 'Basic Document (Audio)',
};
export const mockedCodeDocumentProps = {
	subType: 'Basic Document (Code)',
};
export const mockedCompressDocumentProps = {
	subType: 'Basic Document (Compressed)',
};
export const mockedContentWithPreview = {
	className: 'com.liferay.portal.kernel.repository.model.FileEntry',
	clipboard: {
		name: 'demo.jpg',
		url: 'mocked/view/url/in/portal',
	},
	description: 'Mocked description',
	preview: {
		imageURL: 'mocked/preview/url/demo.jpg',
		url: 'mocked/vuew/url/',
	},
	specificFields: {
		'extension': {
			title: 'Extension',
			type: 'string',
			value: 'jpg',
		},
		'latest-version-url': {
			title: 'Latest Version URL',
			type: 'URL',
			value: 'http://localhost:8080/documents/d/guest/tree-png',
		},
		'size': {
			title: 'Size',
			type: 'string',
			value: '200 KB',
		},
		'web-dav-url': {
			title: 'WebDAV URL',
			type: 'URL',
			value:
				'http://localhost:8080/webdav/guest/document_library/tree.png',
		},
	},
	subType: 'Basic Document (Image)',
	type: 'Document',
	viewURLs: [],
};
export const mockedContentWithPreviewWithoutLink = {
	className: 'com.liferay.portal.kernel.repository.model.FileEntry',
	clipboard: {
		name: 'demo.jpg',
		url: 'mocked/view/url/in/portal',
	},
	description: 'Mocked description',
	preview: {
		imageURL: 'mocked/preview/url/demo.jpg',
	},
	specificFields: {
		'extension': {
			title: 'Extension',
			type: 'string',
			value: 'jpg',
		},
		'latest-version-url': {
			title: 'Latest Version URL',
			type: 'URL',
			value: 'http://localhost:8080/documents/d/guest/tree-png',
		},
		'size': {
			title: 'Size',
			type: 'string',
			value: '200 KB',
		},
		'web-dav-url': {
			title: 'WebDAV URL',
			type: 'URL',
			value:
				'http://localhost:8080/webdav/guest/document_library/tree.png',
		},
	},
	subType: 'Basic Document',
	type: 'Document',
	viewURLs: [],
};
export const mockedCustomDocumentProps = {
	subType: 'Custom Document',
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
		latestVersionUrl: {
			title: 'Latest Version URL',
			type: 'URL',
			value: 'mockedURLValue',
		},
		size: {
			title: 'Size',
			type: 'string',
			value: '9 KB',
		},
		webDavUrl: {
			title: 'WebDAV URL',
			type: 'URL',
			value: 'mockedURLValue2',
		},
	},
	subType: 'Basic Document (Other)',
	type: 'Document',
	viewURLs: [],
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
	subType: 'Basic Document (Image)',
	type: 'Document',
	viewURLs: [],
};
export const mockedNoTaxonomies = {
	tags: [],
	vocabularies: {},
};
export const mockedPresentationDocumentProps = {
	subType: 'Basic Document (Presentation)',
};

export const mockedProps = {
	allVersions: [
		{
			changeLog: '',
			createDate: 'Tue Aug 30 06:58:03 GMT 2022',
			statusLabel: 'Approved',
			statusStyle: 'success',
			userName: 'Test Test',
			version: '1.1',
		},
		{
			changeLog: '',
			createDate: 'Tue Aug 30 06:56:16 GMT 2022',
			statusLabel: 'Approved',
			statusStyle: 'success',
			userName: 'Test Test',
			version: '1.0',
		},
	],
	classPK: '38070',
	createDate: '2020-07-27T10:50:55.19',
	getItemVersionsURL:
		'http://localhost:8080/group/guest/p_p_id=com_liferay_content_dashboard_web_portlet_ContentDashboardAdminPortlet&p_p_lifecycle=2&p_p_state=maximized&p_p_mode=view&p_p_resource_id=%2Fcontent_dashboard%2Fget_content_dashboard_item_versions',
	languageTag: 'en',
	latestVersions: [
		{
			changeLog: '',
			createDate: 'Tue Aug 30 06:58:03 GMT 2022',
			statusLabel: 'Approved',
			statusStyle: 'success',
			userName: 'Test Test',
			version: '1.6',
		},
		{
			changeLog: '',
			createDate: 'Tue Aug 30 06:58:03 GMT 2022',
			statusLabel: 'Draft',
			statusStyle: 'secondary',
			userName: 'Test Test',
			version: '1.7',
		},
	],
	modifiedDate: '2020-07-27T10:56:56.027',
	singlePageApplicationEnabled: false,
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
	subscribe: {
		icon: 'bell-on',
		label: 'Subscribe',
		url: 'http://localhost:8080/subscribe-url',
	},
	tags: ['tag1', 'tag2'],
	title: 'Basic Web Content Title',
	type: 'Web Content Article',
	user: {
		name: 'Kate Williams',
		url: '',
		userId: 20126,
	},
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
export const mockedSpreadsheetDocumentProps = {
	subType: 'Basic Document (Spreadsheet)',
};
export const mockedTextDocumentProps = {
	subType: 'Basic Document (Text)',
};
export const mockedUser = {
	user: {
		name: 'Kate Williams',
		url: 'my/avatar/image/user.jpg',
		userId: 20126,
	},
};
export const mockedVectorialDocumentProps = {
	subType: 'Basic Document (Vectorial)',
};
export const mockedVideoDocumentProps = {
	subType: 'Basic Document (Video)',
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

export const mockedContentWithVersions = {
	getItemVersionsURL:
		'http://localhost:8080/group/guest/p_p_id=com_liferay_content_dashboard_web_portlet_ContentDashboardAdminPortlet&p_p_lifecycle=2&p_p_state=maximized&p_p_mode=view&p_p_resource_id=%2Fcontent_dashboard%2Fget_content_dashboard_item_versions',
};
