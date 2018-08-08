import {JSXComponent} from 'metal-jsx';
import Context from './__mock__/mockContext.es';
import LayoutProvider from '../LayoutProvider.es';

let component;
let context = null;
let spritemap = 'icons.svg';

class Child extends JSXComponent {
	render() {
		return <div />;
	}
}

class Parent extends JSXComponent {
	render() {
		return (
			<LayoutProvider
				context={context}
				spritemap={spritemap}
				ref="provider"
			>
				<Child ref="child" />
			</LayoutProvider>
		);
	}
}

describe('LayoutProvider', () => {
	beforeEach(() => {
		context = JSON.parse(JSON.stringify(Context));

		jest.useFakeTimers();
	});

	afterEach(() => {
		if (component) {
			component.dispose();
		}

		context = null;
	});

	it('should receive context through PROPS and move to the internal state', () => {
		component = new LayoutProvider({
			context,
		});

		expect(component.state.context).toEqual(context);
	});

	it('should attach the events to the child component', () => {
		component = new Parent();

		const {provider} = component.refs;

		expect(provider.props.children[0].props.events).toMatchObject({
			fieldAdded: expect.any(Function),
			fieldClicked: expect.any(Function),
			fieldDeleted: expect.any(Function),
			fieldEdited: expect.any(Function),
			fieldMoved: expect.any(Function),
		});
	});

	it('should pass to the child component the context of the internal state', () => {
		component = new Parent();

		const {provider, child} = component.refs;

		expect(child.props.context).toEqual(provider.state.context);
	});

	it('should pass to the child component the focusedField', () => {
		component = new Parent();

		const {provider, child} = component.refs;

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

	it('should pass to the child component the mode', () => {
		component = new Parent();

		const {provider, child} = component.refs;

		provider.setState({
			mode: 'edit',
		});

		jest.runAllTimers();

		expect(child.props.mode).toEqual(provider.state.mode);
	});

	describe('Field events', () => {
		describe('fieldMoved', () => {
			it('should listen to the fieldMoved event and move the field to the row in context', () => {
				component = new Parent();

				const {provider, child} = component.refs;
				const mockEvent = {
					target: {
						pageIndex: 0,
						rowIndex: 0,
						columnIndex: false,
					},
					source: {
						columnIndex: 0,
						rowIndex: 1,
						pageIndex: 0,
					},
				};

				child.emit('fieldMoved', mockEvent);

				jest.runAllTimers();

				expect(provider.state.context).toMatchSnapshot();
				expect(child.props.context).toEqual(provider.state.context);
			});

			it('should listen to the fieldMoved event and move the field to the column in context', () => {
				component = new Parent();

				const {provider, child} = component.refs;
				const mockEvent = {
					target: {
						columnIndex: 1,
						pageIndex: 0,
						rowIndex: 0,
					},
					source: {
						columnIndex: 1,
						pageIndex: 0,
						rowIndex: 1,
					},
				};

				child.emit('fieldMoved', mockEvent);

				jest.runAllTimers();

				expect(provider.state.context).toMatchSnapshot();
				expect(child.props.context).toEqual(provider.state.context);
			});

			it('should move the field to the column in context and remove the row if there are no fields', () => {
				component = new Parent();

				const {provider, child} = component.refs;
				const mockEvent = {
					target: {
						columnIndex: 1,
						rowIndex: 0,
						pageIndex: 0,
					},
					source: {
						columnIndex: 0,
						pageIndex: 0,
						rowIndex: 2,
					},
				};

				child.emit('fieldMoved', mockEvent);

				jest.runAllTimers();

				expect(provider.state.context).toMatchSnapshot();
				expect(child.props.context).toEqual(provider.state.context);
			});

			it('should move the field to the row in context and remove the row if there are no fields', () => {
				component = new Parent();

				const {provider, child} = component.refs;
				const mockEvent = {
					target: {
						columnIndex: false,
						rowIndex: 0,
						pageIndex: 0,
					},
					source: {
						columnIndex: 0,
						pageIndex: 0,
						rowIndex: 0,
					},
				};

				child.emit('fieldMoved', mockEvent);

				jest.runAllTimers();

				expect(provider.state.context).toMatchSnapshot();
				expect(child.props.context).toEqual(provider.state.context);
			});
		});

		describe('fieldAdded', () => {
			it('should listen the fieldAdded event and add the field in the column to the context', () => {
				component = new Parent();

				const {provider, child} = component.refs;
				const mockEvent = {
					target: {
						rowIndex: 0,
						pageIndex: 0,
						columnIndex: 1,
					},
					fieldProperties: {
						type: 'text',
					},
				};

				child.emit('fieldAdded', mockEvent);

				jest.runAllTimers();

				expect(provider.state.context).toMatchSnapshot();
				expect(child.props.context).toEqual(provider.state.context);
			});

			it('should listen the fieldAdded event and add the field in the row to the context', () => {
				component = new Parent();

				const {provider, child} = component.refs;
				const mockEvent = {
					target: {
						rowIndex: 0,
						pageIndex: 0,
						columnIndex: false,
					},
					fieldProperties: {
						type: 'text',
					},
				};

				child.emit('fieldAdded', mockEvent);

				jest.runAllTimers();

				expect(provider.state.context).toMatchSnapshot();
				expect(child.props.context).toEqual(provider.state.context);
			});

			it('should update the focusedField with the location of the new field when adding to the context', () => {
				component = new Parent();

				const {provider, child} = component.refs;
				const mockEvent = {
					target: {
						rowIndex: 1,
						pageIndex: 0,
						columnIndex: 2,
					},
					fieldProperties: {
						type: 'text',
					},
				};

				child.emit('fieldAdded', mockEvent);

				const focusedField = {
					...mockEvent.target,
					type: mockEvent.fieldProperties.type,
				};

				jest.runAllTimers();

				expect(provider.state.focusedField).toEqual(focusedField);
				expect(child.props.focusedField).toEqual(focusedField);
				expect(provider.state.mode).toBe('edit');
			});
		});

		describe('fieldDeleted', () => {
			it('should listen the fieldDeleted event and delete the field in the column to the context', () => {
				component = new Parent();

				const {provider, child} = component.refs;
				const mockEvent = {
					rowIndex: 1,
					pageIndex: 0,
					columnIndex: 0,
				};

				child.emit('fieldDeleted', mockEvent);

				jest.runAllTimers();

				expect(provider.state.context).toMatchSnapshot();
				expect(child.props.context).toEqual(provider.state.context);
			});
		});

		describe('fieldEdited', () => {
			it('should listen the fieldEdited event and edit the field to the context', () => {
				component = new Parent();

				const {provider, child} = component.refs;
				const mockEvent = {
					value: 'Foo',
					key: 'label',
				};
				const mockFieldFocus = {
					columnIndex: 0,
					pageIndex: 0,
					rowIndex: 1,
				};

				child.emit('fieldClicked', mockFieldFocus);
				child.emit('fieldEdited', mockEvent);

				jest.runAllTimers();

				expect(
					provider.state.context[mockFieldFocus.pageIndex].rows[
						mockFieldFocus.rowIndex
					].columns[mockFieldFocus.columnIndex].fields[0][
						mockEvent.key
					]
				).toBe(mockEvent.value);
				expect(provider.state.context).toMatchSnapshot();
				expect(child.props.context).toEqual(provider.state.context);
			});
		});

		describe('fieldClicked', () => {
			it('should listen the fieldClicked event and change the state of the focusedField to the data receive', () => {
				component = new Parent();

				const {provider, child} = component.refs;
				const mockEvent = {
					columnIndex: 0,
					pageIndex: 0,
					rowIndex: 0,
					mode: 'edit',
				};

				child.emit('fieldClicked', mockEvent);

				expect(provider.props.children[0].props.events).toMatchObject({
					fieldClicked: expect.any(Function),
				});
				expect(provider.state.focusedField).toEqual(mockEvent);
			});
		});
	});
});
