/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Criteria, CriteriaItem} from '../../types/Criteria';

/**
 * Go through the entire criteria, remove empty groups and
 * change groups with only one child to simple items
 */
export default function cleanCriteria(
	criteria: Criteria | CriteriaItem,
	isRoot?: boolean
): Criteria | CriteriaItem | null;
