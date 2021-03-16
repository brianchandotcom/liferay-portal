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
		category: 'filter',
		clauses: [
			{
				context: 'query',
				occur: 'filter',
				query: {
					query: {
						bool: {
							should: [
								{
									bool: {
										must_not: [
											{
												term: {
													entryClasslabel: {
														value:
															'com.liferay.journal.model.JournalArticle',
													},
												},
											},
										],
									},
								},
								{
									bool: {
										must: [
											{
												term: {
													entryClasslabel: {
														value:
															'com.liferay.journal.model.JournalArticle',
													},
												},
											},
											{
												term: {
													head: {
														value: true,
													},
												},
											},
										],
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
			en_US: 'Show only the latest HEAD version of Web Content articles',
		},
		enabled: true,
		icon: 'filter',
		title: {
			en_US: 'Limit Search to HEAD Version',
		},
	},
	uiConfigurationJSON: [],
};
