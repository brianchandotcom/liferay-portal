import {JSXComponent} from 'metal-jsx';
import mockPages from './__mock__/mockPages.es';
import LayoutProvider from '../LayoutProvider.es';
import {PagesVisitor} from '../../../util/visitors.es';

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
						initialPages: pages
					}
				);

				expect(component.state.pages).toEqual(pages);
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
									fieldType: {
										name: 'text'
									},
									target: {
										columnIndex: 1,
										pageIndex: 0,
										rowIndex: 0
									}
								};

								child.emit('fieldAdded', mockEvent);

								jest.runAllTimers();

								expect(provider.state.pages).toMatchSnapshot();
								expect(child.props.pages).toEqual(provider.state.pages);
							}
						);

						it(
							'should listen the fieldAdded event and add the field in the row to the pages',
							() => {
								component = new Parent();

								const {child, provider} = component.refs;
								const mockEvent = {
									fieldType: {
										name: 'text'
									},
									target: {
										columnIndex: 0,
										pageIndex: 0,
										rowIndex: 0
									}
								};

								child.emit('fieldAdded', mockEvent);

								jest.runAllTimers();

								expect(provider.state.pages).toMatchSnapshot();
								expect(child.props.pages).toEqual(provider.state.pages);
							}
						);

						it(
							'should update the focusedField with the location of the new field when adding to the pages',
							() => {
								component = new Parent();

								const {child, provider} = component.refs;
								const mockEvent = {
									fieldType: {
										name: 'text'
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
									mode: 'edit',
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
			}
		);
	}
);