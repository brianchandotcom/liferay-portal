/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SidePanel} from '@clayui/core';
import React, {useRef} from 'react';

import './PageVersionHistory.scss';

interface Props {
	pageSpecificationVersionsURL: string;
}

export default function PageVersionHistory({
	pageSpecificationVersionsURL,
}: Props) {
	const wrapperRef = useRef<HTMLElement | null>(
		document.getElementById('wrapper')
	);

	return (
		<>
			<SidePanel
				className="page-version-history__side-panel"
				containerRef={wrapperRef}
				direction="left"
				displayType="light"
				onOpenChange={setIsOpen}
				open={isSidePanelOpen}
				position="fixed"
			>
				<SidePanel.Header
					messages={{closeAriaLabel: Liferay.Language.get('close')}}
				>
					<SidePanel.Title>
						{Liferay.Language.get('version-history')}
					</SidePanel.Title>
				</SidePanel.Header>

				<SidePanel.Body>
					<p>{pageSpecificationVersionsURL}</p>
				</SidePanel.Body>
			</SidePanel>
		</>
	);
}
