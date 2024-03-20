/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import React from 'react';

import {useDispatch} from '../contexts/StoreContext';
import switchSidebarPanel from '../thunks/switchSidebarPanel';

export default function ToggleConfigurationSidebarButton() {
	const dispatch = useDispatch();

	return (
		<ClayButtonWithIcon
			aria-label={Liferay.Language.get('open-configuration-panel')}
			borderless
			displayType="secondary"
			id="page-editor__toolbar__configuration-button"
			onClick={() => {
				dispatch(
					switchSidebarPanel({
						itemConfigurationOpen: true,
					})
				);

				const configurationSidebar = document.querySelector(
					'.page-editor__item-configuration-sidebar'
				) as HTMLElement;

				if (configurationSidebar) {
					configurationSidebar.style.visibility = 'visible';
					configurationSidebar.focus();
				}
			}}
			size="sm"
			symbol="cog"
			title={Liferay.Language.get('open-configuration-panel')}
		/>
	);
}
