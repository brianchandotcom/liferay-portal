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

	deleteExpiredTemporaryFileEntries({deleteExpiredTemporaryFileEntriesURL}) {
		submitForm(document.hrefFm, deleteExpiredTemporaryFileEntriesURL);
	},

	move({parameterName, parameterValue}, portletNamespace) {
		window[`${portletNamespace}move`](1, parameterName, parameterValue);
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

export default function propsTransformer({items, portletNamespace, ...props}) {
	return {
		...props,
		items: items.map((item) =>
			item?.type === 'group'
				? {
						...item,
						items: item.items?.map((child) => ({
							...child,
							onClick(event) {
								const action = child.data?.action;

								if (action) {
									event.preventDefault();

									ACTIONS[action](
										child.data,
										portletNamespace
									);
								}
							},
						})),
				  }
				: {
						...item,
						onClick(event) {
							const action = item.data?.action;

							if (action) {
								event.preventDefault();

								ACTIONS[action](item.data, portletNamespace);
							}
						},
				  }
		),
	};
}
