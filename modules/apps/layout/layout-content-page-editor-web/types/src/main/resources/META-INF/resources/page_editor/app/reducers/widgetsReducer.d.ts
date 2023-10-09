/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import toggleWidgetHighlighted from '../actions/toggleWidgetHighlighted';
import updateWidgets, {WidgetSet} from '../actions/updateWidgets';
export default function widgetsReducer(
	widgets: WidgetSet[] | null | undefined,
	action:
		| ReturnType<typeof toggleWidgetHighlighted>
		| ReturnType<typeof updateWidgets>
): WidgetSet[] | null | undefined;
