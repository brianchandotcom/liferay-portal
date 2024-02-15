/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useDisabledRedo, useDisabledUndo, useUndoRedo} from '.undo/Undo';
import {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import React from 'react';

export default function ToolbarActionsDropdown() {
	const disabledRedo = useDisabledRedo();
	const disabledUndo = useDisabledUndo();
	const {onRedo, onUndo} = useUndoRedo();

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
