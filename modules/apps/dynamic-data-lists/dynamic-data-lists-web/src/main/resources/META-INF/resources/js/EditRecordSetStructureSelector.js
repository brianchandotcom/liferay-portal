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

import {getOpener} from 'frontend-js-web';

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

					Liferay.Util.setFormValues(form, {
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
