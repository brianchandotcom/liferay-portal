/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from '../../helpers/ApiHelpers';

export default async function addExpiredStructuredContent(
	apiHelpers: ApiHelpers,
	siteId: string,
	contentStructureId: number,
	title: string
): Promise<StructuredContent> {
	const structuredContent =
		await apiHelpers.headlessDelivery.postStructuredContent({
			contentStructureId,
			datePublished: null,
			siteId,
			title,
		});

	await apiHelpers.jsonWebServicesJournal.expireArticle(
		siteId,
		structuredContent.key
	);

	return apiHelpers.headlessDelivery.getStructuredContentByKey(
		siteId,
		structuredContent.key
	);
}
