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
	sxpElementTemplateJSON: {
		category: 'filter',
		clauses: [
			{
				context: 'query',
				occur: 'filter',
				query: {
					wrapper: {
						query: {
							bool: {
								should: [
									{
										bool: {
											must_not: [
												{
													exists: {
														field: 'stagingGroup',
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
														stagingGroup: false,
													},
												},
											],
										},
									},
								],
							},
						},
					},
				},
			},
		],
		conditions: {
			equals: {
				parameter_name: '${context.is_staging_group}',
				value: false,
			},
		},
		description: {
			en_US:
				'Show only published contents on live sites, published and staged contents on staging sites',
		},
		enabled: true,
		icon: 'filter',
		title: {
			en_US: 'Staging Aware',
		},
	},
	uiConfigurationJSON: {},
};
