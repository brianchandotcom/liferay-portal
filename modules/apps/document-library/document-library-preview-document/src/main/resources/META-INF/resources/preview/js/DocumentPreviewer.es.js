/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {debounce} from 'frontend-js-web';
import imagePromise from 'image-promise';
import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import 'clay-button';

import templates from './DocumentPreviewer.soy';

const KEY_CODE_ENTER = 13;

const KEY_CODE_ESC = 27;

/**
 * Valid list of keycodes
 * Includes backspace, tab, arrows, delete and numbers
 * @type {Array<number>}
 */
const VALID_KEY_CODES = [
	8,
	9,
	37,
	38,
	39,
	40,
	46,
	48,
	49,
	50,
	51,
	52,
	53,
	54,
	55,
	56,
	57
];

/**
 * Milisecons between goToPage calls
 * @type {number}
 */
const WAIT_BETWEEN_GO_TO_PAGE = 250;

/**
 * Component that create an pdf preview
 * @review
 */
class DocumentPreviewer extends Component {
	/**
	 * @inheritDoc
	 */
	created() {
		this._loadedPages = {
			[this.currentPage]: {
				loaded: true,
				pagePromise: Promise.resolve()
			}
		};

		this._loadPages(this.currentPage);

		this._goToPageDebounced = debounce(
			this._goToPage.bind(this),
			WAIT_BETWEEN_GO_TO_PAGE
		);
	}

	/**
	 * @inheritDoc
	 */
	rendered() {
		if (this.showPageInput) {
			setTimeout(() => this.refs.pageInput.focus(), 100);
		}
	}

	/**
	 * @inheritDoc
	 */
	syncCurrentPage(currentPage) {
		this.refs.imageContainer.scrollTop = 0;
		this.previousPageDisabled = currentPage === 1;
		this.nextPageDisabled = currentPage === this.totalPages;

		if (
			!this._loadedPages[currentPage] ||
			!this._loadedPages[currentPage].loaded
		) {
			this.currentPageLoading = true;
			this._goToPageDebounced(currentPage);
		} else {
			this.currentPageLoading = false;
		}
	}

	/**
	 * Load adjacent pages of the initial one
	 * @param {number|string} initialPage - the initial page
	 * @param {number} [numberOfPages=2] - number of load pages (before and after)
	 * @private
	 * @review
	 */
	_loadPages(initialPage, numberOfPages = 2) {
		for (let i = 1; i <= numberOfPages; i++) {
			if (initialPage + i <= this.totalPages) {
				this._loadPage(initialPage + i);
			}
			if (initialPage - i > 1) {
				this._loadPage(initialPage - i);
			}
		}
	}

	/**
	 * Trigger a promise to load the image
	 * @param {number} page
	 * @return {Promise} A promise to be resolved when the image is loaded
	 * @private
	 * @review
	 */
	_loadPage(page) {
		let pagePromise =
			this._loadedPages[page] && this._loadedPages[page].pagePromise;

		if (!pagePromise) {
			pagePromise = imagePromise(`${this.baseImageURL}${page}`).then(
				() => {
					this._loadedPages[page].loaded = true;
				}
			);

			this._loadedPages[page] = {
				loaded: false,
				pagePromise
			};
		}

		return pagePromise;
	}

	/**
	 * Show page when it's completely loaded
	 * and load the closest pages
	 * @param {number} page
	 * @private
	 * @review
	 */
	_goToPage(page) {
		this._loadPage(page).then(() => {
			if (page === this.currentPage) {
				this.currentPageLoading = false;
				this._loadPages(page);
			}
		});
	}

	/**
	 * Event handler executed on pageInput blur.
	 * Saves the current value.
	 * @param {!Event} event
	 * @private
	 * @review
	 */
	_handleBlurPageInput(event) {
		this.currentPage = event.delegateTarget.value;
		this._hidePageInput(false);
	}

	/**
	 * Handles click action in the toolbar.
	 *
	 * @param {!Event} event
	 * @private
	 * @review
	 */
	_handleClickToolbar(event) {
		const action = event.currentTarget.value;

		if (action === 'expandToggle') {
			this.expanded = !this.expanded;
		} else if (action === 'go') {
			this.showPageInput = true;
		} else if (action === 'next') {
			this.currentPage++;
		} else if (action === 'previous') {
			this.currentPage--;
		}
	}

	/**
	 * Prevents from introducing non digits in input field.
	 * And map certain actions to escape enter (save) or (cancel)
	 * @param {KeyboardEvent} event The keyboard event.
	 * @private
	 * @review
	 */
	_handleKeyDownPageInput(event) {
		const code = event.keyCode || event.charCode;

		if (code === KEY_CODE_ENTER) {
			this.currentPage = event.delegateTarget.value;
			this._hidePageInput();
		} else if (code === KEY_CODE_ESC) {
			this._hidePageInput();
		} else if (VALID_KEY_CODES.indexOf(code) === -1) {
			event.preventDefault();
		}
	}

	/**
	 * Hide PageInput and return focus to parent button
	 * Saves the current value.
	 * @param {Boolean} [returnFocus=true] - flag to determine if return the focus
	 * @private
	 * @review
	 */
	_hidePageInput(returnFocus = true) {
		this.showPageInput = false;

		if (returnFocus) {
			setTimeout(() => this.refs.showPageInputBtn.element.focus(), 100);
		}
	}

	/**
	 * Set the current page if is valid page and show loader
	 * @param {number|string} page
	 * @private
	 * @review
	 */
	_setCurrentPage(page) {
		const pageNumber = Number.parseInt(page, 10);

		return pageNumber
			? Math.min(Math.max(1, pageNumber), this.totalPages)
			: this.currentPage;
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
DocumentPreviewer.STATE = {
	/**
	 * Base path to page images.
	 * @type {String}
	 */
	baseImageURL: Config.string().required(),

	/**
	 * Current page
	 * @type {Number}
	 */
	currentPage: Config.oneOfType([Config.number(), Config.string()])
		.required()
		.setter('_setCurrentPage'),

	/**
	 * Flag that indicate if currentPgae is loading.
	 * @type {Boolean}
	 */
	currentPageLoading: Config.bool().internal(),

	/**
	 * Flag that indicate if pdf is expanded or fit to container.
	 * @type {Boolean}
	 */
	expanded: Config.bool().internal(),

	/**
	 * Flag that indicate if 'next page' is disabled.
	 * @type {Boolean}
	 */
	nextPageDisabled: Config.bool().internal(),

	/**
	 * Flag that indicate if 'previous page' is disabled.
	 * @type {Boolean}
	 */
	previousPageDisabled: Config.bool().internal(),

	/**
	 * Flag that indicate if 'pageInput' is visible.
	 * @type {Boolean}
	 */
	showPageInput: Config.bool().internal(),

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

Soy.register(DocumentPreviewer, templates);
export {DocumentPreviewer};
export default DocumentPreviewer;
