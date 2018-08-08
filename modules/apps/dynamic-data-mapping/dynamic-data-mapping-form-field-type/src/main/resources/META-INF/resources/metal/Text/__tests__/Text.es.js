import Text from '../Text.es';
import {dom as MetalTestUtil} from 'metal-dom';

let component;
let spritemap = 'icons.svg';

describe('Field Text', () => {
	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	it('should not be editable', () => {
		component = new Text({
			editable: false,
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a helptext', () => {
		component = new Text({
			helpText: 'Type something',
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should have an id', () => {
		component = new Text({
			id: 'ID',
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a label', () => {
		component = new Text({
			label: 'label',
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a placeholder', () => {
		component = new Text({
			placeholder: 'Placeholder',
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should not be required', () => {
		component = new Text({
			required: false,
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should render Label if showLabel is true', () => {
		component = new Text({
			label: 'text',
			showLabel: true,
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a spritemap', () => {
		component = new Text({
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a value', () => {
		component = new Text({
			value: 'value',
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a key', () => {
		component = new Text({
			key: 'key',
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should emit a field edit event on field value change', () => {
		const handleFieldEdit = jest.fn();
		const events = {fieldEdit: handleFieldEdit};

		component = new Text({
			spritemap: spritemap,
			events,
		});

		MetalTestUtil.triggerEvent(
			component.element.querySelector('input'),
			'input',
			{}
		);

		expect(handleFieldEdit).toHaveBeenCalled();
	});

	it('should emit a field edit with correct parameters', done => {
		const handleFieldEdit = data => {
			expect(data).toEqual(
				expect.objectContaining({
					value: expect.any(String),
					key: 'input',
					originalEvent: expect.any(Object),
				})
			);
			done();
		};
		const events = {fieldEdit: handleFieldEdit};

		component = new Text({
			spritemap: spritemap,
			key: 'input',
			events,
		});

		MetalTestUtil.triggerEvent(
			component.element.querySelector('input'),
			'input',
			{}
		);
	});

	it('should propagate the field edit event', () => {
		component = new Text({
			spritemap: spritemap,
			key: 'input',
		});

		const spy = jest.spyOn(component, 'emit');

		MetalTestUtil.triggerEvent(
			component.element.querySelector('input'),
			'input',
			{}
		);

		expect(spy).toHaveBeenCalled();
		expect(spy).toHaveBeenCalledWith('fieldEdit', expect.any(Object));
	});
});
