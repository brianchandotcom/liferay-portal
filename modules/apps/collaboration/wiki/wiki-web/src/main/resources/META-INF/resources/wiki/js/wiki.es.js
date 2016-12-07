import core from 'metal/src/core';
import { EventHandler } from 'metal-events';
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';

/**
 * WikiPortlet
 */
class WikiPortlet extends PortletBase {
	/**
	 * @inheritDoc
	 */
	created() {
		this.eventHandler_ = new EventHandler();
	}

	/**
	 * @inheritDoc
	 */
	attached() {
		let formatSelect = this.one('#format');

		if (formatSelect) {
			this.currentFormatLabel = formatSelect.options[formatSelect.selectedIndex].text.trim();
			this.currentFormatIndex = formatSelect.selectedIndex;

			this.eventHandler_.add(formatSelect.addEventListener('change', (e) => { this.changeWikiFormat_(e); }));
		}

		let publishButton = this.one('#publishButton');

		if (publishButton) {
			this.eventHandler_.add(publishButton.addEventListener('click', (e) => { this.publishPage_(e); }));
		}

		let saveButton = this.one('#saveButton');

		if (saveButton) {
			this.eventHandler_.add(saveButton.addEventListener('click', (e) => { this.saveDraft_(e); }));
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
			this.strings.confirmLoseFormatting,
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
		let tempImages = this.all('img[data-random-id]');

		if (tempImages.length > 0) {
			if (confirm(this.strings.confirmDiscardImages)) {
				tempImages.forEach(
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
	 *
	 * @inheritDoc
	 */
	detached() {
		super.detached();
		this.eventHandler_.removeAllListeners();
	}

	/**
	 * Publish the wiki page
	 *
	 * @protected
	 */
	publishPage_() {
		this.one('#workflowAction').value = this.constants.ACTION_PUBLISH;
		this.checkImagesBeforeSave_();
	}

	/**
	 * Saves the wiki page as a draft
	 *
	 * @protected
	 */
	saveDraft_() {
		this.one('#workflowAction').value = this.constants.ACTION_SAVE_DRAFT;
		this.checkImagesBeforeSave_();
	}

	/**
	 * Submits the wiki page
	 *
	 * @protected
	 */
	savePage_() {
		this.one('#' + this.constants.CMD).value = this.currentAction;

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