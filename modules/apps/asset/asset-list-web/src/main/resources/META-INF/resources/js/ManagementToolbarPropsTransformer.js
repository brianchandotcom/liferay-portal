/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openSimpleInputModal} from 'frontend-js-web';

import openDeleteAssetEntryListModal from './openDeleteAssetEntryListModal';

export default function propsTransformer({portletNamespace, ...otherProps}) {
	return {
		...otherProps,
		onActionButtonClick(event, {item}) {
			if (item?.data?.action === 'deleteSelectedAssetListEntries') {
				openDeleteAssetEntryListModal({
					multiple: true,
					onDelete: () => {
						const form = document.getElementById(
							`${portletNamespace}fm`
						);

						if (form) {
							submitForm(form);
						}
					},
				});
			}
		},
		onCreationMenuItemClick(event, {item}) {
			const data = item?.data;

			if (data?.action === 'addAssetListEntry') {
				openSimpleInputModal({
					dialogTitle: data?.title,
					formSubmitURL: data?.addAssetListEntryURL,
					mainFieldLabel: Liferay.Language.get('title'),
					mainFieldName: 'title',
					mainFieldPlaceholder: Liferay.Language.get('title'),
					namespace: portletNamespace,
				});
			}
		},
	};
}
