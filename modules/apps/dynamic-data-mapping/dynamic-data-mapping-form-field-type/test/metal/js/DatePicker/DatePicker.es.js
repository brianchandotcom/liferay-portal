import DatePicker from 'source/DatePicker/DatePicker.es';
import dom from 'metal-dom';

let component;
const spritemap = 'icons.svg';

const defaultDatePickerConfig = {
	name: 'dateField',
	spritemap
};

describe('DatePicker', () => {
	afterEach(() => {
		component.dispose();
	});

	beforeEach(() => {
		jest.useFakeTimers();
	});

	it('should have a helptext', () => {
		component = new DatePicker({
			...defaultDatePickerConfig,
			tip: 'Type something'
		});

		expect(component).toMatchSnapshot();
	});

	it('should have an id', () => {
		component = new DatePicker({
			...defaultDatePickerConfig,
			id: 'ID'
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a label', () => {
		component = new DatePicker({
			...defaultDatePickerConfig,
			label: 'label'
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a predefinedValue', () => {
		component = new DatePicker({
			...defaultDatePickerConfig,
			predefinedValue: '05/05/2019'
		});

		expect(component).toMatchSnapshot();
	});

	it('should expand the datepicker when clicking the calendar icon', () => {
		component = new DatePicker({
			...defaultDatePickerConfig
		});

		const spy = jest.spyOn(component, 'emit');

		dom.triggerEvent(
			component.element.querySelector('.input-group-item button'),
			'click'
		);

		const event = {};

		component._handleToggle(event);

		expect(spy).toBeCalled();
	});

	it('should fill the input with the current date selected on Date Picker', () => {
		component = new DatePicker({
			...defaultDatePickerConfig
		});

		const spy = jest.spyOn(component, 'emit');

		dom.triggerEvent(
			component.element.querySelector('.input-group-item button'),
			'click'
		);

		jest.runAllTimers();

		dom.triggerEvent(
			component.element.querySelector("[aria-label='live']"),
			'click'
		);

		jest.runAllTimers();

		expect(spy).toHaveBeenCalledWith('fieldEdited', expect.anything());
	});

	it('should decrease the current month when the back arrow is selected on Date Picker', () => {
		component = new DatePicker({
			...defaultDatePickerConfig
		});

		dom.triggerEvent(
			component.element.querySelector('.input-group-item button'),
			'click'
		);

		jest.runAllTimers();

		dom.triggerEvent(
			component.element.querySelector("[aria-label='live']"),
			'click'
		);

		const monthBefore = component._month;

		jest.runAllTimers();

		dom.triggerEvent(
			component.element.querySelector("[aria-label='angle-left']"),
			'click'
		);

		jest.runAllTimers();

		if (monthBefore > 0) {
			expect(component._month).toEqual(monthBefore - 1);
		} else if (monthBefore == 0) {
			expect(component._month).toEqual(11);
		}
	});

	it('should increase the current month when the forward arrow is selected on Date Picker', () => {
		component = new DatePicker({
			...defaultDatePickerConfig
		});

		dom.triggerEvent(
			component.element.querySelector('.input-group-item button'),
			'click'
		);

		jest.runAllTimers();

		dom.triggerEvent(
			component.element.querySelector("[aria-label='live']"),
			'click'
		);

		const monthBefore = component._month;

		jest.runAllTimers();

		dom.triggerEvent(
			component.element.querySelector("[aria-label='angle-right']"),
			'click'
		);

		jest.runAllTimers();

		if (monthBefore < 11) {
			expect(component._month).toEqual(monthBefore + 1);
		} else if (monthBefore == 11) {
			expect(component._month).toEqual(0);
		}
	});
});
