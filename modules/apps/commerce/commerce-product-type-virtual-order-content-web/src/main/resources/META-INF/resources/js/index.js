/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-web';

export function OpenTermsOfUseModalPropsTransformer({
	additionalProps: {
		commerceVirtualOrderItemFileEntryId,
		commerceVirtualOrderItemId,
		dialogId,
		downloadURL,
		title,
	},
	portletNamespace,
	...props
}) {
	return {
		...props,
		onClick() {
			openModal({
				buttons: [
					{
						autoFocus: true,
						displayType: 'secondary',
						label: Liferay.Language.get('i-disagree'),
						type: 'cancel',
					},
					{
						displayType: 'primary',
						label: Liferay.Language.get('i-agree'),
						onClick: ({processClose}) => {
							processClose();

							const formName = `#${portletNamespace}${commerceVirtualOrderItemId}-${commerceVirtualOrderItemFileEntryId}Fm`;

							const form =
								window.document.querySelector(formName);

							submitForm(form);
						},
					},
				],
				containerProps: {
					className: 'commerce-modal modal-height-xl',
				},
				id: dialogId,
				iframeBodyCssClass: '',
				size: 'lg',
				title,
				url: downloadURL,
			});
		},
	};
}
