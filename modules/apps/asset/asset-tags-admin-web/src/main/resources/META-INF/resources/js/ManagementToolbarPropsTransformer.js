/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getCheckedCheckboxes} from 'frontend-js-web';

import openDeleteTagModal from './openDeleteTagModal';

export default function propsTransformer({portletNamespace, ...otherProps}) {
	const deleteTags = () => {
		openDeleteTagModal({
			multiple: true,
			onDelete: () => {
				const form = document.getElementById(`${portletNamespace}fm`);

				if (form) {
					submitForm(form);
				}
			},
		});
	};

	const mergeTags = (itemData) => {
		const form = document.getElementById(`${portletNamespace}fm`);

		if (form) {
			location.href = itemData?.mergeTagsURL.replace(
				escape('[$MERGE_TAGS_IDS$]'),
				getCheckedCheckboxes(form, `${portletNamespace}allRowIds`)
			);
		}
	};

	return {
		...otherProps,
		onActionButtonClick(event, {item}) {
			const data = item?.data;

			const action = data?.action;

			if (action === 'deleteTags') {
				deleteTags();
			}
			else if (action === 'mergeTags') {
				mergeTags(data);
			}
		},
	};
}
