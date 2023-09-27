/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getOpener, setFormValues} from 'frontend-js-web';

export default function ({itemSelectorURL, portletNamespace, selectEventName}) {
	const openRecordSetModalButton = document.querySelector(
		'.open-record-set-modal'
	);

	if (openRecordSetModalButton) {
		openRecordSetModalButton.addEventListener('click', () => {
			const openerWindow = getOpener();

			openerWindow.Liferay.Util.openSelectionModal({
				iframeBodyCssClass: '',
				onSelect: (selectedItem) => {
					const form = document.getElementById(
						`${portletNamespace}fm`
					);

					if (!form) {
						return;
					}

					setFormValues(form, {
						ddmStructureId: selectedItem.ddmstructureid,
						ddmStructureNameDisplay: Liferay.Util.unescape(
							selectedItem.name
						),
					});
				},
				selectEventName,
				title: Liferay.Language.get('data-definitions'),
				url: itemSelectorURL,
			});
		});
	}
}
