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
		rows: [
			{
				columns: [
					{
						fields: [
							{
								fieldName: 'radio',
								label: 'Radio Field',
								options: [
									{
										label: 'Option 1',
										value: 'option1'
									},
									{
										label: 'Option 2',
										value: 'option2'
									}
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
																						columns: []
																					}
																				]
																			}
																		]
																	}
																},
																{
																	fieldName:
																		'name'
																},
																{
																	fieldName:
																		'required'
																},
																{
																	fieldName:
																		'type'
																}
															],
															size: 12
														}
													]
												}
											]
										}
									]
								},
								type: 'radio'
							}
						],
						size: 3
					},
					{
						fields: [],
						size: 9
					}
				]
			},
			{
				columns: [
					{
						fields: [
							{
								fieldName: 'text1',
								label: 'Text Field 1',
								required: true,
								settingsContext: {
									pages: []
								},
								type: 'text'
							},
							{
								fieldName: 'text2',
								label: 'Text Field 2',
								required: true,
								settingsContext: {
									pages: []
								},
								type: 'text'
							}
						],
						size: 4
					},
					{
						fields: [
							{
								fieldName: 'select',
								label: 'Select Field',
								options: [
									{
										label: 'Option 1',
										value: 'option1'
									},
									{
										label: 'Option 2',
										value: 'option2'
									}
								],
								required: true,
								settingsContext: {
									pages: []
								},
								type: 'select'
							}
						],
						size: 6
					},
					{
						fields: [],
						size: 2
					}
				]
			},
			{
				columns: [
					{
						fields: [
							{
								fieldName: 'date',
								label: 'Date Field',
								required: true,
								settingsContext: {
									pages: []
								},
								type: 'date'
							}
						],
						size: 12
					}
				]
			},
			{
				columns: [
					{
						fields: [
							{
								fieldName: 'numeric',
								label: 'Numeric Field',
								required: true,
								settingsContext: {
									pages: []
								},
								type: 'numeric'
							}
						],
						size: 12
					}
				]
			}
		],
		title: 'Untitled name'
	}
];
