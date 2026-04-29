/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayBreadcrumb from '@clayui/breadcrumb';
import React from 'react';

interface Item {
	active?: boolean;
	href?: string;
	label: string;
}

interface StyleBookEditorBreadcrumbProps {
	items: Item[];
}

export default function StyleBookEditorBreadcrumb({
	items,
}: StyleBookEditorBreadcrumbProps) {
	return <ClayBreadcrumb className="mb-0" items={items} />;
}
