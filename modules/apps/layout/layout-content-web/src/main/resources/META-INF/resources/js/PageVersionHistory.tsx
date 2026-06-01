/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {SidePanel} from '@clayui/core';
import ClayToolbar from '@clayui/toolbar';
import {sub} from 'frontend-js-web';
import React, {useRef, useState} from 'react';

import './PageVersionHistory.scss';

interface Props {
	pageSpecificationVersionsURL: string;
}

export default function PageVersionHistory({
	pageSpecificationVersionsURL,
}: Props) {
	const [isOpen, setIsOpen] = useState(true);

	const wrapperRef = useRef<HTMLElement | null>(
		document.getElementById('wrapper')
	);

	return (
		<>
			<ClayToolbar className="bg-white page-version-history__toolbar">
				{!isOpen ? (
					<ClayToolbar.Nav>
						<ClayToolbar.Item>
							<ClayButtonWithIcon
								displayType="secondary"
								onClick={() => setIsOpen(true)}
								size="sm"
								symbol="angle-double-right"
								title={sub(
									Liferay.Language.get('open-x'),
									sub(
										Liferay.Language.get('x-panel'),
										Liferay.Language.get('version-history')
									)
								)}
							/>
						</ClayToolbar.Item>
					</ClayToolbar.Nav>
				) : null}
			</ClayToolbar>

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
