/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getCheckedCheckboxes} from 'frontend-js-web';

export default function propsTransformer({
	additionalProps: {deleteExpandosURL},
	portletNamespace,
	...otherProps
}) {
	return {
		...otherProps,
		onActionButtonClick: (event, {item}) => {
			if (item?.data?.action === 'deleteCustomFields') {
				const form = document.getElementById(`${portletNamespace}fm`);

				if (!form) {
					return;
				}

				const columnIds = form.querySelector(
					`#${portletNamespace}columnIds`
				);

				if (columnIds) {
					const checkedIds = getCheckedCheckboxes(
						form,
						`${portletNamespace}allRowIds`
					);

					columnIds.setAttribute('value', checkedIds);
				}

				submitForm(form, deleteExpandosURL);
			}
		},
	};
}
