import Component from 'metal-component';
import Soy from 'metal-soy';
import { validators } from 'metal-state';

import templates from './Icon.soy';

/**
 * Icon
 * This component implements a way to render icons from Clay library.
 * It supports both SVG based icons and typography based icons.
 * https://liferay.github.io/clay/content/icons-lexicon/.
 */
class Icon extends Component {

}

/**
 * State definition.
 * @ignore
 * @type {!Object}
 * @static
 */
Icon.STATE = {
	/**
	 * @inheritdoc
	 */
	elementClasses: {
		validator: validators.string
	},

	/**
	 * The component container ID. A random value will be generated in the
	 * Java Class if it's not defined.
	 * @type {?string}
	 */
	id: {
		validator: validators.string
	},

	/**
	 * The path to the SVG file. This is initialy defined in the tag lib
	 * Java class.
	 * @type {?string}
	 */
	spritemap: {
		validator: validators.string
	},

	/**
	 * The name of the Clay SVG Icon e.g. `plus`.
	 * @type {?string}
	 */
	symbol: {
		validator: validators.string
	}
};

// Register component
Soy.register(Icon, templates);

export default Icon;