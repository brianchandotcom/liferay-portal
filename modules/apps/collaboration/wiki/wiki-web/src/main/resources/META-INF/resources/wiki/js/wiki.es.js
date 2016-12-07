import core from 'metal';
import { EventHandler } from 'metal-events';
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';

/**
 * WikiPortlet
 *
 * @abstract
 * @extends {Component}
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

			this.eventHandler_.add(formatSelect.addEventListener('change', (e) => {
				this.changeWikiFormat_(e);
			}));
		}

		let publishButton = this.one('#publishButton');

		if (publishButton) {
			this.eventHandler_.add(publishButton.addEventListener('click', (e) => {
				this.publishPage_(e);
			}));
		}

		let saveButton = this.one('#saveButton');

		if (saveButton) {
			this.eventHandler_.add(saveButton.addEventListener('click', (e) => {
				this.saveDraft_(e);
			}));
		}
	}

	/**
	 * Changes the wiki page format. Previously user is informed that she
	 * may lose some formatting with a confirm dialog.
	 *
	 * @protected
	 * @param {Event} event The select event that triggered the change action
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
			this.one('form').setAttribute('action', this.renderUrl);
			this.save_();
		} else {
			formatSelect.selectedIndex = this.currentFormatIndex;
		}
	}

	/**
	 * @inheritDoc
	 */
	detached() {
		super.detached();
		this.eventHandler_.removeAllListeners();
	}

	/**
	 * Publish the wiki page.
	 *
	 * @protected
	 */
	publishPage_() {
		this.one('#workflowAction').value = this.constants.ACTION_PUBLISH;
		this.save_();
	}

	/**
	 * Checks if there are images that have not been uploaded yet.
	 * In that case, it removes them after asking
	 * confirmation to the user.
	 *
	 * @protected
	 * @return {Boolean} False if there are temporal images and
	 * user does not confirm she wants to lose them. True in other case.
	 */
	removeTempImages_() {
		let tempImages = this.all('img[data-random-id]');

		if (tempImages.length > 0) {
			if (confirm(this.strings.confirmDiscardImages)) {
				tempImages.forEach(
					node => {
						node.parentElement.remove();
					}
				);
			} else {
				return false;
			}
		}

		return true;
	}

	/**
	 * Submits the wiki page.
	 *
	 * @protected
	 */
	save_() {
		if (this.removeTempImages_()) {
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
	 * Saves the wiki page as a draft.
	 *
	 * @protected
	 */
	saveDraft_() {
		this.one('#workflowAction').value = this.constants.ACTION_SAVE_DRAFT;
		this.save_();
	}
}

/**
 * WikiPortlet State definition.
 * @ignore
 * @static
 * @type {!Object}
 */
WikiPortlet.STATE = {
	/**
	 * Portlet's constants
	 * @instance
	 * @memberof WikiPortlet
	 * @type {!Object}
	 */
	constants: {
		validator: core.isObject
	},

	/**
	 * The current action (CMD.ADD, CMD.UPDATE, ...)
	 * for the wiki page
	 * @instance
	 * @memberof WikiPortlet
	 * @type {String}
	 */
	currentAction: {
		validator: core.isString
	},

	/**
	 * The render url of the portlet
	 * @instance
	 * @memberof WikiPortlet
	 * @type {String}
	 */
	renderUrl: {
		validator: core.isString
	},

	/**
	 * Portlet's messages
	 * @instance
	 * @memberof WikiPortlet
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