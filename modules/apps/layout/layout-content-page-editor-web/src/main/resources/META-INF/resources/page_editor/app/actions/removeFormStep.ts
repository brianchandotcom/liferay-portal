/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FormStepLayoutDataItem} from '../../types/layout_data/FormStepLayoutDataItem';
import {FragmentEntryLinkMap} from './addFragmentEntryLinks';
import {REMOVE_FORM_STEP} from './types';

import type {LayoutData} from '../../types/layout_data/LayoutData';

export default function removeFormStep({
	fragmentEntryLinks,
	itemId,
	layoutData,
	movedItemIds,
	removedItemIds,
}: {
	fragmentEntryLinks: FragmentEntryLinkMap;
	itemId: FormStepLayoutDataItem['itemId'];
	layoutData: LayoutData;
	movedItemIds: {itemId: string; parentId: string}[];
	removedItemIds: string[];
}) {
	return {
		fragmentEntryLinks,
		itemId,
		layoutData,
		movedItemIds,
		removedItemIds,
		type: REMOVE_FORM_STEP,
	} as const;
}
