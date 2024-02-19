/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import React from 'react';

import {useDispatch, useSelector} from '../contexts/StoreContext';
import selectItemConfigurationOpen from '../selectors/selectItemConfigurationOpen';
import switchSidebarPanel from '../thunks/switchSidebarPanel';

export default function ToggleConfigurationSidebarButton() {
	const itemConfigurationOpen = useSelector(selectItemConfigurationOpen);
	const dispatch = useDispatch();

	const title = itemConfigurationOpen
		? Liferay.Language.get('close-configuration-panel')
		: Liferay.Language.get('open-configuration-panel');

	return (
		<ClayButtonWithIcon
			aria-label={title}
			borderless
			displayType="secondary"
			onClick={() =>
				dispatch(
					switchSidebarPanel({
						itemConfigurationOpen: !itemConfigurationOpen,
					})
				)
			}
			size="sm"
			symbol="cog"
			title={title}
		/>
	);
}
