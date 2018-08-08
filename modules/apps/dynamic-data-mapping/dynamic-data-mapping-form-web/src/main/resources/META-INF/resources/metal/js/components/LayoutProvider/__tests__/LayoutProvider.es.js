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
			fieldAdd: expect.any(Function),
			fieldClicked: expect.any(Function),
			fieldDelete: expect.any(Function),
			fieldEdit: expect.any(Function),
			fieldMove: expect.any(Function),
		});
	});

	it('should pass to the child component the context of the internal state', () => {
		component = new Parent();

		const {provider, child} = component.refs;

		expect(child.props.context).toEqual(provider.state.context);
	});

	it('should pass to the child component the fieldFocus', () => {
		component = new Parent();

		const {provider, child} = component.refs;

		const fieldFocus = {
			indexColumn: 0,
			indexPage: 0,
			indexRow: 0,
			type: 'radio',
		};

		provider.setState({
			fieldFocus,
		});

		jest.runAllTimers();

		expect(child.props.fieldFocus).toEqual(provider.state.fieldFocus);
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
		describe('fieldMove', () => {
			it('should listen to the fieldMove event and move the field to the row in context', () => {
				component = new Parent();

				const {provider, child} = component.refs;
				const mockEvent = {
					target: {
						indexPage: 0,
						indexRow: 0,
						indexColumn: false,
					},
					source: {
						indexColumn: 0,
						indexRow: 1,
						indexPage: 0,
					},
				};

				child.emit('fieldMove', mockEvent);

				jest.runAllTimers();

				expect(provider.state.context).toMatchSnapshot();
				expect(child.props.context).toEqual(provider.state.context);
			});

			it('should listen to the fieldMove event and move the field to the column in context', () => {
				component = new Parent();

				const {provider, child} = component.refs;
				const mockEvent = {
					target: {
						indexColumn: 1,
						indexPage: 0,
						indexRow: 0,
					},
					source: {
						indexColumn: 1,
						indexPage: 0,
						indexRow: 1,
					},
				};

				child.emit('fieldMove', mockEvent);

				jest.runAllTimers();

				expect(provider.state.context).toMatchSnapshot();
				expect(child.props.context).toEqual(provider.state.context);
			});

			it('should move the field to the column in context and remove the row if there are no fields', () => {
				component = new Parent();

				const {provider, child} = component.refs;
				const mockEvent = {
					target: {
						indexColumn: 1,
						indexRow: 0,
						indexPage: 0,
					},
					source: {
						indexColumn: 0,
						indexPage: 0,
						indexRow: 2,
					},
				};

				child.emit('fieldMove', mockEvent);

				jest.runAllTimers();

				expect(provider.state.context).toMatchSnapshot();
				expect(child.props.context).toEqual(provider.state.context);
			});

			it('should move the field to the row in context and remove the row if there are no fields', () => {
				component = new Parent();

				const {provider, child} = component.refs;
				const mockEvent = {
					target: {
						indexColumn: false,
						indexRow: 0,
						indexPage: 0,
					},
					source: {
						indexColumn: 0,
						indexPage: 0,
						indexRow: 0,
					},
				};

				child.emit('fieldMove', mockEvent);

				jest.runAllTimers();

				expect(provider.state.context).toMatchSnapshot();
				expect(child.props.context).toEqual(provider.state.context);
			});
		});

		describe('fieldAdd', () => {
			it('should listen the fieldAdd event and add the field in the column to the context', () => {
				component = new Parent();

				const {provider, child} = component.refs;
				const mockEvent = {
					target: {
						indexRow: 0,
						indexPage: 0,
						indexColumn: 1,
					},
					fieldProperties: {
						type: 'text',
					},
				};

				child.emit('fieldAdd', mockEvent);

				jest.runAllTimers();

				expect(provider.state.context).toMatchSnapshot();
				expect(child.props.context).toEqual(provider.state.context);
			});

			it('should listen the fieldAdd event and add the field in the row to the context', () => {
				component = new Parent();

				const {provider, child} = component.refs;
				const mockEvent = {
					target: {
						indexRow: 0,
						indexPage: 0,
						indexColumn: false,
					},
					fieldProperties: {
						type: 'text',
					},
				};

				child.emit('fieldAdd', mockEvent);

				jest.runAllTimers();

				expect(provider.state.context).toMatchSnapshot();
				expect(child.props.context).toEqual(provider.state.context);
			});

			it('should update the fieldFocus with the location of the new field when adding to the context', () => {
				component = new Parent();

				const {provider, child} = component.refs;
				const mockEvent = {
					target: {
						indexRow: 1,
						indexPage: 0,
						indexColumn: 2,
					},
					fieldProperties: {
						type: 'text',
					},
				};

				child.emit('fieldAdd', mockEvent);

				const fieldFocus = {
					...mockEvent.target,
					type: mockEvent.fieldProperties.type,
				};

				jest.runAllTimers();

				expect(provider.state.fieldFocus).toEqual(fieldFocus);
				expect(child.props.fieldFocus).toEqual(fieldFocus);
				expect(provider.state.mode).toBe('edit');
			});
		});

		describe('fieldDelete', () => {
			it('should listen the fieldDelete event and delete the field in the column to the context', () => {
				component = new Parent();

				const {provider, child} = component.refs;
				const mockEvent = {
					indexRow: 1,
					indexPage: 0,
					indexColumn: 0,
				};

				child.emit('fieldDelete', mockEvent);

				jest.runAllTimers();

				expect(provider.state.context).toMatchSnapshot();
				expect(child.props.context).toEqual(provider.state.context);
			});
		});

		describe('fieldEdit', () => {
			it('should listen the fieldEdit event and edit the field to the context', () => {
				component = new Parent();

				const {provider, child} = component.refs;
				const mockEvent = {
					value: 'Foo',
					key: 'label',
				};
				const mockFieldFocus = {
					indexColumn: 0,
					indexPage: 0,
					indexRow: 1,
				};

				child.emit('fieldClicked', mockFieldFocus);
				child.emit('fieldEdit', mockEvent);

				jest.runAllTimers();

				expect(
					provider.state.context[mockFieldFocus.indexPage].rows[
						mockFieldFocus.indexRow
					].columns[mockFieldFocus.indexColumn].fields[0][
						mockEvent.key
					]
				).toBe(mockEvent.value);
				expect(provider.state.context).toMatchSnapshot();
				expect(child.props.context).toEqual(provider.state.context);
			});
		});

		describe('fieldClicked', () => {
			it('should listen the fieldClicked event and change the state of the fieldFocus to the data receive', () => {
				component = new Parent();

				const {provider, child} = component.refs;
				const mockEvent = {
					indexColumn: 0,
					indexPage: 0,
					indexRow: 0,
					mode: 'edit',
				};

				child.emit('fieldClicked', mockEvent);

				expect(provider.props.children[0].props.events).toMatchObject({
					fieldClicked: expect.any(Function),
				});
				expect(provider.state.fieldFocus).toEqual(mockEvent);
			});
		});
	});
});
