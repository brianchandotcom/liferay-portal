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

export default function MBPortlet({
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
	const rootNode = document.getElementById(rootNodeId);

	const workflowActionInputNode = rootNode.querySelector(
		`#${namespace}workflowAction`
	);

	const publishButton = rootNode.querySelector(
		'.button-holder button[type="submit"]'
	);

	if (publishButton) {
		publishButton.addEventListener('click', () => {
			workflowActionInputNode.value = constants.ACTION_PUBLISH;
			saveFn();
		});
	}

	const saveDrafButton = rootNode.querySelector(`#${namespace}saveButton`);

	if (saveDrafButton) {
		saveDrafButton.addEventListener('click', () => {
			workflowActionInputNode.value = constants.ACTION_SAVE_DRAFT;
			saveFn();
		});
	}

	const advancedReplyLink = rootNode.querySelector('.advanced-reply');

	if (advancedReplyLink) {
		advancedReplyLink.addEventListener('click', () => {
			openAdvancedReply();
		});
	}

	const searchContainerId = `${namespace}messageAttachments`;
	let _searchContainer;

	Liferay.componentReady(searchContainerId).then((searchContainer) => {
		searchContainer
			.get('contentBox')
			.delegate(
				'click',
				removeAttachment.bind(this),
				'.delete-attachment'
			);

		_searchContainer = searchContainer;
	});

	const viewRemovedAttachmentsLink = document.getElementById(
		'view-removed-attachments-link'
	);

	if (viewRemovedAttachmentsLink) {
		viewRemovedAttachmentsLink.addEventListener('click', () => {
			Liferay.Util.openModal({
				id: namespace + 'openRemovedPageAttachments',
				onClose: updateRemovedAttachments,
				title: Liferay.Language.get('removed-attachments'),
				url: viewTrashAttachmentsURL,
			});
		});
	}

	/**
	 * Redirects to the advanced reply page
	 * keeping the current message.
	 *
	 */
	const openAdvancedReply =  () => {
		const bodyInput = rootNode.querySelector(`#${namespace}body`);
		bodyInput.value = window[
			`${namespace}replyMessageBody${replyToMessageId}`
		].getHTML();

		const form = rootNode.querySelector(
			`[name="${namespace}advancedReplyFm${replyToMessageId}"]`
		);

		const advancedReplyInputNode = form.querySelector(
			`[name="${namespace}body"]`
		);

		advancedReplyInputNode.value = bodyInput.value;

		submitForm(form);
	};

	/**
	 * Sends a request to remove the selected attachment.
	 *
	 * @param {Event} event The click event that triggered the remove action
	 */
	const removeAttachment = (event) => {
		const link = event.currentTarget;

		const deleteURL = link.getAttribute('data-url');

		fetch(deleteURL).then(() => {
			_searchContainer.deleteRow(
				link.ancestor('tr'),
				link.getAttribute('data-rowid')
			);
			_searchContainer.updateDataStore();

			updateRemovedAttachments();
		});
	};

	/**
	 * Save the message. Before doing that, checks if there are
	 * images that have not been uploaded yet. In that case,
	 * it removes them after asking confirmation to the user.
	 *
	 */
	const saveFn = () => {
		const tempImages = rootNode.querySelectorAll('img[data-random-id]');

		if (tempImages.length > 0) {
			if (confirm(strings.confirmDiscardImages)) {
				tempImages.forEach((node) => {
					node.parentElement.remove();
				});

				submitMBForm();
			}
		}
		else {
			submitMBForm();
		}
	};

	/**
	 * Submits the message.
	 *
	 */
	const submitMBForm = () => {
		rootNode.querySelector(
			`#${namespace}${constants.CMD}`
		).value = currentAction;

		updateMultipleMBMessageAttachments();

		const bodyInput = rootNode.querySelector(`#${namespace}body`);

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
	};

	/**
	 * Updates the attachments to include the checked attachments.
	 *
	 */

	const updateMultipleMBMessageAttachments = () => {
		const selectedFileNameContainer = rootNode.querySelector(
			`#${namespace}selectedFileNameContainer`
		);

		if (selectedFileNameContainer) {
			const inputName = `${namespace}selectUploadedFile`;

			const input = [].slice.call(
				rootNode.querySelectorAll(`input[name=${inputName}]:checked`)
			);

			const data = input
				.map((item, index) => {
					const id = index;
					const value = item.value;

					return `<input id="${namespace}selectedFileName${id}" name="${namespace}selectedFileName" type="hidden" value="${value}" />`;
				})
				.join('');

			selectedFileNameContainer.innerHTML = data;
		}
	};

	/**
	 * Sends a request to retrieve the deleted attachments
	 *
	 */
	const updateRemovedAttachments = () => {
		fetch(getAttachmentsURL)
			.then((res) => res.json())
			.then((attachments) => {
				if (attachments.active.length > 0) {
					const searchContainerData = _searchContainer.getData();

					document
						.getElementById(namespace + 'fileAttachments')
						.classList.remove('hide');

					attachments.active.forEach((attachment) => {
						if (searchContainerData.indexOf(attachment.id) == -1) {
							_searchContainer.addRow(
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

							_searchContainer.updateDataStore();
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
	};
}
