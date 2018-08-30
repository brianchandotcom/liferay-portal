import Radio from '../Radio.es';

let component;
const spritemap = 'icons.svg';

describe(
	'Field Radio',
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
				component = new Radio(
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
				component = new Radio(
					{
						helpText: 'Type something',
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should render items',
			() => {
				component = new Radio(
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
						]
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should render no items when items is empty',
			() => {
				component = new Radio(
					{
						items: []
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should have an id',
			() => {
				component = new Radio(
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
				component = new Radio(
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
				component = new Radio(
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
				component = new Radio(
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
				component = new Radio(
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
				component = new Radio(
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
				component = new Radio(
					{
						spritemap,
						value: 'value'
					}
				);

				expect(component).toMatchSnapshot();
			}
		);
	}
);