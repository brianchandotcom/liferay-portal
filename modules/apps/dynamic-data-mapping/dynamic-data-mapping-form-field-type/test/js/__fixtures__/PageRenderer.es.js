import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './PageRenderer.soy.js';
import {Config} from 'metal-state';

class PageRenderer extends Component {}

PageRenderer.STATE = {
	items: Config.arrayOf(
		Config.shapeOf({
			type: Config.string()
		})
	)
};

Soy.register(PageRenderer, templates);

export default PageRenderer;
