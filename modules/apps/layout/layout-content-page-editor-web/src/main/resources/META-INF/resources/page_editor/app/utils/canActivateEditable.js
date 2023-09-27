/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {EDITABLE_TYPES} from '../config/constants/editableTypes';
import {VIEWPORT_SIZES} from '../config/constants/viewportSizes';

export default function canActivateEditable(
	selectedViewportSize,
	editableType
) {
	return (
		selectedViewportSize === VIEWPORT_SIZES.desktop ||
		editableType === EDITABLE_TYPES.image ||
		editableType === EDITABLE_TYPES.backgroundImage
	);
}
