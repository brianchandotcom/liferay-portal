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
												exists: {
													field: 'ctCollectionId',
												},
											},
										],
									},
								},
								{
									term: {
										ctCollectionId: {
											value:
												'${context.ct_collection_id}',
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
		description: {
			en_US:
				'When Publications feature is enabled, search only contents on my current timeline',
		},
		enabled: true,
		icon: 'filter',
		title: {
			en_US: 'Publications Aware',
		},
	},
	uiConfigurationJSON: [],
};
