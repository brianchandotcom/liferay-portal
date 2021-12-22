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

import {act, fireEvent, render, waitFor} from '@testing-library/react';
import Color from 'dynamic-data-mapping-form-field-type/ColorPicker/ColorPicker.es';
import Date from 'dynamic-data-mapping-form-field-type/DatePicker/DatePicker.es';
import DocumentLibrary from 'dynamic-data-mapping-form-field-type/DocumentLibrary/DocumentLibrary.es';
import Grid from 'dynamic-data-mapping-form-field-type/Grid/Grid.es';
import Image from 'dynamic-data-mapping-form-field-type/ImagePicker/ImagePicker.es';
import Numeric from 'dynamic-data-mapping-form-field-type/Numeric/Numeric';
import RichText from 'dynamic-data-mapping-form-field-type/RichText/RichText.es';
import Select from 'dynamic-data-mapping-form-field-type/Select/Select.es';
import Text from 'dynamic-data-mapping-form-field-type/Text/Text.es';
import React from 'react';

import {Editor} from '../../../../../../src/main/resources/META-INF/resources/data_layout_builder/js/components/rules/editor/Editor.es';
import {DEFAULT_RULE} from '../../../../../../src/main/resources/META-INF/resources/data_layout_builder/js/components/rules/editor/config.es';
import {
	FIELDS,
	FIELDS_TYPES,
	NUMBER_OPERATORS,
	NUMBER_TYPE_FIELDS,
	OPERATORS_BY_TYPE,
	ROLES,
	STRING_DATATYPE_FIELDS,
	TEXT_OPERATORS,
	UPLOAD_TYPE_FIELD,
	USER_OPERATORS,
} from '../../../../../mock/fields.es';

global.fetch.enableFetchMocks();

const globalLanguageDirection = Liferay.Language.direction;

const pages = [
	{
		label: '1 Page title',
		name: '0',
		value: '0',
	},
	{
		label: '2 Page title',
		name: '1',
		value: '1',
	},
	{
		label: '3 Page title',
		name: '2',
		value: '2',
	},
];

const conditions = [
	{
		operands: [
			{
				type: '',
				value: '',
			},
		],
		operator: '',
	},
	{
		operands: [
			{
				type: '',
				value: '',
			},
		],
		operator: '',
	},
];

const newRule = {
	...DEFAULT_RULE,
	conditions,
};

const defaultProps = (fieldsList = FIELDS) => {
	return {
		allowActions: [
			'auto-fill',
			'calculate',
			'enable',
			'jump-to-page',
			'require',
			'show',
		],
		dataProvider: [
			{
				id: '39421',
				label: 'Get countries',
				name: 'Get countries',
				uuid: 'a6ab90fd-c4ac-b91b-c9a5-cf756170d9d3',
				value: '39421',
			},
		],
		dataProviderInstanceParameterSettingsURL:
			'/o/dynamic-data-mapping-form-builder-provider-instance-parameter-settings/',
		fields: fieldsList,
		operatorsByType: OPERATORS_BY_TYPE,
		pages,
		roles: ROLES,
		rule: DEFAULT_RULE,
	};
};

describe('Editor', () => {
	const originalLiferayLoader = window.Liferay.Loader;

	beforeEach(() => {
		global.fetch.mockResponse(JSON.stringify(FIELDS_TYPES));
	});

	beforeAll(() => {
		Liferay.Language.direction = {
			en_US: 'rtl',
		};

		window.Liferay = {
			...window.Liferay,
			Loader: {
				require: ([fieldModule], resolve) => {
					switch (fieldModule) {
						case 'color':
							resolve({default: Color});
							break;
						case 'date':
							resolve({default: Date});
							break;
						case 'grid':
							resolve({default: Grid});
							break;
						case 'image':
							resolve({default: Image});
							break;
						case 'numeric':
							resolve({default: Numeric});
							break;
						case 'rich_text':
							resolve({default: RichText});
							break;
						case 'select':
							resolve({default: Select});
							break;
						case 'text':
							resolve({default: Text});
							break;
						case 'document_library':
							resolve({default: DocumentLibrary});
							break;
						default:
							break;
					}
				},
			},
		};
	});

	afterAll(() => {
		window.Liferay.Loader = originalLiferayLoader;

		Liferay.Language.direction = globalLanguageDirection;
	});

	describe('Editor', () => {
		describe('Conditions', () => {
			describe('Field operator', () => {
				it.each(STRING_DATATYPE_FIELDS)(
					'shows operators related to texts when field left is a %p',
					async ({type}) => {
						const props = defaultProps();
						const {getByText} = render(
							<Editor
								{...props}
								onChange={() => {}}
								onValidator={() => {}}
							/>
						);

						await waitFor(() => {
							return document.querySelector('.option-selected');
						});

						const fieldLeft = document
							.querySelectorAll('.timeline-item')[1]
							.querySelectorAll('.ddm-field')[0]
							.querySelector('.option-selected');
						fireEvent.click(fieldLeft);

						await waitFor(() => {
							return document.querySelector(
								'.dropdown-menu.show'
							);
						});

						fireEvent.click(getByText(type));

						TEXT_OPERATORS.forEach((operator) => {
							expect(getByText(operator)).toBeTruthy();
						});
					}
				);

				it.each(NUMBER_TYPE_FIELDS)(
					'shows operators related to numbers when field left is a %p',
					async ({type}) => {
						const props = defaultProps();
						const {getByText} = render(
							<Editor
								{...props}
								onChange={() => {}}
								onValidator={() => {}}
							/>
						);

						await waitFor(() => {
							return document.querySelector('.option-selected');
						});

						const fieldLeft = document
							.querySelectorAll('.timeline-item')[1]
							.querySelectorAll('.ddm-field')[0]
							.querySelector('.option-selected');

						await act(async () => {
							fireEvent.click(fieldLeft);
						});

						await waitFor(() => {
							return document.querySelector(
								'.dropdown-menu.show'
							);
						});

						await act(async () => {
							fireEvent.click(getByText(type));
						});

						NUMBER_OPERATORS.forEach((operator) => {
							expect(getByText(operator)).toBeTruthy();
						});
					}
				);

				it('shows operators related to roles when field left is an User', async () => {
					const props = defaultProps();
					const {getByText} = render(
						<Editor
							{...props}
							onChange={() => {}}
							onValidator={() => {}}
						/>
					);

					await waitFor(() => {
						return document.querySelector('.option-selected');
					});

					const fieldLeft = document
						.querySelectorAll('.timeline-item')[1]
						.querySelectorAll('.ddm-field')[0]
						.querySelector('.option-selected');

					await act(async () => {
						fireEvent.click(fieldLeft);
					});

					await waitFor(() => {
						return document.querySelector('.dropdown-menu.show');
					});

					await act(async () => {
						fireEvent.click(getByText('user'));
					});

					USER_OPERATORS.forEach((operator) => {
						expect(getByText(operator)).toBeTruthy();
					});
				});
			});

			describe('Binary operations', () => {
				it.each(
					STRING_DATATYPE_FIELDS.concat(NUMBER_TYPE_FIELDS).concat(
						UPLOAD_TYPE_FIELD
					)
				)(
					'shows a related field input on the right operand when the left operand is a %p and action type is value',
					async ({selector, type}) => {
						const props = defaultProps();
						const mockIsSignedIn = jest.fn();

						/** For document_library field to be displayed a user must by signed in */
						Liferay.ThemeDisplay.isSignedIn = mockIsSignedIn;
						const {getByText} = render(
							<Editor
								{...props}
								onChange={() => {}}
								onValidator={() => {}}
							/>
						);

						await waitFor(() => {
							return document.querySelector('.option-selected');
						});

						const fieldLeft = document
							.querySelectorAll('.timeline-item')[1]
							.querySelectorAll('.ddm-field')[0]
							.querySelector('.option-selected');

						await act(async () => {
							fireEvent.click(fieldLeft);
						});

						await waitFor(() => {
							return document.querySelector(
								'.dropdown-menu.show'
							);
						});

						fireEvent.click(getByText(type));

						const operator = document
							.querySelectorAll('.timeline-item')[1]
							.querySelectorAll('.ddm-field')[1]
							.querySelector('.option-selected');

						await act(async () => {
							fireEvent.click(operator);
						});

						await act(async () => {
							fireEvent.click(getByText('Is equal to'));
						});

						const actionsType = document
							.querySelectorAll('.timeline-item')[1]
							.querySelectorAll('.ddm-field')[2]
							.querySelector('.option-selected');

						fireEvent.click(actionsType);

						fireEvent.click(getByText('value'));

						await waitFor(() => {
							return document.querySelectorAll('.ddm-field')[3];
						});

						expect(
							document
								.querySelectorAll('.timeline-item')[1]
								.querySelectorAll('.ddm-field')[3]
								.querySelector(selector)
						).toBeTruthy();
					}
				);

				it('shows all others fields when action type is Other field', async () => {
					const props = defaultProps();
					const mockIsSignedIn = jest.fn();

					Liferay.ThemeDisplay.isSignedIn = mockIsSignedIn;
					const {getByText, queryAllByText} = render(
						<Editor
							{...props}
							onChange={() => {}}
							onValidator={() => {}}
						/>
					);

					await waitFor(() => {
						return document.querySelector('.option-selected');
					});

					const fieldLeft = document
						.querySelectorAll('.timeline-item')[1]
						.querySelectorAll('.ddm-field')[0]
						.querySelector('.option-selected');

					fireEvent.click(fieldLeft);

					await waitFor(() => {
						return document.querySelector('.dropdown-menu.show');
					});

					fireEvent.click(getByText('text'));

					const operator = document
						.querySelectorAll('.timeline-item')[1]
						.querySelectorAll('.ddm-field')[1]
						.querySelector('.option-selected');

					fireEvent.click(operator);

					fireEvent.click(getByText('Is equal to'));

					const actionsType = document
						.querySelectorAll('.timeline-item')[1]
						.querySelectorAll('.ddm-field')[2]
						.querySelector('.option-selected');

					fireEvent.click(actionsType);

					fireEvent.click(getByText('other-field'));

					await waitFor(() => {
						return document.querySelector('.ddm-field');
					});

					const fieldRight = document
						.querySelectorAll('.timeline-item')[1]
						.querySelectorAll('.ddm-field')[3]
						.querySelector('.option-selected');

					fireEvent.click(fieldRight);

					await waitFor(() => {
						return document.querySelector('.dropdown-menu.show');
					});

					const allFields = STRING_DATATYPE_FIELDS.concat(
						NUMBER_TYPE_FIELDS
					).concat(UPLOAD_TYPE_FIELD);

					allFields.forEach(({type}) => {
						const fieldOption = queryAllByText(
							(content, element) => {
								return (
									element.tagName.toLowerCase() ===
										'button' && content.startsWith(type)
								);
							}
						);
						expect(fieldOption).toBeTruthy();
						expect(fieldOption.length).toBe(2);
					});
				});
			});

			describe('Conditions logical operatores', () => {
				it('shows the OR/AND select disabled by default', async () => {
					const props = defaultProps();
					render(
						<Editor
							{...props}
							onChange={() => {}}
							onValidator={() => {}}
						/>
					);

					await waitFor(() => {
						return document.querySelector('.option-selected');
					});

					expect(
						document.querySelector(
							'.timeline-first .dropdown-toggle'
						).disabled
					).toBe(true);
				});

				it('enables the OR/AND select when there are more than one condition', async () => {
					const props = defaultProps();
					render(
						<Editor
							{...props}
							onChange={() => {}}
							onValidator={() => {}}
							rule={newRule}
						/>
					);

					await waitFor(() => {
						return document.querySelector('.option-selected');
					});

					expect(
						document.querySelector(
							'.timeline-first .dropdown-toggle'
						).disabled
					).toBe(false);
				});
			});

			describe('Add/Remove conditions', () => {
				it('shows the container trash when there are more than one condition', () => {
					const props = defaultProps();
					render(
						<Editor
							{...props}
							onChange={() => {}}
							onValidator={() => {}}
						/>
					);

					expect(
						document.querySelectorAll('.container-trash')[0]
					).toBeFalsy();

					fireEvent.click(
						document.querySelectorAll(
							'.timeline-increment button'
						)[0]
					);

					expect(
						document.querySelectorAll('.container-trash')[0]
					).toBeTruthy();
				});
			});
		});

		describe('Actions', () => {
			it('shows the action types', async () => {
				const props = defaultProps();
				const {getByText} = render(
					<Editor
						{...props}
						onChange={() => {}}
						onValidator={() => {}}
					/>
				);

				await waitFor(() => {
					return document.querySelector('.option-selected');
				});

				const actionType = document
					.querySelectorAll('.timeline-item')[4]
					.querySelectorAll('.ddm-field')[0]
					.querySelector('.option-selected');

				fireEvent.click(actionType);

				await waitFor(() => {
					return document.querySelector('.dropdown-menu.show');
				});

				expect(getByText('show')).toBeTruthy();
				expect(getByText('enable')).toBeTruthy();
				expect(getByText('require')).toBeTruthy();
				expect(getByText('autofill')).toBeTruthy();
				expect(getByText('calculate')).toBeTruthy();
			});

			describe('Add/Remove actions', () => {
				it('shows the container trash when there are more than one action', () => {
					const props = defaultProps();
					render(
						<Editor
							{...props}
							onChange={() => {}}
							onValidator={() => {}}
						/>
					);

					expect(
						document.querySelectorAll('.container-trash')[1]
					).toBeFalsy();

					fireEvent.click(
						document.querySelectorAll(
							'.timeline-increment button'
						)[1]
					);

					expect(
						document.querySelectorAll('.container-trash')[1]
					).toBeTruthy();
				});
			});

			it.each(['show', 'require', 'enable'])(
				'shows all fields on target dropdown when the type is %p',
				async (type) => {
					const fields = STRING_DATATYPE_FIELDS.concat(
						NUMBER_TYPE_FIELDS
					).concat(UPLOAD_TYPE_FIELD);
					const props = defaultProps();
					const {getByText, queryAllByText} = render(
						<Editor
							{...props}
							onChange={() => {}}
							onValidator={() => {}}
						/>
					);

					await waitFor(() => {
						return document.querySelector('.option-selected');
					});

					const actionType = document
						.querySelectorAll('.timeline-item')[4]
						.querySelectorAll('.ddm-field')[0]
						.querySelector('.option-selected');

					fireEvent.click(actionType);

					await waitFor(() => {
						return document.querySelector('.dropdown-menu.show');
					});

					fireEvent.click(getByText(type));

					const actionTarget = document
						.querySelectorAll('.timeline-item')[4]
						.querySelectorAll('.ddm-field')[1]
						.querySelector('.option-selected');

					fireEvent.click(actionTarget);

					fields.forEach(({type}) => {
						const fieldOccurences = queryAllByText(type);
						expect(fieldOccurences).toHaveLength(2);
					});
				}
			);
			it('shows dataprovider when autofill action is selected', async () => {
				const props = defaultProps();
				const {getByText} = render(
					<Editor
						{...props}
						onChange={() => {}}
						onValidator={() => {}}
					/>
				);

				await waitFor(() => {
					return document.querySelector('.option-selected');
				});

				const actionType = document
					.querySelectorAll('.timeline-item')[4]
					.querySelectorAll('.ddm-field')[0]
					.querySelector('.option-selected');

				fireEvent.click(actionType);

				await waitFor(() => {
					return document.querySelector('.dropdown-menu.show');
				});

				fireEvent.click(getByText('autofill'));

				const actionTarget = document
					.querySelectorAll('.timeline-item')[4]
					.querySelectorAll('.ddm-field')[1]
					.querySelector('.option-selected');

				fireEvent.click(actionTarget);

				expect(getByText('Get countries')).toBeTruthy();
			});

			it('shows the calculator area when calculate action is selected', async () => {
				const props = defaultProps();
				const {getByText, queryAllByText} = render(
					<Editor
						{...props}
						onChange={() => {}}
						onValidator={() => {}}
					/>
				);

				await waitFor(() => {
					return document.querySelector('.option-selected');
				});

				const actionType = document
					.querySelectorAll('.timeline-item')[4]
					.querySelectorAll('.ddm-field')[0]
					.querySelector('.option-selected');

				fireEvent.click(actionType);

				await waitFor(() => {
					return document.querySelector('.dropdown-menu.show');
				});

				fireEvent.click(getByText('calculate'));

				const actionTarget = document
					.querySelectorAll('.timeline-item')[4]
					.querySelectorAll('.ddm-field')[1]
					.querySelector('.option-selected');

				expect(queryAllByText('integer')).toHaveLength(2);
				expect(queryAllByText('double')).toHaveLength(2);

				fireEvent.click(actionTarget);

				fireEvent.click(queryAllByText('integer')[1]);

				await waitFor(() => {
					return document.querySelector('.calculate-container');
				});

				expect(
					document.querySelector('.calculate-container')
				).toBeTruthy();
			});

			it('shows available pages when jump to page action is selected', async () => {
				const props = defaultProps();
				const {getByText, queryAllByText} = render(
					<Editor
						{...props}
						onChange={() => {}}
						onValidator={() => {}}
					/>
				);

				await waitFor(() => {
					return document.querySelector('.option-selected');
				});

				const actionType = document
					.querySelectorAll('.timeline-item')[4]
					.querySelectorAll('.ddm-field')[0]
					.querySelector('.option-selected');

				fireEvent.click(actionType);

				await waitFor(() => {
					return document.querySelector('.dropdown-menu.show');
				});

				fireEvent.click(getByText('jump-to-page'));

				const actionTarget = document
					.querySelectorAll('.timeline-item')[4]
					.querySelectorAll('.ddm-field')[1]
					.querySelector('.option-selected');

				fireEvent.click(actionTarget);

				expect(queryAllByText('2 Page title')).toBeTruthy();
				expect(queryAllByText('3 Page title')).toBeTruthy();
			});
		});
	});
});
