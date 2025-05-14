/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export type Fields = {
	DESCRIPTION: string;
	HTML: string;
	THUMBNAIL_URL: string;
	TITLE: string;
	URL: string;
};

export function updateDLVideoFields({
	getVideoFieldsURL,
	namespace,
	onError,
	onUpdate,
	videoURL,
}: {
	getVideoFieldsURL: string;
	namespace: string;
	onError: () => void;
	onUpdate: (fields: Fields) => void;
	videoURL: string;
});

export function validateUrl(url: string);
