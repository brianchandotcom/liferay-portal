/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface IProps {
	addLayoutURL: string;
	getLayoutPageTemplateEntryListURL: string;
	layoutPageTemplateEntryId: string;
	portletNamespace: string;
	subtitle: string;
	thumbnailURL: string;
	title: string;
}
export default function LayoutPageTemplateEntryCard({
	addLayoutURL,
	getLayoutPageTemplateEntryListURL,
	layoutPageTemplateEntryId,
	subtitle,
	thumbnailURL,
	title,
}: IProps): JSX.Element;
export {};
