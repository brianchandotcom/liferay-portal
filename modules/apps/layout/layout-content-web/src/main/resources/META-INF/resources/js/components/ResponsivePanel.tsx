/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SidePanel} from '@clayui/core';
import React, {useRef} from 'react';

import '../../css/VersionHistory.scss';

export default function ResponsivePanel({
	children,
	onOpenChange,
	open,
}: {
	children: React.ReactNode;
	onOpenChange: (open: boolean) => void;
	open: boolean;
}) {
	const wrapperRef = useRef<HTMLElement | null>(
		document.getElementById('wrapper')
	);

	return (
		<SidePanel
			className="shadow-none version-history__side-panel"
			containerRef={wrapperRef}
			direction="left"
			displayType="light"
			onOpenChange={onOpenChange}
			open={open}
			position="fixed"
		>
			<SidePanel.Header
				messages={{closeAriaLabel: Liferay.Language.get('close')}}
			>
				<SidePanel.Title>
					{Liferay.Language.get('version-history')}
				</SidePanel.Title>
			</SidePanel.Header>

			<SidePanel.Body>{children}</SidePanel.Body>
		</SidePanel>
	);
}
