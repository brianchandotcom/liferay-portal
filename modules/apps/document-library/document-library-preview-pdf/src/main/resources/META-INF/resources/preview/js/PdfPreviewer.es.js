import {Config} from 'metal-state';
import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './PdfPreviewer.soy';

/**
 * Valid list of keycodes
 * Includes backspace, tab, enter, escape, arrows, delete and numbers
 * @type {Array<number>}
 */
const validKeyCodes = [8, 9, 13, 37, 38, 39, 40, 46, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57];

/**
 * Component that create an pdf preview
 * @review
 */
class PdfPreviewer extends Component {

	/**
	 * @inheritDoc
	 */
	rendered() {
		if (this.showPageInput) {
			this.refs.pageInput.focus();
		}
	}

	/**
	 * @inheritDoc
	 */
	syncCurrentPage(currentPage) {
		this.refs.imageContainer.scrollTop = 0;
		this.previousPageDiabled = currentPage === 1;
		this.nextPageDisabled = currentPage === this.totalPages;
	}

	/**
	 * Set the current page if is valid page
	 * @param {number|string} page
	 * @private
	 * @review
	 */
	_setPage(page) {
		const pageNumber = Number.parseInt(page, 10);

		if (pageNumber) {
			this.currentPage = Math.min(
				Math.max(1, pageNumber),
				this.totalPages
			);
		}
	}

	/**
	 * Event handler executed in pageInput blur
	 * Save the current value
	 * @param {!Event} event
	 * @private
	 * @review
	 */
	_handleBlurPageInput(event) {
		this._setPage(event.delegateTarget.value);
		this.showPageInput = false;
	}

	/**
	 * Prevents from introducing non digits in input field.
	 * And map certain acctions to escape enter (save) or (cancel)
	 * @param {KeyboardEvent} event The keyboard event.
	 * @private
	 * @review
	 */
	_handleKeyDownPageInput(event) {
		const code = event.keyCode || event.charCode;

		if (code === 13) {
			this._setPage(event.delegateTarget.value);
			this.showPageInput = false;
		}
		else if (code === 27) {
			this.showPageInput = false;
		}
 		else if (validKeyCodes.indexOf(code) == -1) {
			event.preventDefault();
		}
	}

	_handleClickToolbar(event) {
		const action = event.currentTarget.value;

		if (action === 'next') {
			this._setPage(this.currentPage + 1);
		}
		else if (action === 'previous') {
			this._setPage(this.currentPage - 1);
		}
		else if (action === 'go') {
			this.showPageInput = true;
		}
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
PdfPreviewer.STATE = {

	/**
	 * Base path to page images.
	 * @type {String}
	 */
	baseImageURL: Config.string().required(),

	/**
	 * Current page
	 * @type {Number}
	 */
	currentPage: Config.number().required(),

	/**
	 * Flag that indicate if 'next page' is disabled.
	 * @type {Boolean}
	 */
	nextPageDisabled: Config.bool(),

	/**
	 * Flag that indicate if 'previous page' is disabled.
	 * @type {Boolean}
	 */
	previousPageDisabled: Config.bool(),

	/**
	 * Flag that indicate if 'pageInput' is visible.
	 * @type {Boolean}
	 */
	showPageInput: Config.bool(),

	/**
	 * Path to icon images.
	 * @type {String}
	 */
	spritemap: Config.string().required(),

	/**
	 * Pdf pages lenght
	 * @type {Number}
	 */
	totalPages: Config.number().required()
};

Soy.register(PdfPreviewer, templates);
export {PdfPreviewer};
export default PdfPreviewer;