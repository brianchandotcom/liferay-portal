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

import {PortletBase, fetch} from 'frontend-js-web';
import core from 'metal';
import {EventHandler} from 'metal-events';

const CONFIRM_DISCARD_IMAGES = Liferay.Language.get(
	'uploads-are-in-progress-confirmation'
);

const CONFIRM_LOSE_FORMATTING = Liferay.Language.get(
		'you-may-lose-formatting-when-switching-from-x-to-x'
	);
/**
 * WikiPortlet
 *
 */

 class WikiPortlet {
	constructor({
		constants,
		currentAction,
		namespace,
		renderUrl,
		rootNodeId,
		strings = {
			confirmDiscardImages: CONFIRM_DISCARD_IMAGES,
			confirmLoseFormatting: CONFIRM_LOSE_FORMATTING,
		},
	}) {
		this._constants = constants;
		this._currentAction = currentAction;
		this._namespace = namespace;
		this._renderUrl = renderUrl;
		this._strings = strings;

		this.rootNode = document.getElementById(rootNodeId);
		this.workflowActionInputNode = document.getElementById(
			`${namespace}workflowAction`
		);

		this._events = [];
		this._attachEvents();
	}

	dispose() {
		this._events.forEach(({event, listener, target}) =>
			target.removeEventListener(event, listener)
		);

		this._events = [];
	}

	_addEventListener(target, event, fn) {
		target.addEventListener(event, fn);
		this._events.push({event, fn, target});
	}

	_attachEvents() {
		const namespace = this._namespace;

		const formatSelect = document.getElementById(
			`${namespace}format`
		);

		if (formatSelect) {
			this._currentFormatLabel = formatSelect.options[
				formatSelect.selectedIndex
			].text.trim();
			this._currentFormatIndex = formatSelect.selectedIndex;

			this._addEventListener(formatSelect, 'change', (e) => {
				this._changeWikiFormat(e);
			});
		}

		const publishButton = document.getElementById(
			`${namespace}publishButton`
		);

		if (publishButton) {
			this._addEventListener(publishButton, 'click', (e) => {
				this.workflowActionInputNode.value = this._constants.ACTION_PUBLISH;
				this._save();
			});
		}

		const saveButton = document.getElementById(
			`${namespace}saveButton`
		);

		if (saveButton) {
			this._addEventListener(saveButton, 'click', (e) => {
				this.workflowActionInputNode.value = this._constants.ACTION_SAVE_DRAFT;
				this._save();
			});
		}
	}

	/**
	 * Changes the wiki page format. Previously user is informed that she
	 * may lose some formatting with a confirm dialog.
	 *
	 * @protected
	 * @param {Event} event The select event that triggered the change action
	 */
	_changeWikiFormat(event) {
		const formatSelect = event.currentTarget;

		const newFormat = formatSelect.options[
			formatSelect.selectedIndex
		].text.trim();

		const confirmMessage = Liferay.Util.sub(
			this._strings.confirmLoseFormatting,
			this._currentFormatLabel,
			newFormat
		);

		if (confirm(confirmMessage)) {
			const form = this.rootNode.querySelector(
				`[name="${this._namespace}fm"]`
			);
			form.setAttribute('action', this._renderUrl);
			this._save();
		}
		else {
			formatSelect.selectedIndex = this.currentFormatIndex;
		}
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
	_removeTempImages() {
		const tempImages = this.rootNode.querySelector('img[data-random-id]');
		let discardTempImages = true;

		if (tempImages && tempImages.length > 0) {
			if (confirm(this._strings.confirmDiscardImages)) {
				tempImages.forEach((node) => {
					node.parentElement.remove();
				});
			}
			else {
				discardTempImages = false;
			}
		}

		return discardTempImages;
	}

	/**
	 * Submits the wiki page.
	 *
	 * @protected
	 */
	_save() {
		const namespace = this._namespace;

		if (this._removeTempImages()) {
			document.getElementById(namespace + this._constants.CMD).value = this._currentAction;

			const contentEditor = window[`${namespace}contentEditor`];

			if (contentEditor) {
				document.getElementById(`${namespace}content`).value = contentEditor.getHTML();
			}

			submitForm(document[`${namespace}fm`]);
		}
	}
 }

 export default WikiPortlet;

class WikiPortlet2 extends PortletBase {

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
		const formatSelect = this.one('#format');

		if (formatSelect) {
			this.currentFormatLabel = formatSelect.options[
				formatSelect.selectedIndex
			].text.trim();
			this.currentFormatIndex = formatSelect.selectedIndex;

			this.eventHandler_.add(
				formatSelect.addEventListener('change', (e) => {
					this.changeWikiFormat_(e);
				})
			);
		}

		const publishButton = this.one('#publishButton');

		if (publishButton) {
			this.eventHandler_.add(
				publishButton.addEventListener('click', (e) => {
					this.publishPage_(e);
				})
			);
		}

		const saveButton = this.one('#saveButton');

		if (saveButton) {
			this.eventHandler_.add(
				saveButton.addEventListener('click', (e) => {
					this.saveDraft_(e);
				})
			);
		}

		const searchContainerId = this.ns('pageAttachments');

		Liferay.componentReady(searchContainerId).then((searchContainer) => {
			this.eventHandler_.add(
				searchContainer
					.get('contentBox')
					.delegate(
						'click',
						this.removeAttachment_.bind(this),
						'.delete-attachment'
					)
			);

			this.searchContainer_ = searchContainer;
		});
	}

	/**
	 * Changes the wiki page format. Previously user is informed that she
	 * may lose some formatting with a confirm dialog.
	 *
	 * @protected
	 * @param {Event} event The select event that triggered the change action
	 */
	changeWikiFormat_(event) {
		const formatSelect = event.currentTarget;

		const newFormat = formatSelect.options[
			formatSelect.selectedIndex
		].text.trim();

		const confirmMessage = Liferay.Util.sub(
			this.strings.confirmLoseFormatting,
			this.currentFormatLabel,
			newFormat
		);

		if (confirm(confirmMessage)) {
			this.one('form').setAttribute('action', this.renderUrl);
			this.save_();
		}
		else {
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
	 * Sends a request to remove the selected attachment.
	 *
	 * @protected
	 * @param {Event} event The click event that triggered the remove action
	 */
	removeAttachment_(event) {
		const link = event.currentTarget;

		const deleteURL = link.getAttribute('data-url');

		fetch(deleteURL).then(() => {
			const searchContainer = this.searchContainer_;

			searchContainer.deleteRow(
				link.ancestor('tr'),
				link.getAttribute('data-rowid')
			);
			searchContainer.updateDataStore();
		});
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
		const tempImages = this.all('img[data-random-id]');
		let discardTempImages = true;

		if (tempImages.length > 0) {
			if (confirm(this.strings.confirmDiscardImages)) {
				tempImages.forEach((node) => {
					node.parentElement.remove();
				});
			}
			else {
				discardTempImages = false;
			}
		}

		return discardTempImages;
	}

	/**
	 * Submits the wiki page.
	 *
	 * @protected
	 */
	save_() {
		if (this.removeTempImages_()) {
			this.one('#' + this.constants.CMD).value = this.currentAction;

			const contentEditor = window[this.ns('contentEditor')];

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
		validator: core.isObject,
	},

	/**
	 * The current action (CMD.ADD, CMD.UPDATE, ...)
	 * for the wiki page
	 * @instance
	 * @memberof WikiPortlet
	 * @type {String}
	 */
	currentAction: {
		validator: core.isString,
	},

	/**
	 * The render url of the portlet
	 * @instance
	 * @memberof WikiPortlet
	 * @type {String}
	 */
	renderUrl: {
		validator: core.isString,
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
			confirmDiscardImages: Liferay.Language.get(
				'uploads-are-in-progress-confirmation'
			),
			confirmLoseFormatting: Liferay.Language.get(
				'you-may-lose-formatting-when-switching-from-x-to-x'
			),
		},
	},
};

//export default WikiPortlet;
