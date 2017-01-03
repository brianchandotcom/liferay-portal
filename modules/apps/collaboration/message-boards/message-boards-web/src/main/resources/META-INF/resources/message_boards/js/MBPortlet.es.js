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
		let publishButton = this.one('#publishButton');

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
	}

	/**
	 * @inheritDoc
	 */
	detached() {
		super.detached();
		this.eventHandler_.removeAllListeners();
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
	 * Submits the message.
	 *
	 * @protected
	 */
	save_() {
		this.one('#' + this.constants.CMD).value = this.currentAction;
		this.one('#body').value = window[this.namespace + 'getHTML']();

		submitForm(document[this.ns('fm')]);
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
	 * Portlet's constants
	 * @@instance
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
	}
};

export default MBPortlet;