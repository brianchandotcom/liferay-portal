/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Icon from '@clayui/icon';
import React from 'react';

import {useSidePanel} from './context';

type Props = {

	/**
	 * Children content to render a content.
	 */
	children: React.ReactNode;
};

export function Header({children}: Props) {
	const {onOpenChange} = useSidePanel();

	return (
		<div className="sidebar-header">
			<div className="autofit-row sidebar-section">
				<div className="autofit-col autofit-col-expand">{children}</div>

				<div className="autofit-col">
					<button
						aria-label="Close"
						className="close"
						onClick={() => onOpenChange(false)}
						type="button"
					>
						<Icon symbol="times" />
					</button>
				</div>
			</div>
		</div>
	);
}

export function Title({children}: React.HTMLAttributes<HTMLDivElement>) {
	const {titleId} = useSidePanel();

	return (
		<span className="component-title" id={titleId}>
			{children}
		</span>
	);
}
