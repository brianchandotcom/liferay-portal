/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import React from 'react';

import {useSelector} from '../contexts/StoreContext';
import {useOnToggleSidebars} from './HideSidebarButton';
import {useDisabledRedo, useDisabledUndo, useUndoRedo} from './undo/Undo';

export default function ToolbarActionsDropdown() {
	const disabledRedo = useDisabledRedo();
	const disabledUndo = useDisabledUndo();
	const {onRedo, onUndo} = useUndoRedo();
	const onToggleSidebars = useOnToggleSidebars();
	const sidebarHidden = useSelector((state) => state.sidebar.hidden);

	return (
		<ClayDropDownWithItems
			hasLeftSymbols
			items={[
				{
					disabled: disabledUndo,
					label: Liferay.Language.get('undo'),
					onClick: onUndo,
					symbolLeft: 'undo',
				},
				{
					disabled: disabledRedo,
					label: Liferay.Language.get('redo'),
					onClick: onRedo,
					symbolLeft: 'redo',
				},
				{type: 'divider'},
				{
					label: sidebarHidden
						? Liferay.Language.get('show-sidebars')
						: Liferay.Language.get('hide-sidebars'),
					onClick: onToggleSidebars,
					symbolLeft: 'view',
				},
				{type: 'divider'},
			]}
			trigger={
				<ClayButtonWithIcon
					aria-label={Liferay.Language.get('actions')}
					displayType="secondary"
					size="sm"
					symbol="ellipsis-v"
					title={Liferay.Language.get('actions')}
				/>
			}
		/>
	);
}
