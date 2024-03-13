/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openConfirmModal, openModal} from 'frontend-js-web';

const _webDAVHTML = (
	learnMessage,
	learnURL,
	portletNamespace,
	webDavURL
) => `<div class="portlet-document-library">
		${Liferay.Language.get('webdav-help')}

		<a href="${learnURL}" target="_blank">${learnMessage}</a>

		<br/><br/>

		<div class="form-group input-resource-wrapper">
			<label class="control-label" for="${portletNamespace}webDavURL">
				${Liferay.Language.get('web-dav-url')}
			</label>

			<input class="form-control lfr-input-resource" disabled id="${portletNamespace}webDavURL" name="${portletNamespace}webDavURL" type="text" value="${webDavURL}"/>
		</div>
	</div>`;

const ACTIONS = {
	accessFromDesktop({learnMessage, learnURL, webDavURL}, portletNamespace) {
		openModal({
			bodyHTML: _webDAVHTML(
				learnMessage,
				learnURL,
				portletNamespace,
				webDavURL
			),
			onOpen() {
				const webdavURLInput = document.getElementById(
					`${portletNamespace}webDavURL`
				);

				if (webdavURLInput) {
					webdavURLInput.focus();
				}
			},
			title: Liferay.Language.get('access-from-desktop'),
		});
	},

	delete({deleteURL}) {
		openConfirmModal({
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-delete-this'
			),
			onConfirm: (isConfirmed) =>
				isConfirmed && submitForm(document.hrefFm, deleteURL),
		});
	},

	permissions({permissionsURL}) {
		openModal({
			title: Liferay.Language.get('permissions'),
			url: permissionsURL,
		});
	},

	publish({publishURL}) {
		openConfirmModal({
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-publish-the-selected-folder'
			),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					location.href = publishURL;
				}
			},
		});
	},

	slideShow({viewSlideShowURL}) {
		const slideShowWindow = window.open(
			viewSlideShowURL,
			'slideShow',
			'directories=no,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no'
		);

		slideShowWindow.focus();
	},
};

export default function propsTransformer({
	actions,
	items,
	portletNamespace,
	...props
}) {
	const updateItem = (item) => {
		const newItem = {
			...item,
			onClick(event) {
				const action = item.data?.action;

				if (action) {
					event.preventDefault();

					ACTIONS[action]?.(item.data, portletNamespace);
				}
			},
		};

		if (Array.isArray(item.items)) {
			newItem.items = item.items.map(updateItem);
		}

		return newItem;
	};

	return {
		...props,
		actions: actions?.map(updateItem),
		items: items?.map(updateItem),
	};
}
