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

import {openSelectionModal} from 'frontend-js-web';

const ACTIONS = {
	deleteOrganizations(portletNamespace) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			const form = document[`${portletNamespace}fm`];

			const removeOrganizationIdsInput = document.getElementById(
				`${portletNamespace}removeOrganizationIds`
			);

			if (removeOrganizationIdsInput) {
				removeOrganizationIdsInput.setAttribute(
					'value',
					Liferay.Util.listCheckedExcept(
						form,
						`${portletNamespace}allRowIds`
					)
				);

				submitForm(form);
			}
		}
	},

	deleteUsers(portletNamespace) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			const form = document[`${portletNamespace}fm`];

			const removeUserIdsInput = document.getElementById(
				`${portletNamespace}removeUserIds`
			);

			if (removeUserIdsInput) {
				removeUserIdsInput.setAttribute(
					'value',
					Liferay.Util.listCheckedExcept(
						form,
						`${portletNamespace}allRowIds`
					)
				);

				submitForm(form);
			}
		}
	},
};

export default function propsTransformer({
	additionalProps,
	portletNamespace,
	...props
}) {
	return {
		...props,
		onActionButtonClick(event, {item}) {
			const action = item.data?.action;

			if (action) {
				ACTIONS[action](portletNamespace);
			}
		},
		onCreateButtonClick() {
			const {passwordPolicyName, selectMembersURL} = additionalProps;

			openSelectionModal({
				multiple: true,
				onSelect(result) {
					if (result && result.item) {
						if (result.memberType == 'users') {
							const addUserIdsInput = document.getElementById(
								`${portletNamespace}addUserIds`
							);

							if (addUserIdsInput) {
								addUserIdsInput.setAttribute(
									'value',
									result.item
								);
							}
						}
						else if (result.memberType == 'organizations') {
							const addOrganizationIdsInput = document.getElementById(
								`${portletNamespace}addOrganizationIds`
							);

							if (addOrganizationIdsInput) {
								addOrganizationIdsInput.setAttribute(
									'value',
									result.item
								);
							}
						}

						submitForm(document[`${portletNamespace}fm`]);
					}
				},
				selectEventName: `${portletNamespace}selectMember`,
				title: Liferay.Util.sub(
					Liferay.Language.get('add-assignees-to-x'),
					passwordPolicyName
				),
				url: selectMembersURL,
			});
		},
	};
}
