/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openToast} from 'frontend-js-components-web';
import {escapeHTML, fetch, sub} from 'frontend-js-web';

import openCopyCompanyModal from './openCopyCompanyModal';
import openDeleteCompanyModal from './openDeleteCompanyModal';
import openExportCompanyModal from './openExportCompanyModal';

const pendingExportURLs = new Set();

const getErrorMessage = (response) =>
	response.json().then(
		({title}) => title || response.statusText,
		() => response.statusText
	);

const showUnexpectedErrorToast = () => {
	openToast({
		message: Liferay.Language.get('an-unexpected-error-occurred'),
		type: 'danger',
	});
};

const ACTIONS = {
	copyDBPartitionCompany(itemData, portletNamespace) {
		openCopyCompanyModal({
			portletNamespace,
			url: itemData.copyURL,
		});
	},

	deleteInstance(itemData) {
		openDeleteCompanyModal({
			onDelete: async () => {
				try {
					const response = await fetch(itemData.deleteURL, {
						method: 'POST',
					});

					if (!response.ok) {
						throw new Error(await getErrorMessage(response));
					}

					const responseJSON = await response.json();

					if (responseJSON.error) {
						throw new Error(responseJSON.error);
					}

					openToast({
						message: sub(
							Liferay.Language.get(
								'the-instance-x-is-being-deleted-you-will-be-notified-when-it-finishes'
							),
							escapeHTML(itemData.portalInstanceId)
						),
						type: 'info',
					});
				}
				catch (error) {
					openToast({
						message: escapeHTML(error.message),
						type: 'danger',
					});
				}
			},
		});
	},

	exportInstance(itemData) {
		if (pendingExportURLs.has(itemData.exportURL)) {
			openToast({
				message: Liferay.Language.get(
					'exporting-an-instance-is-already-in-progress'
				),
				type: 'info',
			});

			return;
		}

		openExportCompanyModal({
			onExport: () => {
				pendingExportURLs.add(itemData.exportURL);

				fetch(itemData.exportURL, {method: 'POST'})
					.then((response) => {
						if (!response.ok) {
							throw new Error(response.status);
						}

						return response.json();
					})
					.then((responseJSON) => {
						if (responseJSON.successMessage) {
							openToast({
								message: responseJSON.successMessage,
								type: 'success',
							});
						}
						else if (responseJSON.error) {
							openToast({
								message: responseJSON.error,
								type: 'danger',
							});
						}
						else {
							showUnexpectedErrorToast();
						}
					})
					.catch(() => {
						showUnexpectedErrorToast();
					})
					.finally(() => {
						pendingExportURLs.delete(itemData.exportURL);
					});
			},
		});
	},
};

export default function propsTransformer({items, portletNamespace, ...props}) {
	return {
		...props,
		items: items.map((item) => {
			return {
				...item,
				items: item.items.map((child) => ({
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
