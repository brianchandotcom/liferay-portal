import PageRenderer from './__fixtures__/PageRenderer.es';
import '../Checkbox/index.es';
import '../Date/index.es';
import '../Grid/index.es';
import '../Radio/index.es';
import '../Select/index.es';
import '../Text/index.es';
import '../Options/index.es';

let component;

const spritemap = 'icons.svg';

describe(
	'Layout Render',
	() => {
		it(
			'should render fields',
			() => {
				component = new PageRenderer(
					{
						items: [
							{
								name: 'checkboxField',
								spritemap,
								type: 'checkbox'
							},
							{
								name: 'dateField',
								spritemap,
								type: 'date'
							},
							{
								name: 'gridField',
								spritemap,
								type: 'grid'
							},
							{
								name: 'selectField',
								spritemap,
								type: 'select'
							},
							{
								name: 'textField',
								spritemap,
								type: 'text'
							},
							{
								name: 'optionsField',
								spritemap,
								type: 'options'
							}
						]
					}
				);

				expect(component).toMatchSnapshot();
			}
		);
	}
);