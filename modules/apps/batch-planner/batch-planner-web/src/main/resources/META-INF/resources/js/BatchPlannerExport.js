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

import {fetch} from 'frontend-js-web';

import {HEADERS, HEADLESS_BATCH_ENGINE_URL} from './constants';

export function getExportTaskStatusURL(externalReferenceCode) {
	return `${HEADLESS_BATCH_ENGINE_URL}/export-task/by-external-reference-code/${externalReferenceCode}`;
}

export function getExportFileURL(externalReferenceCode) {
	return `${HEADLESS_BATCH_ENGINE_URL}/export-task/by-external-reference-code/${externalReferenceCode}/content`;
}

export async function exportStatus(externalReferenceCode) {
	const response = await fetch(
		getExportTaskStatusURL(externalReferenceCode),
		{
			headers: HEADERS,
		}
	);

	if (!response.ok) {
		throw new Error(response);
	}

	return await response.json();
}

export async function fetchExportedFile(externalReferenceCode) {
	const response = await fetch(getExportFileURL(externalReferenceCode));
	const blob = await response.blob();

	return URL.createObjectURL(blob);
}
