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
							boost: '${config.boost}',
							gauss: {
								modified: {
									decay: '${config.decay}',
									offset: '${config.offset}',
									origin:
										'${time.current_date|dateFormat=yyyyMMddHHmmss}',
									scale: '${config.scale}',
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
	uiConfigurationJSON: [
		{
			defaultValue: 0.5,
			key: 'decay',
			label: 'Decay',
			max: 0.99,
			min: 0.01,
			step: 0.1,
			type: 'number',
		},
		{
			defaultValue: 0,
			key: 'offset',
			label: 'Offset',
			min: 0,
			type: 'number',
			unit: 'days',
			unitSuffix: 'd',
		},
		{
			defaultValue: 10,
			key: 'scale',
			label: 'Scale',
			min: 0,
			type: 'number',
			unit: 'days',
			unitSuffix: 'd',
		},
		{
			defaultValue: 2,
			key: 'boost',
			label: 'Boost',
			type: 'slider',
		},
	],
};
