import Text from '../Text.es';
import {dom as MetalTestUtil} from 'metal-dom';

let component;
const spritemap = 'icons.svg';

const defaultTextConfig = {
	name: 'textField',
	spritemap
};

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
			'should not be readOnly',
			() => {
				component = new Text(
					{
						...defaultTextConfig,
						readOnly: false
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
						...defaultTextConfig,
						tip: 'Type something'
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
						...defaultTextConfig,
						id: 'ID'
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
						...defaultTextConfig,
						label: 'label'
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
						...defaultTextConfig,
						placeholder: 'Placeholder'
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
						...defaultTextConfig,
						required: false
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
						...defaultTextConfig,
						label: 'text',
						showLabel: true
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should have a spritemap',
			() => {
				component = new Text(defaultTextConfig);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should have a value',
			() => {
				component = new Text(
					{
						...defaultTextConfig,
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
						...defaultTextConfig,
						key: 'key'
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
						...defaultTextConfig,
						events
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
								fieldInstance: component,
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
						...defaultTextConfig,
						events,
						key: 'input'
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
						...defaultTextConfig,
						key: 'input'
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