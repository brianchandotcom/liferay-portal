import Options from '../Options.es';
import {dom as MetalTestUtil} from 'metal-dom';

let component;
const spritemap = 'icons.svg';

describe(
	'Options',
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
				component = new Options(
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
				component = new Options(
					{
						spritemap,
						tip: 'Type something'
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should render items',
			() => {
				component = new Options(
					{
						items: [
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
			'should render no items when items is empty',
			() => {
				component = new Options(
					{
						items: [],
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should have an id',
			() => {
				component = new Options(
					{
						id: 'ID',
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should have a label',
			() => {
				component = new Options(
					{
						label: 'label',
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should not be required',
			() => {
				component = new Options(
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
				component = new Options(
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
				component = new Options(
					{
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should have a key',
			() => {
				component = new Options(
					{
						key: 'key',
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should have a placeholder',
			() => {
				component = new Options(
					{
						placeholder: 'Placeholder',
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should emit a field edit event on any item value change',
			() => {
				const handleFieldEdited = jest.fn();

				const events = {fieldEdited: handleFieldEdited};

				component = new Options(
					{
						events,
						items: [
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
					component.element.querySelector('input'),
					'input',
					{}
				);

				expect(handleFieldEdited).toHaveBeenCalled();
			}
		);

		it(
			'should add a new item when add a content in the last option',
			() => {
				const handleFieldEdited = jest.fn();

				const events = {fieldEdited: handleFieldEdited};

				component = new Options(
					{
						events,
						items: [
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
				const initialSize = component.items.length;

				MetalTestUtil.triggerEvent(
					component.element.querySelector('.field-options .form-group:last-child input'),
					'input',
					{}
				);
				const finalSize = component.items.length;

				expect(finalSize > initialSize).toBe(true);
			}
		);

		it(
			'should propagate the field edit event when any item value change',
			() => {
				component = new Options(
					{
						items: [
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
					component.element.querySelector('input'),
					'input',
					{}
				);

				expect(spy).toHaveBeenCalled();
				expect(spy).toHaveBeenCalledWith('fieldEdited', expect.any(Object));
			}
		);
	}
);