/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getCheckedCheckboxes, postForm} from 'frontend-js-web';

export default function propsTransformer({portletNamespace, ...otherProps}) {
	const activateRegions = (itemData) => {
		const form = document.getElementById(`${portletNamespace}fm`);

		if (form) {
			postForm(form, {
				data: {
					regionIds: getCheckedCheckboxes(
						form,
						`${portletNamespace}allRowIds`
					),
				},
				url: itemData?.activateRegionsURL,
			});
		}
	};

	const deactivateRegions = (itemData) => {
		if (
			confirm(
				Liferay.Language.get(
					'are-you-sure-you-want-to-deactivate-the-selected-regions'
				)
			)
		) {
			const form = document.getElementById(`${portletNamespace}fm`);

			if (form) {
				postForm(form, {
					data: {
						regionIds: getCheckedCheckboxes(
							form,
							`${portletNamespace}allRowIds`
						),
					},
					url: itemData?.deactivateRegionsURL,
				});
			}
		}
	};

	const deleteRegions = (itemData) => {
		if (
			confirm(
				Liferay.Language.get(
					'are-you-sure-you-want-to-delete-the-selected-regions'
				)
			)
		) {
			const form = document.getElementById(`${portletNamespace}fm`);

			if (form) {
				postForm(form, {
					data: {
						regionIds: getCheckedCheckboxes(
							form,
							`${portletNamespace}allRowIds`
						),
					},
					url: itemData?.deleteRegionsURL,
				});
			}
		}
	};

	return {
		...otherProps,
		onActionButtonClick: (event, {item}) => {
			const data = item?.data;

			const action = data?.action;

			if (action === 'activateRegions') {
				activateRegions(data);
			}
			else if (action === 'deactivateRegions') {
				deactivateRegions(data);
			}
			else if (action === 'deleteRegions') {
				deleteRegions(data);
			}
		},
	};
}
