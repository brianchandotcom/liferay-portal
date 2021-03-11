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
										assetCategoryIds: {
											value:
												'${configuration.asset_category_id}',
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
			en_US: 'Hide contents in a category',
		},
		enabled: true,
		icon: 'hidden',
		title: {
			en_US: 'Hide Contents in a Category',
		},
	},
	uiConfigurationJSON: {
		fieldSets: [
			{
				fields: [
					{
						helpText: 'Add asset category ID',
						label: 'Asset Category',
						name: 'asset_category_id',
						type: 'number',
					},
				],
			},
		],
	},
};
