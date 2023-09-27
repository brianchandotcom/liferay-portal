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
}: SheetProps) {
	return (
		<div className="sheet sheet-lg">
			<div className="sheet-header">
				<h2 className="sheet-title">{title}</h2>

				<div className="sheet-text">{description}</div>
			</div>

			<div className="sheet-section">{children}</div>

			<div className="sheet-footer sheet-footer-btn-block-sm-down">
				{footer}
			</div>
		</div>
	);
}
