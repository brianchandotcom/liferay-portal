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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import {
	ReactPortal,
	useEventListener,
	useIsMounted,
} from '@liferay/frontend-js-react-web';
import React, {useState} from 'react';

import {UNDO_TYPES} from './constants/undoTypes';
import {
	useMultipleUndo,
	useRedoHistory,
	useUndoHistory,
} from './contexts/StyleBookEditorContext';

export default function UndoHistory() {
	const isMounted = useIsMounted();
	const multipleUndo = useMultipleUndo();
	const redoHistory = useRedoHistory();
	const undoHistory = useUndoHistory();

	const [active, setActive] = useState(false);
	const [loading, setLoading] = useState(false);

	const onHistoryItemClick = (event, numberOfActions, type) => {
		event.preventDefault();

		setLoading(true);

		multipleUndo({
			numberOfActions,
			type,
		}).then(() => {
			if (isMounted()) {
				setLoading(false);
			}
		});
	};

	return (
		<>
			<ClayDropDown
				active={active}
				alignmentPosition={Align.BottomRight}
				className="ml-2 mr-2"
				menuElementAttrs={{
					className: 'style-book-editor__undo-history',
					containerProps: {
						className: 'cadmin',
					},
				}}
				onActiveChange={setActive}
				trigger={
					<ClayButtonWithIcon
						aria-label={Liferay.Language.get('history')}
						aria-pressed={active}
						disabled={!undoHistory.length && !redoHistory.length}
						displayType="secondary"
						small
						symbol="time"
						title={Liferay.Language.get('history')}
					/>
				}
			>
				<ClayDropDown.ItemList>
					<History
						actions={redoHistory}
						onHistoryItemClick={onHistoryItemClick}
						type={UNDO_TYPES.redo}
					/>

					<History
						actions={undoHistory}
						onHistoryItemClick={onHistoryItemClick}
						type={UNDO_TYPES.undo}
					/>

					<ClayDropDown.Divider />

					<ClayDropDown.Item
						disabled={!undoHistory.length}
						onClick={(event) => {
							onHistoryItemClick(
								event,
								undoHistory.length,
								UNDO_TYPES.undo
							);
						}}
					>
						{Liferay.Language.get('undo-all')}
					</ClayDropDown.Item>
				</ClayDropDown.ItemList>
			</ClayDropDown>

			{loading && (
				<ReactPortal className="cadmin">
					<Overlay />
				</ReactPortal>
			)}
		</>
	);
}

const Overlay = () => {
	useEventListener(
		'keydown',
		(event) => {
			event.preventDefault();
			event.stopPropagation();
			event.stopImmediatePropagation();
		},
		true,
		window
	);

	return (
		<div
			className="style-book-editor__undo-history__overlay"
			onClickCapture={(event) => {
				event.preventDefault();
				event.stopPropagation();
			}}
		></div>
	);
};
const History = ({actions = [], type, onHistoryItemClick}) => {
	const isSelectedAction = (index) => type === UNDO_TYPES.undo && index === 0;

	const actionList =
		type === UNDO_TYPES.undo ? actions : [...actions].reverse();

	return actionList.map((action, index) => (
		<ClayDropDown.Item
			disabled={isSelectedAction(index)}
			key={index}
			onClick={(event) => {
				const numberOfActions =
					type === UNDO_TYPES.undo
						? index
						: actionList.length - index;

				onHistoryItemClick(event, numberOfActions, type);
			}}
			symbolRight={isSelectedAction(index) ? 'check' : ''}
		>
			{Liferay.Util.sub(Liferay.Language.get('update-x'), action.label)}
		</ClayDropDown.Item>
	));
};
