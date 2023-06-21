/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

declare function TableCell({
	actions,
	inlineEditSettings,
	itemData,
	itemId,
	itemInlineChanges,
	options,
	rootPropertyName,
	value,
	valuePath,
	view,
}: any): JSX.Element | undefined;
export default TableCell;
