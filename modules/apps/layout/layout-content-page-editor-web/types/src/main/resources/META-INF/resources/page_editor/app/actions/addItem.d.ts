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
	portletIds,
}: {
	fragmentEntryLinkIds: string[];
	itemId: string;
	layoutData: LayoutData;
	pageContents: PageContent[];
	portletIds: string[];
}): {
	readonly fragmentEntryLinkIds: string[];
	readonly itemId: string;
	readonly layoutData: LayoutData;
	readonly pageContents: PageContent[];
	readonly portletIds: string[];
	readonly type: 'ADD_ITEM';
};
