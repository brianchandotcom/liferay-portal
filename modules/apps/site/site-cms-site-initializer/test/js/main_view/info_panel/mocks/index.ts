/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const CONTENT_OBJECT_ENTRY = {
	dateCreated: '2026-02-18T17:15:35Z',
	dateModified: '2026-02-18T17:15:51Z',
	description: 'content: Example content, title: My Web Content',
	embedded: {
		actions: {
			'copy': {
				href: 'http://localhost:8080/o/cms/basic-web-contents/36141/by-object-entry-folder-id/{objectEntryFolderId}/copy',
				method: 'POST',
			},
			'copy-replace': {
				href: 'http://localhost:8080/o/cms/basic-web-contents/36141/by-object-entry-folder-id/{objectEntryFolderId}/copy-replace',
				method: 'POST',
			},
			'delete': {
				href: 'http://localhost:8080/o/cms/basic-web-contents/36141',
				method: 'DELETE',
			},
			'expire': {
				href: 'http://localhost:8080/o/cms/basic-web-contents/scopes/36030/by-external-reference-code/3fd76346-c116-ff19-e398-d7508ca98962/expire',
				method: 'POST',
			},
			'get': {
				href: 'http://localhost:8080/o/cms/basic-web-contents/36141',
				method: 'GET',
			},
			'get-by-scope': {
				href: 'http://localhost:8080/o/cms/basic-web-contents/scopes/36030',
				method: 'GET',
			},
			'move': {
				href: 'http://localhost:8080/o/cms/basic-web-contents/36141/by-object-entry-folder-id/{objectEntryFolderId}/move',
				method: 'POST',
			},
			'move-replace': {
				href: 'http://localhost:8080/o/cms/basic-web-contents/36141/by-object-entry-folder-id/{objectEntryFolderId}/move-replace',
				method: 'POST',
			},
			'permissions': {
				href: 'http://localhost:8080/o/cms/basic-web-contents/36141/permissions',
				method: 'GET',
			},
			'replace': {
				href: 'http://localhost:8080/o/cms/basic-web-contents/36141',
				method: 'PUT',
			},
			'share': {
				href: 'http://localhost:8080/o/cms/basic-web-contents/36141',
				method: 'GET',
			},
			'update': {
				href: 'http://localhost:8080/o/cms/basic-web-contents/36141',
				method: 'PATCH',
			},
			'versions': {
				href: 'http://localhost:8080/o/cms/basic-web-contents/scopes/36030/by-external-reference-code/3fd76346-c116-ff19-e398-d7508ca98962/versions',
				method: 'GET',
			},
		},
		content: '<p>Example content</p>',
		content_i18n: {
			en_US: '<p>Example content</p>',
		},
		contentRawText: 'Example content',
		creator: {
			additionalName: '',
			contentType: 'UserAccount',
			externalReferenceCode: '427af2d3-6072-5cf9-c00b-2995c23b7163',
			familyName: 'Test',
			givenName: 'Test',
			id: 20133,
			name: 'Test Test',
		},
		dateCreated: '2026-02-18T17:15:35Z',
		dateModified: '2026-02-18T17:15:51Z',
		defaultLanguageId: 'en_US',
		externalReferenceCode: '3fd76346-c116-ff19-e398-d7508ca98962',
		friendlyUrlPath: '3fd76346-c116-ff19-e398-d7508ca98962',
		friendlyUrlPath_i18n: {
			ar_SA: '3fd76346-c116-ff19-e398-d7508ca98962',
			ca_ES: '3fd76346-c116-ff19-e398-d7508ca98962',
			de_DE: '3fd76346-c116-ff19-e398-d7508ca98962',
			en_US: '3fd76346-c116-ff19-e398-d7508ca98962',
			es_ES: '3fd76346-c116-ff19-e398-d7508ca98962',
			fi_FI: '3fd76346-c116-ff19-e398-d7508ca98962',
			fr_FR: '3fd76346-c116-ff19-e398-d7508ca98962',
			hu_HU: '3fd76346-c116-ff19-e398-d7508ca98962',
			ja_JP: '3fd76346-c116-ff19-e398-d7508ca98962',
			nl_NL: '3fd76346-c116-ff19-e398-d7508ca98962',
			pt_BR: '3fd76346-c116-ff19-e398-d7508ca98962',
			sv_SE: '3fd76346-c116-ff19-e398-d7508ca98962',
			zh_CN: '3fd76346-c116-ff19-e398-d7508ca98962',
		},
		id: 36141,
		keywords: [],
		objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
		objectEntryFolderId: 36031,
		scopeId: 36030,
		scopeKey: 'GSpace',
		status: {
			code: 0,
			label: 'approved',
			label_i18n: 'Approved',
		},
		systemProperties: {
			objectDefinitionBrief: {
				classNameId: 33005,
				externalReferenceCode: 'L_CMS_BASIC_WEB_CONTENT',
				label: 'Basic Web Content',
				objectFolderExternalReferenceCode: 'L_CMS_CONTENT_STRUCTURES',
			},
			scope: {
				externalReferenceCode: 'a0a0a05e-61d2-248e-4ffa-ae1df094e695',
				type: 'AssetLibrary',
			},
			version: {
				number: 1,
			},
		},
		taxonomyCategoryBriefs: [],
		title: 'My Web Content',
		title_i18n: {
			en_US: 'My Web Content',
		},
	},
	entryClassName: 'com.liferay.object.model.ObjectDefinition#H4T4',
	score: 1.0,
	title: 'My Web Content',
};

export const DOCUMENT_OBJECT_ENTRY = {
	dateCreated: '2026-02-18T17:20:12Z',
	dateModified: '2026-02-18T17:20:41Z',
	description: 'file: orange_cat.jpeg, title: My file',
	embedded: {
		actions: {
			'copy': {
				href: 'http://localhost:8080/o/cms/basic-documents/36170/by-object-entry-folder-id/{objectEntryFolderId}/copy',
				method: 'POST',
			},
			'copy-replace': {
				href: 'http://localhost:8080/o/cms/basic-documents/36170/by-object-entry-folder-id/{objectEntryFolderId}/copy-replace',
				method: 'POST',
			},
			'delete': {
				href: 'http://localhost:8080/o/cms/basic-documents/36170',
				method: 'DELETE',
			},
			'expire': {
				href: 'http://localhost:8080/o/cms/basic-documents/scopes/36030/by-external-reference-code/a6d58af4-04a9-63e3-b3fc-002948d514c6/expire',
				method: 'POST',
			},
			'get': {
				href: 'http://localhost:8080/o/cms/basic-documents/36170',
				method: 'GET',
			},
			'get-by-scope': {
				href: 'http://localhost:8080/o/cms/basic-documents/scopes/36030',
				method: 'GET',
			},
			'move': {
				href: 'http://localhost:8080/o/cms/basic-documents/36170/by-object-entry-folder-id/{objectEntryFolderId}/move',
				method: 'POST',
			},
			'move-replace': {
				href: 'http://localhost:8080/o/cms/basic-documents/36170/by-object-entry-folder-id/{objectEntryFolderId}/move-replace',
				method: 'POST',
			},
			'permissions': {
				href: 'http://localhost:8080/o/cms/basic-documents/36170/permissions',
				method: 'GET',
			},
			'replace': {
				href: 'http://localhost:8080/o/cms/basic-documents/36170',
				method: 'PUT',
			},
			'share': {
				href: 'http://localhost:8080/o/cms/basic-documents/36170',
				method: 'GET',
			},
			'update': {
				href: 'http://localhost:8080/o/cms/basic-documents/36170',
				method: 'PATCH',
			},
			'versions': {
				href: 'http://localhost:8080/o/cms/basic-documents/scopes/36030/by-external-reference-code/a6d58af4-04a9-63e3-b3fc-002948d514c6/versions',
				method: 'GET',
			},
		},
		creator: {
			additionalName: '',
			contentType: 'UserAccount',
			externalReferenceCode: '427af2d3-6072-5cf9-c00b-2995c23b7163',
			familyName: 'Test',
			givenName: 'Test',
			id: 20133,
			name: 'Test Test',
		},
		dateCreated: '2026-02-18T17:20:12Z',
		dateModified: '2026-02-18T17:20:41Z',
		defaultLanguageId: 'en_US',
		externalReferenceCode: 'a6d58af4-04a9-63e3-b3fc-002948d514c6',
		file: {
			alternativeText: '',
			externalReferenceCode: 'fea3e5a6-cc47-2459-3583-2f228aef2563',
			fileURL:
				'http://localhost:8080/documents/36035/36037/orange_cat.jpeg/fea3e5a6-cc47-2459-3583-2f228aef2563?version=1.0&t=1771435241269&imageThumbnail=1',
			id: 36182,
			link: {
				href: '/documents/36035/36037/orange_cat.jpeg/fea3e5a6-cc47-2459-3583-2f228aef2563?version=1.0&t=1771435241269&download=true&groupExternalReferenceCode=a0a0a05e-61d2-248e-4ffa-ae1df094e695&objectDefinitionExternalReferenceCode=L_CMS_BASIC_DOCUMENT&objectEntryExternalReferenceCode=a6d58af4-04a9-63e3-b3fc-002948d514c6&objectFieldExternalReferenceCode=FILE',
				label: 'orange_cat.jpeg',
			},
			mimeType: 'image/jpeg',
			name: 'orange_cat.jpeg',
			previewURL: '/documents/d/asset-library-36029/orange_cat',
			thumbnailURL:
				'/documents/36035/36037/orange_cat.jpeg/fea3e5a6-cc47-2459-3583-2f228aef2563?version=1.0&t=1771435241269&imageThumbnail=1',
		},
		friendlyUrlPath: 'a6d58af4-04a9-63e3-b3fc-002948d514c6',
		friendlyUrlPath_i18n: {
			ar_SA: 'a6d58af4-04a9-63e3-b3fc-002948d514c6',
			ca_ES: 'a6d58af4-04a9-63e3-b3fc-002948d514c6',
			de_DE: 'a6d58af4-04a9-63e3-b3fc-002948d514c6',
			en_US: 'a6d58af4-04a9-63e3-b3fc-002948d514c6',
			es_ES: 'a6d58af4-04a9-63e3-b3fc-002948d514c6',
			fi_FI: 'a6d58af4-04a9-63e3-b3fc-002948d514c6',
			fr_FR: 'a6d58af4-04a9-63e3-b3fc-002948d514c6',
			hu_HU: 'a6d58af4-04a9-63e3-b3fc-002948d514c6',
			ja_JP: 'a6d58af4-04a9-63e3-b3fc-002948d514c6',
			nl_NL: 'a6d58af4-04a9-63e3-b3fc-002948d514c6',
			pt_BR: 'a6d58af4-04a9-63e3-b3fc-002948d514c6',
			sv_SE: 'a6d58af4-04a9-63e3-b3fc-002948d514c6',
			zh_CN: 'a6d58af4-04a9-63e3-b3fc-002948d514c6',
		},
		id: 36170,
		keywords: [],
		objectEntryFolderExternalReferenceCode: 'L_FILES',
		objectEntryFolderId: 36033,
		scopeId: 36030,
		scopeKey: 'GSpace',
		status: {
			code: 0,
			label: 'approved',
			label_i18n: 'Approved',
		},
		systemProperties: {
			objectDefinitionBrief: {
				classNameId: 32870,
				externalReferenceCode: 'L_CMS_BASIC_DOCUMENT',
				label: 'Basic Document',
				objectFolderExternalReferenceCode: 'L_CMS_FILE_TYPES',
			},
			scope: {
				externalReferenceCode: 'a0a0a05e-61d2-248e-4ffa-ae1df094e695',
				type: 'AssetLibrary',
			},
			version: {
				number: 1,
			},
		},
		taxonomyCategoryBriefs: [],
		title: 'My file',
		title_i18n: {
			en_US: 'My file',
		},
	},
	entryClassName: 'com.liferay.object.model.ObjectDefinition#Z7P5',
	score: 1.0,
	title: 'My file',
};

export const FOLDER_OBJECT_ENTRY = {
	actions: {
		'copy': {
			href: 'http://localhost:8080/o/headless-object/v1.0/object-entry-folders/36134/by-parent-object-entry-folder-id/{parentObjectEntryFolderId}/copy',
			method: 'POST',
		},
		'copy-replace': {
			href: 'http://localhost:8080/o/headless-object/v1.0/object-entry-folders/36134/by-parent-object-entry-folder-id/{parentObjectEntryFolderId}/copy-replace',
			method: 'POST',
		},
		'delete': {
			href: 'http://localhost:8080/o/headless-object/v1.0/object-entry-folders/36134',
			method: 'DELETE',
		},
		'get': {
			href: 'http://localhost:8080/o/headless-object/v1.0/object-entry-folders/36134',
			method: 'GET',
		},
		'get-by-scope': {
			href: 'http://localhost:8080/o/headless-object/v1.0/scopes/36030/object-entry-folders',
			method: 'GET',
		},
		'move': {
			href: 'http://localhost:8080/o/headless-object/v1.0/object-entry-folders/36134/by-parent-object-entry-folder-id/{parentObjectEntryFolderId}/move',
			method: 'POST',
		},
		'move-replace': {
			href: 'http://localhost:8080/o/headless-object/v1.0/object-entry-folders/36134/by-parent-object-entry-folder-id/{parentObjectEntryFolderId}/move-replace',
			method: 'POST',
		},
		'permissions': {
			href: 'http://localhost:8080/o/headless-object/v1.0/object-entry-folders/36134/permissions',
			method: 'GET',
		},
		'share': {
			href: 'http://localhost:8080/o/headless-object/v1.0/object-entry-folders/36134',
			method: 'GET',
		},
		'update': {
			href: 'http://localhost:8080/o/headless-object/v1.0/object-entry-folders/36134',
			method: 'PATCH',
		},
	},

	dateCreated: '2026-02-18T17:15:31Z',
	dateModified: '2026-02-18T17:15:31Z',
	description: '',
	embedded: {
		creator: {
			additionalName: '',
			contentType: 'UserAccount',
			externalReferenceCode: '427af2d3-6072-5cf9-c00b-2995c23b7163',
			familyName: 'Test',
			givenName: 'Test',
			id: 20133,
			name: 'Test Test',
		},
		dateCreated: '2026-02-18T17:15:31Z',
		dateModified: '2026-02-18T17:15:31Z',
		description: '',
		externalReferenceCode: '9431b543-71e2-f7d7-ad3e-212f68af097b',
		id: 36134,
		label: 'My Folder',
		label_i18n: {
			en_US: 'My Folder',
		},
		numberOfObjectEntries: 2,
		numberOfObjectEntryFolders: 0,
		parentObjectEntryFolderExternalReferenceCode: 'L_CONTENTS',
		parentObjectEntryFolderId: 36031,
		scope: {
			externalReferenceCode: 'a0a0a05e-61d2-248e-4ffa-ae1df094e695',
			type: 'AssetLibrary',
		},
		scopeId: 36030,
		scopeKey: 'GSpace',
		status: {
			code: 0,
			label: 'approved',
			label_i18n: 'Approved',
		},
		title: 'My Folder',
	},
	entryClassName: 'com.liferay.object.model.ObjectEntryFolder',
	score: 1.0,
	title: 'my folder',
};
