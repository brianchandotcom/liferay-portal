/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ADD_ITEM} from './types';

import type {LayoutData} from '../../types/LayoutData';

export interface PageContent {
	actions: {
		editImage?: {
			editImageURL: string;
			fileEntryId: string;
			previewURL: string;
		};
		editURL?: string;
		permissionsURL?: string;
		viewUsagesURL?: string;
	};
	className: string;
	classNameId: string;
	classPK: string;
	classTypeId: string;
	externalReferenceCode: string;
	icon: string;
	isRestricted: boolean;
	status: {
		hasApprovedVersion: boolean;
		label: string;
		style: string;
	};
	subtype: string;
	title: string;
	type: string;
	usagesCount: number;
}

export default function addItem({
	fragmentEntryLinkIds,
	itemId,
	layoutData,
	pageContents,
	portletIds = [],
}: {
	fragmentEntryLinkIds: string[];
	itemId: string;
	layoutData: LayoutData;
	pageContents: PageContent[];
	portletIds: string[];
}) {
	return {
		fragmentEntryLinkIds,
		itemId,
		layoutData,
		pageContents,
		portletIds,
		type: ADD_ITEM,
	} as const;
}
