/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

import {DEFAULT_ERROR} from './errorMessages';
import {DEFAULT_HEADERS} from './fetch/fetch_data';
import {openErrorToast, openSuccessToast} from './toasts';

export function download(url, parameters, title) {
	fetch(url, parameters)
		.then((response) => {
			if (!response.ok) {
				throw DEFAULT_ERROR;
			}

			return response.blob();
		})
		.then((responseBlob) => {
			const downloadElement = document.createElement('a');

			downloadElement.download = title + '.json';
			downloadElement.href = URL.createObjectURL(responseBlob);

			document.body.appendChild(downloadElement);

			downloadElement.click();

			openSuccessToast();
		})
		.catch(() => {
			openErrorToast();
		});
}

export function updateCollectionProvider({
	collectionProvider = true,
	itemData,
	loadData,
}) {

	// Fetch is being used here to get the `title_i18n` and `description_i18n`
	// properties. For these to be included, the header needed to be modified with
	// `X-Liferay-Accept-All-Languages` but there currently isn't a way to modify
	// the FDS apiURL header. Using PATCH as a method also currently does not work
	// for updating part of a blueprint.

	fetch(`/o/search-experiences-rest/v1.0/sxp-blueprints/${itemData.id}`, {
		headers: {
			...DEFAULT_HEADERS,
			'X-Liferay-Accept-All-Languages': true,
		},
		method: 'GET',
	})
		.then((response) => {
			if (!response.ok) {
				throw DEFAULT_ERROR;
			}

			return response.json();
		})
		.then((responseData) => {
			return fetch(
				`/o/search-experiences-rest/v1.0/sxp-blueprints/${itemData.id}`,
				{
					body: JSON.stringify({
						configuration: {
							...itemData.configuration,
							generalConfiguration: {
								...itemData.configuration?.generalConfiguration,
								collectionProvider,
							},
						},
						description_i18n: responseData.description_i18n,
						elementInstances: itemData.elementInstances,
						externalReferenceCode: itemData.externalReferenceCode,
						title_i18n: responseData.title_i18n,
					}),
					headers: DEFAULT_HEADERS,
					method: 'PUT',
				}
			);
		})
		.then((response) => {
			if (!response.ok) {
				throw DEFAULT_ERROR;
			}

			return response.json();
		})
		.then(() => {
			loadData();

			openSuccessToast();
		})
		.catch(() => {
			openErrorToast();
		});
}
