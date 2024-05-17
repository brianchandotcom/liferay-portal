/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
declare const Cell: ({
	children,
	className,
	columnName,
	defaultWidth,
	heading,
	resizable,
}: {
	children?: React.ReactNode;
	className?: string | undefined;
	columnName: string;
	defaultWidth?: string | number | undefined;
	heading?: boolean | undefined;
	resizable?: boolean | undefined;
}) => JSX.Element;
export default Cell;
