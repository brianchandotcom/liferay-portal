import {dom as MetalTestUtil} from 'metal-dom';
import createElement from './__mock__/createElement.es';
import Sidebar from '../Sidebar.es';

let component;
const spritemap = 'icons.svg';

const fieldLists = [
	{
		description: 'Select date from a Datepicker.',
		icon: 'calendar',
		name: 'Date',
		type: 'date'
	},
	{
		description: 'Single line or multiline text area.',
		icon: 'text',
		name: 'Text Field',
		type: 'text'
	},
	{
		description: 'Select only one item with a radio button.',
		icon: 'radio-button',
		name: 'Single Selection',
		type: 'radio'
	},
	{
		description: 'Choose an or more options from a list.',
		icon: 'list',
		name: 'Select from list',
		type: 'select'
	},
	{
		description: 'Select options from a matrix.',
		icon: 'grid',
		name: 'Grid',
		type: 'grid'
	},
	{
		description: 'Select multiple options using a checkbox.',
		icon: 'select-from-list',
		name: 'Multiple Selection',
		type: 'checkbox'
	}
];

const fieldContext = [
	{
		rows: [
			{
				columns: [
					{
						fields: [
							{
								key: 'label',
								label: 'Label',
								spritemap,
								type: 'text'
							}
						],
						size: 12
					}
				]
			}
		]
	}
];

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
						spritemap
					}
				);
				component.show();

				jest.runAllTimers();

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should render a Sidebar closed',
			() => {
				component = new Sidebar(
					{
						spritemap
					}
				);

				component.show();
				component.close();

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should render a Sidebar with fieldLists',
			() => {
				component = new Sidebar(
					{
						fieldLists,
						spritemap
					}
				);

				component.show();

				jest.runAllTimers();

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should render a Sidebar with spritemap',
			() => {
				component = new Sidebar(
					{
						spritemap
					}
				);

				component.show();

				jest.runAllTimers();

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should open Sidebar when is edit mode',
			() => {
				component = new Sidebar(
					{
						fieldContext,
						fieldLists,
						focusedField: {
							columnIndex: 0,
							pageIndex: 0,
							rowIndex: 0,
							type: 'text'
						},
						spritemap
					}
				);

				const spy = jest.spyOn(component, 'show');

				component.props.mode = 'edit';

				jest.runAllTimers();

				expect(component.state.show).toBeTruthy();
				expect(spy).toHaveBeenCalled();
			}
		);

		it(
			'should not update the internal mode with the above mode changes if are not in edit mode',
			() => {
				component = new Sidebar(
					{
						spritemap
					}
				);

				const spy = jest.spyOn(component, '_setMode');

				component.props.mode = 'edit';

				jest.runAllTimers();

				expect(component.state.mode).toBe('add');
				expect(spy).toHaveBeenCalled();
			}
		);

		it(
			'should update the internal mode with the above mode changes when in edit mode',
			() => {
				component = new Sidebar(
					{
						fieldContext,
						fieldLists,
						focusedField: {
							columnIndex: 0,
							pageIndex: 0,
							rowIndex: 0,
							type: 'text'
						},
						spritemap
					}
				);

				const spy = jest.spyOn(component, '_setMode');

				component.props.mode = 'edit';

				jest.runAllTimers();

				expect(component.state.mode).toBe(component.props.mode);
				expect(spy).toHaveBeenCalled();
			}
		);

		it(
			'should reset drag and drop when context changes',
			() => {
				component = new Sidebar(
					{
						spritemap
					}
				);

				const spy = jest.spyOn(component, '_startDrag');
				const spyDrag = jest.spyOn(component._dragAndDrop, 'disposeInternal');

				component.props.context = [
					{
						title: 'Untitled page'
					}
				];

				jest.runAllTimers();

				expect(spy).toHaveBeenCalled();
				expect(spyDrag).toHaveBeenCalled();
			}
		);

		it(
			'should continue to propagate the fieldEdited event',
			() => {
				component = new Sidebar(
					{
						fieldContext,
						fieldLists,
						focusedField: {
							columnIndex: 0,
							pageIndex: 0,
							rowIndex: 0,
							type: 'text'
						},
						mode: 'edit',
						spritemap
					}
				);

				const spy = jest.spyOn(component, 'emit');
				const {layoutRenderer} = component.refs;

				layoutRenderer.emit('fieldEdited', {});

				expect(spy).toHaveBeenCalled();
				expect(spy).toHaveBeenCalledWith('fieldEdited', expect.any(Object));
			}
		);

		it(
			'should close sidebar when dragging an item',
			() => {
				component = new Sidebar(
					{
						fieldLists,
						spritemap
					}
				);

				component.show();

				expect(component.state.show).toBeTruthy();

				component._handleDrag(jest.fn());

				jest.runAllTimers();

				expect(component.state.show).toBeFalsy();
			}
		);

		it(
			'should emit a fieldAdded event when adding in layout',
			() => {
				component = new Sidebar(
					{
						fieldLists,
						spritemap
					}
				);

				const spy = jest.spyOn(component, 'emit');
				const {field1} = component.refs;
				const element = createElement(
					{
						attributes: [
							{
								key: 'data-ddm-field-column',
								value: 0
							},
							{
								key: 'data-ddm-field-row',
								value: 2
							},
							{
								key: 'data-ddm-field-page',
								value: 2
							}
						],
						tagname: 'div'
					}
				);
				const mockEvent = {
					source: field1,
					target: {
						parentElement: element
					}
				};

				component._handleFieldMoved(
					mockEvent,
					{
						preventDefault: jest.fn()
					}
				);

				jest.runAllTimers();

				expect(spy).toHaveBeenCalled();
				expect(spy).toHaveBeenCalledWith(
					'fieldAdded',
					{
						data: expect.anything(),
						fieldProperties: expect.any(Object),
						target: {
							columnIndex: 0,
							pageIndex: 2,
							rowIndex: 2
						}
					}
				);
			}
		);

		it(
			'should not emit when there is no target to drag item',
			() => {
				component = new Sidebar(
					{
						fieldLists,
						spritemap
					}
				);

				const mockEvent = {
					source: undefined,
					target: undefined
				};
				const spy = jest.spyOn(component, 'emit');

				component._handleFieldMoved(
					mockEvent,
					{
						preventDefault: jest.fn()
					}
				);

				expect(spy).not.toHaveBeenCalled();
			}
		);

		describe(
			'Edit mode',
			() => {
				it(
					'should not go into edit mode with just fieldContext',
					() => {
						component = new Sidebar(
							{
								fieldContext,
								spritemap
							}
						);

						component.show();
						jest.runAllTimers();

						expect(component).toMatchSnapshot();
					}
				);

				it(
					'should not enter editing mode with only `edit` in mode',
					() => {
						component = new Sidebar(
							{
								mode: 'edit',
								spritemap
							}
						);

						component.show();
						jest.runAllTimers();

						expect(component._isEditMode()).toBeFalsy();
						expect(component).toMatchSnapshot();
					}
				);

				it(
					'should go into edit mode with just fieldContext, fieldLists and focusedField',
					() => {
						component = new Sidebar(
							{
								fieldContext,
								fieldLists,
								focusedField: {
									columnIndex: 0,
									pageIndex: 0,
									rowIndex: 0,
									type: 'text'
								},
								mode: 'edit',
								spritemap
							}
						);

						component.show();
						jest.runAllTimers();

						expect(component).toMatchSnapshot();
					}
				);

				it(
					'should return true when there is focusedField, edit mode, fieldLists, and fieldContext',
					() => {
						component = new Sidebar(
							{
								fieldContext,
								fieldLists,
								focusedField: {
									columnIndex: 0,
									pageIndex: 0,
									rowIndex: 0,
									type: 'text'
								},
								mode: 'edit',
								spritemap
							}
						);

						expect(component._isEditMode()).toBe(true);
					}
				);

				it(
					'should return false when there is only focusedField',
					() => {
						component = new Sidebar(
							{
								focusedField: {
									columnIndex: 0,
									pageIndex: 0,
									rowIndex: 0,
									type: 'text'
								},
								spritemap
							}
						);

						expect(component._isEditMode()).toBe(false);
					}
				);
			}
		);

		describe(
			'Interaction with markup',
			() => {
				it(
					'should the close Sidebar when click outside Sidebar',
					() => {
						component = new Sidebar(
							{
								spritemap
							}
						);

						const spy = jest.spyOn(component, '_handleDocClick');

						component.show();

						MetalTestUtil.triggerEvent(document, 'click', {});

						jest.runAllTimers();

						expect(component.state.show).toBeFalsy();
						expect(spy).toHaveBeenCalled();
					}
				);

				it(
					'should not close Sidebar when click inside Sidebar',
					() => {
						component = new Sidebar(
							{
								spritemap
							}
						);

						const spy = jest.spyOn(component, '_handleDocClick');

						component.show();

						MetalTestUtil.triggerEvent(component.element, 'click', {});

						jest.runAllTimers();

						expect(component.state.show).toBeTruthy();
						expect(spy).toHaveBeenCalled();
					}
				);

				it(
					'should close Sidebar when click the button close',
					() => {
						component = new Sidebar(
							{
								spritemap
							}
						);

						component.show();

						expect(component.state.show).toBeTruthy();

						const spy = jest.spyOn(component, 'close');
						const {close} = component.refs;

						close.click();

						jest.runAllTimers();

						expect(component.state.show).toBeFalsy();
						expect(spy).toHaveBeenCalled();
					}
				);

				it(
					'should change the tab on edit mode',
					() => {
						component = new Sidebar(
							{
								fieldContext,
								fieldLists,
								focusedField: {
									columnIndex: 0,
									pageIndex: 0,
									rowIndex: 0,
									type: 'text'
								},
								mode: 'edit',
								spritemap
							}
						);

						const {tab1} = component.refs;

						MetalTestUtil.triggerEvent(tab1, 'click', {});

						jest.runAllTimers();

						expect(component.state.activeTab).toBe(1);
					}
				);

				it(
					'should return to add mode',
					() => {
						component = new Sidebar(
							{
								fieldContext,
								fieldLists,
								focusedField: {
									columnIndex: 0,
									pageIndex: 0,
									rowIndex: 0,
									type: 'text'
								},
								mode: 'edit',
								spritemap
							}
						);

						component.show();

						jest.runAllTimers();

						const {previousButton} = component.refs;

						MetalTestUtil.triggerEvent(previousButton.element, 'click', {});

						jest.runAllTimers();

						expect(component.state.mode).toBe('add');
					}
				);
			}
		);
	}
);