/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export interface PortletDataHandlerBoolean {
	label: string;
	name: string;
	portletDataHandlerControls?: PortletDataHandlerControl[];
	type: 'Boolean';
}

export interface PortletDataHandlerChoice {
	choices: {label: string; name: string}[];
	label: string;
	name: string;
	type: 'Choice';
}

export type PortletDataHandlerControl =
	| PortletDataHandlerBoolean
	| PortletDataHandlerChoice;

export type PortletDataHandler = Omit<PortletDataHandlerBoolean, 'type'>;

export interface PortletDataHandlerSection {
	label: string;
	name: string;
	portletDataHandlers: PortletDataHandler[];
}

export interface ExportPreview {
	portletDataHandlerSections: PortletDataHandlerSection[];
}

const sections: PortletDataHandlerSection[] = [
	{
		label: 'Design',
		name: 'category.site_administration.design',
		portletDataHandlers: [
			{
				label: 'Theme Settings',
				name: 'THEME_REFERENCE',
			},
			{
				label: 'Logo',
				name: 'LOGO',
			},
			{
				label: 'Fragments',
				name: 'PORTLET_DATA_com_liferay_fragment_web_portlet_FragmentPortlet',
			},
		],
	},
	{
		label: 'Site Builder',
		name: 'category.site_administration.build',
		portletDataHandlers: [
			{
				label: 'Pages',
				name: 'PORTLET_DATA_com_liferay_layout_admin_web_portlet_GroupPagesPortlet',
			},
		],
	},
	{
		label: 'Content & Data',
		name: 'category.site_administration.content',
		portletDataHandlers: [
			{
				label: 'Web Content',
				name: 'PORTLET_DATA_com_liferay_journal_web_portlet_JournalPortlet',
				portletDataHandlerControls: [
					{
						label: 'Web Content',
						name: '_journal_web-content',
						type: 'Boolean',
					},
					{
						label: 'Referenced Content',
						name: '_journal_referenced-content',
						portletDataHandlerControls: [
							{
								choices: [
									{
										label: 'Include Always',
										name: 'include-always',
									},
									{
										label: 'Include If Modified',
										name: 'include-if-modified',
									},
								],
								label: 'Referenced Content Behavior',
								name: '_journal_referenced-content-behavior',
								type: 'Choice',
							},
						],
						type: 'Boolean',
					},
					{
						label: 'Version History',
						name: '_journal_version-history',
						type: 'Boolean',
					},
				],
			},
			{
				label: 'Documents and Media',
				name: 'PORTLET_DATA_com_liferay_document_library_web_portlet_DLAdminPortlet',
				portletDataHandlerControls: [
					{
						label: 'Repositories',
						name: '_document_library_repositories',
						type: 'Boolean',
					},
					{
						label: 'Folders',
						name: '_document_library_folders',
						type: 'Boolean',
					},
					{
						label: 'Documents',
						name: '_document_library_documents',
						portletDataHandlerControls: [
							{
								label: 'Previews and Thumbnails',
								name: '_document_library_previews-and-thumbnails',
								type: 'Boolean',
							},
							{
								label: 'Referenced Content',
								name: '_document_library_referenced-content',
								portletDataHandlerControls: [
									{
										choices: [
											{
												label: 'Include Always',
												name: 'include-always',
											},
											{
												label: 'Include If Modified',
												name: 'include-if-modified',
											},
										],
										label: 'Referenced Content Behavior',
										name: '_document_library_referenced-content-behavior',
										type: 'Choice',
									},
								],
								type: 'Boolean',
							},
						],
						type: 'Boolean',
					},
					{
						label: 'Document Types',
						name: '_document_library_document-types',
						type: 'Boolean',
					},
					{
						label: 'Shortcuts',
						name: '_document_library_shortcuts',
						type: 'Boolean',
					},
				],
			},
		],
	},
];

export const mockExportPreview: ExportPreview = {
	portletDataHandlerSections: sections,
};
