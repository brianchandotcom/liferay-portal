import core from 'metal/src/core';
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';

/**
 * WikiPortlet
 */
class WikiPortlet extends PortletBase {
	constructor(options) {
		super(options);

		this.STR_CLICK = 'click';

		this.bindUI_();
	}

	bindUI_() {
		let eventHandles = [];

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

	publishPage_() {
		this.one('#workflowAction').value = this.get('constants').ACTION_PUBLISH;
		this.savePage_();
	}

	saveDraft_() {
		this.one('#workflowAction').value = this.get('constants').ACTION_SAVE_DRAFT;
		this.savePage_();
	}

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
 * PortletBase State definition.
 * @type {!Object}
 * @static
 */
WikiPortlet.STATE = {
	constants: {
		validator: core.isObject
	},
	currentAction: {
		validator: core.isString
	},
	strings: {
		validator: core.isObject,
		value: {
			confirmDiscardImages: Liferay.Language.get('uploads-are-in-progress-confirmation')
		}
	}
};

export default WikiPortlet;

