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

import {
	getCheckedCheckboxes,
	openSimpleInputModal,
	postForm,
} from 'frontend-js-web';

import openConfirm from './openConfirm.es';

export default function propsTransformer({
	additionalProps: {deleteDepotEntriesURL},
	portletNamespace,
	...otherProps
}) {
	const deleteSelectedDepotEntries = () => {
		openConfirm({
			message: Liferay.Language.get(
				'removing-an-asset-library-can-affect-sites-that-use-the-contents-stored-in-it.-are-you-sure-you-want-to-continue-removing-this-asset-library'
			),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					const form = document.getElementById(
						`${portletNamespace}fm`
					);

					if (form) {
						postForm(form, {
							data: {
								deleteEntryIds: getCheckedCheckboxes(
									form,
									`${portletNamespace}allRowIds`
								),
							},
							url: deleteDepotEntriesURL,
						});
					}
				}
			},
		});
	};

	return {
		...otherProps,
		onActionButtonClick: (event, {item}) => {
			if (item?.data?.action === 'deleteSelectedDepotEntries') {
				deleteSelectedDepotEntries();
			}
		},
		onCreateButtonClick: (event, {item}) => {
			openSimpleInputModal({
				dialogTitle: Liferay.Language.get('add-asset-library'),
				formSubmitURL: item?.data?.addDepotEntryURL,
				mainFieldLabel: Liferay.Language.get('name'),
				mainFieldName: 'name',
				namespace: `${portletNamespace}`,
			});
		},
	};
}
