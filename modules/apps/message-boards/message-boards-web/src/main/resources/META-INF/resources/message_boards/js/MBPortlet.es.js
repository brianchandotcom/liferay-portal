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
		this.namespace = namespace;
		this.constants = constants;
		this.currentAction = currentAction;
		this.getAttachmentsURL = getAttachmentsURL;
		this.replyToMessageId = replyToMessageId;
		this.rootNode = document.getElementById(rootNode);
		this.strings = strings;
		this.viewTrashAttachmentsURL = viewTrashAttachmentsURL;

		this.workflowActionInputNode = this.rootNode.querySelector(
			`#${this.namespace}workflowAction`
		);

		this.init();
	}

	init() {
		const publishButton = this.rootNode.querySelector(
			'.button-holder button[type="submit"]'
		);

		if (publishButton) {
			publishButton.addEventListener('click', (e) => {
				this.publish_(e);
			});
		}

		const saveButton = this.rootNode.querySelector(
			`#${this.namespace}saveButton`
		);

		if (saveButton) {
			saveButton.addEventListener('click', (e) => {
				this.saveDraft_(e);
			});
		}

		const advancedReplyLink = this.rootNode.querySelector(
			'.advanced-reply'
		);

		if (advancedReplyLink) {
			advancedReplyLink.addEventListener('click', (e) => {
				this.openAdvancedReply_(e);
			});
		}

		const searchContainerId = `${this.namespace}messageAttachments`;

		Liferay.componentReady(searchContainerId).then((searchContainer) => {
			searchContainer
				.get('contentBox')
				.delegate(
					'click',
					this.removeAttachment_.bind(this),
					'.delete-attachment'
				);

			this.searchContainer_ = searchContainer;
		});

		const viewRemovedAttachmentsLink = document.getElementById(
			'view-removed-attachments-link'
		);

		if (viewRemovedAttachmentsLink) {
			viewRemovedAttachmentsLink.addEventListener('click', () => {
				Liferay.Util.openWindow({
					dialog: {
						on: {
							visibleChange: (event) => {
								if (!event.newVal) {
									this.updateRemovedAttachments_();
								}
							},
						},
					},
					id: this.namespace + 'openRemovedPageAttachments',
					title: Liferay.Language.get('removed-attachments'),
					uri: this.viewTrashAttachmentsURL,
				});
			});
		}
	}

	/**
	 * Redirects to the advanced reply page
	 * keeping the current message.
	 *
	 * @protected
	 */

	openAdvancedReply_() {
		const bodyInput = this.rootNode.querySelector(`#${this.namespace}body`);
		bodyInput.value = window[
			`${this.namespace}replyMessageBody${this.replyToMessageId}`
		].getHTML();

		const form = this.rootNode.querySelector(
			`[name="${this.namespace}advancedReplyFm${this.replyToMessageId}"]`
		);

		const advancedReplyInputNode = form.querySelector(
			`[name="${this.namespace}body"]`
		);

		advancedReplyInputNode.value = bodyInput.value;

		submitForm(form);
	}

	/**
	 * Publish the message.
	 *
	 * @protected
	 */

	_publish() {
		this.workflowActionInputNode.value = this.constants.ACTION_PUBLISH;
		this._save();
	}

	/**
	 * Save the message. Before doing that, checks if there are
	 * images that have not been uploaded yet. In that case,
	 * it removes them after asking confirmation to the user.
	 *
	 * @protected
	 */

	save_() {
		const tempImages = this.rootNode.querySelectorAll(
			'img[data-random-id]'
		);

		if (tempImages.length > 0) {
			if (confirm(this.strings.confirmDiscardImages)) {
				tempImages.forEach((node) => {
					node.parentElement.remove();
				});

				this.submitForm_();
			}
		}
		else {
			this.submitForm_();
		}
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

			this.updateRemovedAttachments_();
		});
	}

	/**
	 * Sends a request to retrieve the deleted attachments
	 *
	 * @protected
	 */

	updateRemovedAttachments_() {
		fetch(this.getAttachmentsURL)
			.then((res) => res.json())
			.then((attachments) => {
				if (attachments.active.length > 0) {
					const searchContainer = this.searchContainer_;
					const searchContainerData = searchContainer.getData();

					document
						.getElementById(this.namespace + 'fileAttachments')
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

	updateMultipleMBMessageAttachments_() {
		const selectedFileNameContainer = this.rootNode.querySelector(
			`#${this.namespace}selectedFileNameContainer`
		);

		if (selectedFileNameContainer) {
			const inputName = `${this.namespace}selectUploadedFile`;

			const input = [].slice.call(
				this.rootNode.querySelectorAll(
					`input[name=${inputName}]:checked`
				)
			);

			const data = input
				.map((item, index) => {
					const id = index;
					const namespace = this.namespace;
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

	submitForm_() {
		this.rootNode.querySelector(
			`#${this.namespace}${this.constants.CMD}`
		).value = this.currentAction;

		this.updateMultipleMBMessageAttachments_();

		const bodyInput = this.rootNode.querySelector(`#${this.namespace}body`);

		if (this.replyToMessageId) {
			bodyInput.value = window[
				`${this.namespace}replyMessageBody${this.replyToMessageId}`
			].getHTML();

			submitForm(
				document[
					`${this.namespace}addQuickReplyFm${this.replyToMessageId}`
				]
			);
		}
		else {
			bodyInput.value = window[`${this.namespace}bodyEditor`].getHTML();

			submitForm(document[`${this.namespace}fm`]);
		}
	}

	/**
	 * Saves the message as a draft.
	 *
	 * @protected
	 */

	saveDraft_() {
		this._workflowActionInput.value = this.constants.ACTION_SAVE_DRAFT;
		this.save_();
	}
}

export default MBPortlet;
