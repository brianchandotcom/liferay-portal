/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayToolbar from '@clayui/toolbar';
import React from 'react';

interface Props {
	isSidePanelOpen: boolean;
	openSidePanel: () => void;
}

export default function Toolbar({isSidePanelOpen, openSidePanel}: Props) {
	return (
		<ClayToolbar className="bg-white px-3 version-history__toolbar">
			{!isSidePanelOpen ? (
				<ClayToolbar.Nav>
					<ClayToolbar.Item>
						<ClayButtonWithIcon
							displayType="secondary"
							onClick={openSidePanel}
							size="sm"
							symbol="angle-double-right"
							title={Liferay.Language.get(
								'open-version-history-panel'
							)}
						/>
					</ClayToolbar.Item>
				</ClayToolbar.Nav>
			) : null}
		</ClayToolbar>
	);
}
