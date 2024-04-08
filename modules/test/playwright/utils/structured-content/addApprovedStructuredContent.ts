/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from '../../helpers/ApiHelpers';

export default function addApprovedStructuredContent({
	apiHelpers,
	contentStructureId,
	siteId,
	title,
}: {
	apiHelpers: ApiHelpers;
	contentStructureId: number;
	siteId: string;
	title: string;
}): Promise<StructuredContent> {
	return apiHelpers.headlessDelivery.postStructuredContent({
		contentStructureId,
		datePublished: null,
		siteId,
		title,
	});
}
