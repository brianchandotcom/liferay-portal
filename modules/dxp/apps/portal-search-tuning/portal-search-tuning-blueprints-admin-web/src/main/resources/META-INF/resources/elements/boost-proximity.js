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
								'${config.field}': {
									decay: '${config.decay}',
									offset: 0,
									origin: {
										lat: '${ipstack.latitude}',
										lon: '${ipstack.longitude}',
									},
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
			en_US:
				'Boost contents tagged close to my location with a Gaussian function',
		},
		enabled: true,
		icon: 'thumbs-up',
		title: {
			en_US: 'Boost Proximity',
		},
	},
	uiConfigurationJSON: [
		{
			defaultValue:
				'expando__keyword__custom_fields__location_geolocation',
			helpText: 'A geopoint field',
			key: 'field',
			label: 'Field',
			type: 'text',
		},
		{
			defaultValue: 0.3,
			key: 'decay',
			label: 'Decay',
			max: 0.99,
			min: 0.01,
			step: 0.1,
			type: 'number',
		},
		{
			defaultValue: 100,
			key: 'scale',
			label: 'Scale',
			min: 0,
			type: 'number',
			unit: 'km',
			unitSuffix: 'km',
		},
		{
			defaultValue: 2,
			key: 'boost',
			label: 'Boost',
			type: 'slider',
		},
	],
};
