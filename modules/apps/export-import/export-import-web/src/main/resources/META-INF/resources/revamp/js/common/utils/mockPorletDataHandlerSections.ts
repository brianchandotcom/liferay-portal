/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const mockPorletDataHandlerSections: {
	name: string;
	portletEntries: {
		portletDescription: string;
		portletId: string;
		portletTitle: string;
	}[];
}[] = [
	{
		name: 'Design',
		portletEntries: [
			{
				portletDescription: 'Configuration for themes',
				portletId: 'theme-settings',
				portletTitle: 'Theme Settings',
			},
			{
				portletDescription: 'Configuration for templates',
				portletId: 'template-settings',
				portletTitle: 'Template Settings',
			},
			{
				portletDescription: 'Site logo settings',
				portletId: 'logo',
				portletTitle: 'Logo',
			},
			{
				portletDescription: 'UI fragments',
				portletId: 'fragments',
				portletTitle: 'Fragments',
			},
			{
				portletDescription: 'General templates',
				portletId: 'templates',
				portletTitle: 'Templates',
			},
			{
				portletDescription: 'Templates for specific pages',
				portletId: 'page-templates',
				portletTitle: 'Page Templates',
			},
		],
	},
	{
		name: 'Site Builder',
		portletEntries: [
			{
				portletDescription: 'Individual page configurations',
				portletId: 'page-settings',
				portletTitle: 'Page Settings',
			},
			{
				portletDescription: 'Management of static content pages',
				portletId: 'static-pages',
				portletTitle: 'Static Pages',
			},
			{
				portletDescription: '404, login, and other utility pages',
				portletId: 'utility-pages',
				portletTitle: 'Utility Pages',
			},
			{
				portletDescription: 'Grouped content collections',
				portletId: 'collections',
				portletTitle: 'Collections',
			},
		],
	},
	{
		name: 'Content & Data',
		portletEntries: [
			{
				portletDescription: 'Articles and web assets',
				portletId: 'web-content',
				portletTitle: 'Web Content',
			},
			{
				portletDescription: 'Blog posts and entries',
				portletId: 'blogs',
				portletTitle: 'Blogs',
			},
			{
				portletDescription: 'File repository and media library',
				portletId: 'documents-media',
				portletTitle: 'Documents and Media',
			},
			{
				portletDescription: 'User input forms and surveys',
				portletId: 'forms',
				portletTitle: 'Forms',
			},
		],
	},
	{
		name: 'Objects',
		portletEntries: [
			{
				portletDescription: 'Custom object data entries',
				portletId: 'object-entries',
				portletTitle: 'Object Entries',
			},
		],
	},
];
