/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	addParams,
	fetch,
	objectToFormData,
	openConfirmModal,
	openModal,
	openSelectionModal,
	openToast,
	sub,
} from 'frontend-js-web';

import showSuccessMessage from './utils/showSuccessMessage';

const ITEM_TYPES = {
	KBArticle: 'KBArticle',
	KBFolder: 'KBFolder',
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
			kbObjectClassNameId,
			kbObjectId,
			kbObjectTitle,
			kbObjectType,
			moveKBObjectActionURL,
			moveKBObjectModalURL,
		},
		portletNamespace
	) {
		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('save'),
			height: '50vh',
			iframeBodyCssClass: '',
			multiple: true,
			onSelect: ({destinationItem, index}) => {
				if (
					kbObjectType === ITEM_TYPES.KBFolder &&
					destinationItem.type === ITEM_TYPES.KBArticle
				) {
					openToast({
						message: Liferay.Language.get(
							'folders-cannot-be-moved-into-articles'
						),
						type: 'danger',
					});

					return false;
				}

				fetch(moveKBObjectActionURL, {
					body: objectToFormData({
						[`${portletNamespace}dragAndDrop`]: true,
						[`${portletNamespace}position`]: index?.next ?? -1,
						[`${portletNamespace}resourceClassNameId`]: kbObjectClassNameId,
						[`${portletNamespace}resourcePrimKey`]: kbObjectId,
						[`${portletNamespace}parentResourceClassNameId`]: destinationItem.classNameId,
						[`${portletNamespace}parentResourcePrimKey`]: destinationItem.id,
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
			title: sub(Liferay.Language.get('move-x-to'), `"${kbObjectTitle}"`),
			url: addParams(
				`${portletNamespace}moveKBObjectId=${kbObjectId}&${portletNamespace}moveKBObjectClassName=${kbObjectType}`,
				moveKBObjectModalURL
			),
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
