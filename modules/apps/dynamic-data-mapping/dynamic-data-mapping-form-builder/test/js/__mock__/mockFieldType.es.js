/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

export default {
	dataSourceType: 'manual',
	description: 'Single line or multiline text area.',
	group: 'basic',
	icon: 'text',
	initialConfig_: {
		locale: 'en_US',
	},
	label: 'Text Field',
	name: 'TextField',
	required: true,
	settingsContext: {
		pages: [
			{
				rows: [
					{
						columns: [
							{
								fields: [
									{
										fieldName: 'label',
										localizable: true,
										localizedValue: {
											en_US: 'Text Field',
										},
										type: 'text',
										value: 'Text Field',
										visible: true,
									},
									{
										fieldName: 'name',
										value: 'TextField',
										visible: true,
									},
									{
										fieldName: 'required',
										value: true,
										visible: true,
									},
									{
										fieldName: 'showLabel',
										value: false,
										visible: true,
									},
								],
							},
						],
					},
				],
			},
		],
	},
	showLabel: true,
	type: 'text',
};
