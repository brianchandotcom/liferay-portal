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

const RECENTLY_REMOVED_ATTACHMENTS = {
	multiple: Liferay.Language.get('x-recently-removed-attachments'),
	single: Liferay.Language.get('x-recently-removed-attachment'),
};

const CONFIRM_DISCARD_IMAGES = Liferay.Language.get(
	'uploads-are-in-progress-confirmation'
);

/**
 * MBPortlet handles the actions of replying or editing a
 * message board.
 */

class MBPortlet {
	constructor({
		constants,
		currentAction,
		getAttachmentsURL,
		namespace,
		replyToMessageId,
		rootNodeId,
		strings = {
			confirmDiscardImages: CONFIRM_DISCARD_IMAGES,
		},
		viewTrashAttachmentsURL,
	}) {
		this._namespace = namespace;
		this._constants = constants;
		this._currentAction = currentAction;
		this._getAttachmentsURL = getAttachmentsURL;
		this._replyToMessageId = replyToMessageId;
		this._strings = strings;
		this._viewTrashAttachmentsURL = viewTrashAttachmentsURL;

		this.rootNode = document.getElementById(rootNodeId);

		this.workflowActionInputNode = document.getElementById(
			`${this._namespace}workflowAction`
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
		const publishButton = this.rootNode.querySelector(
			'.sheet-footer button[type="submit"]'
		);

		if (publishButton) {
			this._addEventListener(publishButton, 'click', () => {
				this.workflowActionInputNode.value = this._constants.ACTION_PUBLISH;
				this._saveFn();
			});
		}

		const saveDrafButton = document.getElementById(
			`${this._namespace}saveButton`
		);

		if (saveDrafButton) {
			this._addEventListener(saveDrafButton, 'click', () => {
				this.workflowActionInputNode.value = this._constants.ACTION_SAVE_DRAFT;
				this._saveFn();
			});
		}

		const advancedReplyLink = this.rootNode.querySelector(
			'.advanced-reply'
		);

		if (advancedReplyLink) {
			this._addEventListener(advancedReplyLink, 'click', () => {
				this._openAdvancedReply();
			});
		}

		const searchContainerId = `${this._namespace}messageAttachments`;

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

		const viewRemovedAttachmentsLink = document.getElementById(
			'view-removed-attachments-link'
		);

		if (viewRemovedAttachmentsLink) {
			this._addEventListener(viewRemovedAttachmentsLink, 'click', () => {
				Liferay.Util.openModal({
					id: this._namespace + 'openRemovedPageAttachments',
					onClose: this._updateRemovedAttachments.bind(this),
					title: Liferay.Language.get('removed-attachments'),
					url: this._viewTrashAttachmentsURL,
				});
			});
		}
	}

	/**
	 * Redirects to the advanced reply page
	 * keeping the current message.
	 *
	 */
	_openAdvancedReply() {
		const namespace = this._namespace;
		const replyToMessageId = this._replyToMessageId;

		const bodyInput = document.getElementById(`${namespace}body`);
		bodyInput.value = window[
			`${namespace}replyMessageBody${replyToMessageId}`
		].getHTML();

		const form = this.rootNode.querySelector(
			`[name="${namespace}advancedReplyFm${replyToMessageId}"]`
		);

		const advancedReplyInputNode = form.querySelector(
			`[name="${namespace}body"]`
		);

		advancedReplyInputNode.value = bodyInput.value;

		submitForm(form);
	}

	/**
	 * Sends a request to remove the selected attachment.
	 *
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

			this._updateRemovedAttachments();
		});
	}

	/**
	 * Save the message. Before doing that, checks if there are
	 * images that have not been uploaded yet. In that case,
	 * it removes them after asking confirmation to the user.
	 *
	 */
	_saveFn() {
		const tempImages = this.rootNode.querySelectorAll(
			'img[data-random-id]'
		);

		if (tempImages.length > 0) {
			if (confirm(this._strings.confirmDiscardImages)) {
				tempImages.forEach((node) => {
					node.parentElement.remove();
				});

				this._submitMBForm();
			}
		}
		else {
			this._submitMBForm();
		}
	}

	/**
	 * Submits the message.
	 *
	 */
	_submitMBForm() {
		const namespace = this._namespace;
		const replyToMessageId = this._replyToMessageId;

		document.getElementById(
			`${namespace}${this._constants.CMD}`
		).value = this._currentAction;

		this._updateMultipleMBMessageAttachments();

		const bodyInput = document.getElementById(`${namespace}body`);

		if (replyToMessageId) {
			bodyInput.value = window[
				`${namespace}replyMessageBody${replyToMessageId}`
			].getHTML();

			submitForm(
				document[`${namespace}addQuickReplyFm${replyToMessageId}`]
			);
		}
		else {
			bodyInput.value = window[`${namespace}bodyEditor`].getHTML();

			submitForm(document[`${namespace}fm`]);
		}
	}

	/**
	 * Updates the attachments to include the checked attachments.
	 *
	 */

	_updateMultipleMBMessageAttachments() {
		const namespace = this._namespace;

		const selectedFileNameContainer = document.getElementById(
			`${namespace}selectedFileNameContainer`
		);

		if (selectedFileNameContainer) {
			const inputName = `${namespace}selectUploadedFile`;

			const input = [].slice.call(
				this.rootNode.querySelectorAll(
					`input[name=${inputName}]:checked`
				)
			);

			const data = input
				.map((item, index) => {
					const id = index;
					const value = item.value;

					return `<input id="${namespace}selectedFileName${id}" name="${namespace}selectedFileName" type="hidden" value="${Liferay.Util.escapeHTML(
						value
					)}" />`;
				})
				.join('');

			selectedFileNameContainer.innerHTML = data;
		}
	}

	/**
	 * Sends a request to retrieve the deleted attachments
	 *
	 */
	_updateRemovedAttachments() {
		fetch(this._getAttachmentsURL)
			.then((res) => res.json())
			.then((attachments) => {
				if (attachments.active.length > 0) {
					Liferay.componentReady(this.searchContainerId).then(
						(searchContainer) => {
							const searchContainerData = searchContainer.getData();

							document
								.getElementById(
									this._namespace + 'fileAttachments'
								)
								.classList.remove('hide');

							attachments.active.forEach((attachment) => {
								if (
									searchContainerData.indexOf(
										attachment.id
									) == -1
								) {
									searchContainer.addRow(
										[
											attachment.title,
											attachment.size,
											`<a class="delete-attachment" data-rowId="${
												attachment.id
											}" data-url="${
												attachment.deleteURL
											}" href="javascript:;">${Liferay.Language.get(
												'move-to-recycle-bin'
											)}</a>`,
										],
										attachment.id.toString()
									);

									searchContainer.updateDataStore();
								}
							});
						}
					);
				}

				const deletedAttachmentsElement = document.getElementById(
					'view-removed-attachments-link'
				);

				if (attachments.deleted.length > 0) {
					deletedAttachmentsElement.style.display = 'initial';
					deletedAttachmentsElement.innerHTML =
						Liferay.Util.sub(
							attachments.deleted.length > 1
								? RECENTLY_REMOVED_ATTACHMENTS.multiple
								: RECENTLY_REMOVED_ATTACHMENTS.single,
							attachments.deleted.length
						) + ' &raquo';
				}
				else {
					deletedAttachmentsElement.style.display = 'none';
				}
			});
	}
}

export default MBPortlet;
