import core from 'metal';
import { EventHandler } from 'metal-events';
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';

/**
 * MBPortlet
 *
 * @abstract
 * @extends {PortletBase}
 */
class MBPortlet extends PortletBase {
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
		let publishButton = this.one('.button-holder button[type="submit"]');

		if (publishButton) {
			this.eventHandler_.add(publishButton.addEventListener('click', (e) => {
				this.publish_(e);
			}));
		}

		let saveButton = this.one('#saveButton');

		if (saveButton) {
			this.eventHandler_.add(saveButton.addEventListener('click', (e) => {
				this.saveDraft_(e);
			}));
		}

		let advancedReplyLink = this.one('.advanced-reply');
		if (advancedReplyLink) {
			this.eventHandler_.add(advancedReplyLink.addEventListener('click', (e) => {
				this.openAdvancedReply_(e);
			}));
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
	 * Redirects to the advanced reply page
	 * keeping the current message.
	 *
	 * @protected
	 */
	openAdvancedReply_() {
		let inputNode = this.one('#body');
		inputNode.value = window[this.ns('replyMessageBody' + this.replyToMessageId)].getHTML();

		let form = document.createElement('form');

		form.appendChild(inputNode.cloneNode());
		form.setAttribute('action', this.advancedReplyUrl);
		form.setAttribute('method', 'post');

		submitForm(form);
	}

	/**
	 * Publish the message.
	 *
	 * @protected
	 */
	publish_() {
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
	 * Submits the message.
	 *
	 * @protected
	 */
	save_() {
		if (this.removeTempImages_()) {
			this.one('#' + this.constants.CMD).value = this.currentAction;

			if (this.replyToMessageId) {
				this.one('#body').value = window[this.ns('replyMessageBody' + this.replyToMessageId)].getHTML();

				submitForm(document[this.ns('addQuickReplyFm' + this.replyToMessageId)]);
			}
			else {
				this.one('#body').value = window[this.ns('bodyEditor')].getHTML();

				submitForm(document[this.ns('fm')]);
			}
		}
	}

	/**
	 * Saves the message as a draft.
	 *
	 * @protected
	 */
	saveDraft_() {
		this.one('#workflowAction').value = this.constants.ACTION_SAVE_DRAFT;
		this.save_();
	}
}

/**
 * MBPortlet State definition.
 * @ignore
 * @static
 * @type {!Object}
 */
MBPortlet.STATE = {
	/**
	 * Url to the advanced reply page
	 * @instance
	 * @memberof MBPortlet
	 * @type {String}
	 */
	advancedReplyUrl: {
		validator: core.isString
	},
	/**
	 * Portlet's constants
	 * @instance
	 * @memberof MBPortlet
	 * @type {!Object}
	 */
	constants: {
		validator: core.isObject
	},

	/**
	 * The current action (CMD.ADD, CMD.UPDATE, ...)
	 * for the message
	 * @instance
	 * @memberof MBPortlet
	 * @type {String}
	 */
	currentAction: {
		validator: core.isString
	},

	/**
	 * The id of the message that
	 * you are replying to
	 * @instance
	 * @memberof MBPortlet
	 * @type {String}
	 */
	replyToMessageId: {
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
			confirmDiscardImages: Liferay.Language.get('uploads-are-in-progress-confirmation')
		}
	}
};

export default MBPortlet;