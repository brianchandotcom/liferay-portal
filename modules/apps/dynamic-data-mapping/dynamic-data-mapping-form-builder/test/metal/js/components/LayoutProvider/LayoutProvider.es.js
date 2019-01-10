import {JSXComponent} from 'metal-jsx';
import mockPages from 'mock/mockPages.es';
import LayoutProvider from 'source/components/LayoutProvider/LayoutProvider.es';
import {PagesVisitor} from 'source/util/visitors.es';

let component;
let pages = null;
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

describe.only(
	'LayoutProvider',
	() => {
		beforeEach(
			() => {
				pages = JSON.parse(JSON.stringify(mockPages));

				jest.useFakeTimers();
			}
		);

		afterEach(
			() => {
				if (component) {
					component.dispose();
				}

				pages = null;
			}
		);

		it(
			'should receive pages through PROPS and move to the internal state',
			() => {
				component = new LayoutProvider(
					{
						initialPages: pages,
						rules: []
					}
				);

				expect(component.state.pages).toEqual(component.props.initialPages);
			}
		);

		it(
			'should attach the events to the child component',
			() => {
				component = new Parent();

				const {provider} = component.refs;

				expect(provider.props.children[0].props.events).toMatchObject(
					{
						fieldAdded: expect.any(Function),
						fieldClicked: expect.any(Function),
						fieldDeleted: expect.any(Function),
						fieldEdited: expect.any(Function),
						fieldMoved: expect.any(Function)
					}
				);
			}
		);

		it(
			'should pass to the child component the pages of the internal state',
			() => {
				component = new Parent();

				const {child, provider} = component.refs;

				expect(child.props.pages).toEqual(provider.state.pages);
			}
		);

		it(
			'should pass to the child component the focusedField',
			() => {
				component = new Parent();

				const {child, provider} = component.refs;

				const focusedField = {
					columnIndex: 0,
					pageIndex: 0,
					rowIndex: 0,
					type: 'radio'
				};

				provider.setState(
					{
						focusedField
					}
				);

				jest.runAllTimers();

				expect(child.props.focusedField).toEqual(provider.state.focusedField);
			}
		);

		it(
			'should receive ruleAdded event to save a rule',
			() => {
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
			}
		);

		it(
			'should pass to the child component the mode',
			() => {
				component = new Parent();

				const {child, provider} = component.refs;

				provider.setState(
					{
						mode: 'edit'
					}
				);

				jest.runAllTimers();

				expect(child.props.mode).toEqual(provider.state.mode);
			}
		);

		describe(
			'Field events',
			() => {
				describe(
					'fieldMoved',
					() => {
						it(
							'should listen to the fieldMoved event and move the field to the row in pages',
							() => {
								component = new Parent();

								const {child, provider} = component.refs;
								const mockEvent = {
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

								child.emit('fieldMoved', mockEvent);

								jest.runAllTimers();

								expect(provider.state.pages).toMatchSnapshot();
								expect(child.props.pages).toEqual(provider.state.pages);
							}
						);

						it(
							'should listen to the pagesUpdated event',
							() => {
								component = new Parent();

								const {child, provider} = component.refs;

								child.emit('pagesUpdated', pages);

								jest.runAllTimers();

								expect(provider.state.pages).toMatchSnapshot();
								expect(child.props.pages).toEqual(provider.state.pages);
							}
						);

						it(
							'should listen to the fieldMoved event and move the field to the column in pages',
							() => {
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
									}
								};

								child.emit('fieldMoved', mockEvent);

								jest.runAllTimers();

								expect(provider.state.pages).toMatchSnapshot();
								expect(child.props.pages).toEqual(provider.state.pages);
							}
						);

						it(
							'should move the field to the column in pages and remove the row if there are no fields',
							() => {
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
									}
								};

								child.emit('fieldMoved', mockEvent);

								jest.runAllTimers();

								expect(provider.state.pages).toMatchSnapshot();
								expect(child.props.pages).toEqual(provider.state.pages);
							}
						);

						it(
							'should move the field to the row in pages and remove the row if there are no fields',
							() => {
								component = new Parent();

								const {child, provider} = component.refs;
								const mockEvent = {
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
							}
						);
					}
				);

				describe(
					'fieldAdded',
					() => {
						it(
							'should listen the fieldAdded event and add the field in the column to the pages',
							() => {
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
							}
						);

						it(
							'should listen the fieldAdded event and add the field in the row to the pages',
							() => {
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
							}
						);

						it(
							'should update the focusedField with the location of the new field when adding to the pages',
							() => {
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

								expect(child.props.focusedField).toEqual(provider.state.focusedField);
							}
						);
					}
				);

				describe(
					'fieldDeleted',
					() => {
						it(
							'should listen the fieldDeleted event and delete the field in the column to the pages',
							() => {
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
							}
						);
					}
				);

				describe(
					'fieldDuplicated',
					() => {
						it(
							'should listen the duplicate field event and add this field in the pages',
							() => {
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
										(field, fieldIndex, columnIndex, rowIndex, pageIndex) => {
											const {pages} = field.settingsContext;

											if (pages.length) {
												pages[0].rows[0].columns[0].fields[1].value = 'Liferay';
											}

											return {
												...field,
												fieldName: `name${fieldIndex}${columnIndex}${rowIndex}${pageIndex}`,
												name: `name${fieldIndex}${columnIndex}${rowIndex}${pageIndex}`
											};
										}
									)
								).toMatchSnapshot();
							}
						);
					}
				);

				describe(
					'fieldClicked',
					() => {
						it(
							'should listen the fieldClicked event and change the state of the focusedField to the data receive',
							() => {
								component = new Parent();

								const {child, provider} = component.refs;
								const mockEvent = {
									columnIndex: 0,
									pageIndex: 0,
									rowIndex: 0
								};

								child.emit('fieldClicked', mockEvent);

								expect(provider.props.children[0].props.events).toMatchObject(
									{
										fieldClicked: expect.any(Function)
									}
								);
								expect(provider.state.focusedField).toEqual(mockEvent);
							}
						);
					}
				);

				describe(
					'fieldBlurred',
					() => {
						it(
							'should listen the fieldBlurred event and change the state of the focusedField to the data receive',
							() => {
								component = new Parent();

								const {child, provider} = component.refs;

								child.emit('fieldBlurred');

								expect(provider.props.children[0].props.events).toMatchObject(
									{
										fieldBlurred: expect.any(Function)
									}
								);
								expect(provider.state.focusedField).toMatchSnapshot();
							}
						);
					}
				);

				describe(
					'fieldEdited',
					() => {
						it(
							'should listen the fieldEdited event and change the state of the focusedField and pages for the data wich was received',
							() => {
								component = new Parent();

								const {child, provider} = component.refs;
								const mockEvent = {
									columnIndex: 0,
									pageIndex: 0,
									rowIndex: 0
								};

								child.emit('fieldEdited', mockEvent);

								expect(provider.props.children[0].props.events).toMatchObject(
									{
										fieldEdited: expect.any(Function)
									}
								);
								expect(provider.state.focusedField).toEqual(mockEvent);
								expect(provider.state.pages).toMatchSnapshot();
							}
						);
					}
				);

				describe(
					'fieldChangesCanceled',
					() => {
						it(
							'should listen the fieldChangesCanceled event and change the state of the focusedField and pages for the data wich was received',
							() => {
								component = new Parent(
									{
										initialPages: pages
									}
								);

								const {child, provider} = component.refs;
								const mockedData = {
									fieldName: 'text1',
									name: 'text1',
									settingsContext: [],
									type: 'text'
								};

								provider.setState(
									{
										focusedField: {
											icon: 'text',
											name: 'text1',
											originalContext: mockedData
										}
									}
								);

								child.emit('fieldChangesCanceled');

								jest.runAllTimers();

								expect(provider.state.focusedField).toEqual(
									{
										...provider.state.focusedField,
										...mockedData
									}
								);
								expect(provider.state.pages).toMatchSnapshot();
							}
						);
					}
				);

				describe(
					'focusedFieldUpdated',
					() => {
						it(
							'should listen the focusedFieldUpdated event and change the state of the focusedField and pages for the data wich was received',
							() => {
								component = new Parent();

								const {child, provider} = component.refs;
								const mockEvent = {
									columnIndex: 0,
									pageIndex: 0,
									rowIndex: 0
								};

								child.emit('focusedFieldUpdated', mockEvent);

								expect(provider.props.children[0].props.events).toMatchObject(
									{
										focusedFieldUpdated: expect.any(Function)
									}
								);
								expect(provider.state.focusedField).toEqual(mockEvent);
								expect(provider.state.pages).toMatchSnapshot();
							}
						);
					}
				);

				describe(
					'pageDeleted',
					() => {
						it(
							'should listen the pageDeleted event and change the state of pages for the data wich was received',
							() => {
								component = new Parent();

								const {child, provider} = component.refs;
								const pageIndex = 0;

								child.emit('pageDeleted', pageIndex);

								expect(provider.props.children[pageIndex].props.events).toMatchObject(
									{
										pageDeleted: expect.any(Function)
									}
								);
								expect(provider.state.pages).toMatchSnapshot();
							}
						);
					}
				);

				describe(
					'pageAdded',
					() => {
						it(
							'should listen the pageAdded event and change the state of pages for the data wich was received',
							() => {
								component = new Parent();

								const {child, provider} = component.refs;

								child.emit('pageAdded');

								expect(provider.props.children[0].props.events).toMatchObject(
									{
										pageAdded: expect.any(Function)
									}
								);
								expect(provider.state.pages).toMatchSnapshot();
							}
						);
					}
				);

				describe(
					'pageReset',
					() => {
						it(
							'should listen the pageReset event and change the state of pages for the data wich was received',
							() => {
								component = new Parent();

								const {child, provider} = component.refs;

								child.emit('pageReset');

								expect(provider.props.children[0].props.events).toMatchObject(
									{
										pageReset: expect.any(Function)
									}
								);
								expect(provider.state.pages).toMatchSnapshot();
							}
						);
					}
				);

				describe(
					'paginationModeUpdated',
					() => {
						it(
							'should listen the paginationModeUpdated event and change the state of pagination mode for the data wich was received',
							() => {
								component = new Parent();

								const {child, provider} = component.refs;

								child.emit('paginationModeUpdated');

								expect(provider.props.children[0].props.events).toMatchObject(
									{
										paginationModeUpdated: expect.any(Function)
									}
								);
								expect(provider.state.pages).toMatchSnapshot();
							}
						);

						it(
							'should listen the paginationModeUpdated event and change the state of pagination mode from pagination to wizard',
							() => {
								component = new Parent();

								const {child, provider} = component.refs;

								provider.setState(
									{
										paginationMode: 'pagination'
									}
								);

								jest.runAllTimers();

								child.emit('paginationModeUpdated');

								expect(provider.props.children[0].props.events).toMatchObject(
									{
										paginationModeUpdated: expect.any(Function)
									}
								);
								expect(provider.state.pages).toMatchSnapshot();
							}
						);
					}
				);

				describe(
					'successPageChanged',
					() => {
						it(
							'should listen the successPageChanged event and change the state of success page for the data wich was received',
							() => {
								component = new Parent();

								const {child, provider} = component.refs;

								child.emit(
									'successPageChanged',
									{
										enabled: true
									}
								);

								expect(provider.props.children[0].props.events).toMatchObject(
									{
										successPageChanged: expect.any(Function)
									}
								);
								expect(provider.state.pages).toMatchSnapshot();
							}
						);
					}
				);

				describe(
					'activePageUpdated',
					() => {
						it(
							'should listen the activePageUpdated event and change the state of the active page for the data wich was received',
							() => {
								component = new Parent();

								const {child, provider} = component.refs;

								child.emit('activePageUpdated', 1);

								expect(provider.props.children[0].props.events).toMatchObject(
									{
										activePageUpdated: expect.any(Function)
									}
								);
								expect(provider.state.pages).toMatchSnapshot();
							}
						);
					}
				);
			}
		);
	}
);