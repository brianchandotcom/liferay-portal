import KeyValue from '../KeyValue.es';
import {dom as MetalTestUtil} from 'metal-dom';

let component;
const spritemap = 'icons.svg';

describe(
	'KeyValue',
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
				component = new KeyValue(
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
				component = new KeyValue(
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
				component = new KeyValue(
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
				component = new KeyValue(
					{
						label: 'label',
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should have a predefined Value',
			() => {
				component = new KeyValue(
					{
						placeholder: 'Option 1',
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should not be required',
			() => {
				component = new KeyValue(
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
				component = new KeyValue(
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
				component = new KeyValue(
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
				component = new KeyValue(
					{
						spritemap,
						value: 'value'
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should render component with a key',
			() => {
				component = new KeyValue(
					{
						key: 'key',
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should remove spaces and convert first letter after space to upper case on change label value',
			() => {
				component = new KeyValue(
					{
						key: 'keyValue',
						spritemap
					}
				);

				const generatedName = component.getGeneratedKey('tes te_A_a');

				expect(generatedName).toEqual('tesTeAa');
			}
		);

		it(
			'should not allow generating field name after editing field name input',
			() => {
				component = new KeyValue(
					{
						generateKey: true,
						spritemap
					}
				);

				MetalTestUtil.triggerEvent(
					component.element.querySelector('input.key-value-input'),
					'input',
					{value: 'foo'}
				);

				expect(component.generateKey).toEqual(false);
			}
		);

		it(
			'should emit a "fieldEdited" event when changing the value input',
			done => {
				jest.useFakeTimers();

				component = new KeyValue(
					{
						spritemap
					}
				);

				component.on(
					'fieldEdited',
					event => {
						expect(event.value).toEqual('foo');
						done();
					}
				);

				jest.runAllTimers();

				const valueInput = component.element.querySelector('input.form-control');
				valueInput.value = 'foo';
				MetalTestUtil.triggerEvent(valueInput, 'input', {});
			}
		);

		it(
			'should generate name when editing the value input if "generateKey" is true',
			done => {
				jest.useFakeTimers();

				component = new KeyValue(
					{
						generateKey: true,
						spritemap
					}
				);

				component.on(
					'fieldEdited',
					event => {
						expect(component.key).toEqual(component.getGeneratedKey('My Name'));

						done();
					}
				);

				const valueInput = component.element.querySelector('input.form-control');
				valueInput.value = 'My Name';
				MetalTestUtil.triggerEvent(valueInput, 'input', {});

				jest.runAllTimers();
			}
		);
	}
);