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
							term: {
								status: 0,
							},
						},
					},
				},
			},
		],
		conditions: {},
		description: {
			en_US: 'Limit search to contents that have been published',
		},
		enabled: true,
		icon: 'filter',
		title: {
			en_US: 'Limit Search to Published Contents',
		},
	},
	uiConfigurationJSON: {},
};
