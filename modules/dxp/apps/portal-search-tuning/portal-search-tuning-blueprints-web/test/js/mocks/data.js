/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

export const FETCH_URL = 'https://fetchURL';
export const SUGGEST_URL = 'https://suggestURL';

export const RESOURCE = {
	facets: [
		{
			facetLabel: 'Asset Type',
			parameterName: 'entryClassName',
			values: [
				{
					text: 'com.liferay.wiki.model.WikiPage',
					value: 'com.liferay.wiki.model.WikiPage',
				},
				{
					text:
						'com.liferay.document.library.kernel.model.DLFileEntry',
					value:
						'com.liferay.document.library.kernel.model.DLFileEntry',
				},
				{
					text: 'com.liferay.message.boards.model.MBMessage',
					value: 'com.liferay.message.boards.model.MBMessage',
				},
			],
		},
		{
			facetLabel: 'Format',
			parameterName: 'extension',
			values: [
				{
					text: 'pdf',
					value: 'pdf',
				},
				{
					text: 'zip',
					value: 'zip',
				},
				{
					text: 'MS Powerpoint',
					value: 'MS Powerpoint',
				},
			],
		},
		{
			facetLabel: 'Tag',
			parameterName: 'assetTagNames',
			values: [
				{
					text: 'dxp',
					value: 'dxp',
				},
				{
					text: 'technical support',
					value: 'technical support',
				},
				{
					text: 'fix pack',
					value: 'fix pack',
				},
				{
					text: 'pull request tester',
					value: 'pull request tester',
				},
				{
					text: 'tickets',
					value: 'tickets',
				},
			],
		},
		{
			facetLabel: 'User',
			parameterName: 'userName',
			values: [
				{
					text: 'test user1',
					value: 'test user1',
				},
				{
					text: 'test user2',
					value: 'test user2',
				},
				{
					text: 'test user3',
					value: 'test user3',
				},
			],
		},
	],
	items: [
		{
			result: {
				assetTagNames: ['test', 'staging', 'official'],
				content_highlight:
					'Staging <liferay-hl>Test</liferay-hl> Plan Staging <liferay-hl>Test</liferay-hl>',
				date: '7/23/18',
				description: 'Staging Test Plan Staging Test Cases Upgrade',
				entryClassPK: '898797',
				official_content: 'true',
				title: 'Title Staging Testing',
				type: 'Web Content',
				viewURL: '/',
			},
		},
		{
			result: {
				date: '5/22/19',
				description: 'Creating a LRSUPPORT Ticket',
				entryClassPK: '1360528',
				title: 'Title Support Testing',
				type: 'Blog',
				viewURL: '/',
			},
		},
		{
			result: {
				assetTagNames: [
					'testing',
					'fixpack',
					'gauntlet',
					'cnqa test tips',
				],
				date: '1/17/18',
				description:
					'Definition Gauntlets that the ER team trigger everyday',
				entryClassPK: '682986',
				title: 'Title Gauntlet Testing',
				type: 'Web Content',
				viewURL: '/',
			},
		},
	],
	meta: {
		executionTime: '0.109',
		keywords: 'test',
		start: 0,
		totalHits: 1992,
	},
	pagination: {
		activePage: 1,
		totalPages: 200,
	},
};

export const SELECTED_FACETS = {
	entryClassName: RESOURCE.facets[0].values,
};

export const SUGGESTIONS = {
	suggestions: ['keyword', 'suggestion'],
};
