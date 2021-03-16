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
						terms: {
							scopeGroupId: '${config.scope_group_ids}',
						},
					},
				},
				type: 'wrapper',
			},
		],
		conditions: [],
		description: {
			en_US: 'Limit search scope to the given sites',
		},
		enabled: true,
		icon: 'filter',
		title: {
			en_US: 'Limit Search to These Sites',
		},
	},
	uiConfigurationJSON: [
		{
			helpText: 'Add group IDs',
			key: 'scope_group_ids',
			label: 'Group IDs',
			type: 'multiselect',
		},
	],
};
