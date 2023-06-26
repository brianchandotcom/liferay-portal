/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
