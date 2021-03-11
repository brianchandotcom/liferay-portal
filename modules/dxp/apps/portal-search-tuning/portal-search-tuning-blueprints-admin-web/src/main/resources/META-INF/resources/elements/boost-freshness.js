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
						function_score: {
							boost: '${configuration.boost}',
							gauss: {
								modified: {
									decay: '${configuration.decay}',
									offset: '${configuration.offset}',
									origin:
										'${time.current_date|dateFormat=yyyyMMddHHmmss}',
									scale: '${configuration.scale}',
								},
							},
						},
					},
				},
				type: 'wrapper',
			},
		],
		conditions: [],
		description: {
			en_US: 'Give a gaussian boost to contents modified recently',
		},
		enabled: true,
		icon: 'thumbs-up',
		title: {
			en_US: 'Boost Freshness',
		},
	},
	uiConfigurationJSON: {
		fieldSets: [
			{
				fields: [
					{
						defaultValue: 0.5,
						label: 'Decay',
						name: 'decay',
						type: 'number',
						typeOptions: {
							max: 0.99,
							min: 0.01,
							step: 0.1,
						},
					},
					{
						defaultValue: 0,
						label: 'Offset',
						name: 'offset',
						type: 'number',
						typeOptions: {
							min: 0,
							unit: 'days',
							unitSuffix: 'd',
						},
					},
					{
						defaultValue: 10,
						label: 'Scale',
						name: 'scale',
						type: 'number',
						typeOptions: {
							min: 0,
							unit: 'days',
							unitSuffix: 'd',
						},
					},
					{
						defaultValue: 2,
						label: 'Boost',
						name: 'boost',
						type: 'slider',
					},
				],
			},
		],
	},
};
