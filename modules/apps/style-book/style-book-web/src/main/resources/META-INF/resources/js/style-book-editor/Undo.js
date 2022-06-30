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
import {useEventListener} from '@liferay/frontend-js-react-web';
import PropTypes from 'prop-types';
import React from 'react';

import {
	useOnRedo,
	useOnUndo,
	useRedoHistory,
	useUndoHistory,
} from './contexts/StyleBookEditorContext';

export default function Undo() {
	const onUndo = useOnUndo();
	const onRedo = useOnRedo();
	const redoHistory = useRedoHistory();
	const undoHistory = useUndoHistory();

	useEventListener(
		'keydown',
		(event) => {
			const ctrlOrMeta = (event) =>
				(event.ctrlKey && !event.metaKey) ||
				(!event.ctrlKey && event.metaKey);

			if (
				ctrlOrMeta(event) &&
				event.key === 'z' &&
				!event.target.closest('.style-book-editor__sidebar-content')
			) {
				if (!event.shiftKey && undoHistory.length !== 0) {
					onUndo();
				}

				if (event.shiftKey && redoHistory.length !== 0) {
					onRedo();
				}
			}
		},
		true,
		window
	);

	return (
		<>
			<ClayButton.Group className="flex-nowrap">
				<ClayButtonWithIcon
					aria-label={Liferay.Language.get('undo')}
					className="btn-monospaced"
					disabled={!undoHistory || !undoHistory.length}
					displayType="secondary"
					onClick={onUndo}
					small
					symbol="undo"
					title={Liferay.Language.get('undo')}
				/>

				<ClayButtonWithIcon
					aria-label={Liferay.Language.get('redo')}
					className="btn-monospaced"
					disabled={!redoHistory || !redoHistory.length}
					displayType="secondary"
					onClick={onRedo}
					small
					symbol="redo"
					title={Liferay.Language.get('redo')}
				/>
			</ClayButton.Group>
		</>
	);
}

Undo.propTypes = {
	onRedo: PropTypes.func,
	onUndo: PropTypes.func,
};
