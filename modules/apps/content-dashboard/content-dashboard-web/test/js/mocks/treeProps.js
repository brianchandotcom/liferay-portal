/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const mockExtensionsProps = {
	childrenPropertyKey: 'fileExtensions',
	itemSelectorSaveEvent:
		'_com_liferay_content_dashboard_web_portlet_ContentDashboardAdminPortlet_selectedFileExtension',
	mandatoryFieldsForFiltering: ['id'],
	namePropertyKey: 'fileExtension',
	nodes: [
		{
			children: [
				{
					children: null,
					expanded: false,
					fileExtension: 'zip',
					id: 'zip',
					name: 'zip',
					selected: false,
				},
			],
			expanded: true,
			fileExtensions: [{fileExtension: 'zip', selected: false}],
			icon: 'document-compressed',
			id: '_0',
			label: 'Compressed',
			name: 'Compressed (1 Item)',
		},
		{
			children: [
				{
					children: null,
					expanded: false,
					fileExtension: 'gif',
					id: 'gif',
					name: 'gif',
					selected: false,
				},
				{
					children: null,
					expanded: false,
					fileExtension: 'jpg',
					id: 'jpg',
					name: 'jpg',
					selected: false,
				},
				{
					children: null,
					expanded: false,
					fileExtension: 'jpeg',
					id: 'jpeg',
					name: 'jpeg',
					selected: false,
				},
				{
					children: null,
					expanded: false,
					fileExtension: 'png',
					id: 'png',
					name: 'png',
					selected: false,
				},
			],
			expanded: false,
			fileExtensions: [
				{fileExtension: 'gif', selected: false},
				{fileExtension: 'jpg', selected: false},
				{fileExtension: 'jpeg', selected: false},
				{fileExtension: 'png', selected: false},
			],
			icon: 'document-image',
			id: '_1',
			label: 'Image',
			name: 'Image (4 Items)',
		},
		{
			children: [
				{
					children: null,
					expanded: false,
					fileExtension: 'mpga',
					id: 'mpga',
					name: 'mpga',
					selected: false,
				},
				{
					children: null,
					expanded: false,
					fileExtension: 'mp3',
					id: 'mp3',
					name: 'mp3',
					selected: true,
				},
				{
					children: null,
					expanded: false,
					fileExtension: 'mp2',
					id: 'mp2',
					name: 'mp2',
					selected: false,
				},
				{
					children: null,
					expanded: false,
					fileExtension: 'ogg',
					id: 'ogg',
					name: 'ogg',
					selected: false,
				},
				{
					children: null,
					expanded: false,
					fileExtension: 'wav',
					id: 'wav',
					name: 'wav',
					selected: false,
				},
			],
			expanded: false,
			fileExtensions: [
				{fileExtension: 'mpga', selected: false},
				{fileExtension: 'mp3', selected: false},
				{fileExtension: 'mp2', selected: false},
				{fileExtension: 'ogg', selected: false},
				{fileExtension: 'wav', selected: false},
			],
			icon: 'document-multimedia',
			id: '_2',
			label: 'Audio',
			name: 'Audio (5 Items)',
		},
	],
	portletNamespace:
		'_com_liferay_item_selector_web_portlet_ItemSelectorPortlet_',
};

export const mockTypesProps = {
	childrenPropertyKey: 'itemSubtypes',
	itemSelectorSaveEvent:
		'_com_liferay_content_dashboard_web_portlet_ContentDashboardAdminPortlet_selectedContentDashboardItemSubtype',
	mandatoryFieldsForFiltering: ['className', 'classPK'],
	namePropertyKey: 'label',
	nodes: [
		{
			children: [
				{
					children: null,
					className:
						'com.liferay.dynamic.data.mapping.model.DDMStructure',
					classPK: '41895',
					expanded: false,
					id: 'Basic Web Content',
					label: 'Basic Web Content',
					name: 'Basic Web Content',
					selected: false,
				},
			],
			expanded: true,
			icon: 'web-content',
			id: '_0',
			itemSubtypes: [
				{
					className:
						'com.liferay.dynamic.data.mapping.model.DDMStructure',
					classPK: '41895',
					label: 'Basic Web Content (Global)',
					selected: false,
				},
			],
			label: 'Web Content Article',
			name: 'Web Content Article (1 Item)',
		},
		{
			children: [
				{
					children: null,
					className:
						'com.liferay.document.library.kernel.model.DLFileEntryType',
					classPK: '0',
					expanded: false,
					id: 'Basic Document',
					label: 'Basic Document',
					name: 'Basic Document',
					selected: false,
				},
				{
					children: null,
					className:
						'com.liferay.document.library.kernel.model.DLFileEntryType',
					classPK: '41842',
					expanded: false,
					id: 'Google Drive Shortcut',
					label: 'Google Drive Shortcut',
					name: 'Google Drive Shortcut',
					selected: false,
				},
				{
					children: null,
					className:
						'com.liferay.document.library.kernel.model.DLFileEntryType',
					classPK: '42001',
					expanded: false,
					id: 'External Video Shortcut',
					label: 'External Video Shortcut',
					name: 'External Video Shortcut',
					selected: false,
				},
				{
					children: null,
					className:
						'com.liferay.document.library.kernel.model.DLFileEntryType',
					classPK: '42390',
					expanded: false,
					id: 'Image with title',
					label: 'Image with title (Guest)',
					name: 'Image with title',
					selected: false,
				},
			],
			expanded: false,
			icon: 'documents-and-media',
			id: '_1',
			itemSubtypes: [
				{
					className:
						'com.liferay.document.library.kernel.model.DLFileEntryType',
					classPK: '0',
					label: 'Basic Document',
					selected: false,
				},
				{
					className:
						'com.liferay.document.library.kernel.model.DLFileEntryType',
					classPK: '41842',
					label: 'Google Drive Shortcut (Global)',
					selected: false,
				},
				{
					className:
						'com.liferay.document.library.kernel.model.DLFileEntryType',
					classPK: '42001',
					label: 'External Video Shortcut (Global)',
					selected: false,
				},
				{
					className:
						'com.liferay.document.library.kernel.model.DLFileEntryType',
					classPK: '42390',
					label: 'Image with title (Guest)',
					selected: false,
				},
			],
			label: 'Document',
			name: 'Document (4 Items)',
		},
	],
	portletNamespace:
		'_com_liferay_item_selector_web_portlet_ItemSelectorPortlet_',
};

export const mockEmptyTreeProps = {
	childrenPropertyKey: 'itemSubtypes',
	itemSelectorSaveEvent:
		'_com_liferay_content_dashboard_web_portlet_ContentDashboardAdminPortlet_selectedContentDashboardItemSubtype',
	mandatoryFieldsForFiltering: ['className', 'classPK'],
	namePropertyKey: 'label',
	nodes: [],
	portletNamespace:
		'_com_liferay_item_selector_web_portlet_ItemSelectorPortlet_',
};
