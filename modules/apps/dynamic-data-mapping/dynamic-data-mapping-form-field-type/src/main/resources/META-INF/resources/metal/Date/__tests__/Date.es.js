import Date from '../Date.es';

let component;
const spritemap = 'icons.svg';

describe(
	'Field Date',
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
				component = new Date(
					{
						readOnly: false,
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should have a tip',
			() => {
				component = new Date(
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
				component = new Date(
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
				component = new Date(
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
				component = new Date(
					{
						placeholder: '__/__/____',
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should not be required',
			() => {
				component = new Date(
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
				component = new Date(
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
				component = new Date(
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
				component = new Date(
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