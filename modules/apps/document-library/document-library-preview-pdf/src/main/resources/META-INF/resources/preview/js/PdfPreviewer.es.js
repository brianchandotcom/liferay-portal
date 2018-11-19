import {Config} from 'metal-state';
import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './PdfPreviewer.soy';

/**
 * Component that create an pdf preview
 * @review
 */
class PdfPreviewer extends Component {


	/**
	 * @inheritDoc
	 */
	syncCurrentPage(currentPage) {
		this.previousPageDiabled = currentPage === 1;
		this.nextPageDisabled = currentPage === this.totalPages;
	}

	_askPageNumber() {
		const response = parseInt(prompt(), 0);

		return Number.isInteger(response) ?
			this._normalizePage(response) :
			null
		;
	}

	_normalizePage(page) {
		return Math.min(
			Math.max(1, page),
			this.totalPages
		);
	}

	_handleClickGoToPage(event) {
		const action = event.currentTarget.value;

		if (action === 'next') {
			this.currentPage = this.currentPage + 1;
		}
		else if (action === 'previous') {
			this.currentPage = this.currentPage - 1;
		}
		else if (action === 'ask') {
			const page = this._askPageNumber();

			if (page) {
				this.currentPage = page;
			}
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
	 * Pdf pages lenght
	 * @type {Number}
	 */
	totalPages: Config.number().required(),

	/**
	 * Path to icon images.
	 * @type {String}
	 */
	spritemap: Config.string().required()
};

Soy.register(PdfPreviewer, templates);
export {PdfPreviewer};
export default PdfPreviewer;