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

export default [
	{
		description: 'Add a short description for this page.',
		localizedDescription: {
			en_US: 'Add a short description for this page.',
		},
		localizedTitle: {
			en_US: 'Page title',
		},
		rows: [
			{
				columns: [
					{
						fields: [
							{
								fieldName: 'radio',
								instanceId: '123njndk1',
								label: 'Radio Field',
								options: [
									{
										label: 'Option 1',
										value: 'option1',
									},
									{
										label: 'Option 2',
										value: 'option2',
									},
								],
								settingsContext: {
									pages: [
										{
											rows: [
												{
													columns: [
														{
															fields: [
																{
																	fieldName:
																		'label',
																	localizable: true,
																	settingsContext: {
																		pages: [
																			{
																				rows: [
																					{
																						columns: [],
																					},
																				],
																			},
																		],
																	},
																},
																{
																	fieldName:
																		'name',
																},
																{
																	fieldName:
																		'required',
																},
																{
																	fieldName:
																		'type',
																},
															],
															size: 12,
														},
													],
												},
											],
										},
									],
								},
								type: 'radio',
							},
						],
						size: 3,
					},
					{
						fields: [],
						size: 9,
					},
				],
			},
			{
				columns: [
					{
						fields: [
							{
								fieldName: 'text1',
								instanceId: '1fnjndk1',
								label: 'Text Field 1',
								required: true,
								settingsContext: {
									pages: [],
								},
								type: 'text',
							},
							{
								fieldName: 'text2',
								instanceId: '193jndk1',
								label: 'Text Field 2',
								required: true,
								settingsContext: {
									pages: [],
								},
								type: 'text',
							},
						],
						size: 4,
					},
					{
						fields: [
							{
								fieldName: 'select',
								instanceId: '1fnjfqk1',
								label: 'Select Field',
								options: [
									{
										label: 'Option 1',
										value: 'option1',
									},
									{
										label: 'Option 2',
										value: 'option2',
									},
								],
								required: true,
								settingsContext: {
									pages: [],
								},
								type: 'select',
							},
						],
						size: 6,
					},
					{
						fields: [],
						size: 2,
					},
				],
			},
			{
				columns: [
					{
						fields: [
							{
								fieldName: 'date',
								instanceId: '1ffqwjndk1',
								label: 'Date Field',
								required: true,
								settingsContext: {
									pages: [],
								},
								type: 'date',
							},
						],
						size: 12,
					},
				],
			},
			{
				columns: [
					{
						fields: [
							{
								fieldName: 'numeric',
								instanceId: '1ljqjndk1',
								label: 'Numeric Field',
								required: true,
								settingsContext: {
									pages: [],
								},
								type: 'numeric',
							},
						],
						size: 12,
					},
				],
			},
		],
		title: 'Page title',
	},
];
