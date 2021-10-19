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
														field: 'displayDate',
													},
												},
											],
										},
									},
									{
										bool: {
											must: [
												{
													range: {
														displayDate_sortable: {
															from:
																'-9223372036854775808',
															include_lower: true,
															include_upper: true,
															to:
																'${time.current_date|dateFormat=timestamp}',
														},
													},
												},
											],
											must_not: [
												{
													exists: {
														field: 'expirationDate',
													},
												},
											],
										},
									},
									{
										bool: {
											must: [
												{
													range: {
														displayDate_sortable: {
															from:
																'-9223372036854775808',
															include_lower: true,
															include_upper: true,
															to:
																'${time.current_date|dateFormat=timestamp}',
														},
													},
												},
												{
													range: {
														expirationDate_sortable: {
															from:
																'${time.current_date|dateFormat=timestamp}',
															include_lower: true,
															include_upper: true,
															to:
																'9223372036854775807',
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
				},
			},
		],
		conditions: {},
		description: {
			en_US: 'Show only contents with a valid display date',
		},
		enabled: true,
		icon: 'filter',
		title: {
			en_US: 'Scheduling Aware',
		},
	},
	uiConfigurationJSON: {},
};
