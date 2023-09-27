/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
interface SheetProps {
	children: React.ReactNode;
	description?: String;
	footer?: React.ReactNode;
	title: String;
}
export default function Sheet({
	children,
	description,
	footer,
	title,
}: SheetProps): JSX.Element;
export {};
