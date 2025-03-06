/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

type ContentSetElement = {
	content: {
		contentFields: Array<{
			contentFieldValue: {data: string};
			name: string;
		}>;
	};
};

export function getContentSetElementContent(element: ContentSetElement) {
	const field = element.content.contentFields.find(
		({name}) => name === 'Content'
	);

	return field.contentFieldValue.data;
}
