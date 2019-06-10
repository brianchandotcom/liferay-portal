import '../../containers/Pagination/Pagination.es';
import '../../containers/PaginationControls/PaginationControls.es';
import '../../containers/Tabs/Tabs.es';
import '../../containers/Wizard/Wizard.es';
import '../PageRenderer/PageRenderer.es';
import 'clay-button';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './FormRenderer.soy.js';
import {Config} from 'metal-state';

/**
 * FormRenderer.
 * @extends Component
 */

class FormRenderer extends Component {
	_defaultLanguageIdValueFn() {
		return themeDisplay.getLanguageId();
	}

	_editingLanguageIdValueFn() {
		const {defaultLanguageId} = this;

		return defaultLanguageId;
	}

	_handleFieldBlurred(event) {
		this.emit('fieldBlurred', event);
	}

	_handleFieldClicked(index) {
		this.emit('fieldClicked', index);
	}

	_handleFieldEdited(event) {
		this.emit('fieldEdited', event);
	}

	_handleFieldFocused(event) {
		this.emit('fieldFocused', event);
	}
}

FormRenderer.STATE = {
	/**
	 * @default
	 * @instance
	 * @memberof FormRenderer
	 * @type {?number}
	 */

	activePage: Config.number().value(0),

	/**
	 * @default undefined
	 * @memberof FormRenderer
	 * @type {string}
	 * @required
	 */

	defaultLanguageId: Config.string().valueFn('_defaultLanguageIdValueFn'),

	/**
	 * @default false
	 * @instance
	 * @memberof FormRenderer
	 * @type {?bool}
	 */

	editable: Config.bool().value(false),

	/**
	 * @default undefined
	 * @memberof FormRenderer
	 * @type {string}
	 * @required
	 */

	editingLanguageId: Config.string().valueFn('_editingLanguageIdValueFn'),

	/**
	 * @default []
	 * @instance
	 * @memberof FormRenderer
	 * @type {?array<object>}
	 */

	pages: Config.array().value([]),

	/**
	 * @instance
	 * @memberof FormRenderer
	 * @type {string}
	 */

	portletNamespace: Config.string().required(),

	/**
	 * @default []
	 * @instance
	 * @memberof FormRenderer
	 * @type {?array<object>}
	 */

	rules: Config.array().value([]),

	/**
	 * @default undefined
	 * @instance
	 * @memberof FormRenderer
	 * @type {!string}
	 */

	spritemap: Config.string().required()
};

Soy.register(FormRenderer, templates);

export default FormRenderer;
