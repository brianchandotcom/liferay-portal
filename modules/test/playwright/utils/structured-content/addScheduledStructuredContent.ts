/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from '../../helpers/ApiHelpers';

export default function addScheduledStructuredContent(
	apiHelpers: ApiHelpers,
	siteId: string,
	contentStructureId: number,
	title: string
): Promise<StructuredContent> {
	const nowDate = new Date();

	const oneYearFromNowDate = new Date(
		nowDate.getFullYear() + 1,
		nowDate.getMonth(),
		nowDate.getDate()
	);

	return apiHelpers.headlessDelivery.postStructuredContent({
		contentStructureId,
		datePublished: oneYearFromNowDate.toISOString(),
		siteId,
		title,
	});
}
