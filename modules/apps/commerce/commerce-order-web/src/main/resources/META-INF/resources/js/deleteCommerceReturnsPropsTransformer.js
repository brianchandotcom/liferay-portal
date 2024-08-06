/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch, openConfirmModal, openToast, sub} from 'frontend-js-web';

import CommerceCurrencyDataRenderer from './CommerceCurrencyDataRenderer';
import {CommerceReturnStatusDataRenderer} from './CommerceReturnStatusDataRenderer';
import CommerceStatusDataRenderer from './CommerceStatusDataRenderer';

export default function propsTransformer({...otherProps}) {
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

	return {
		...otherProps,
		customDataRenderers: {
			commerceCurrencyDataRenderer: CommerceCurrencyDataRenderer,
			commerceReturnStatusDataRenderer: CommerceReturnStatusDataRenderer,
			commerceStatusDataRenderer: CommerceStatusDataRenderer,
		},
		onActionDropdownItemClick({action, itemData, loadData}) {
			if (action.data.id === 'delete') {
				openConfirmModal({
					message: `${sub(
						Liferay.Language.get(
							'are-you-sure-you-want-to-delete-return-x'
						),
						itemData.id
					)}\n${Liferay.Language.get(
						'this-operation-cannot-be-undone'
					)}`,
					onConfirm: (confirm) =>
						handleDelete(confirm, itemData, loadData),
					title: sub(
						Liferay.Language.get('delete-return-x'),
						itemData.id
					),
				});
			}
		},
	};
}
