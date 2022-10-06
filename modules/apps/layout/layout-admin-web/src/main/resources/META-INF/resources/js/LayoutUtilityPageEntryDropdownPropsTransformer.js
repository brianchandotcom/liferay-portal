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

import {openConfirmModal, openSimpleInputModal} from 'frontend-js-web';

import openDeleteLayoutModal from './openDeleteLayoutModal';

const ACTIONS = {
	deleteLayoutUtilityPageEntry({
		deleteLayoutUtilityPageEntryMessage,
		deleteLayoutUtilityPageEntryURL,
	}) {
		openDeleteLayoutModal({
			message: deleteLayoutUtilityPageEntryMessage,
			onDelete: () => {
				send(deleteLayoutUtilityPageEntryURL);
			},
			title: Liferay.Language.get('utility-pages'),
		});
	},

	markAsDefaultLayoutUtilityPageEntry({
		markAsDefaultLayoutUtilityPageEntryURL,
		message,
	}) {
		if (message !== '') {
			openConfirmModal({
				message: Liferay.Language.get(message),
				onConfirm: (isConfirmed) => {
					if (isConfirmed) {
						send(markAsDefaultLayoutUtilityPageEntryURL);
					}
				},
			});
		}
		else {
			send(markAsDefaultLayoutUtilityPageEntryURL);
		}
	},

	renameLayoutUtilityPageEntry(
		{
			layoutUtilityPageEntryId,
			layoutUtilityPageEntryName,
			updateLayoutUtilityPageEntryURL,
		},
		namespace
	) {
		openSimpleInputModal({
			dialogTitle: Liferay.Language.get('rename-layout-utility-page'),
			formSubmitURL: updateLayoutUtilityPageEntryURL,
			idFieldName: 'layoutUtilityPageEntryId',
			idFieldValue: layoutUtilityPageEntryId,
			mainFieldLabel: Liferay.Language.get('name'),
			mainFieldName: 'name',
			mainFieldPlaceholder: Liferay.Language.get('name'),
			mainFieldValue: layoutUtilityPageEntryName,
			namespace,
		});
	},
};

function send(url) {
	submitForm(document.hrefFm, url);
}

export default function LayoutUtilityPageEntryDropdownPropsTransformer({
	actions,
	portletNamespace,
	...otherProps
}) {
	return {
		...otherProps,
		actions: actions?.map((item) => {
			return {
				...item,
				items: item.items?.map((child) => {
					return {
						...child,
						onClick(event) {
							const action = child.data?.action;

							if (action) {
								event.preventDefault();

								ACTIONS[action](child.data, portletNamespace);
							}
						},
					};
				}),
			};
		}),
		portletNamespace,
	};
}
