/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
					size="sm"
					symbol="undo"
					title={Liferay.Language.get('undo')}
				/>

				<ClayButtonWithIcon
					aria-label={Liferay.Language.get('redo')}
					className="btn-monospaced"
					disabled={!redoHistory || !redoHistory.length}
					displayType="secondary"
					onClick={onRedo}
					size="sm"
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
