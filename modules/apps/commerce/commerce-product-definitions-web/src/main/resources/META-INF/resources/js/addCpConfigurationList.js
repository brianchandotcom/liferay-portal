/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CommerceServiceProvider} from 'commerce-frontend-js';
import {createPortletURL, openModal, openToast} from 'frontend-js-web';

function openAddCPConfigurationListModal({
	addCPConfigurationListRenderURL,
	editCPConfigurationListRenderURL,
	namespace,
	windowState,
}) {
	openModal({
		buttons: [
			{
				displayType: 'secondary',
				label: Liferay.Language.get('cancel'),
				onClick: ({processClose}) => {
					processClose();
				},
				type: 'button',
			},
			{
				displayType: 'primary',
				label: Liferay.Language.get('submit'),
				onClick: ({processClose}) => {
					const iframeContentWindow = document.querySelector(
						'#add-new-product-configuration-modal iframe'
					).contentWindow;
					const iframeDocument = iframeContentWindow.document;
					const formattedData = {};

					formattedData.catalogId = iframeDocument.querySelector(
						`#${namespace}commerceCatalogId`
					)?.value;

					formattedData.name = iframeDocument.querySelector(
						`#${namespace}name`
					)?.value;

					formattedData.priority = iframeDocument.querySelector(
						`#${namespace}priority`
					)?.value;

					const AdminCatalogResource =
						CommerceServiceProvider.AdminCatalogAPI('v1');
					AdminCatalogResource.addProductConfigurationList(
						formattedData
					)
						.then((productConfigurationList) => {
							const redirectURL = createPortletURL(
								editCPConfigurationListRenderURL,
								{
									cpConfigurationListId:
										productConfigurationList.id,
									p_p_state: windowState,
								}
							);

							window.location.href = redirectURL;
						})
						.catch(({message}) => {
							if (message !== 'cancel') {
								openToast({
									message:
										message ||
										Liferay.Language.get(
											'an-unexpected-error-occurred'
										),
									type: 'danger',
								});
							}
						});
					processClose();
				},
				type: 'button',
			},
		],
		id: 'add-new-product-configuration-modal',
		size: 'md',
		title: Liferay.Language.get('add-new-product-configuration'),
		url: addCPConfigurationListRenderURL,
	});
}

export default function main({
	addCPConfigurationListRenderURL,
	editCPConfigurationListRenderURL,
	namespace,
	windowState,
}) {
	const handler = () =>
		openAddCPConfigurationListModal({
			addCPConfigurationListRenderURL,
			editCPConfigurationListRenderURL,
			namespace,
			windowState,
		});

	Liferay.on('addCPConfigurationList', handler);

	return {
		dispose: () => {
			Liferay.detach('addCPConfigurationList', handler);
		},
	};
}
