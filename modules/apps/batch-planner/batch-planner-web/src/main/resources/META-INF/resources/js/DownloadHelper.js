/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

import {HEADLESS_BATCH_ENGINE_URL} from './constants';

export function getEndpoint(type, externalReferenceCode) {
	externalReferenceCode = encodeURIComponent(externalReferenceCode);
	const endpoints = {
		batchPlannerTemplate: `/o/batch-planner/v1.0/plans/${externalReferenceCode}/template`,
		errorReport: `${HEADLESS_BATCH_ENGINE_URL}/import-task/by-external-reference-code/${externalReferenceCode}/failed-items/report`,
		exportFile: `${HEADLESS_BATCH_ENGINE_URL}/export-task/by-external-reference-code/${externalReferenceCode}/content`,
		importFile: `${HEADLESS_BATCH_ENGINE_URL}/import-task/by-external-reference-code/${externalReferenceCode}/content`,
	};

	return endpoints[type];
}

export function downloadFile({externalReferenceCode, fileName, fileType}) {
	fetch(getEndpoint(fileType, externalReferenceCode)).then((response) => {
		response.blob().then((blob) => {
			const LinkElement = document.createElement('a');

			LinkElement.href = URL.createObjectURL(blob);

			if (fileName === undefined) {
				fileName = response.headers
					.get('Content-Disposition')
					.match(/filename=(.*)/)[1];
			}

			LinkElement.download = fileName;

			document.body.appendChild(LinkElement);

			LinkElement.click();

			LinkElement.remove();
		});
	});
}

export default function ({
	HTMLElementId,
	externalReferenceCode,
	namespace,
	type,
}) {
	document
		.getElementById(HTMLElementId)
		.addEventListener('click', (event) => {
			event.preventDefault();

			if (type === 'batchPlannerTemplate') {
				const valueElement = document.getElementById(
					`${namespace}internalClassNameKey`
				);

				externalReferenceCode =
					valueElement.options[valueElement.options.selectedIndex]
						.value;
			}

			downloadFile({externalReferenceCode, fileType: type});
		});
}
