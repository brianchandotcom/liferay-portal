import Sidebar from 'source/components/Sidebar/Sidebar.es';
import {dom as MetalTestUtil} from 'metal-dom';

let component;
const focusedField = {
	colIndex: 0,
	pageIndex: 0,
	rowIndex: 0,
	settingsContext: {
		pages: []
	},
	type: 'date'
};
const spritemap = 'icons.svg';

const fieldTypes = [
	{
		description: 'Select date from a Datepicker.',
		group: 'basic',
		icon: 'calendar',
		label: 'Date',
		name: 'date'
	},
	{
		description: 'Single line or multiline text area.',
		group: 'basic',
		icon: 'text',
		label: 'Text Field',
		name: 'text'
	},
	{
		description: 'Select only one item with a radio button.',
		group: 'basic',
		icon: 'radio-button',
		label: 'Single Selection',
		name: 'radio'
	},
	{
		description: 'Choose one or more options from a list.',
		group: 'basic',
		icon: 'list',
		label: 'Select from list',
		name: 'select'
	},
	{
		description: 'Select options from a matrix.',
		group: 'basic',
		icon: 'grid',
		label: 'Grid',
		name: 'grid'
	},
	{
		description: 'Select multiple options using a checkbox.',
		group: 'basic',
		icon: 'select-from-list',
		label: 'Multiple Selection',
		name: 'checkbox'
	}
];
const mockFieldType = {
	description: 'Single line or multiline text area.',
	icon: 'text',
	initialConfig_: {
		locale: 'en_US'
	},
	label: 'Text Field',
	name: 'text',
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
										localizable: true
									},
									{
										fieldName: 'name'
									},
									{
										fieldName: 'required'
									},
									{
										fieldName: 'type'
									}
								]
							}
						]
					}
				]
			}
		]
	},
	type: 'text'
};

describe(
	'Sidebar',
	() => {
		beforeEach(() => jest.useFakeTimers());

		afterEach(
			() => {
				if (component) {
					component.dispose();
				}
			}
		);

		it(
			'should render the default markup',
			() => {
				component = new Sidebar(
					{
						fieldTypes,
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should render a Sidebar open',
			() => {
				component = new Sidebar(
					{
						fieldTypes,
						spritemap
					}
				);
				component.open();

				jest.runAllTimers();

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should render a Sidebar closed',
			() => {
				component = new Sidebar(
					{
						fieldTypes,
						spritemap
					}
				);

				component.open();
				component.close();

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should render a Sidebar with fieldTypes',
			() => {
				component = new Sidebar(
					{
						fieldTypes,
						spritemap
					}
				);

				component.open();

				jest.runAllTimers();

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should close the sidebar when the mouse down event is not on it',
			() => {
				component = new Sidebar(
					{
						fieldTypes,
						spritemap
					}
				);

				jest.runAllTimers();

				component.open();

				component._handleDocumentMouseDown(
					{
						target: null
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should emit fieldMoved when the dragEnd method is called',
			() => {
				component = new Sidebar(
					{
						fieldTypes,
						spritemap
					}
				);

				const event = {
					preventDefault: jest.fn()
				};

				const data = {
					source: {
						dataset: {
							fieldTypeName: 'paragraph'
						}
					},
					target: {
						parentElement: {
							getAttribute: jest.fn()
						}
					}
				};

				jest.runAllTimers();

				component.open();

				component._handleDragEnded(data, event);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should emit the fieldDuplicated event when the duplicate field option is clicked on the sidebar settings',
			() => {
				component = new Sidebar(
					{
						fieldTypes,
						focusedField,
						spritemap
					}
				);

				const spy = jest.spyOn(component, 'emit');

				const data = {
					item: {
						settingsItem: 'duplicate-field'
					}
				};

				jest.runAllTimers();

				component.open();

				component._handleFieldSettingsClicked({data});

				expect(spy).toHaveBeenCalled();
			}
		);

		it(
			'should emit the fieldDeleted event when the delete field option is clicked on the sidebar settings',
			() => {
				component = new Sidebar(
					{
						fieldTypes,
						focusedField,
						spritemap
					}
				);

				const spy = jest.spyOn(component, 'emit');

				const data = {
					item: {
						settingsItem: 'delete-field'
					}
				};

				jest.runAllTimers();

				component.open();

				component._handleFieldSettingsClicked({data});

				expect(spy).toHaveBeenCalled();
			}
		);

		it(
			'should render a Sidebar with spritemap',
			() => {
				component = new Sidebar(
					{
						fieldTypes,
						spritemap
					}
				);

				component.open();

				jest.runAllTimers();

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should close the sidebar in edition mode',
			() => {
				const focusedField = mockFieldType;

				component = new Sidebar(
					{
						fieldTypes,
						focusedField,
						spritemap
					}
				);

				component.open();

				jest.runAllTimers();

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should close the sidebar in edition mode',
			() => {
				const focusedField = mockFieldType;

				component = new Sidebar(
					{
						fieldTypes,
						focusedField,
						spritemap
					}
				);

				component.open();

				jest.runAllTimers();

				MetalTestUtil.triggerEvent(component.refs.previousButton.element, 'click', {});

				jest.runAllTimers();

				expect(component.state.open).toBeFalsy();
				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should propagates evaluator changed event',
			() => {
				const focusedField = mockFieldType;

				component = new Sidebar(
					{
						fieldTypes,
						focusedField,
						spritemap
					}
				);

				component.open();

				jest.runAllTimers();

				const {FormRenderer} = component.refs;
				const spy = jest.spyOn(component, 'emit');

				FormRenderer.emit(
					'evaluated',
					{
						focusedField
					},
				);

				expect(spy).toHaveBeenCalled();
			}
		);

		it(
			'should propagates field edited event',
			() => {
				const focusedField = mockFieldType;

				component = new Sidebar(
					{
						fieldTypes,
						focusedField,
						spritemap
					}
				);

				component.open();

				jest.runAllTimers();

				const {FormRenderer} = component.refs;
				const spy = jest.spyOn(component, 'emit');

				FormRenderer.emit(
					'fieldEdited',
					{},
				);

				expect(spy).toHaveBeenCalled();
			}
		);

		describe(
			'Interaction with markup',
			() => {
				it(
					'should close Sidebar when click the button close',
					() => {
						component = new Sidebar(
							{
								fieldTypes,
								spritemap
							}
						);

						component.open();

						expect(component.state.open).toBeTruthy();

						const spy = jest.spyOn(component, 'close');
						const {close} = component.refs;

						close.click();

						jest.runAllTimers();

						expect(component.state.open).toBeFalsy();
						expect(spy).toHaveBeenCalled();
					}
				);
			}
		);
	}
);