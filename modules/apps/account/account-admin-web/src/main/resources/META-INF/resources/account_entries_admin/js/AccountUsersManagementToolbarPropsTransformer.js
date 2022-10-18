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

import {
	fetch,
	getCheckedCheckboxes,
	getOpener,
	objectToFormData,
	openConfirmModal,
	openModal,
	openSelectionModal,
	openToast,
	postForm,
	sub,
} from 'frontend-js-web';

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
		onCreationMenuItemClick: (event, {item}) => {
			const data = item?.data;

			const action = data?.action;

			if (action === 'inviteAccountUsers') {
				openModal({
					containerProps: {
						className: 'modal-height-md',
					},
					customEvents: [
						{
							name: `${portletNamespace}inviteUsers`,
							onEvent(event) {
								fetch(data?.inviteAccountUsersURL, {
									body: objectToFormData({
										[`${portletNamespace}accountEntryId`]: event.accountEntryId,
										[`${portletNamespace}emailAddresses`]: event.emailAddresses,
									}),
									method: 'POST',
								})
									.then((response) => response.json())
									.then(({success}) => {
										if (success) {
											getOpener().Liferay.fire(
												'closeModal',
												{
													id: `${portletNamespace}inviteUsersDialog`,
													redirect: event.redirect,
												}
											);
										}
										else {
											throw new Error();
										}
									})
									.catch(() => {
										openToast({
											message: Liferay.Language.get(
												'please-enter-email-addresses-with-valid-domains-only'
											),
											title: Liferay.Language.get(
												'error'
											),
											type: 'danger',
										});
									});
							},
						},
					],
					id: `${portletNamespace}inviteUsersDialog`,
					iframeBodyCssClass: '',
					size: 'lg',
					title: sub(
						Liferay.Language.get('invite-users-to-x'),
						accountEntryName
					),
					url: data?.modalURL,
				});
			}
			else if (action === 'selectAccountUsers') {
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

						const form = document.getElementById(
							`${portletNamespace}fm`
						);

						if (form) {
							const values = selectedItems.map(
								(item) => item.value
							);

							postForm(form, {
								data: {
									accountUserIds: values.join(','),
								},
								url: data?.assignAccountUsersURL,
							});
						}
					},
					title: sub(
						Liferay.Language.get('assign-users-to-x'),
						accountEntryName
					),
					url: data?.modalURL,
				});
			}
		},
	};
}
