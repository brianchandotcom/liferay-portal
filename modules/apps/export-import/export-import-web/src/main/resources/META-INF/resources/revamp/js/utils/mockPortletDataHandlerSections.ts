/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export interface PortletDataControl {
	controls?: PortletDataControl[];
	label: string;
	name: string;
	options?: {label: string; value: string}[];
	type: 'boolean' | 'choice';
}

export interface PortletDataHandler {
	controls?: PortletDataControl[];
	label: string;
	name: string;
	type: 'boolean';
}

export interface PortletDataHandlerSection {
	id: string;
	key: string;
	name: string;
	portletDataHandlers: PortletDataHandler[];
}

export const mockPortletDataHandlerSections: PortletDataHandlerSection[] = [
	{
		id: 'category.site_administration.design',
		key: 'category.site_administration.design',
		name: 'Design',
		portletDataHandlers: [
			{
				label: 'Theme Settings',
				name: 'THEME_REFERENCE',
				type: 'boolean',
			},
			{
				label: 'Logo',
				name: 'LOGO',
				type: 'boolean',
			},
			{
				label: 'Fragments',
				name: 'PORTLET_DATA_com_liferay_fragment_web_portlet_FragmentPortlet',
				type: 'boolean',
			},
		],
	},
	{
		id: 'category.site_administration.build',
		key: 'category.site_administration.build',
		name: 'Site Builder',
		portletDataHandlers: [
			{
				label: 'Pages',
				name: 'PORTLET_DATA_com_liferay_layout_admin_web_portlet_GroupPagesPortlet',
				type: 'boolean',
			},
		],
	},
	{
		id: 'category.site_administration.content',
		key: 'category.site_administration.content',
		name: 'Content & Data',
		portletDataHandlers: [
			{
				controls: [
					{
						label: 'Web Content',
						name: '_journal_web-content',
						type: 'boolean',
					},
					{
						controls: [
							{
								label: 'Referenced Content Behavior',
								name: '_journal_referenced-content-behavior',
								options: [
									{
										label: 'Include Always',
										value: 'include-always',
									},
									{
										label: 'Include If Modified',
										value: 'include-if-modified',
									},
								],
								type: 'choice',
							},
						],
						label: 'Referenced Content',
						name: '_journal_referenced-content',
						type: 'boolean',
					},
					{
						label: 'Version History',
						name: '_journal_version-history',
						type: 'boolean',
					},
				],
				label: 'Web Content',
				name: 'PORTLET_DATA_com_liferay_journal_web_portlet_JournalPortlet',
				type: 'boolean',
			},
			{
				controls: [
					{
						label: 'Repositories',
						name: '_document_library_repositories',
						type: 'boolean',
					},
					{
						label: 'Folders',
						name: '_document_library_folders',
						type: 'boolean',
					},
					{
						controls: [
							{
								label: 'Previews and Thumbnails',
								name: '_document_library_previews-and-thumbnails',
								type: 'boolean',
							},
							{
								controls: [
									{
										label: 'Referenced Content Behavior',
										name: '_document_library_referenced-content-behavior',
										options: [
											{
												label: 'Include Always',
												value: 'include-always',
											},
											{
												label: 'Include If Modified',
												value: 'include-if-modified',
											},
										],
										type: 'choice',
									},
								],
								label: 'Referenced Content',
								name: '_document_library_referenced-content',
								type: 'boolean',
							},
						],
						label: 'Documents',
						name: '_document_library_documents',
						type: 'boolean',
					},
					{
						label: 'Document Types',
						name: '_document_library_document-types',
						type: 'boolean',
					},
					{
						label: 'Shortcuts',
						name: '_document_library_shortcuts',
						type: 'boolean',
					},
				],
				label: 'Documents and Media',
				name: 'PORTLET_DATA_com_liferay_document_library_web_portlet_DLAdminPortlet',
				type: 'boolean',
			},
		],
	},
];
