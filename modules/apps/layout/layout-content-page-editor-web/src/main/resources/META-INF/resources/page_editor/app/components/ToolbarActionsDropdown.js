/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import React from 'react';

import {useSelector} from '../contexts/StoreContext';
import {onDiscardDraft, useDisabledDiscardDraft} from './DiscardDraftButton';
import {useOnToggleSidebars} from './HideSidebarButton';
import {useDisabledRedo, useDisabledUndo, useUndoRedo} from './undo/Undo';
import {useOnHistoryItemClick} from './undo/UndoHistory';
import UndoOverlay from './undo/UndoOverlay';

export default function ToolbarActionsDropdown({discardDraftFormRef}) {
	const disabledDiscardDraft = useDisabledDiscardDraft();
	const disabledRedo = useDisabledRedo();
	const disabledUndo = useDisabledUndo();
	const {loadingHistory, onHistoryItemClick} = useOnHistoryItemClick();
	const {onRedo, onUndo} = useUndoRedo();
	const onToggleSidebars = useOnToggleSidebars();
	const sidebarHidden = useSelector((state) => state.sidebar.hidden);
	const undoHistory = useSelector((state) => state.undoHistory);

	return (
		<>
			{loadingHistory && <UndoOverlay />}
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
					{
						items: [
							{type: 'divider'},
							{
								disabled: !undoHistory.length,
								label: Liferay.Language.get('undo-all'),
								onClick: onHistoryItemClick,
							},
						],
						label: Liferay.Language.get('history'),
						symbolLeft: 'time',
						type: 'contextual',
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
					{
						disabled: disabledDiscardDraft,
						label: Liferay.Language.get('discard-draft'),
						onClick: (event) =>
							onDiscardDraft(event, discardDraftFormRef.current),
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
		</>
	);
}
