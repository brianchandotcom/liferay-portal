import {Config} from 'metal-state';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './PageRenderer.soy.js';

class PageRenderer extends Component {
	static STATE = {
		items: Config.arrayOf(
			Config.shapeOf(
				{
					type: Config.string()
				}
			)
		)
	};
}

Soy.register(PageRenderer, templates);

export default PageRenderer;