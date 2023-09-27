/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch, openConfirmModal, sub} from 'frontend-js-web';

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

		const confirmMessage = sub(
			this._strings.confirmLoseFormatting,
			this._currentFormatLabel,
			newFormat
		);

		openConfirmModal({
			message: confirmMessage,
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					const form = this.rootNode.querySelector(
						`[name="${this._namespace}fm"]`
					);
					form.setAttribute('action', this._renderUrl);
					this._save();
				}
				else {
					formatSelect.selectedIndex = this.currentFormatIndex;
				}
			},
		});
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
	 * @return {void} Call the callback if there aren't temporal
	 * images, and the user does not confirm she wants to lose them.
	 * Otherwise, the callback will not be called.
	 */
	_maybeRemoveTempImages(callback) {
		const tempImages = this.rootNode.querySelector('img[data-random-id]');

		if (tempImages && !!tempImages.length) {
			openConfirmModal({
				message: this._strings.confirmDiscardImages,
				onConfirm: (isConfirmed) => {
					if (isConfirmed) {
						tempImages.forEach((node) => {
							node.parentElement.remove();
						});

						callback();
					}
				},
			});
		}
		else {
			callback();
		}
	}

	/**
	 * Submits the wiki page.
	 *
	 * @protected
	 */
	_save() {
		const namespace = this._namespace;

		this._maybeRemoveTempImages(() => {
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
		});
	}
}

export default WikiPortlet;
