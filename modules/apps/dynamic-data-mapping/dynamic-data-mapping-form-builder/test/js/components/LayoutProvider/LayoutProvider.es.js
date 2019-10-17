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

import {JSXComponent} from 'metal-jsx';

import LayoutProvider from '../../../../src/main/resources/META-INF/resources/js/components/LayoutProvider/LayoutProvider.es';
import {PagesVisitor} from '../../../../src/main/resources/META-INF/resources/js/util/visitors.es';
import mockPages from '../../___mock__/mockPages';

let component;
let pages = null;

const rules = [
	{
		actions: [
			{
				action: 'require',
				expression: '[x+2]',
				label: 'label text 1',
				target: 'text1'
			}
		],
		conditions: [
			{
				operands: [
					{
						type: 'field',
						value: 'text1'
					},
					{
						type: 'value',
						value: 'value 2'
					}
				],
				operator: 'equals-to'
			}
		],
		['logical-operator']: 'OR'
	}
];

const spritemap = 'icons.svg';

class Child extends JSXComponent {
	render() {
		return <div />;
	}
}

class Parent extends JSXComponent {
	render() {
		return (
			<LayoutProvider
				initialPages={[...pages]}
				ref="provider"
				rules={[]}
				spritemap={spritemap}
			>
				<Child ref="child" />
			</LayoutProvider>
		);
	}
}

describe('LayoutProvider', () => {
	beforeEach(() => {
		pages = JSON.parse(JSON.stringify(mockPages));

		jest.useFakeTimers();
	});

	afterEach(() => {
		if (component) {
			component.dispose();
		}

		pages = null;
	});

	it('receives pages through PROPS and move to the internal state', () => {
		component = new LayoutProvider({
			initialPages: pages,
			rules: []
		});

		expect(component.state.pages).toEqual(component.props.initialPages);
	});

	it('attaches the events to the child component', () => {
		component = new Parent();

		const {provider} = component.refs;

		expect(provider.props.children[0].props.events).toMatchObject({
			fieldAdded: expect.any(Function),
			fieldClicked: expect.any(Function),
			fieldDeleted: expect.any(Function),
			fieldEdited: expect.any(Function),
			fieldMoved: expect.any(Function)
		});
	});

	it('passes to the child component the pages of the internal state', () => {
		component = new Parent();

		const {child, provider} = component.refs;

		expect(child.props.pages).toEqual(provider.state.pages);
	});

	it('passes to the child component the focusedField', () => {
		component = new Parent();

		const {child, provider} = component.refs;

		const focusedField = {
			columnIndex: 0,
			pageIndex: 0,
			rowIndex: 0,
			type: 'radio'
		};

		provider.setState({
			focusedField
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
			target: 'liferay'
		};

		child.emit('ruleAdded', mockEvent);

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
			rules
		});

		jest.runAllTimers();

		const originalRule = JSON.parse(JSON.stringify(provider.state.rules));

		jest.runAllTimers();

		const mockEvent = {
			...rules[0],
			actions: [
				{
					...rules[0].actions[0],
					action: 'show'
				}
			],
			ruleEditedIndex: 0
		};

		child.emit('ruleSaved', mockEvent);

		jest.runAllTimers();

		expect(originalRule[0]).not.toEqual(provider.state.rules[0]);
	});

	it('passes to the child component the mode', () => {
		component = new Parent();

		const {child, provider} = component.refs;

		provider.setState({
			mode: 'edit'
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
					addedToPlaceholder: true,
					source: {
						columnIndex: 0,
						pageIndex: 0,
						rowIndex: 1
					},
					target: {
						columnIndex: 0,
						pageIndex: 0,
						rowIndex: 0
					}
				};

				const fields =
					provider.state.pages[0].rows[1].columns[0].fields;

				child.emit('fieldMoved', mockEvent);

				jest.runAllTimers();

				expect(
					provider.state.pages[0].rows[0].columns[0].fields
				).toEqual(fields);
				expect(child.props.pages).toEqual(provider.state.pages);
			});

			it('listens to the pagesUpdated event', () => {
				component = new Parent();

				const {child, provider} = component.refs;

				child.emit('pagesUpdated', pages);

				jest.runAllTimers();

				expect(provider.state.pages).toMatchSnapshot();
				expect(child.props.pages).toEqual(provider.state.pages);
			});

			it('listens to the fieldMoved event and move the field to the column in pages', () => {
				component = new Parent();

				const {child, provider} = component.refs;
				const mockEvent = {
					source: {
						columnIndex: 1,
						pageIndex: 0,
						rowIndex: 1
					},
					target: {
						columnIndex: 1,
						pageIndex: 0,
						rowIndex: 0
					},
					targetIsEmptyRow: false
				};

				child.emit('fieldMoved', mockEvent);

				jest.runAllTimers();

				expect(provider.state.pages).toMatchSnapshot();
				expect(child.props.pages).toEqual(provider.state.pages);
			});

			it('moves the field to the column in pages and remove the row if there are no fields', () => {
				component = new Parent();

				const {child, provider} = component.refs;
				const mockEvent = {
					source: {
						columnIndex: 0,
						pageIndex: 0,
						rowIndex: 2
					},
					target: {
						columnIndex: 1,
						pageIndex: 0,
						rowIndex: 0
					},
					targetIsEmptyRow: false
				};

				child.emit('fieldMoved', mockEvent);

				jest.runAllTimers();

				expect(provider.state.pages).toMatchSnapshot();
				expect(child.props.pages).toEqual(provider.state.pages);
			});

			it('moves the field to the row in pages and remove the row if there are no fields', () => {
				component = new Parent();

				const {child, provider} = component.refs;
				const mockEvent = {
					addedToPlaceholder: true,
					source: {
						columnIndex: 0,
						pageIndex: 0,
						rowIndex: 0
					},
					target: {
						columnIndex: 0,
						pageIndex: 0,
						rowIndex: 0
					}
				};

				child.emit('fieldMoved', mockEvent);

				jest.runAllTimers();

				expect(provider.state.pages).toMatchSnapshot();
				expect(child.props.pages).toEqual(provider.state.pages);
			});
		});

		describe('fieldAdded', () => {
			it('listens the fieldAdded event and add the field in the column to the pages', () => {
				component = new Parent();

				const {child, provider} = component.refs;
				const mockEvent = {
					focusedField: {
						name: 'text',
						settingsContext: {
							pages: mockPages
						}
					},
					target: {
						columnIndex: 1,
						pageIndex: 0,
						rowIndex: 0
					}
				};

				child.emit('fieldAdded', mockEvent);

				jest.runAllTimers();

				expect(child.props.pages).toEqual(provider.state.pages);
			});

			it('listen the fieldAdded event and add the field in the row to the pages', () => {
				component = new Parent();

				const {child, provider} = component.refs;
				const mockEvent = {
					focusedField: {
						name: 'text',
						settingsContext: {
							pages: mockPages
						}
					},
					target: {
						columnIndex: 0,
						pageIndex: 0,
						rowIndex: 0
					}
				};

				child.emit('fieldAdded', mockEvent);

				jest.runAllTimers();

				expect(child.props.pages).toEqual(provider.state.pages);
			});

			it('updates the focusedField with the location of the new field when adding to the pages', () => {
				component = new Parent();

				const {child, provider} = component.refs;
				const mockEvent = {
					focusedField: {
						name: 'text',
						settingsContext: {
							pages: mockPages
						}
					},
					target: {
						columnIndex: 2,
						pageIndex: 0,
						rowIndex: 1
					}
				};

				child.emit('fieldAdded', mockEvent);

				jest.runAllTimers();

				expect(child.props.focusedField).toEqual(
					provider.state.focusedField
				);
			});
		});

		describe('fieldDeleted', () => {
			it('listens the fieldDeleted event and delete the field in the column to the pages', () => {
				component = new Parent();

				const {child, provider} = component.refs;
				const mockEvent = {
					columnIndex: 0,
					pageIndex: 0,
					rowIndex: 1
				};

				child.emit('fieldDeleted', mockEvent);

				jest.runAllTimers();

				expect(provider.state.pages).toMatchSnapshot();
				expect(child.props.pages).toEqual(provider.state.pages);
			});
		});

		describe('fieldDuplicated', () => {
			it('listens the duplicate field event and add this field in the pages', () => {
				component = new Parent();

				const {child, provider} = component.refs;
				const mockEvent = {
					columnIndex: 0,
					pageIndex: 0,
					rowIndex: 0
				};

				child.emit('fieldDuplicated', mockEvent);

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

							if (pages.length) {
								pages[0].rows[0].columns[0].fields[1].value =
									'Liferay';
							}

							return {
								...field,
								fieldName: `name${fieldIndex}${columnIndex}${rowIndex}${pageIndex}`,
								name: `name${fieldIndex}${columnIndex}${rowIndex}${pageIndex}`
							};
						}
					)
				).toMatchSnapshot();
			});
		});

		describe('fieldClicked', () => {
			it('listens the fieldClicked event and change the state of the focusedField to the data receive', () => {
				component = new Parent();

				const {child, provider} = component.refs;
				const mockEvent = {
					columnIndex: 0,
					pageIndex: 0,
					rowIndex: 0
				};

				child.emit('fieldClicked', mockEvent);

				expect(provider.props.children[0].props.events).toMatchObject({
					fieldClicked: expect.any(Function)
				});
				expect(provider.state.focusedField).toEqual(mockEvent);
			});
		});

		describe('fieldBlurred', () => {
			it('listens the fieldBlurred event and change the state of the focusedField to the data receive', () => {
				component = new Parent();

				const {child, provider} = component.refs;

				child.emit('fieldBlurred');

				expect(provider.props.children[0].props.events).toMatchObject({
					fieldBlurred: expect.any(Function)
				});
				expect(provider.state.focusedField).toMatchSnapshot();
			});
		});

		describe('fieldResized', () => {
			it('listens to the columnResized event and resize the field when the left arrow is pulled', () => {
				component = new Parent();

				const {child, provider} = component.refs;
				const mockEvent = {
					column: 7,
					direction: 'left',
					source: {
						dataset: {
							ddmFieldColumn: 1,
							ddmFieldPage: 0,
							ddmFieldRow: 0
						}
					}
				};

				child.emit('columnResized', mockEvent);

				jest.runAllTimers();

				expect(provider.state.pages).toMatchSnapshot();
				expect(child.props.pages).toEqual(provider.state.pages);
			});
		});

		xdescribe('fieldChangesCanceled', () => {
			it('listens the fieldChangesCanceled event and change the state of the focusedField and pages for the data wich was received', () => {
				component = new Parent({
					initialPages: pages
				});

				const {child, provider} = component.refs;
				const mockedData = {
					fieldName: 'original',
					name: 'text1',
					settingsContext: {
						pages: []
					},
					type: 'text'
				};

				provider.setState({
					focusedField: {
						fieldName: 'changed',
						icon: 'text',
						name: 'text1',
						originalContext: mockedData,
						settingsContext: {
							pages: []
						}
					}
				});

				child.emit('fieldChangesCanceled');

				jest.runAllTimers();

				expect(provider.state.focusedField.fieldName).toEqual(
					'original'
				);
				expect(provider.state.pages).toMatchSnapshot();
			});
		});

		describe('focusedFieldUpdated', () => {
			it('listens the focusedFieldUpdated event and change the state of the focusedField and pages for the data wich was received', () => {
				component = new Parent();

				const {child, provider} = component.refs;
				const mockEvent = {
					columnIndex: 0,
					pageIndex: 0,
					rowIndex: 0
				};

				child.emit('focusedFieldUpdated', mockEvent);

				expect(provider.props.children[0].props.events).toMatchObject({
					focusedFieldUpdated: expect.any(Function)
				});
				expect(provider.state.focusedField).toEqual(mockEvent);
				expect(provider.state.pages).toMatchSnapshot();
			});
		});

		describe('pageDeleted', () => {
			it('listens the pageDeleted event and change the state of pages for the data wich was received', () => {
				component = new Parent();

				const {child, provider} = component.refs;
				const pageIndex = 0;

				child.emit('pageDeleted', pageIndex);

				expect(
					provider.props.children[pageIndex].props.events
				).toMatchObject({
					pageDeleted: expect.any(Function)
				});
				expect(provider.state.pages).toMatchSnapshot();
			});
		});

		describe('pageAdded', () => {
			it('listens the pageAdded event and change the state of pages for the data wich was received', () => {
				component = new Parent();

				const {child, provider} = component.refs;

				child.emit('pageAdded');

				expect(provider.props.children[0].props.events).toMatchObject({
					pageAdded: expect.any(Function)
				});
				expect(provider.state.pages).toMatchSnapshot();
			});
		});

		describe('pageReset', () => {
			it('listens the pageReset event and change the state of pages for the data wich was received', () => {
				component = new Parent();

				const {child, provider} = component.refs;

				child.emit('pageReset');

				expect(provider.props.children[0].props.events).toMatchObject({
					pageReset: expect.any(Function)
				});
				expect(provider.state.pages).toMatchSnapshot();
			});
		});

		describe('paginationModeUpdated', () => {
			it('listens the paginationModeUpdated event and change the state of pagination mode for the data wich was received', () => {
				component = new Parent();

				const {child, provider} = component.refs;

				child.emit('paginationModeUpdated');

				expect(provider.props.children[0].props.events).toMatchObject({
					paginationModeUpdated: expect.any(Function)
				});
				expect(provider.state.pages).toMatchSnapshot();
			});

			it('listens the paginationModeUpdated event and change the state of pagination mode from pagination to wizard', () => {
				component = new Parent();

				const {child, provider} = component.refs;

				provider.setState({
					paginationMode: 'pagination'
				});

				jest.runAllTimers();

				child.emit('paginationModeUpdated');

				expect(provider.props.children[0].props.events).toMatchObject({
					paginationModeUpdated: expect.any(Function)
				});
				expect(provider.state.pages).toMatchSnapshot();
			});
		});

		describe('successPageChanged', () => {
			it('listens the successPageChanged event and change the state of success page for the data wich was received', () => {
				component = new Parent();

				const {child, provider} = component.refs;

				child.emit('successPageChanged', {
					enabled: true
				});

				expect(provider.props.children[0].props.events).toMatchObject({
					successPageChanged: expect.any(Function)
				});
				expect(provider.state.pages).toMatchSnapshot();
			});
		});

		describe('activePageUpdated', () => {
			it('listens the activePageUpdated event and change the state of the active page for the data wich was received', () => {
				component = new Parent();

				const {child, provider} = component.refs;

				child.emit('activePageUpdated', 1);

				expect(provider.props.children[0].props.events).toMatchObject({
					activePageUpdated: expect.any(Function)
				});
				expect(provider.state.pages).toMatchSnapshot();
			});
		});
	});
});
