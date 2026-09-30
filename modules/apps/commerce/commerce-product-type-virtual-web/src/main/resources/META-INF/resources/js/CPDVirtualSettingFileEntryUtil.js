/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function ({fileEntryItemSelectorURL, portletNamespace}) {
	const fileEntryNameInput = document.getElementById(
		`${portletNamespace}fileEntryNameInput`
	);
	const fileEntryRemove = document.getElementById(
		`${portletNamespace}fileEntryRemove`
	);
	const selectFile = document.getElementById(`${portletNamespace}selectFile`);

	if (fileEntryNameInput && fileEntryRemove && selectFile) {
		selectFile.addEventListener('click', (event) => {
			event.preventDefault();

			Liferay.Util.openSelectionModal({
				onSelect: (selectedItem) => {
					if (!selectedItem) {
						return;
					}

					const value = JSON.parse(selectedItem.value);

					const fileEntryIdInput = document.getElementById(
						`${portletNamespace}fileEntryId`
					);

					if (fileEntryIdInput) {
						fileEntryIdInput.value = value.fileEntryId;
					}

					const url = document.getElementById(
						`${portletNamespace}url`
					);

					if (url) {
						url.setAttribute('disabled', true);
					}

					const message = document.getElementById(
						'lfr-definition-virtual-button-row-message'
					);

					if (message) {
						message.classList.add('hide');
					}

					fileEntryRemove.classList.remove('hide');

					fileEntryNameInput.innerHTML = `<a>${Liferay.Util.escape(
						value.title
					)}</a>`;
				},
				selectEventName: 'uploadCPDefinitionVirtualSetting',
				title: Liferay.Language.get('select-file'),
				url: fileEntryItemSelectorURL,
			});
		});

		fileEntryRemove.addEventListener('click', (event) => {
			event.preventDefault();

			const fileEntryIdInput = document.getElementById(
				`${portletNamespace}fileEntryId`
			);

			if (fileEntryIdInput) {
				fileEntryIdInput.value = 0;
			}

			const url = document.getElementById(`${portletNamespace}url`);

			if (url) {
				url.removeAttribute('disabled');
			}

			const message = document.getElementById(
				'lfr-definition-virtual-button-row-message'
			);

			if (message) {
				message.classList.remove('hide');
			}

			fileEntryNameInput.innerText = Liferay.Language.get('none');

			fileEntryRemove.classList.add('hide');
		});
	}

	const saveAndPropagate = document.getElementById(
		`${portletNamespace}saveAndPropagate`
	);

	if (saveAndPropagate) {
		saveAndPropagate.addEventListener('click', (event) => {
			event.preventDefault();

			Liferay.Util.openModal({
				bodyHTML: `<p>${Liferay.Language.get(
					'you-are-about-to-add-this-file-to-all-the-orders-containing-this-product'
				)}</p>`,
				buttons: [
					{
						displayType: 'secondary',
						label: Liferay.Language.get('cancel'),
						type: 'cancel',
					},
					{
						label: Liferay.Language.get('continue'),
						onClick: ({processClose}) => {
							processClose();

							const propagate = document.getElementById(
								`${portletNamespace}propagate`
							);

							if (propagate) {
								propagate.value = true;
							}

							Liferay.Util.submitForm(
								document.getElementById(`${portletNamespace}fm`)
							);
						},
					},
				],
				center: true,
				status: 'warning',
				title: Liferay.Language.get('save-and-propagate'),
			});
		});
	}
}
