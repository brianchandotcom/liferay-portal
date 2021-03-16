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
		category: 'boost',
		clauses: [
			{
				context: 'query',
				occur: 'should',
				query: {
					query: {
						bool: {
							must: [
								{
									terms: {
										entryClasslabel: [
											'com.liferay.portal.kernel.model.Layout',
											'com.liferay.journal.model.JournalArticle',
										],
									},
								},
								{
									term: {
										defaultLanguageId: {
											boost: '${config.boost}',
											value: '${context.language_id}',
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
			en_US:
				'Boost contents having the current session language as the default language',
		},
		enabled: true,
		icon: 'thumbs-up',
		title: {
			en_US: 'Boost Contents for the Current Language',
		},
	},
	uiConfigurationJSON: [
		{
			defaultValue: 20,
			key: 'boost',
			label: 'Boost',
			type: 'slider',
		},
	],
};
