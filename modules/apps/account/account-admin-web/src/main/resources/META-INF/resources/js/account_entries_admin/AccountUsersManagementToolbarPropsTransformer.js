/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	getCheckedCheckboxes,
	openConfirmModal,
	openModal,
	openSelectionModal,
	postForm,
	sub,
} from 'frontend-js-web';

function openInviteAccountUsersModal(
	accountEntryName,
	requestInvitationsURL,
	portletNamespace
) {
	openModal({
		buttons: [
			{
				displayType: 'secondary',
				label: Liferay.Language.get('cancel'),
				type: 'cancel',
			},
			{
				formId: `${portletNamespace}inviteUserForm`,
				label: Liferay.Language.get('invite'),
				type: 'submit',
			},
		],
		containerProps: {
			className: 'modal-height-xl',
		},
		id: `${portletNamespace}inviteUsersDialog`,
		iframeBodyCssClass: '',
		size: 'lg',
		title: sub(Liferay.Language.get('invite-users-to-x'), accountEntryName),
		url: requestInvitationsURL,
	});
}

function openSelectAccountUsersModal(
	accountEntryName,
	assignAccountUsersURL,
	selectAccountUsersURL,
	portletNamespace
) {
	openSelectionModal({
		buttonAddLabel: Liferay.Language.get('assign'),
		containerProps: {
			className: '',
		},
		iframeBodyCssClass: '',
		multiple: true,
		onSelect: (selectedItems) => {
			if (!selectedItems?.length) {
				return;
			}

			const form = document.getElementById(`${portletNamespace}fm`);

			if (form) {
				const values = selectedItems.map((item) => item.value);

				postForm(form, {
					data: {
						accountUserIds: values.join(','),
					},
					url: assignAccountUsersURL,
				});
			}
		},
		title: sub(Liferay.Language.get('assign-users-to-x'), accountEntryName),
		url: selectAccountUsersURL,
	});
}

export default function propsTransformer({
	additionalProps: {accountEntryName},
	portletNamespace,
	...otherProps
}) {
	return {
		...otherProps,
		onActionButtonClick: (event, {item}) => {
			const data = item?.data;

			const action = data?.action;

			if (action === 'removeUsers') {
				openConfirmModal({
					message: Liferay.Language.get(
						'are-you-sure-you-want-to-remove-the-selected-users'
					),
					onConfirm: (isConfirmed) => {
						if (isConfirmed) {
							const form = document.getElementById(
								`${portletNamespace}fm`
							);

							if (form) {
								postForm(form, {
									data: {
										accountUserIds: getCheckedCheckboxes(
											form,
											`${portletNamespace}allRowIds`
										),
									},
									url: data?.removeUsersURL,
								});
							}
						}
					},
				});
			}
		},
		onCreateButtonClick: (event, {item}) => {
			const data = item.data;

			if (data?.action === 'inviteAccountUsers') {
				openInviteAccountUsersModal(
					accountEntryName,
					data?.requestInvitationsURL,
					portletNamespace
				);
			}
			else if (data?.action === 'selectAccountUsers') {
				openSelectAccountUsersModal(
					accountEntryName,
					data?.assignAccountUsersURL,
					data?.selectAccountUsersURL,
					portletNamespace
				);
			}
		},
		onCreationMenuItemClick: (event, {item}) => {
			const data = item?.data;

			const action = data?.action;

			if (action === 'inviteAccountUsers') {
				openInviteAccountUsersModal(
					accountEntryName,
					data?.requestInvitationsURL,
					portletNamespace
				);
			}
			else if (action === 'selectAccountUsers') {
				openSelectAccountUsersModal(
					accountEntryName,
					data?.assignAccountUsersURL,
					data?.selectAccountUsersURL,
					portletNamespace
				);
			}
		},
	};
}
