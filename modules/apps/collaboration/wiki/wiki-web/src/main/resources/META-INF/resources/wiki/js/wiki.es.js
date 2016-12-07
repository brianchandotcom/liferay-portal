import core from 'metal/src/core';
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';

/**
 * WikiPortlet
 */
class WikiPortlet extends PortletBase {
	/**
	 * @inheritDoc
	 */
	constructor(opt_config) {
		super(opt_config);

		this.bindUI_();
	}

	/**
	 * Bind DOM Events
	 *
	 * @protected
	 */
	bindUI_() {
		let eventHandles = [];

		let formatSelect = this.one('#format');

		this.currentFormatLabel = formatSelect.options[formatSelect.selectedIndex].text.trim();
		this.currentFormatIndex = formatSelect.selectedIndex;

		if (formatSelect) {
			eventHandles.push(
				formatSelect.addEventListener('change', (e) => { this.changeWikiFormat_(e); })
			);
		}

		let publishButton = this.one('#publishButton');

		if (publishButton) {
			eventHandles.push(
				publishButton.addEventListener('click', (e) => { this.publishPage_(e); })
			);
		}

		let saveButton = this.one('#saveButton');

		if (saveButton) {
			eventHandles.push(
				saveButton.addEventListener('click', (e) => { this.saveDraft_(e); })
			);
		}
	}

	/**
	 * Changes the wiki page format, only if the
	 * user accepts that some formatting may lose.
	 *
	 * @param  {Event} event The select event that triggered the change action
	 * @protected
	 */
	changeWikiFormat_(event) {
		let formatSelect = event.currentTarget;

		let newFormat = formatSelect.options[formatSelect.selectedIndex].text.trim();

		let confirmMessage = _.sub(
			this.get('strings').confirmLoseFormatting,
			this.currentFormatLabel,
			newFormat
		);

		if (confirm(confirmMessage)) {
			this.savePage_();
		}
		else {
			formatSelect.selectedIndex = this.currentFormatIndex;
		}
	}

	/**
	 * Checks if there are images that have not
	 * been uploaded yet, and removes them if user accepts
	 * before saving the page
	 *
	 * @protected
	 */
	checkImagesBeforeSave_() {
		if (this.hasTempImages_()) {
			if (confirm(this.get('strings').confirmDiscardImages)) {
				this.getTempImages_().forEach(
					node => { node.parentElement.remove(); }
				);

				this.savePage_();
			}
		}
		else {
			this.savePage_();
		}
	}

	/**
	 * Get all the images that have not been completely uploaded.
	 *
	 * @return {NodeList object} List of <img> elements that have
	 * the data-random-id attribute
	 * @protected
	 */
	getTempImages_() {
		return this.all('img[data-random-id]');
	}

	/**
	 * Checks if there are images that have not
	 * been uploaded yet
	 *
	 * @return {Boolean} true if there are any image that
	 * have not been uploaded, false otherwise.
	 * @protected
	 */
	hasTempImages_() {
		return this.getTempImages_().length > 0;
	}

	/**
	 * Publish the wiki page
	 *
	 * @protected
	 */
	publishPage_() {
		this.one('#workflowAction').value = this.get('constants').ACTION_PUBLISH;
		this.checkImagesBeforeSave_();
	}

	/**
	 * Saves the wiki page as a draft
	 *
	 * @protected
	 */
	saveDraft_() {
		this.one('#workflowAction').value = this.get('constants').ACTION_SAVE_DRAFT;
		this.checkImagesBeforeSave_();
	}

	/**
	 * Submits the wiki page
	 *
	 * @protected
	 */
	savePage_() {
		this.one('#' + this.get('constants').CMD).value = this.get('currentAction');

		let titleEditor = window[this.ns('titleEditor')];

		if (titleEditor) {
			this.one('#title').value = titleEditor.getText();
		}

		let contentEditor = window[this.ns('contentEditor')];

		if (contentEditor) {
			this.one('#content').value = contentEditor.getHTML();
		}

		submitForm(document[this.ns('fm')]);
	}
}

/**
 * WikiPortlet State definition.
 * @type {!Object}
 * @static
 */
WikiPortlet.STATE = {
	/**
	 * WikiPortlet Constants
	 * @type {!Object}
	 */
	constants: {
		validator: core.isObject
	},

	/**
	 * The current action (CMD.ADD, CMD.UPDATE, ...) for the
	 * wiki page.
	 * @type {String}
	 */
	currentAction: {
		validator: core.isString
	},

	/**
	 * String messages
	 * @type {Object}
	 */
	strings: {
		validator: core.isObject,
		value: {
			confirmDiscardImages: Liferay.Language.get('uploads-are-in-progress-confirmation'),
			confirmLoseFormatting: Liferay.Language.get('you-may-lose-formatting-when-switching-from-x-to-x')
		}
	}
};

export default WikiPortlet;