/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import FragmentService from '../app/services/FragmentService';
declare type DataType = Parameters<
	typeof FragmentService.renderFragmentEntryLinksContent
>[number]['data'][number];
export default function batchRenderFragmentEntryContentRequest(
	languageId: string,
	segmentsExperienceId: string,
	data: DataType,
	callback: (content: string) => void
): void;
export {};
