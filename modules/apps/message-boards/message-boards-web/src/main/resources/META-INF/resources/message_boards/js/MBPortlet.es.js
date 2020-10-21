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
		rootNode,
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

		this.rootNode = document.getElementById(rootNode);
		this.workflowActionInputNode = this.rootNode.querySelector(
			`#${this._namespace}workflowAction`
		);

		this._init();
	}

	/**
	 * Add events if needed.
	 *
	 * @protected
	 */

	_init() {
		const publishButton = this.rootNode.querySelector(
			'.button-holder button[type="submit"]'
		);

		if (publishButton) {
			publishButton.addEventListener('click', (e) => {
				this._publish(e);
			});
		}

		const saveButton = this.rootNode.querySelector(
			`#${this._namespace}saveButton`
		);

		if (saveButton) {
			saveButton.addEventListener('click', (e) => {
				this._saveDraft(e);
			});
		}

		const advancedReplyLink = this.rootNode.querySelector(
			'.advanced-reply'
		);

		if (advancedReplyLink) {
			advancedReplyLink.addEventListener('click', (e) => {
				this._openAdvancedReply(e);
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

			this.searchContainer_ = searchContainer;
		});

		const viewRemovedAttachmentsLink = document.getElementById(
			'view-removed-attachments-link'
		);

		if (viewRemovedAttachmentsLink) {
			viewRemovedAttachmentsLink.addEventListener(
				'click',
				this._openRemovedAttachments.bind(this)
			);
		}
	}

	/**
	 * Redirects to the advanced reply page
	 * keeping the current message.
	 *
	 * @protected
	 */

	_openAdvancedReply() {
		const bodyInput = this.rootNode.querySelector(
			`#${this._namespace}body`
		);
		bodyInput.value = window[
			`${this._namespace}replyMessageBody${this._replyToMessageId}`
		].getHTML();

		const form = this.rootNode.querySelector(
			`[name="${this._namespace}advancedReplyFm${this._replyToMessageId}"]`
		);

		const advancedReplyInputNode = form.querySelector(
			`[name="${this._namespace}body"]`
		);

		advancedReplyInputNode.value = bodyInput.value;

		submitForm(form);
	}

	/**
	 * Redirects to the advanced reply page
	 * keeping the current message.
	 *
	 * @protected
	 */

	_openRemovedAttachments() {
		Liferay.Util.openWindow({
			dialog: {
				on: {
					visibleChange: (event) => {
						if (!event.newVal) {
							this._updateRemovedAttachments();
						}
					},
				},
			},
			id: this._namespace + 'openRemovedPageAttachments',
			title: Liferay.Language.get('removed-attachments'),
			uri: this._viewTrashAttachmentsURL,
		});
	}

	/**
	 * Publish the message.
	 *
	 * @protected
	 */

	_publish() {
		this.workflowActionInputNode.value = this._constants.ACTION_PUBLISH;
		this._save();
	}

	/**
	 * Save the message. Before doing that, checks if there are
	 * images that have not been uploaded yet. In that case,
	 * it removes them after asking confirmation to the user.
	 *
	 * @protected
	 */

	_save() {
		const tempImages = this.rootNode.querySelectorAll(
			'img[data-random-id]'
		);

		if (tempImages.length > 0) {
			if (confirm(this._strings.confirmDiscardImages)) {
				tempImages.forEach((node) => {
					node.parentElement.remove();
				});

				this._submitForm();
			}
		}
		else {
			this._submitForm();
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
			const searchContainer = this.searchContainer_;

			searchContainer.deleteRow(
				link.ancestor('tr'),
				link.getAttribute('data-rowid')
			);
			searchContainer.updateDataStore();

			this._updateRemovedAttachments();
		});
	}

	/**
	 * Sends a request to retrieve the deleted attachments
	 *
	 * @protected
	 */

	_updateRemovedAttachments() {
		fetch(this._getAttachmentsURL)
			.then((res) => res.json())
			.then((attachments) => {
				if (attachments.active.length > 0) {
					const searchContainer = this.searchContainer_;
					const searchContainerData = searchContainer.getData();

					document
						.getElementById(this._namespace + 'fileAttachments')
						.classList.remove('hide');

					attachments.active.forEach((attachment) => {
						if (searchContainerData.indexOf(attachment.id) == -1) {
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

	/**
	 * Updates the attachments to include the checked attachments.
	 *
	 * @protected
	 */

	_updateMultipleMBMessageAttachments() {
		const selectedFileNameContainer = this.rootNode.querySelector(
			`#${this._namespace}selectedFileNameContainer`
		);

		if (selectedFileNameContainer) {
			const inputName = `${this._namespace}selectUploadedFile`;

			const input = [].slice.call(
				this.rootNode.querySelectorAll(
					`input[name=${inputName}]:checked`
				)
			);

			const data = input
				.map((item, index) => {
					const id = index;
					const namespace = this._namespace;
					const value = item.value;

					return `<input id="${namespace}selectedFileName${id}" name="${namespace}selectedFileName" type="hidden" value="${value}" />`;
				})
				.join('');

			selectedFileNameContainer.innerHTML = data;
		}
	}

	/**
	 * Submits the message.
	 *
	 * @protected
	 */

	_submitForm() {
		this.rootNode.querySelector(
			`#${this._namespace}${this._constants.CMD}`
		).value = this._currentAction;

		this._updateMultipleMBMessageAttachments();

		const bodyInput = this.rootNode.querySelector(
			`#${this._namespace}body`
		);

		if (this._replyToMessageId) {
			bodyInput.value = window[
				`${this._namespace}replyMessageBody${this._replyToMessageId}`
			].getHTML();

			submitForm(
				document[
					`${this._namespace}addQuickReplyFm${this._replyToMessageId}`
				]
			);
		}
		else {
			bodyInput.value = window[`${this._namespace}bodyEditor`].getHTML();

			submitForm(document[`${this._namespace}fm`]);
		}
	}

	/**
	 * Saves the message as a draft.
	 *
	 * @protected
	 */

	_saveDraft() {
		this._workflowActionInput.value = this._constants.ACTION_SAVE_DRAFT;
		this._save();
	}
}

export default MBPortlet;
