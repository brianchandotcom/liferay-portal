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

/**
 * Keep these in sync with the elements in
 * portal-search-tuning-blueprints-resources/
 * src/main/resources/META-INF/search/elements
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
								'${configuration.field}': {
									decay: '${configuration.decay}',
									offset: 0,
									origin: {
										lat: '${ipstack.latitude}',
										lon: '${ipstack.longitude}',
									},
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
			en_US:
				'Boost contents tagged close to my location with a Gaussian function',
		},
		enabled: true,
		icon: 'thumbs-up',
		title: {
			en_US: 'Boost Proximity',
		},
	},
	uiConfigurationJSON: {
		fieldSets: [
			{
				fields: [
					{
						defaultValue:
							'expando__keyword__custom_fields__location_geolocation',
						helpText: 'A geopoint field',
						label: 'Field',
						name: 'field',
						type: 'text',
					},
					{
						defaultValue: 0.3,
						label: 'Decay',
						name: 'decay',
						type: 'slider',
						typeOptions: {
							max: 0.99,
							min: 0.01,
							step: 0.01,
						},
					},
					{
						defaultValue: 100,
						label: 'Scale',
						name: 'scale',
						type: 'number',
						typeOptions: {
							min: 0,
							unit: 'km',
							unitSuffix: 'km',
						},
					},
					{
						defaultValue: 2,
						label: 'Boost',
						name: 'boost',
						type: 'number',
						typeOptions: {
							min: 0,
						},
					},
				],
			},
		],
	},
};
