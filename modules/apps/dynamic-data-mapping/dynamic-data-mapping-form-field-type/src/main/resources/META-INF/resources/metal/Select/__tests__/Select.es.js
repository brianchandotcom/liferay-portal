import Select from '../Select.es';
import {dom as MetalTestUtil} from 'metal-dom';

let component;
const spritemap = 'icons.svg';

describe(
	'Select',
	() => {
		afterEach(
			() => {
				if (component) {
					component.dispose();
				}
			}
		);

		it(
			'should be not edidable',
			() => {
				component = new Select(
					{
						readOnly: false,
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should have a helptext',
			() => {
				component = new Select(
					{
						spritemap,
						tip: 'Type something'
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should have an id',
			() => {
				component = new Select(
					{
						id: 'ID',
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should render options',
			() => {
				component = new Select(
					{
						options: [
							{
								checked: false,
								disabled: false,
								id: 'id',
								inline: false,
								label: 'label',
								name: 'name',
								showLabel: true,
								value: 'item'
							}
						],
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should render no options when options come empty',
			() => {
				component = new Select(
					{
						options: [],
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should have a label',
			() => {
				component = new Select(
					{
						label: 'label',
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should be closed by default',
			() => {
				component = new Select(
					{
						open: false,
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should have class dropdown-opened when it\'s opened',
			() => {
				component = new Select(
					{
						open: true,
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should have a placeholder',
			() => {
				component = new Select(
					{
						placeholder: 'Placeholder',
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should have a predefinedValue',
			() => {
				component = new Select(
					{
						predefinedValue: ['Select'],
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should not be required',
			() => {
				component = new Select(
					{
						required: false,
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should render Label if showLabel is true',
			() => {
				component = new Select(
					{
						label: 'text',
						showLabel: true,
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should have a spritemap',
			() => {
				component = new Select(
					{
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should have a value',
			() => {
				component = new Select(
					{
						spritemap,
						value: ['value']
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should have a key',
			() => {
				component = new Select(
					{
						key: 'key',
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should emit a field edit event when an item is selected',
			() => {
				const handleFieldEdited = jest.fn();

				const events = {fieldEdited: handleFieldEdited};

				component = new Select(
					{
						events,
						options: [
							{
								checked: false,
								disabled: false,
								id: 'id',
								inline: false,
								label: 'label',
								name: 'name',
								showLabel: true,
								value: 'item'
							}
						],
						spritemap
					}
				);

				MetalTestUtil.triggerEvent(
					component.element.querySelector('.dropdown-menu'),
					'click',
					{}
				);

				expect(handleFieldEdited).toHaveBeenCalled();
			}
		);

		it(
			'should open dropdown when select is clicked',
			() => {
				component = new Select(
					{
						options: [
							{
								checked: false,
								disabled: false,
								id: 'id',
								inline: false,
								label: 'label',
								name: 'name',
								showLabel: true,
								value: 'item'
							}
						],
						spritemap
					}
				);

				MetalTestUtil.triggerEvent(
					component.element.querySelector('.select-field-trigger'),
					'click',
					{}
				);
				expect(component.getState().open).toBe(true);
			}
		);

		it(
			'should propagate the field edit event',
			() => {
				component = new Select(
					{
						options: [
							{
								checked: false,
								disabled: false,
								id: 'id',
								inline: false,
								label: 'label',
								name: 'name',
								showLabel: true,
								value: 'item'
							}
						],
						spritemap
					}
				);

				const spy = jest.spyOn(component, 'emit');

				MetalTestUtil.triggerEvent(
					component.element.querySelector('.dropdown-menu'),
					'click',
					{}
				);

				expect(spy).toHaveBeenCalled();
				expect(spy).toHaveBeenCalledWith('fieldEdited', expect.any(Object));
			}
		);
	}
);