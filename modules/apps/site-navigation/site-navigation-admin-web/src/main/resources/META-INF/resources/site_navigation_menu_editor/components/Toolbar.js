/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {useModal} from '@clayui/modal';
import {sub} from 'frontend-js-web';
import React, {useState} from 'react';

import {SIDEBAR_PANEL_IDS} from '../constants/sidebarPanelIds';
import {
	useSetSidebarPanelId,
	useSidebarPanelId,
} from '../contexts/SidebarPanelIdContext';
import {AddItemDropDown} from './AddItemDropdown';
import {AppLayout} from './AppLayout';
import {PreviewModal} from './PreviewModal';

export function Toolbar({configButtonRef, sidebarPanelRef}) {
	const setSidebarPanelId = useSetSidebarPanelId();
	const sidebarPanelId = useSidebarPanelId();

	const settingsPanelOpen = sidebarPanelId === SIDEBAR_PANEL_IDS.menuSettings;

	const onSettingsButtonClick = () => {
		setSidebarPanelId(
			settingsPanelOpen ? null : SIDEBAR_PANEL_IDS.menuSettings
		);
	};

	const [previewModalOpen, setPreviewModalOpen] = useState(false);

	const {observer} = useModal({
		onClose: () => setPreviewModalOpen(false),
	});

	return (
		<>
			<AppLayout.ToolbarItem expand />

			<AppLayout.ToolbarItem>
				<ClayButton
					displayType="secondary"
					onClick={() => setPreviewModalOpen(true)}
					size="sm"
				>
					{Liferay.Language.get('preview')}
				</ClayButton>
			</AppLayout.ToolbarItem>

			<AppLayout.ToolbarItem>
				<AddItemDropDown
					trigger={
						<ClayButton
							aria-label={sub(
								Liferay.Language.get('add-x'),
								Liferay.Language.get('menu-item')
							)}
							size="sm"
							symbol="plus"
							title={sub(
								Liferay.Language.get('add-x'),
								Liferay.Language.get('menu-item')
							)}
						>
							{Liferay.Language.get('add')}
						</ClayButton>
					}
				/>
			</AppLayout.ToolbarItem>

			<AppLayout.ToolbarItem>
				<ClayButtonWithIcon
					aria-label={
						settingsPanelOpen
							? Liferay.Language.get('close-configuration-panel')
							: Liferay.Language.get('open-configuration-panel')
					}
					className="text-secondary"
					displayType="unstyled"
					monospaced
					onClick={onSettingsButtonClick}
					onKeyDown={(event) => {
						if (event.key === 'Enter') {
							onSettingsButtonClick();

							if (!settingsPanelOpen) {
								sidebarPanelRef.current.focus();
							}
						}
					}}
					ref={configButtonRef}
					size="sm"
					symbol="cog"
					title={
						settingsPanelOpen
							? Liferay.Language.get('close-configuration-panel')
							: Liferay.Language.get('open-configuration-panel')
					}
				/>
			</AppLayout.ToolbarItem>

			{previewModalOpen && <PreviewModal observer={observer} />}
		</>
	);
}
