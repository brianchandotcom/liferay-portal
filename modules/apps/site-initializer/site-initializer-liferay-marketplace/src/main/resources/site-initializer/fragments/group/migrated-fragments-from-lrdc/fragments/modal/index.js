/* eslint-disable no-undef */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

window.addEventListener('load', () => {
	if (configuration.modalOverlayColor) {
		document.body.classList.add(
			'modal-overlay-' + configuration.modalOverlayColor
		);
	}

	if (document.querySelector('body.has-edit-mode-menu')) {
		return;
	}

	if (configuration.modalId) {
		if (configuration.contentInitEvent) {
			document.addEventListener(
				configuration.contentInitEvent,
				loadModalContent
			);
		}
		else {
			loadModalContent();
		}
	}
});

function loadModalContent() {
	const modalTriggers = document.querySelectorAll(
		'[href="#' + configuration.modalId + '"]'
	);
	const modal = document.getElementById(configuration.modalId);
	const modalContent = modal.firstElementChild;

	for (let i = 0; i < modalTriggers.length; i++) {
		modalTriggers[i].addEventListener('click', () => {
			Liferay.Util.openModal({
				bodyHTML: '<div></div>',
				containerProps: {
					className: '',
				},
				id: 'f-modal',
				onOpen() {
					const modalBody = document.querySelector(
						'#f-modal .liferay-modal-body'
					);
					modalBody.appendChild(modalContent);
				},
				size: configuration.modalSize,
			});
		});
	}
}
