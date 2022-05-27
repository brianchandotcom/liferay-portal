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

import {getCheckedCheckboxes, openSelectionModal, sub} from 'frontend-js-web';

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
	if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this'))) {
		const form = document.getElementById(`${portletNamespace}fm`);

		const input = document.getElementById(
			`${portletNamespace}${inputName}`
		);

		if (form && input) {
			input.setAttribute(
				'value',
				getCheckedCheckboxes(form, `${portletNamespace}allRowIds`)
			);

			submitForm(form);
		}
	}
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
