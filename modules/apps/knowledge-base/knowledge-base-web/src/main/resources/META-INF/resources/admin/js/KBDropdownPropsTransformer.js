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
	objectToFormData,
	openConfirmModal,
	openModal,
	openSelectionModal,
	openToast,
} from 'frontend-js-web';

import showSuccessMessage from './utils/showSuccessMessage';

const ITEM_TYPES = {
	article: 'article',
	folder: 'folder',
};

const ACTIONS = {
	delete({deleteURL}) {
		openConfirmModal({
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-delete-this'
			),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					submitForm(document.hrefFm, deleteURL);
				}
			},
		});
	},

	move(
		{
			moveItemActionUrl,
			moveItemModalUrl,
			itemClassNameId,
			itemId,
			itemType,
		},
		portletNamespace
	) {
		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('move'),
			height: '50vh',
			multiple: true,
			onSelect: ({index, item, parentItem}) => {
				if (!item) {
					item = {
						classNameId: itemClassNameId,
						id: itemId,
						type: itemType,
					};
				}

				if (
					item.type === ITEM_TYPES.folder &&
					parentItem.type === ITEM_TYPES.article
				) {
					openToast({
						message: Liferay.Language.get(
							'folders-cannot-be-moved-into-articles'
						),
						type: 'danger',
					});

					return false;
				}

				fetch(moveItemActionUrl, {
					body: objectToFormData({
						[`${portletNamespace}dragAndDrop`]: true,
						[`${portletNamespace}position`]: index?.next ?? -1,
						[`${portletNamespace}resourceClassNameId`]: item.classNameId,
						[`${portletNamespace}resourcePrimKey`]: item.id,
						[`${portletNamespace}parentResourceClassNameId`]: parentItem.classNameId,
						[`${portletNamespace}parentResourcePrimKey`]: parentItem.id,
					}),
					method: 'POST',
				})
					.then((response) => {
						if (!response.ok) {
							throw new Error();
						}

						return response.json();
					})
					.then((response) => {
						if (!response.success) {
							throw new Error(response.errorMessage);
						}

						showSuccessMessage(portletNamespace);
					})
					.catch(
						({
							message = Liferay.Language.get(
								'an-unexpected-error-occurred'
							),
						}) => {
							openToast({
								message,
								type: 'danger',
							});
						}
					);

				return true;
			},
			selectEventName: `selectKBMoveFolder`,
			size: 'md',
			title: Liferay.Language.get('move'),
			url: moveItemModalUrl,
		});
	},

	permissions({permissionsURL}) {
		openModal({
			title: Liferay.Language.get('permissions'),
			url: permissionsURL,
		});
	},

	print({printURL}) {
		openModal({
			title: Liferay.Language.get('print'),
			url: printURL,
		});
	},
};

export default function propsTransformer({items, portletNamespace, ...props}) {
	return {
		...props,
		items: items.map((item) => {
			return {
				...item,
				items: item.items?.map((child) => ({
					...child,
					onClick(event) {
						const action = child.data?.action;

						if (action) {
							event.preventDefault();

							ACTIONS[action](child.data, portletNamespace);
						}
					},
				})),
			};
		}),
	};
}
