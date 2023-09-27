/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openSimpleInputModal} from 'frontend-js-web';

import openDeleteSiteNavigationMenuModal from './openDeleteSiteNavigationMenuModal';

export default function propsTransformer({portletNamespace, ...otherProps}) {
	return {
		...otherProps,
		onActionButtonClick(event, {item}) {
			if (item?.data?.action === 'deleteSelectedSiteNavigationMenus') {
				openDeleteSiteNavigationMenuModal({
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
		onCreateButtonClick(event, {item}) {
			const data = item?.data;

			const action = data?.action;

			if (action === 'addSiteNavigationMenu') {
				openSimpleInputModal({
					dialogTitle: Liferay.Language.get('add-menu'),
					formSubmitURL: data?.addSiteNavigationMenuURL,
					mainFieldLabel: Liferay.Language.get('name'),
					mainFieldName: 'name',
					mainFieldPlaceholder: Liferay.Language.get('name'),
					namespace: portletNamespace,
				});
			}
		},
	};
}
