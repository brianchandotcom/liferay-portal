/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

declare const TableHeadCell: ({
	contentRenderer,
	fieldName,
	hideColumnLabel,
	label,
	sortable,
	sortingKey: sortingKeyProp,
}: {
	contentRenderer?: string | undefined;
	fieldName: string | Array<string>;
	hideColumnLabel?: boolean | undefined;
	label: string;
	sortable?: boolean | undefined;
	sortingKey?: string | undefined;
}) => JSX.Element;
export default TableHeadCell;
