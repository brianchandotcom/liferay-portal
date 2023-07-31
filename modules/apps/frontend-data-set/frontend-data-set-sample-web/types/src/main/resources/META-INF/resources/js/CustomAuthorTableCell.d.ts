/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface ICustomAuthorTableCell {
	actions: Array<{
		label: string;
	}>;
	itemData: {
		color: string;
	};
	itemId: string;
	loadData: Function;
	openSidePanel: Function;
	options: {
		label: string;
	};
	rootPropertyName: string;
	value: string;
	valuePath: Array<string>;
}
declare const CustomAuthorTableCell: ({
	actions,
	itemData,
	itemId,
	loadData,
	openSidePanel,
	options,
	rootPropertyName,
	value,
	valuePath,
}: ICustomAuthorTableCell) => JSX.Element;
export default CustomAuthorTableCell;
