import Text from '../Text.es';
import {dom as MetalTestUtil} from 'metal-dom';

let component;
const spritemap = 'icons.svg';

describe(
	'Field Text',
	() => {
		afterEach(
			() => {
				if (component) {
					component.dispose();
				}
			}
		);

		it(
			'should not be editable',
			() => {
				component = new Text(
					{
						editable: false,
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should have a helptext',
			() => {
				component = new Text(
					{
						helpText: 'Type something',
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should have an id',
			() => {
				component = new Text(
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
				component = new Text(
					{
						label: 'label',
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should have a placeholder',
			() => {
				component = new Text(
					{
						placeholder: 'Placeholder',
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should not be required',
			() => {
				component = new Text(
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
				component = new Text(
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
				component = new Text(
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
				component = new Text(
					{
						spritemap,
						value: 'value'
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should have a key',
			() => {
				component = new Text(
					{
						key: 'key',
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should emit a field edit event on field value change',
			() => {
				const handleFieldEdited = jest.fn();

				const events = {fieldEdited: handleFieldEdited};

				component = new Text(
					{
						events,
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
			'should emit a field edit with correct parameters',
			done => {
				const handleFieldEdited = data => {
					expect(data).toEqual(
						expect.objectContaining(
							{
								key: 'input',
								originalEvent: expect.any(Object),
								value: expect.any(String)
							}
						)
					);
					done();
				};

				const events = {fieldEdited: handleFieldEdited};

				component = new Text(
					{
						events,
						key: 'input',
						spritemap
					}
				);

				MetalTestUtil.triggerEvent(
					component.element.querySelector('input'),
					'input',
					{}
				);
			}
		);

		it(
			'should propagate the field edit event',
			() => {
				component = new Text(
					{
						key: 'input',
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