import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './LayoutRenderer.soy.js';

class LayoutRenderer extends Component {
	static STATE = {
		items: Config.arrayOf(
			Config.shapeOf({
				type: Config.string(),
			})
		),
	};
}

Soy.register(LayoutRenderer, templates);

export default LayoutRenderer;
