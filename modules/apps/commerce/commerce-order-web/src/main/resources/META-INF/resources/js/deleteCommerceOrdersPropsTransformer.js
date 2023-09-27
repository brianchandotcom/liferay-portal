/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	fetch,
	openConfirmModal,
	openToast,
	postForm,
	sub,
} from 'frontend-js-web';

import {CommerceStatusDataRenderer} from './CommerceStatusDataRenderer';

export default function propsTransformer({
	additionalProps: {namespace},
	formName,
	selectedItemsKey,
	...otherProps
}) {
	const handleDelete = (confirm, itemData, loadData) => {
		const deleteURL = itemData.actions.delete.href;

		if (confirm) {
			fetch(deleteURL.replace('{id}', itemData.id), {method: 'DELETE'})
				.then(({ok}) => {
					if (ok) {
						loadData();
						openToast({
							message: Liferay.Language.get(
								'your-request-completed-successfully'
							),
							type: 'success',
						});
					}
					else {
						throw new Error();
					}
				})
				.catch(() => {
					openToast({
						message: Liferay.Language.get(
							'an-unexpected-error-occurred'
						),
						type: 'danger',
					});
				});
		}
	};

	const handleBulkDelete = (action, confirm, keyValues) => {
		if (confirm) {
			const form = document.getElementById(`${namespace}${formName}`);

			if (form) {
				postForm(form, {
					data: {
						...action.data,
						[`${selectedItemsKey}`]: keyValues.join(','),
					},
					url: action.href,
				});
			}
			else {
				openToast({
					message: Liferay.Language.get(
						'an-unexpected-error-occurred'
					),
					type: 'danger',
				});
			}
		}
	};

	return {
		...otherProps,
		customDataRenderers: {
			commerceStatusDataRenderer: CommerceStatusDataRenderer,
		},
		onActionDropdownItemClick({action, itemData, loadData}) {
			if (action.data.id === 'delete') {
				openConfirmModal({
					message: `${sub(
						Liferay.Language.get(
							'are-you-sure-you-want-to-delete-order-x'
						),
						itemData.id
					)}\n${Liferay.Language.get(
						'this-operation-cannot-be-undone'
					)}`,
					onConfirm: (confirm) =>
						handleDelete(confirm, itemData, loadData),
					title: sub(
						Liferay.Language.get('delete-order-x'),
						itemData.id
					),
				});
			}
		},
		onBulkActionItemClick({action, selectedData: {keyValues}}) {
			if (action.data.id === 'delete') {
				openConfirmModal({
					message: `${Liferay.Language.get(
						'are-you-sure-you-want-to-delete-all-selected-orders'
					)}\n${Liferay.Language.get(
						'this-operation-cannot-be-undone'
					)}`,
					onConfirm: (confirm) =>
						handleBulkDelete(action, confirm, keyValues),
					title: Liferay.Language.get('bulk-delete-orders'),
				});
			}
		},
	};
}
