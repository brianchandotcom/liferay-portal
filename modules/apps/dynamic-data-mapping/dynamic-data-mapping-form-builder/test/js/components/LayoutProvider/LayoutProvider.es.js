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

import {PagesVisitor} from 'data-engine-js-components-web';
import {JSXComponent} from 'metal-jsx';

import LayoutProvider from '../../../../src/main/resources/META-INF/resources/js/components/LayoutProvider/LayoutProvider.es';
import {DEFAULT_FIELD_NAME_REGEX} from '../../../../src/main/resources/META-INF/resources/js/util/regex.es';
import mockFieldType from '../../__mock__/mockFieldType.es';
import mockPages from '../../__mock__/mockPages.es';

let component;
let liferayLanguageSpy;

const changeField = ({settingsContext}, fieldName, value) => {
	const visitor = new PagesVisitor(settingsContext.pages);

	return visitor.mapFields((field) => {
		if (field.fieldName === fieldName) {
			field = {
				...field,
				value,
			};
		}

		return field;
	});
};

const mockLiferayLanguage = () => {
	liferayLanguageSpy = jest.spyOn(Liferay.Language, 'get');

	liferayLanguageSpy.mockImplementation((key) => {
		if (key === 'field') {
			return 'Field';
		}

		return key;
	});
};

const rules = [
	{
		actions: [
			{
				action: 'require',
				expression: '[x+2]',
				label: 'label text 1',
				target: 'text1',
			},
		],
		conditions: [
			{
				operands: [
					{
						type: 'field',
						value: 'text1',
					},
					{
						type: 'value',
						value: 'value 2',
					},
				],
				operator: 'equals-to',
			},
		],
		['logical-operator']: 'OR',
	},
];

const unmockLiferayLanguage = () => {
	liferayLanguageSpy.mockRestore();
};

class Child extends JSXComponent {
	render() {
		return <div />;
	}
}

class Parent extends JSXComponent {
	render() {
		return (
			<LayoutProvider
				availableLanguageIds={['en_US']}
				defaultLanguageId="en_US"
				editingLanguageId="en_US"
				initialPages={[...mockPages]}
				pages={[...mockPages]}
				ref="provider"
				rules={[...rules]}
				spritemap="icons.svg"
			>
				<Child ref="child" />
			</LayoutProvider>
		);
	}
}

describe('LayoutProvider', () => {
	beforeEach(() => {
		fetch.mockResponse(JSON.stringify({}), {
			status: 200,
		});

		jest.useFakeTimers();
	});

	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	it('receives pages through PROPS and move to the internal state', () => {
		component = new Parent();

		expect(component.state.pages).toEqual(component.props.initialPages);
	});

	it('passes to the child component the focusedField', () => {
		component = new Parent();

		const {child, provider} = component.refs;

		const focusedField = {
			columnIndex: 0,
			pageIndex: 0,
			rowIndex: 0,
			type: 'radio',
		};

		provider.setState({
			focusedField,
		});

		jest.runAllTimers();

		expect(child.props.focusedField).toEqual(provider.state.focusedField);
	});

	it('receives ruleAdded event to save a rule', () => {
		component = new Parent();

		jest.runAllTimers();

		const {child, provider} = component.refs;
		const oldRules = [...child.props.rules];

		const mockEvent = {
			action: 'calculate',
			expression: '22+2',
			label: 'liferay',
			target: 'liferay',
		};

		const {dispatch} = child.context;

		dispatch('ruleAdded', mockEvent);

		jest.runAllTimers();

		expect(provider.state.rules).toMatchSnapshot();
		expect(oldRules.length).toEqual(provider.state.rules.length - 1);
		expect([...oldRules, mockEvent]).toEqual(provider.state.rules);
	});

	it('receives ruleSaved event to edit a rule', () => {
		component = new Parent();

		jest.runAllTimers();

		const {child, provider} = component.refs;

		provider.setState({
			rules,
		});

		jest.runAllTimers();

		const originalRule = JSON.parse(JSON.stringify(provider.state.rules));

		jest.runAllTimers();

		const mockEvent = {
			...rules[0],
			actions: [
				{
					...rules[0].actions[0],
					action: 'show',
				},
			],
			ruleEditedIndex: 0,
		};

		const {dispatch} = child.context;

		dispatch('ruleSaved', mockEvent);

		jest.runAllTimers();

		expect(originalRule[0]).not.toEqual(provider.state.rules[0]);
	});

	it('passes to the child component the mode', () => {
		component = new Parent();

		const {child, provider} = component.refs;

		provider.setState({
			mode: 'edit',
		});

		jest.runAllTimers();

		expect(child.props.mode).toEqual(provider.state.mode);
	});

	describe('Field events', () => {
		describe('fieldMoved', () => {
			it('listens to the fieldMoved event and move the field to the row in pages', () => {
				component = new Parent();

				const {child, provider} = component.refs;
				const mockEvent = {
					sourceFieldName: 'text1',
					targetIndexes: {
						columnIndex: 0,
						pageIndex: 0,
						rowIndex: 0,
					},
				};

				const fields =
					provider.state.pages[0].rows[1].columns[0].fields[0];

				const {dispatch} = child.context;

				dispatch('fieldMoved', mockEvent);

				jest.runAllTimers();

				expect(
					provider.state.pages[0].rows[0].columns[0].fields[0]
				).toEqual(fields);
			});

			it('listens to the pagesUpdated event', () => {
				component = new Parent();

				const {child, provider} = component.refs;

				const {dispatch} = child.context;

				dispatch('pagesUpdated', mockPages);

				jest.runAllTimers();

				expect(provider.state.pages).toMatchSnapshot();
			});

			it('listens to the fieldMoved event and move the field to the column in pages', () => {
				component = new Parent();

				const {child, provider} = component.refs;

				const mockEvent = {
					sourceFieldName: 'select',
					targetIndexes: {
						columnIndex: 1,
						pageIndex: 0,
						rowIndex: 0,
					},
				};

				const {dispatch} = child.context;

				dispatch('fieldMoved', mockEvent);

				jest.runAllTimers();

				expect(provider.state.pages).toMatchSnapshot();
			});

			it('moves the field to the column in pages and remove the row if there are no fields', () => {
				component = new Parent();

				const {child, provider} = component.refs;
				const mockEvent = {
					sourceFieldName: 'date',
					sourceFieldPage: 0,
					targetIndexes: {
						columnIndex: 1,
						pageIndex: 0,
						rowIndex: 0,
					},
				};

				const {dispatch} = child.context;

				dispatch('fieldMoved', mockEvent);

				jest.runAllTimers();

				expect(provider.state.pages).toMatchSnapshot();
			});

			it('moves the field to the row in pages and remove the row if there are no fields', () => {
				component = new Parent();

				const {child, provider} = component.refs;
				const mockEvent = {
					sourceFieldName: 'radio',
					sourceFieldPage: 0,
					targetIndexes: {
						columnIndex: 0,
						pageIndex: 0,
						rowIndex: 0,
					},
				};

				const {dispatch} = child.context;

				dispatch('fieldMoved', mockEvent);

				jest.runAllTimers();

				expect(provider.state.pages).toMatchSnapshot();
			});
		});

		describe('fieldAdded', () => {
			it('listens the fieldAdded event and add the field in the column to the pages', () => {
				component = new Parent();

				const {child, provider} = component.refs;
				const mockEvent = {
					data: {
						parentFieldName: undefined,
					},
					fieldType: mockFieldType,
					indexes: {
						columnIndex: 1,
						pageIndex: 0,
						rowIndex: 0,
					},
				};

				mockLiferayLanguage();

				const {dispatch} = child.context;

				dispatch('fieldAdded', mockEvent);

				jest.runAllTimers();

				expect(
					provider.state.pages[0].rows[0].columns[1].fields[0]
						.fieldName
				).toEqual(expect.stringMatching(DEFAULT_FIELD_NAME_REGEX));

				unmockLiferayLanguage();
			});

			it('listen the fieldAdded event and add the field in the row to the pages', () => {
				component = new Parent();

				const {child, provider} = component.refs;
				const mockEvent = {
					data: {
						parentFieldName: undefined,
					},
					fieldType: mockFieldType,
					indexes: {
						columnIndex: 0,
						pageIndex: 0,
						rowIndex: 0,
					},
				};

				mockLiferayLanguage();

				const {dispatch} = child.context;

				dispatch('fieldAdded', mockEvent);

				jest.runAllTimers();

				expect(
					provider.state.pages[0].rows[0].columns[0].fields[0]
						.fieldName
				).toEqual(expect.stringMatching(DEFAULT_FIELD_NAME_REGEX));

				unmockLiferayLanguage();
			});

			it('listen the fieldAdded event and check if field reference has the same value as field name', () => {
				component = new Parent();

				const {child, provider} = component.refs;
				const mockEvent = {
					data: {
						parentFieldName: undefined,
					},
					fieldType: mockFieldType,
					indexes: {
						columnIndex: 0,
						pageIndex: 0,
						rowIndex: 0,
					},
				};

				const {dispatch} = child.context;

				dispatch('fieldAdded', mockEvent);

				const field =
					provider.state.pages[0].rows[0].columns[0].fields[0];

				expect(field.fieldName).toEqual(field.fieldReference);
			});

			it('updates the focusedField with the location of the new field when adding to the pages', () => {
				component = new Parent();

				const {child, provider} = component.refs;
				const mockEvent = {
					data: {
						parentFieldName: undefined,
					},
					fieldType: mockFieldType,
					indexes: {
						columnIndex: 2,
						pageIndex: 0,
						rowIndex: 1,
					},
				};

				mockLiferayLanguage();

				const {dispatch} = child.context;

				dispatch('fieldAdded', mockEvent);

				jest.runAllTimers();

				expect(provider.state.focusedField.fieldName).toEqual(
					expect.stringMatching(DEFAULT_FIELD_NAME_REGEX)
				);

				unmockLiferayLanguage();
			});
		});

		describe('fieldDeleted', () => {
			it('listens the fieldDeleted event and delete the field in the column to the pages', () => {
				component = new Parent();

				const {child, provider} = component.refs;

				expect(
					provider.state.pages[0].rows[1].columns[0].fields.length
				).toEqual(2);

				const mockEvent = {
					activePage: 0,
					fieldName: 'text2',
				};

				const {dispatch} = child.context;

				dispatch('fieldDeleted', mockEvent);

				expect(
					provider.state.pages[0].rows[1].columns[0].fields.length
				).toEqual(1);
			});

			it('does not delete field that belongs to rules', () => {
				component = new Parent();

				const {child, provider} = component.refs;

				expect(
					provider.state.pages[0].rows[1].columns[0].fields.length
				).toEqual(2);

				const mockEvent = {
					activePage: 0,
					fieldName: 'text1',
				};

				const {dispatch} = child.context;

				dispatch('fieldDeleted', mockEvent);

				expect(
					provider.state.pages[0].rows[1].columns[0].fields.length
				).toEqual(2);
			});
		});

		describe('fieldEdited', () => {
			it('listens the fieldEdited event and edit the field label keeping the same fieldName', () => {
				component = new Parent();

				const {child, provider} = component.refs;
				let mockEvent = {
					data: {
						parentFieldName: undefined,
					},
					fieldType: mockFieldType,
					indexes: {
						columnIndex: 0,
						pageIndex: 0,
						rowIndex: 0,
					},
				};

				const {dispatch} = child.context;

				dispatch('fieldAdded', mockEvent);

				jest.runAllTimers();

				const expectedFieldName =
					provider.state.pages[0].rows[0].columns[0].fields[0]
						.fieldName;

				mockEvent = {
					fieldName: expectedFieldName,
					propertyName: 'label',
					propertyValue: 'newLabel',
				};

				dispatch('fieldEdited', mockEvent);

				jest.runAllTimers();

				expect(
					provider.state.pages[0].rows[0].columns[0].fields[0]
						.fieldName
				).toEqual(expectedFieldName);
			});
		});

		describe('fieldDuplicated', () => {
			it('listens the duplicate field event and add this field in the pages', () => {
				component = new Parent();

				const {child, provider} = component.refs;
				const mockEvent = {
					activePage: 0,
					fieldName: 'radio',
				};

				mockLiferayLanguage();

				const {dispatch} = child.context;

				dispatch('fieldDuplicated', mockEvent);

				jest.runAllTimers();

				const visitor = new PagesVisitor(provider.state.pages);

				expect(
					visitor.mapFields(
						(
							field,
							fieldIndex,
							columnIndex,
							rowIndex,
							pageIndex
						) => {
							const {pages} = field.settingsContext;
							let newPages = [];

							if (pages.length) {
								pages[0].rows[0].columns[0].fields[1].value =
									'Liferay';

								const validation =
									pages[0].rows[0].columns[0].fields[4]
										.validation;

								if (validation && validation.fieldName) {

									// Overrides the fieldName because it is generated when a field is duplicated,
									// toMatchSnapshot has problems with deep arrays so we override it here to
									// avoid this.

									validation.fieldName = 'Any<String>';
								}

								const visitor = new PagesVisitor(pages);

								newPages = visitor.mapFields((field) => {
									const newField = {
										...field,

										// Overrides the fieldName because it is generated when a field is duplicated,
										// toMatchSnapshot has problems with deep arrays so we override it here to
										// avoid this.

										instanceId: 'Any<String>',
									};

									const {
										defaultLanguageId,
										localizedValue,
									} = newField;

									if (
										defaultLanguageId &&
										newField.fieldName !== 'label'
									) {
										localizedValue[defaultLanguageId] =
											'Any<String>';
									}

									return newField;
								});
							}

							const name = `name${fieldIndex}${columnIndex}${rowIndex}${pageIndex}`;

							return {
								...field,
								fieldName: name,
								fieldReference: name,

								// Overrides the instanceId because it is generated when a field is duplicated,
								// toMatchSnapshot has problems with deep arrays so we override it here to
								// avoid this.

								instanceId: 'Any<String>',
								name: `name${fieldIndex}${columnIndex}${rowIndex}${pageIndex}`,
								settingsContext: {
									...field.settingsContext,
									pages: newPages,
								},
							};
						}
					)
				).toMatchSnapshot();

				unmockLiferayLanguage();
			});
		});

		describe('fieldClicked', () => {
			it('listens the fieldClicked event and change the state of the focusedField to the data receive', () => {
				component = new Parent();

				const {child, provider} = component.refs;
				const mockEvent = {
					activePage: 0,
					fieldName: 'radio',
				};

				const {dispatch} = child.context;

				dispatch('fieldClicked', mockEvent);

				jest.runAllTimers();

				expect(provider.state.focusedField).toMatchSnapshot();
			});
		});

		describe('fieldBlurred', () => {
			it('listens the fieldBlurred event and change the state of the focusedField to the data receive', () => {
				component = new Parent();

				const {child, provider} = component.refs;
				const mockEvent = {
					editingLanguageId: 'en_US',
					fieldName: 'name',
					propertyValue: 'radio',
				};

				const {dispatch} = child.context;

				dispatch('fieldBlurred', mockEvent);

				jest.runAllTimers();

				expect(provider.state.focusedField).toMatchSnapshot();
			});
		});

		describe('fieldChangesCanceled', () => {
			it('listens the fieldChangesCanceled event and change the state of the focusedField and pages for the data wich was received', () => {
				component = new Parent();

				const {child, provider} = component.refs;

				provider.setState({
					previousFocusedField: mockFieldType,
				});

				const changedFocusedField = {
					...mockFieldType,
					fieldName: 'text1',
					settingsContext: {
						...mockFieldType.settingsContext,
						pages: changeField(mockFieldType, 'required', false),
					},
				};

				provider.setState({
					focusedField: changedFocusedField,
				});

				expect(provider.state.focusedField).toEqual(
					changedFocusedField
				);

				const {dispatch} = child.context;

				dispatch('fieldChangesCanceled');

				jest.runAllTimers();

				expect(provider.state.focusedField).toEqual(
					provider.state.previousFocusedField
				);
			});
		});

		describe('focusedFieldUpdated', () => {
			it.skip('listens the focusedFieldEvaluationEnded event and change the state of the focusedField and pages for the data wich was received', () => {
				component = new Parent();

				const {child, provider} = component.refs;

				const {dispatch} = child.context;

				dispatch('focusedFieldEvaluationEnded', {
					...mockFieldType,
					instanceId: 'instanceId',
				});

				jest.runAllTimers();

				expect(provider.state.focusedField).toMatchSnapshot({
					instanceId: expect.any(String),
				});
			});
		});

		describe('pageDeleted', () => {
			it('listens the pageDeleted event and change the state of pages for the data wich was received', () => {
				component = new Parent();

				const {child, provider} = component.refs;
				const pageIndex = 0;

				const {dispatch} = child.context;

				dispatch('pageDeleted', pageIndex);

				jest.runAllTimers();

				expect(provider.state.pages.length).toEqual(0);
				expect(provider.state.pages).toMatchSnapshot();
			});
		});

		describe('pageAdded', () => {
			it('listens the pageAdded event and change the state of pages for the data wich was received', () => {
				component = new Parent();

				const {child, provider} = component.refs;

				const {dispatch} = child.context;

				dispatch('pageAdded', {pageIndex: 1});

				jest.runAllTimers();

				expect(provider.state.pages.length).toEqual(2);
				expect(provider.state.pages).toMatchSnapshot();
			});
		});

		describe('pageReset', () => {
			it('listens the pageReset event and change the state of pages for the data wich was received', () => {
				component = new Parent();

				const {child, provider} = component.refs;

				expect(provider.state.pages[0].rows.length).toEqual(4);

				const {dispatch} = child.context;

				dispatch('pageReset', {pageIndex: 0});

				jest.runAllTimers();

				expect(provider.state.pages[0].rows.length).toEqual(1);
				expect(provider.state.pages).toMatchSnapshot();
			});
		});

		describe('paginationModeUpdated', () => {
			it('listens the paginationModeUpdated event and change the state of pagination mode for the data wich was received', () => {
				component = new Parent();

				const {child, provider} = component.refs;

				expect(provider.state.paginationMode).toEqual('wizard');

				const {dispatch} = child.context;

				dispatch('paginationModeUpdated');

				jest.runAllTimers();

				expect(provider.state.paginationMode).toEqual('paginated');
			});

			it('listens the paginationModeUpdated event and change the state of pagination mode from pagination to wizard', () => {
				component = new Parent();

				const {child, provider} = component.refs;

				expect(provider.state.paginationMode).toEqual('wizard');

				provider.setState({
					paginationMode: 'paginated',
				});

				jest.runAllTimers();

				expect(provider.state.paginationMode).toEqual('paginated');

				const {dispatch} = child.context;

				dispatch('paginationModeUpdated');

				jest.runAllTimers();

				expect(provider.state.paginationMode).toEqual('wizard');
			});
		});

		describe('successPageChanged', () => {
			it('listens the successPageChanged event and change the state of success page for the data wich was received', () => {
				component = new Parent();

				const {child, provider} = component.refs;

				const {dispatch} = child.context;

				dispatch('successPageChanged', {
					enabled: true,
				});

				jest.runAllTimers();

				expect(provider.state.successPageSettings.enabled).toBeTruthy();
			});
		});

		describe('activePageUpdated', () => {
			it('listens the activePageUpdated event and change the state of the active page for the data wich was received', () => {
				component = new Parent();

				const {child, provider} = component.refs;

				const {dispatch} = child.context;

				dispatch('activePageUpdated', 1);

				jest.runAllTimers();

				expect(provider.state.activePage).toEqual(1);
			});
		});
	});
});
