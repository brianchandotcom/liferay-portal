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
	openConfirmModal,
	openToast,
	postForm,
	sub,
} from 'frontend-js-web';

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
