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
