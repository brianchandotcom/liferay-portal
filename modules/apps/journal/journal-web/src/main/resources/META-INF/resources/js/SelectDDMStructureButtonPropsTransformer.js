/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openConfirmModal, openSelectionModal} from 'frontend-js-web';

export default function propsTransformer({
	additionalProps: {description, selectDDMStructurePropsTransformerURL},
	portletNamespace,
	...props
}) {
	return {
		...props,
		onClick() {
			openSelectionModal({
				onSelect: (selectedItem) => {
					const itemValue = JSON.parse(selectedItem.value);

					const ddmStructureId = document.getElementById(
						`${portletNamespace}ddmStructureId`
					);

					if (ddmStructureId.value !== itemValue.ddmstructureid) {
						openConfirmModal({
							message: Liferay.Language.get(
								'selecting-a-new-structure-changes-the-available-templates-and-available-feed-item-content'
							),
							onConfirm: (isConfirmed) => {
								if (isConfirmed) {
									ddmStructureId.value =
										itemValue.ddmstructureid;

									const ddmTemplateKey = document.getElementById(
										`${portletNamespace}ddmTemplateKey`
									);

									ddmTemplateKey.value = '';

									const ddmRendererTemplateKey = document.getElementById(
										`${portletNamespace}ddmRendererTemplateKey`
									);

									ddmRendererTemplateKey.value = '';

									const contentField = document.getElementById(
										`${portletNamespace}contentField`
									);

									contentField.value = description;

									const form = document.getElementById(
										`${portletNamespace}fm`
									);

									submitForm(form, null, false, false);
								}
							},
						});
					}
				},
				selectEventName: `${portletNamespace}selectDDMStructure`,
				title: Liferay.Language.get('structures'),
				url: selectDDMStructurePropsTransformerURL,
			});
		},
	};
}
