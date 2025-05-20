/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-components-web';

const EDIT_ERC_MODAL_ID = 'edit-erc-modal';

const ExternalReferenceCodeButtonPropsTransformer = ({
	additionalProps,
	...props
}) => ({
	...props,
	onClick(event) {
		event.preventDefault();

		const formId = `_${new URL(additionalProps.url).searchParams.get(
			'p_p_id'
		)}_fm`;

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
					type: 'submit',
				},
			],
			id: EDIT_ERC_MODAL_ID,
			onClose: () => {
				const refreshTimeout = setTimeout(() => {
					clearTimeout(refreshTimeout);

					window.top.location.reload();
				}, 200);
			},
			onOpen: ({iframeWindow}) => {
				const formElement = iframeWindow.document.querySelector(
					`#${formId}`
				);

				if (formElement) {
					formElement.addEventListener('submit', () => {
						Liferay.fire('closeModal', {
							id: EDIT_ERC_MODAL_ID,
						});
					});
				}
			},
			size: 'md',
			title: additionalProps.title,
			url: additionalProps.url,
		});
	},
});

export default ExternalReferenceCodeButtonPropsTransformer;
