/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from '../../helpers/ApiHelpers';

export default function addDraftStructuredContent({
	apiHelpers,
	categoryIds,
	contentStructureId,
	siteId,
	tags,
	title,
}: {
	apiHelpers: ApiHelpers;
	categoryIds?: number[];
	contentStructureId: number;
	siteId: string;
	tags?: string[];
	title: string;
}): Promise<StructuredContent> {
	return apiHelpers.headlessAdminContent.postStructuredContentDraft({
		categoryIds,
		contentStructureId,
		datePublished: null,
		siteId,
		tags,
		title,
	});
}
