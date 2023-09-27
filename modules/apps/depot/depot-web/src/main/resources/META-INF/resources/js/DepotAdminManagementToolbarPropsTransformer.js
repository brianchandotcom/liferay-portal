/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	getCheckedCheckboxes,
	openConfirmModal,
	openSimpleInputModal,
	postForm,
} from 'frontend-js-web';

export default function propsTransformer({
	additionalProps: {deleteDepotEntriesURL},
	portletNamespace,
	...otherProps
}) {
	const deleteSelectedDepotEntries = () => {
		openConfirmModal({
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
