import Builder from '../Builder.es';
import {dom as MetalTestUtil} from 'metal-dom';

let component;
const spritemap = 'icons.svg';
let addButton;

const fieldTypes = [
	{
		description: 'Select date from a Datepicker.',
		icon: 'calendar',
		label: 'Date',
		name: 'date'
	},
	{
		description: 'Single line or multiline text area.',
		icon: 'text',
		label: 'Text Field',
		name: 'text'
	},
	{
		description: 'Select only one item with a radio button.',
		icon: 'radio-button',
		label: 'Single Selection',
		name: 'radio'
	},
	{
		description: 'Choose an or more options from a list.',
		icon: 'list',
		label: 'Select from list',
		name: 'select'
	},
	{
		description: 'Select options from a matrix.',
		icon: 'grid',
		label: 'Grid',
		name: 'grid'
	},
	{
		description: 'Select multiple options using a checkbox.',
		icon: 'select-from-list',
		label: 'Multiple Selection',
		name: 'checkbox'
	}
];

describe(
	'Builder',
	() => {
		beforeEach(
			() => {
				jest.useFakeTimers();

				MetalTestUtil.enterDocument('<button id="addFieldButton"></button>');

				MetalTestUtil.enterDocument('<div class="ddm-translation-manager"></div>');

				MetalTestUtil.enterDocument('<div class="ddm-form-basic-info"></div>');

				addButton = document.querySelector('#addFieldButton');

				component = new Builder(
					{
						fieldTypes,
						spritemap
					}
				);
			}
		);

		afterEach(
			() => {
				MetalTestUtil.exitDocument(addButton);
				if (component) {
					component.dispose();
				}
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
				const spy = jest.spyOn(component, 'emit');
				const {sidebar} = component.refs;
				const mockEvent = {
					fieldType: {
						settingsContext: {
							pages: []
						}
					}
				};

				sidebar.emit('fieldAdded', mockEvent);

				expect(spy).toHaveBeenCalled();
				expect(spy).toHaveBeenCalledWith('fieldAdded', expect.anything());
			}
		);

		it(
			'should continue to propagate the fieldEdited event',
			() => {
				component.props.focusedField = {
					settingsContext: {
						pages: []
					}
				};

				const spy = jest.spyOn(component, 'emit');
				const {sidebar} = component.refs;
				const mockEvent = {
					fieldInstance: {
						fieldName: 'text'
					}
				};

				sidebar.emit('fieldEdited', mockEvent);

				expect(spy).toHaveBeenCalled();
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
	}
);