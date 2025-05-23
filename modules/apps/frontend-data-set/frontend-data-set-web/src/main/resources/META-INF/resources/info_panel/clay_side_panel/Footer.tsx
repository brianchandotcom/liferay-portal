/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

type Props = {

	/**
	 * Children content to render a content.
	 */
	children: React.ReactNode;
};

export function Footer({children}: Props) {
	return <div className="sidebar-footer">{children}</div>;
}
