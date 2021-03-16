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

export default {
	elementTemplateJSON: {
		category: 'hide',
		clauses: [
			{
				context: 'query',
				occur: 'filter',
				query: {
					query: {
						bool: {
							must_not: [
								{
									term: {
										'${config.field}': {
											value: '${config.value}',
										},
									},
								},
							],
						},
					},
				},
				type: 'wrapper',
			},
		],
		conditions: [],
		description: {
			en_US: 'Hide results by an exact term match',
		},
		enabled: true,
		icon: 'hidden',
		title: {
			en_US: 'Hide by an Exact Term Match',
		},
	},
	uiConfigurationJSON: [
		{
			defaultValue: {
				field: '',
				locale: '',
			},
			key: 'field',
			label: 'Field',
			type: 'field',
			typeOptions: [
				{
					label: 'Entry Class PK',
					value: 'entryClassPK',
				},
				{
					label: 'Folder ID',
					value: 'folderId',
				},
				{
					label: 'Group ID',
					value: 'groupId',
				},
				{
					label: 'User ID',
					value: 'userId',
				},
				{
					label: 'Web Content Article ID',
					value: 'articleId_String_sortable',
				},
			],
		},
		{
			key: 'value',
			label: 'Value',
			type: 'text',
		},
	],
};
