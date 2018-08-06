import LayoutRenderer from './__fixtures__/LayoutRenderer.es';
import Checkbox from '../Checkbox/index.es';
import Date from '../Date/index.es';
import Grid from '../Grid/index.es';
import Radio from '../Radio/index.es';
import Select from '../Select/index.es';
import Text from '../Text/index.es';
import Options from '../Options/index.es';

let component;

describe('Layout Render', () => {
	it('should render fields', () => {
		component = new LayoutRenderer({
			items: [
				{type: 'checkbox'},
				{type: 'date'},
				{type: 'grid'},
				// {type: 'radio'},
				{type: 'select'},
				{type: 'text'},
				{type: 'options'},
			],
		});
		expect(component).toMatchSnapshot();
	});
});
