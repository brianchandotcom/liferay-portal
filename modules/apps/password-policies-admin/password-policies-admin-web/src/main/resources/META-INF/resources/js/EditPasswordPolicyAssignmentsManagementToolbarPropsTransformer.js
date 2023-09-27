/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	getCheckedCheckboxes,
	openConfirmModal,
	openSelectionModal,
	sub,
} from 'frontend-js-web';

function addEntity(portletNamespace, inputName, entity) {
	const addUserIdsInput = document.getElementById(
		`${portletNamespace}${inputName}`
	);

	if (addUserIdsInput) {
		addUserIdsInput.setAttribute('value', entity);
	}

	const form = document.getElementById(`${portletNamespace}fm`);

	if (form) {
		submitForm(form);
	}
}

function deleteEntities(portletNamespace, inputName) {
	openConfirmModal({
		message: Liferay.Language.get('are-you-sure-you-want-to-delete-this'),
		onConfirm: (isConfirmed) => {
			if (isConfirmed) {
				const form = document.getElementById(`${portletNamespace}fm`);

				const input = document.getElementById(
					`${portletNamespace}${inputName}`
				);

				if (form && input) {
					input.setAttribute(
						'value',
						getCheckedCheckboxes(
							form,
							`${portletNamespace}allRowIds`
						)
					);

					submitForm(form);
				}
			}
		},
	});
}

const ACTIONS = {
	deleteOrganizations(portletNamespace) {
		deleteEntities(portletNamespace, 'removeOrganizationIds');
	},

	deleteUsers(portletNamespace) {
		deleteEntities(portletNamespace, 'removeUserIds');
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
			const action = item?.data?.action;

			if (action) {
				ACTIONS[action](portletNamespace);
			}
		},
		onCreateButtonClick() {
			const {passwordPolicyName, selectMembersURL} = additionalProps;

			openSelectionModal({
				multiple: true,
				onSelect(result) {
					if (result?.item) {
						const inputName = {
							organizations: 'addOrganizationIds',
							users: 'addUserIds',
						}[result?.memberType];

						addEntity(portletNamespace, inputName, result?.item);
					}
				},
				selectEventName: `${portletNamespace}selectMember`,
				title: sub(
					Liferay.Language.get('add-assignees-to-x'),
					passwordPolicyName
				),
				url: selectMembersURL,
			});
		},
	};
}
