import FieldBase from '../FieldBase.es';

let component;
const spritemap = 'icons.svg';

describe(
	'FieldBase',
	() => {
		afterEach(
			() => {
				if (component) {
					component.dispose();
				}
			}
		);

		it(
			'should render the default markup',
			() => {
				component = new FieldBase(
					{
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should render the FieldBase with required',
			() => {
				component = new FieldBase(
					{
						required: true,
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should render the FieldBase with id',
			() => {
				component = new FieldBase(
					{
						id: 'Id',
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should render the FieldBase with help text',
			() => {
				component = new FieldBase(
					{
						spritemap,
						tip: 'Type something!'
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should render the FieldBase with label',
			() => {
				component = new FieldBase(
					{
						label: 'Text',
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should not render the label if showLabel is false',
			() => {
				component = new FieldBase(
					{
						label: 'Text',
						showLabel: false,
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should render the FieldBase with contentRenderer',
			() => {
				component = new FieldBase(
					{
						contentRenderer: `
                <div>
                    <h1>Foo bar</h1>
                </div>
            `,
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);
	}
);