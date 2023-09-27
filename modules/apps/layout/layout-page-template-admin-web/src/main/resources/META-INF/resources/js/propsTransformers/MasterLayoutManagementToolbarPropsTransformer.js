/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openSimpleInputModal} from 'frontend-js-web';

import openDeletePageTemplateModal from '../modal/openDeletePageTemplateModal';

export default function propsTransformer({portletNamespace, ...otherProps}) {
	const deleteSelectedMasterLayouts = () => {
		openDeletePageTemplateModal({
			onDelete: () => {
				const form = document.getElementById(`${portletNamespace}fm`);

				if (form) {
					submitForm(form);
				}
			},
			title: Liferay.Language.get('masters'),
		});
	};

	const exportMasterLayouts = (itemData) => {
		const form = document.getElementById(`${portletNamespace}fm`);

		if (form) {
			submitForm(form, itemData?.exportMasterLayoutURL);
		}
	};

	return {
		...otherProps,
		onActionButtonClick(event, {item}) {
			const data = item?.data;

			const action = data?.action;

			if (action === 'deleteSelectedMasterLayouts') {
				deleteSelectedMasterLayouts();
			}
			else if (action === 'exportMasterLayouts') {
				exportMasterLayouts(data);
			}
		},
		onCreateButtonClick(event, {item}) {
			openSimpleInputModal({
				dialogTitle: Liferay.Language.get('add-master-page'),
				formSubmitURL: item?.data?.addMasterLayoutURL,
				mainFieldLabel: Liferay.Language.get('name'),
				mainFieldName: 'name',
				mainFieldPlaceholder: Liferay.Language.get('name'),
				namespace: portletNamespace,
			});
		},
	};
}
