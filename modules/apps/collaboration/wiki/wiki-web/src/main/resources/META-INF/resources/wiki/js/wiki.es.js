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

	checkImagesBeforeSave_() {
		if (this.hasTempImages_()) {
			if (confirm(this.get('strings').confirmDiscardImages)) {
				this.getTempImages_().each(
					function(node) {
						node.ancestor().remove();
					}
				);

				this.savePage_();
			}
		}
		else {
			this.savePage_();
		}
	}

	getTempImages_() {
		return this.all('img[data-random-id]');
	}

	hasTempImages_() {
		return this.getTempImages_().length > 0;
	}

	publishPage_() {
		this.one('#workflowAction').value = this.get('constants').ACTION_PUBLISH;
		this.checkImagesBeforeSave_();
	}

	saveDraft_() {
		this.one('#workflowAction').value = this.get('constants').ACTION_SAVE_DRAFT;
		this.checkImagesBeforeSave_();
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

