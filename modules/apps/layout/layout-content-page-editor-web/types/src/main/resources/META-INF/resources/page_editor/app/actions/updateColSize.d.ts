/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import type {LayoutData} from '../../types/LayoutData';
export default function updateColSize({
	layoutData,
	rowItemId,
}: {
	layoutData: LayoutData;
	rowItemId: string;
}): {
	readonly layoutData: LayoutData;
	readonly rowItemId: string;
	readonly type: 'UPDATE_COL_SIZE';
};
