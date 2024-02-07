/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {IQuickActions} from '../index';
declare function QuickActions({
	actions,
	itemData,
	itemId,
	onClick,
}: IQuickActions): JSX.Element;
export default QuickActions;
