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

import {openSelectionModal, postForm} from 'frontend-js-web';

export default function propsTransformer({portletNamespace, ...otherProps}) {
	return {
		...otherProps,
		onActionButtonClick: (event, {item}) => {
			const data = item.data;

			const action = data?.action;

			if (action === 'removeOrganizations') {
				if (
					confirm(
						Liferay.Language.get(
							'are-you-sure-you-want-to-remove-the-selected-organizations'
						)
					)
				) {
					const form = document.getElementById(
						`${portletNamespace}fm`
					);

					if (form) {
						postForm(form, {
							data: {
								accountOrganizationIds: Liferay.Util.listCheckedExcept(
									form,
									`${portletNamespace}allRowIds`
								),
							},
							url: data.removeOrganizationsURL,
						});
					}
				}
			}
		},
		onCreateButtonClick: (event, {item}) => {
			const data = item.data;

			if (!data) {
				return;
			}

			openSelectionModal({
				buttonAddLabel: Liferay.Language.get('assign'),
				multiple: true,
				onSelect: (selectedItem) => {
					if (selectedItem) {
						const form = document.getElementById(
							`${portletNamespace}fm`
						);

						if (form) {
							postForm(form, {
								data: {
									accountOrganizationIds: selectedItem.value,
								},
								url: data.assignAccountOrganizationsURL,
							});
						}
					}
				},
				selectEventName: `${portletNamespace}assignAccountOrganizations`,
				title: Liferay.Util.sub(
					Liferay.Language.get('assign-organizations-to-x'),
					data.accountEntryName
				),
				url: data.selectAccountOrganizationsURL,
			});
		},
	};
}
