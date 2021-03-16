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
		category: 'parameters',
		clauses: [
			{
				context: 'query',
				occur: 'filter',
				query: {
					query: {
						bool: {
							must: [
								{
									range: {
										modified_sortable: {
											from:
												'${parameter.time|dateFormat=timestamp}',
											include_lower: true,
											include_upper: true,
											to:
												'${time.current_date|dateFormat=timestamp}',
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
				'Enables filtering results with time parameter (requires configuring parameter ‘time’ of type ‘date’)',
		},
		enabled: true,
		icon: 'filter',
		title: {
			en_US: 'Filter by Time Range',
		},
	},
};
