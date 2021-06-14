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

import {fetch} from 'frontend-js-web';

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

		const formatSelect = document.getElementById(`${namespace}format`);

		if (formatSelect) {
			this._currentFormatLabel = formatSelect.options[
				formatSelect.selectedIndex
			].text.trim();
			this._currentFormatIndex = formatSelect.selectedIndex;

			this._addEventListener(formatSelect, 'change', (event) => {
				this._changeWikiFormat(event);
			});
		}

		const publishButton = document.getElementById(
			`${namespace}publishButton`
		);

		if (publishButton) {
			this._addEventListener(publishButton, 'click', () => {
				this.workflowActionInputNode.value = this._constants.ACTION_PUBLISH;
				this._save();
			});
		}

		const saveButton = document.getElementById(`${namespace}saveButton`);

		if (saveButton) {
			this._addEventListener(saveButton, 'click', () => {
				this.workflowActionInputNode.value = this._constants.ACTION_SAVE_DRAFT;
				this._save();
			});
		}

		const searchContainerId = `${namespace}pageAttachments`;

		Liferay.componentReady(searchContainerId).then((searchContainer) => {
			searchContainer
				.get('contentBox')
				.delegate(
					'click',
					this._removeAttachment.bind(this),
					'.delete-attachment'
				);
		});

		this.searchContainerId = searchContainerId;
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
	 * Sends a request to remove the selected attachment.
	 *
	 * @protected
	 * @param {Event} event The click event that triggered the remove action
	 */
	_removeAttachment(event) {
		const link = event.currentTarget;

		const deleteURL = link.getAttribute('data-url');

		fetch(deleteURL).then(() => {
			Liferay.componentReady(this.searchContainerId).then(
				(searchContainer) => {
					searchContainer.deleteRow(
						link.ancestor('tr'),
						link.getAttribute('data-rowid')
					);
					searchContainer.updateDataStore();
				}
			);
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
			document.getElementById(
				namespace + this._constants.CMD
			).value = this._currentAction;

			const contentEditor = window[`${namespace}contentEditor`];

			if (contentEditor) {
				document.getElementById(
					`${namespace}content`
				).value = contentEditor.getHTML();
			}

			submitForm(document[`${namespace}fm`]);
		}
	}
}

export default WikiPortlet;
