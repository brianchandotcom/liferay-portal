/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayEmptyState from '@clayui/empty-state';
import React, {useContext} from 'react';

import FrontendDataSetContext from '../FrontendDataSetContext';
import {SidePanel, SidePanelProps} from './clay_side_panel';

export function InfoPanel({component: InfoPanelContent, ...props}: any) {
	const {selectedItems} = useContext(FrontendDataSetContext);

	if (InfoPanelContent) {
		return (
			<SidePanel {...props}>
				<InfoPanelContent items={selectedItems} />
			</SidePanel>
		);
	}

	return (
		<SidePanel {...props}>
			<SidePanel.Header>
				<SidePanel.Title>Info Panel Title</SidePanel.Title>
			</SidePanel.Header>

			<SidePanel.Body>
				<ClayEmptyState
					description={Liferay.Language.get(
						'sorry,-no-results-were-found'
					)}
					imgSrc={
						Liferay.ThemeDisplay.getPathThemeImages() +
						'/states/empty_state.svg'
					}
					title={Liferay.Language.get('no-results-found')}
				/>
			</SidePanel.Body>

			<SidePanel.Footer>Footer</SidePanel.Footer>
		</SidePanel>
	);
}
