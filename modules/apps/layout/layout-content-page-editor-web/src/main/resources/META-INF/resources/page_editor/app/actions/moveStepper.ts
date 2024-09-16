/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MOVE_STEPPER} from './types';

import type {LayoutData} from '../../types/layout_data/LayoutData';

export default function moveStepper({
	addedItemIds,
	formId,
	itemId,
	layoutData,
	movedItemIds,
	removedItemIds,
}: {
	addedItemIds: string[];
	formId: string;
	itemId: string;
	layoutData: LayoutData;
	movedItemIds: {itemId: string; parentId: string}[];
	removedItemIds: string[];
}) {
	return {
		addedItemIds,
		formId,
		itemId,
		layoutData,
		movedItemIds,
		removedItemIds,
		type: MOVE_STEPPER,
	} as const;
}
