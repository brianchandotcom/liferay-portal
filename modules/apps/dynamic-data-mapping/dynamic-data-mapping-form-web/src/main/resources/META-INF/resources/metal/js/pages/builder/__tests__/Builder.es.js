import Builder from '../Builder.es';
import {dom as MetalTestUtil} from 'metal-dom';
import Pages from './__mock__/mockPages.es';

const spritemap = 'icons.svg';

let addButton;
let basicInfo;
let component;
let pages;
let translationManager;

const mockFieldType = {
	description: 'Single line or multiline text area.',
	icon: 'text',
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

const fieldTypes = [
	{
		description: 'Select date from a Datepicker.',
		icon: 'calendar',
		label: 'Date',
		name: 'date',
		settingsContext: {
			pages: []
		}
	},
	mockFieldType,
	{
		description: 'Select only one item with a radio button.',
		icon: 'radio-button',
		label: 'Single Selection',
		name: 'radio',
		settingsContext: {
			pages: []
		}
	},
	{
		description: 'Choose an or more options from a list.',
		icon: 'list',
		label: 'Select from list',
		name: 'select',
		settingsContext: {
			pages: []
		}
	},
	{
		description: 'Select options from a matrix.',
		icon: 'grid',
		label: 'Grid',
		name: 'grid',
		settingsContext: {
			pages: []
		}
	},
	{
		description: 'Select multiple options using a checkbox.',
		icon: 'select-from-list',
		label: 'Multiple Selection',
		name: 'checkbox',
		settingsContext: {
			pages: []
		}
	}
];

describe(
	'Builder',
	() => {
		beforeEach(
			() => {
				pages = JSON.parse(JSON.stringify(Pages));

				jest.useFakeTimers();

				MetalTestUtil.enterDocument('<button id="addFieldButton"></button>');
				MetalTestUtil.enterDocument('<div class="ddm-translation-manager"></div>');
				MetalTestUtil.enterDocument('<div class="ddm-form-basic-info"></div>');

				addButton = document.querySelector('#addFieldButton');
				basicInfo = document.querySelector('.ddm-form-basic-info');
				translationManager = document.querySelector('.ddm-translation-manager');

				component = new Builder(
					{
						fieldTypes,
						pages,
						spritemap
					}
				);
			}
		);

		afterEach(
			() => {
				MetalTestUtil.exitDocument(addButton);
				MetalTestUtil.exitDocument(basicInfo);
				MetalTestUtil.exitDocument(translationManager);

				if (component) {
					component.dispose();
				}

				jest.clearAllTimers();
			}
		);

		it(
			'should render the default markup',
			() => {
				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should continue to propagate the fieldAdded event',
			() => {
				const {sidebar} = component.refs;
				const spy = jest.spyOn(component, 'emit');

				sidebar.emit(
					'fieldAdded',
					{
						fieldType: mockFieldType
					}
				);

				jest.runAllTimers();

				expect(spy).toHaveBeenCalledWith('fieldAdded', expect.anything());
			}
		);

		it(
			'should continue to propagate the fieldBlurred event',
			() => {
				const {sidebar} = component.refs;
				const spy = jest.spyOn(component, 'emit');

				sidebar.emit('fieldBlurred');

				jest.runAllTimers();

				expect(spy).toHaveBeenCalledWith('fieldBlurred');
			}
		);

		it(
			'should continue to propagate the fieldClicked event',
			() => {
				const {FormRenderer} = component.refs;
				const spy = jest.spyOn(component, 'emit');

				FormRenderer.emit('fieldClicked', 1);

				jest.runAllTimers();

				expect(spy).toHaveBeenCalledWith('fieldClicked', 1);
			}
		);

		it(
			'should continue to propagate the pageAdded event',
			() => {
				const {FormRenderer} = component.refs;
				const spy = jest.spyOn(component, 'emit');

				FormRenderer.emit('pageAdded');

				jest.runAllTimers();

				expect(spy).toHaveBeenCalledWith('pageAdded');
			}
		);

		it(
			'should continue to propagate the pageDeleted event',
			() => {
				const {FormRenderer} = component.refs;
				const spy = jest.spyOn(component, 'emit');

				FormRenderer.emit('pageDeleted');

				jest.runAllTimers();

				expect(spy).toHaveBeenCalledWith('pageDeleted', expect.anything());
			}
		);

		it(
			'should continue to propagate the pagesUpdated event',
			() => {
				const {FormRenderer} = component.refs;
				const spy = jest.spyOn(component, 'emit');

				FormRenderer.emit('pagesUpdated');

				jest.runAllTimers();

				expect(spy).toHaveBeenCalledWith('pagesUpdated', expect.anything());
			}
		);

		it(
			'should continue to propagate the activePageUpdated event',
			() => {
				const {FormRenderer} = component.refs;
				const spy = jest.spyOn(component, 'emit');

				FormRenderer.emit('activePageUpdated');

				jest.runAllTimers();

				expect(spy).toHaveBeenCalledWith('activePageUpdated', expect.anything());
			}
		);

		it(
			'should continue to propagate the fieldDeleted event',
			() => {
				const {FormRenderer} = component.refs;
				const spy = jest.spyOn(component, 'emit');

				FormRenderer.emit('fieldDeleted');

				jest.runAllTimers();

				expect(spy).toHaveBeenCalledWith('fieldDeleted', expect.anything());
			}
		);

		it(
			'should continue to propagate the fieldDuplicated event',
			() => {
				const {FormRenderer} = component.refs;
				const spy = jest.spyOn(component, 'emit');

				FormRenderer.emit('fieldDuplicated');

				jest.runAllTimers();

				expect(spy).toHaveBeenCalledWith('fieldDuplicated', expect.anything());
			}
		);

		it(
			'should continue to propagate the fieldEdited event',
			() => {
				const {sidebar} = component.refs;
				const spy = jest.spyOn(component, 'emit');

				component.props.focusedField = mockFieldType;

				sidebar.emit(
					'fieldEdited',
					{
						fieldInstance: {
							...mockFieldType,
							fieldName: 'label'
						}
					}
				);

				jest.runAllTimers();

				expect(spy).toHaveBeenCalledWith('fieldEdited', expect.anything());
			}
		);

		it(
			'should continue to propagate the fieldMoved event',
			() => {
				const spy = jest.spyOn(component, 'emit');
				const {FormRenderer} = component.refs;
				const mockEvent = jest.fn();

				FormRenderer.emit('fieldMoved', mockEvent);

				expect(spy).toHaveBeenCalled();
				expect(spy).toHaveBeenCalledWith('fieldMoved', expect.anything());
			}
		);

		it(
			'should open sidebar when the "pageReset" event is received',
			() => {
				const {FormRenderer, sidebar} = component.refs;

				FormRenderer.emit('pageReset');

				jest.runAllTimers();

				expect(sidebar.state.open).toBeTruthy();
			}
		);

		it(
			'should open sidebar when activePage changes and new page has no fields',
			() => {
				const spy = jest.spyOn(component, 'openSidebar');

				component.props.pages = [
					...pages,
					{
						rows: []
					}
				];
				component.props.activePage = 1;

				jest.runAllTimers();

				expect(spy).toHaveBeenCalled();
			}
		);

		it(
			'should not open sidebar when activePage changes and new page has fields',
			() => {
				const spy = jest.spyOn(component, 'openSidebar');

				component.props.pages = [
					...pages,
					...pages
				];
				component.props.activePage = 1;

				jest.runAllTimers();

				expect(spy).not.toHaveBeenCalled();
			}
		);
	}
);