import '../Page/PageRenderer.es';
import '../SuccessPage/SuccessPage.es';
import 'clay-button';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './FormRenderer.soy.js';
import {Config} from 'metal-state';
import {pageStructure} from '../../util/config.es';

/**
 * FormRenderer.
 * @extends Component
 */

class FormRenderer extends Component {
	_handleFieldBlurred(event) {
		this.emit('fieldBlurred', event);
	}

	_handleFieldClicked(index) {
		this.emit('fieldClicked', index);
	}

	_handleFieldEdited(event) {
		this.emit('fieldEdited', event);
	}

	_handlePaginationItemClicked({delegateTarget: {dataset}}) {
		const {dispatch} = this.context;
		const {pageIndex} = dataset;

		dispatch('activePageUpdated', Number(pageIndex));
	}

	_handlePaginationLeftClicked() {
		const {activePage} = this;
		let index = activePage - 1;

		if (activePage == -1) {
			index = this.pages.length - 1;
		}

		this.emit('activePageUpdated', index);
	}

	_handlePaginationRightClicked() {
		const {activePage} = this;
		let index = activePage + 1;

		if (index == this.pages.length) {
			index = -1;
		}

		this.emit('activePageUpdated', index);
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
	 * @default false
	 * @instance
	 * @memberof FormRenderer
	 * @type {?bool}
	 */

	editable: Config.bool().value(false),

	/**
	 * @default []
	 * @instance
	 * @memberof FormRenderer
	 * @type {?array<object>}
	 */

	pages: Config.arrayOf(pageStructure).value([]),

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
