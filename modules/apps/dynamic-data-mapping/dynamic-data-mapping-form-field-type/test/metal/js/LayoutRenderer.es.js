import PageRenderer from './__fixtures__/PageRenderer.es';
import 'source/Checkbox/index.es';
import 'source/Date/index.es';
import 'source/Grid/index.es';
import 'source/Radio/index.es';
import 'source/Select/index.es';
import 'source/Text/index.es';
import 'source/Options/index.es';

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