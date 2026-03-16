/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-components-web';

export default function ModalActionContextHandler({
	containerCssClasses: className = '',
	linkId,
	modalId = null,
	namespace,
	refreshOnClose,
	size,
	title,
	url,
}) {
	const formId = `${namespace}fm`;
	const linkElement = document.querySelector(`#${linkId}`);

	const handleOpenModal = (event) => {
		event.preventDefault();

		const id = modalId ?? namespace;

		openModal({
			buttons: [
				{
					displayType: 'secondary',
					label: Liferay.Language.get('cancel'),
					type: 'cancel',
				},
				{
					formId,
					label: Liferay.Language.get('submit'),
					onClick: () => {
						Liferay.fire('isLoadingModal', {id, loading: true});
					},
					type: 'submit',
				},
			],
			containerProps: {
				className,
			},
			disableAutoClose: true,
			disableButtonsOnLoading: true,
			id,
			onClose: () => {
				if (refreshOnClose) {
					window.top.location.reload();
				}
			},
			onOpen: ({iframeWindow}) => {
				const formElement = iframeWindow.document.querySelector(
					`#${formId}`
				);

				if (formElement) {
					const {
						[`${namespace}requestProcessed`]: {
							value = 'false',
						} = {},
					} = formElement;

					try {
						const requestProcessed = JSON.parse(value);

						if (requestProcessed) {
							Liferay.fire('isLoadingModal', {
								id,
								loading: false,
							});

							Liferay.fire('closeModal', {
								id,
							});
						}
					}
					catch (_e) {
						console.error('Unable to process the request.');
					}
				}
			},
			size,
			title,
			url,
		});
	};

	linkElement.addEventListener('click', handleOpenModal);

	return {
		dispose() {
			linkElement.removeEventListener('click', handleOpenModal);
		},
	};
}
